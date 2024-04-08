package com.zhang.servlet.bill;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.zhang.pojo.Bill;
import com.zhang.pojo.Provider;
import com.zhang.pojo.User;
import com.zhang.service.bill.BillService;
import com.zhang.service.bill.BillServiceImp;
import com.zhang.service.provider.ProviderService;
import com.zhang.service.provider.ProviderServiceImp;
import com.zhang.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BillServlet extends HttpServlet {
    public static void main(String[] args) {
        System.out.println(new BigDecimal("23.235").setScale(2,BigDecimal.ROUND_HALF_DOWN));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String method = req.getParameter("method");
    if (method!=null && method.equals("add")){
     this.add(req,resp);
    }else if (method!=null && method.equals("query")){
        this.query(req,resp);
     }else if (method!=null && method.equals("view")){
        this.getBillByID(req,resp,"billview.jsp");
    }else if(method != null && method.equals("modify")){
        this.getBillByID(req,resp,"billmodify.jsp");
    }else if (method!=null && method.equals("delbill")){
        this.delBill(req,resp);
    }else if (method!=null && method.equals("modifysave")){
        this.modify(req,resp);
    }else if (method!=null && method.equals("getproviderlist")){
        this.getProviderlist(req,resp);
    }
    }
    //获取供应商列表
    private void getProviderlist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("getProviderlist-----------");
       List<Provider> providerList = new ArrayList<>();
        ProviderService providerService = new ProviderServiceImp();
        providerList=providerService.getProviderList("","");
        //把providerlist转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(providerList));
        outPrintWriter.flush();
        outPrintWriter.close();

    }

    //修改
    private void modify(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("正在修改------------->");
        String id = req.getParameter("id");
        String productName = req.getParameter("productName");
        String productDesc = req.getParameter("productDesc");
        String productUnit = req.getParameter("productUnit");
        String productCount = req.getParameter("productCount");
        String totalPrice = req.getParameter("totalPrice");
        String providerID = req.getParameter("providerID");
        String isPayment = req.getParameter("isPayment");
        Bill bill = new Bill();
        bill.setId(Integer.valueOf(id));
        System.out.println("这里");
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setProviderID(Integer.parseInt(providerID));

        bill.setModifyBy(((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setModifyDate(new Date());
        boolean flag = false;
        BillService billService = new BillServiceImp();
        flag= billService.modify(bill);
        if (flag){//修改成功
            resp.sendRedirect(req.getContextPath()+"/jsp/bill.do?method=query");
        }else {
            req.getRequestDispatcher("billmodify.jsp").forward(req,resp);
        }
    }

    //通过id删除bill
    private void delBill(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("billid");
        HashMap<String, String> resulMap = new HashMap<>();
        if (!StringUtils.isNullOrEmpty(id)){
            BillService billService = new BillServiceImp();
            boolean flag = billService.deleteBill(id);
            if (flag){//删除成功
               resulMap.put("delResult","true");
            }else {//删除失败
                resulMap.put("delResult","false");
            }
        }else {
            resulMap.put("delResult","notexit");
        }
        //把resultMap转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resulMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //通过billid获取bill
    private void getBillByID(HttpServletRequest req, HttpServletResponse resp, String url) throws ServletException, IOException {
        String id = req.getParameter("billid");
        if (!StringUtils.isNullOrEmpty(id)){
            BillService billService = new BillServiceImp();
            Bill bill = null;
            bill = billService.getBillByID(id);
            req.setAttribute("bill",bill);
            req.getRequestDispatcher(url).forward(req,resp);
        }

    }

    //获取供应商列表
    private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Provider> providers = new ArrayList<Provider>();
        ProviderService providerService = new ProviderServiceImp();
        providers = providerService.getProviderList("","");
        req.setAttribute("providerList",providers);

        String queryProductName = req.getParameter("queryProviderName");
        String queryProviderID = req.getParameter("queryProviderID");
        String queryIsPayment = req.getParameter("queryIsPayment");
        if (StringUtils.isNullOrEmpty(queryProductName)){
            queryProductName = "";
        }

        List<Bill> billList = new ArrayList<Bill>();
        BillService billService = new BillServiceImp();
        Bill bill = new Bill();
        if (StringUtils.isNullOrEmpty(queryIsPayment)){
            bill.setIsPayment(0);
        }else {
            System.out.println("333333");
            bill.setIsPayment(Integer.parseInt(queryIsPayment));
        }
        if (StringUtils.isNullOrEmpty(queryProviderID)){
            bill.setProviderID(0);
        }else {
            bill.setProviderID(Integer.parseInt(queryProviderID));
        }
        bill.setProductName(queryProductName);
        System.out.println("+++++++++++++++++++");
        billList = billService.getBillList(bill);
        System.out.println("------------------");
        req.setAttribute("billList",billList);
        req.setAttribute("queryProductName",queryProductName);
        req.setAttribute("queryProviderId",queryProviderID);
        req.setAttribute("queryIsPayment",queryIsPayment);
        req.getRequestDispatcher("billlist.jsp").forward(req,resp);
    }
    //添加订单
    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("正在添加订单--");
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productDesc = req.getParameter("productDesc");
        String productUnit = req.getParameter("productUnit");
        String productCount = req.getParameter("productCount");
        String totalPrice = req.getParameter("totalPrice");
        String providerId = req.getParameter("providerId");
        String isPayment = req.getParameter("isPayment");
        Bill bill = new Bill();
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductUnit(productUnit);
        bill.setProductDesc(productDesc);
        bill.setProductCount(new BigDecimal(productCount).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setProviderID(Integer.valueOf(providerId));
        bill.setIsPayment(Integer.valueOf(isPayment));
        bill.setCreationDate(new Date());
        //获取当前登录用户的id
        bill.setCreatedBy(((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        BillServiceImp billServiceImp = new BillServiceImp();
        Boolean flag = billServiceImp.add(bill);
        System.out.println("add flag---->"+flag);
        //如果添加成功，则页面转发，否则重新刷新，再次跳转到当前页面
            if (flag) {
                resp.sendRedirect(req.getContextPath() + "/jsp/bill.do?method=query");
            }else {
                req.getRequestDispatcher("billadd.jsp").forward(req,resp);
            }
    }
}
