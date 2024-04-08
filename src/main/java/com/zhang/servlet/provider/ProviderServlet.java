package com.zhang.servlet.provider;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.zhang.dao.provider.ProviderDaoImp;
import com.zhang.pojo.Provider;
import com.zhang.pojo.User;
import com.zhang.service.provider.ProviderService;
import com.zhang.service.provider.ProviderServiceImp;
import com.zhang.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProviderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method!=null && method.equals("query")){
            this.query(req,resp);
        }else if (method!=null && method.equals("add")){
            this.add(req,resp);
        }else if (method!=null && method.equals("view")){
            this.getProviderById(req,resp,"providerview.jsp");
        }else if(method != null && method.equals("modify")){
            this.getProviderById(req,resp,"providermodify.jsp");
        }else if (method!=null && method.equals("delprovider")){
            this.delProvider(req,resp);
        }else if (method!=null && method.equals("modifysave")){
            this.modify(req,resp);
        }
    }
    //修改
    private void modify(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("进入修改----------》");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String proDesc = req.getParameter("proDesc");
        String id = req.getParameter("id");

        Provider provider = new Provider();
        provider.setId(Integer.valueOf(id));
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);
        provider.setProDesc(proDesc);
        provider.setModifyBy(((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        provider.setModifyDate(new Date());

        boolean flag = false;
        ProviderService providerService= new ProviderServiceImp();
       flag= providerService.modify(provider);
        System.out.println("修改来了------》");
        if (flag){
            resp.sendRedirect(req.getContextPath()+"/jsp/provider.do?method=query");
        }else {
            req.getRequestDispatcher("providermodify.jsp").forward(req,resp);
        }
    }

    //通过id删除提供者信息
    private void delProvider(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("删除开始--------》");
        String id = req.getParameter("proid");
        HashMap<String, String> resultMap = new HashMap<>();
        if (!StringUtils.isNullOrEmpty(id)){
            ProviderService providerService = new ProviderServiceImp();
            int num = providerService.deleteProviderById(id);
            System.out.println("开始判断-----》");
            if (num==0){//删除成功
                resultMap.put("delResult","true");
                System.out.println("删除成功-----》");
            }else if (num == -1){//删除失败
                resultMap.put("delResult","false");
            }else if (num > 0){//该供应商下面还有订单，不能删除，返回一个订单数
                System.out.println("还有订单删不了哦------》");
                resultMap.put("delResult", String.valueOf(num));

            }
        }else {
            resultMap.put("delResult","notexit");
        }
        //把Map对象转换成JSON对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //通过id显示提供者信息
    private void getProviderById(HttpServletRequest req, HttpServletResponse resp,String url) throws ServletException, IOException {
        System.out.println("getProviderById");
        String proid = req.getParameter("proid");
        if (!StringUtils.isNullOrEmpty(proid)){
            ProviderService providerService = new ProviderServiceImp();
            Provider provider = providerService.getProviderById(proid);
            req.setAttribute("provider",provider);
            req.getRequestDispatcher(url).forward(req,resp);
        }
    }

    //添加提供者信息
    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("add-------------->");
        String proCode = req.getParameter("proCode");
        String proName = req.getParameter("proName");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String proDesc = req.getParameter("proDesc");

        Provider provider = new Provider();
        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);
        provider.setProDesc(proDesc);
        provider.setCreatedBy(((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        provider.setCreationDate(new Date());

        ProviderService providerService = new ProviderServiceImp();
        boolean flag = false;
        flag = providerService.add(provider);
        if (flag){
            resp.sendRedirect(req.getContextPath()+"/jsp/provider.do?method=query");
        }else {
           req.getRequestDispatcher("provideradd.jsp").forward(req,resp);
        }
    }

    //显示提供者列表
    private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryProName = req.getParameter("queryProName");
        String queryProCode = req.getParameter("queryProCode");
        if (StringUtils.isNullOrEmpty(queryProName)){
            queryProName = "";
        }
        if (StringUtils.isNullOrEmpty(queryProCode)){
            queryProCode = "";
        }
        List<Provider> providerList = new ArrayList<>();
        ProviderService providerService= new ProviderServiceImp();
        providerList = providerService.getProviderList(queryProName,queryProCode);
        req.setAttribute("providerList",providerList);
        req.setAttribute("queryProName",queryProName);
        req.setAttribute("queryProCode",queryProCode);
        req.getRequestDispatcher("providerlist.jsp").forward(req,resp);

    }
}
