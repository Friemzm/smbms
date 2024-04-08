package com.zhang.servlet.user;

import com.zhang.pojo.User;
import com.zhang.service.user.UserService;
import com.zhang.service.user.UserServiceImp;
import com.zhang.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    //servlet:控制层调用业务层代码
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("servlet进入");
        //获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        //和数据库中的密码进行对比,调用业务层
        UserService userService = new UserServiceImp();
        User user = userService.login(userCode, userPassword);//查找这个人

        if (user!=null){//有这个人
            //将用户的信息放到session中
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            //跳转到内部主页
            resp.sendRedirect("jsp/frame.jsp");
        }else {//查无此人
            //转发回登录页面，顺便提示他账号或密码错误
            req.setAttribute("error","用户名或者密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
