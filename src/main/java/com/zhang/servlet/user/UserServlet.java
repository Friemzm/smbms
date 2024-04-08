package com.zhang.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.zhang.pojo.Role;
import com.zhang.pojo.User;
import com.zhang.service.role.RoleServliceImp;
import com.zhang.service.user.UserService;
import com.zhang.service.user.UserServiceImp;
import com.zhang.util.Constants;
import com.zhang.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//实现servlet复用
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method!=null && method.equals("savepwd")){
            this.updatePwd(req, resp);
        }else if (method!=null && method.equals("pwdmodify")){
            this.pwdModify(req, resp);
        }else if (method!=null && method.equals("query")){
            this.query(req, resp);
        }else if (method!=null && method.equals("add")){
            this.add(req,resp);
        }else if(method != null && method.equals("deluser")){
            //删除用户
            this.delUser(req,resp);
        }else if(method != null && method.equals("view")){
            //通过用户id得到用户
            this.getUserById(req,resp,"userview.jsp");
        }else if(method != null && method.equals("modify")){
            this.getUserById(req, resp,"usermodify.jsp");
        }else if(method != null && method.equals("modifyexe")){
            //修改用户信息
            this.modify(req,resp);
        }



    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    //修改密码
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp){
        //从session中拿ID
        Object attribute = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");
        System.out.println("新密码："+newpassword);

        boolean flag = false;
        //System.out.println((User)attribute);
        //System.out.println(attribute != null);
        //System.out.println(!StringUtils.isNullOrEmpty(newpassword));
        if (attribute!=null && newpassword!=null){
            UserService userService = new UserServiceImp();
            flag = userService.updatePwd(((User)attribute).getId(),newpassword);
            if (flag){
                req.setAttribute("message","修改密码成功，请退出，使用新密码登录");
                //修改密码成功，移除当前session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else {
                req.setAttribute("message","修改密码失败");
                //修改密码失败
            }

        }else {
            req.setAttribute("message","新密码有问题");
        }
        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //验证旧密码，session中有用户的密码
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp){
        //从session中拿ID
        Object attribute = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");
        //万能的Map   结果集
        Map<String, String> resultMap = new HashMap<String,String>();

        if (attribute == null){//Session失效了，
            resultMap.put("result","sessionerror");
        }else if (StringUtils.isNullOrEmpty(oldpassword)){//输入密码为空
            resultMap.put("result","error");
        }else {
            String userPassword = ((User) attribute).getUserPassword();
            if (oldpassword.equals(userPassword)){
                resultMap.put("result","true");
            }else {
                resultMap.put("result","false");
            }
        }


        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            //JSONArray JSON工具类，转换格式
            /*
            * resultMap = {"result","sessionerror"，"result","error"}
            * JSON格式 = {key,value}
            * */

            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //难点、
    public void query(HttpServletRequest req, HttpServletResponse resp){
    //查询用户列表
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;
        //获取用户列表
        UserServiceImp userServiceImp = new UserServiceImp();
        List<User> userList = null;
        //第一次走这个请求，一定是第一页，页面大小固定的
        int pageSize = 5;//可以把这个配置到文件中，方便后期修改
        int currentPageNo = 1;
        if (queryUserName == null){
            queryUserName = "";
        }
        if (temp!=null && !temp.equals("")){
            queryUserRole = Integer.parseInt(temp);//给查询赋值0123
        }
        if (pageIndex!=null){
           currentPageNo = Integer.parseInt(pageIndex);
        }
        //获取用户总数
        int totalCount = userServiceImp.getUserCount(queryUserName, queryUserRole);
        //总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        int totalPageCount = pageSupport.getTotalPageCount();
        //控制首页和尾页
        //如果页面要小于1就赋值为第一页
        if (currentPageNo<1){
            currentPageNo = 1;
        }else if (currentPageNo>totalPageCount){//当前页面大于了最后一页
            currentPageNo = totalPageCount;
        }
        //获取用户列表展示
        userList = userServiceImp.getUserList(queryUserName,queryUserRole,currentPageNo,pageSize);
        req.setAttribute("userList",userList);

        RoleServliceImp roleServliceImp = new RoleServliceImp();
        List<Role> roleList = roleServliceImp.getRoleList();
        req.setAttribute("roleList",roleList);
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);
        req.setAttribute("queryUserName",queryUserName);
        req.setAttribute("queryUserRole",queryUserRole);

        //返回前端
        try {
            req.getRequestDispatcher("userlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //添加用户
    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("当前正在执行增加用户操作");
        //从前端得到页面的请求的参数即用户输入的值
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        //String ruserPassword = req.getParameter("ruserPassword");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");
        //把这些值塞进一个用户属性中
        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setPhone(phone);
        user.setUserPassword(userPassword);
        user.setGender(Integer.valueOf(gender));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setAddress(address);
        user.setUserRole(Integer.valueOf(userRole));
        user.setCreationDate(new Date());
        //获取当前登录用户的id
        user.setCreatedBy(((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        UserServiceImp userServiceImp = new UserServiceImp();
        Boolean flag = userServiceImp.addUser(user);
        //如果添加成功，则页面转发，否则重新刷新，再次跳转到当前页面
        if(flag){
            resp.sendRedirect(req.getContextPath()+"/jsp/user.do?method=query");
        }else{
            req.getRequestDispatcher("useradd.jsp").forward(req,resp);
        }
    }
    //删除用户信息
    private void delUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("uid");
        Integer delId = 0;
        try{
            delId = Integer.parseInt(id);
        }catch (Exception e) {
            // TODO: handle exception
            delId = 0;
        }
        //需要判断是否能删除成功
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(delId <= 0){
            resultMap.put("delResult", "notexist");
        }else{
            UserService userService = new UserServiceImp();
            if(userService.deletUser(delId)){
                resultMap.put("delResult", "true");
            }else{
                resultMap.put("delResult", "false");
            }
        }
        //把resultMap转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }
    //通过用户id得到用户
    private void getUserById(HttpServletRequest req, HttpServletResponse resp, String url) throws ServletException, IOException {
        String id = req.getParameter("uid");
        if (!StringUtils.isNullOrEmpty(id)){
            //调用后台方法得到用户id
            UserServiceImp userService = new UserServiceImp();
            User user = userService.getUserById(id);
            req.setAttribute("user",user);
            req.getRequestDispatcher(url).forward(req,resp);
        }
    }
    //修改用户信息
    private void modify(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //需要拿到前端传递进来的参数
        String id = req.getParameter("uid");
        String userName = req.getParameter("userName");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");
        //创建一个对象接收参数
        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setUserName(userName);
        user.setGender(Integer.valueOf(gender));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.valueOf(userRole));
        user.setModifyBy(((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        user.setModifyDate(new Date());
        //调用service层
        UserServiceImp userService = new UserServiceImp();
        Boolean flag = userService.modify(user);
        //判断是否修改成功来决定跳转到哪个页面
        if(flag){
            resp.sendRedirect(req.getContextPath()+"/jsp/user.do?method=query");
        }else{
            req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
        }



    }


}
