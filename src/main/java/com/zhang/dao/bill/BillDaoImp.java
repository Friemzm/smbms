package com.zhang.dao.bill;

import com.mysql.cj.util.StringUtils;
import com.zhang.dao.BaseDao;
import com.zhang.pojo.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImp implements BillDao{
    //添加订单
    @Override
    public int aad(Connection connection, Bill bill) throws SQLException {
        PreparedStatement pstm = null;
        int updateNum = 0;
        if (connection!=null){
            String sql = "insert into smbms_bill(billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, creationDate, providerID) VALUES (?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {bill.getBillCode(),bill.getProductName(),bill.getProductDesc(),bill.getProductUnit(),bill.getProductCount(),bill.getTotalPrice(),bill.getIsPayment(),bill.getCreatedBy(),bill.getCreationDate(),bill.getProviderID()};
            updateNum = BaseDao.executes(connection,pstm,sql,params);
            BaseDao.closeResource(null,pstm,null);
        }
        return updateNum;
    }
    //订单列表
    @Override
    public List<Bill> getBillList(Connection connection, Bill bill) throws SQLException {
        List<Bill> billlist = new ArrayList<Bill>();
        System.out.println("进来了");
        PreparedStatement pstm = null;
        ResultSet rs =null;
        if (connection!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT b.*,p.proName AS providerName FROM smbms_bill b, smbms_provider p WHERE b.providerID = p.id");
            List<Object> list = new ArrayList<>();//暂存用户输入
            if (!StringUtils.isNullOrEmpty(bill.getProductName())){//判断用户是否输入商品名称
                sql.append(" and b.`productName like ?`");
                list.add("%"+bill.getProductName()+"%");
                System.out.println("进来了1");
            }
            if (bill.getProviderID()>0){//判断是否选择了供应商
                sql.append(" and p.`productID`=?");
                list.add(bill.getProviderID());
                System.out.println("成功");
            }if (bill.getIsPayment()>0){//判断是否付款
                System.out.println("成功00");
                sql.append(" and b.`isPayment`=?");
                list.add(bill.getIsPayment());
            }
            Object[] params = list.toArray();
            System.out.println("当前sql------>"+sql.toString());
            rs = BaseDao.execute(connection,pstm,rs,sql.toString(),params);
            while (rs.next()){
                Bill _bill = new Bill();//创建一个bill对象存储查询到的属性
                _bill.setId(rs.getInt("id"));
                _bill.setBillCode(rs.getString("billCode"));
                _bill.setProductName(rs.getString("productName"));
                _bill.setProductDesc(rs.getString("productDesc"));
                _bill.setProductUnit(rs.getString("productUnit"));
                _bill.setProductCount(rs.getBigDecimal("productCount"));
                _bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                _bill.setIsPayment(rs.getInt("isPayment"));
                _bill.setProviderID(rs.getInt("providerId"));
                _bill.setProviderName(rs.getString("providerName"));
                _bill.setCreationDate(rs.getTimestamp("creationDate"));
                _bill.setCreatedBy(rs.getInt("createdBy"));
                billlist.add(_bill);
            }
                BaseDao.closeResource(null,pstm,rs);
        }
        return billlist;
    }
    //通过id获取订单
    @Override
    public Bill getBillByID(Connection connection, String id) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Bill bill = new Bill();
        if (connection!=null){
            String sql = "select b.*,p.proName as providerName from smbms_bill b,smbms_provider p\n" +
                    "where b.providerID = p.id and b.id = ?";
            Object[] params = {id};
            rs = BaseDao.execute(connection,pstm,rs,sql,params);
            if (rs.next()){
                bill.setId(rs.getInt("id"));
                bill.setBillCode(rs.getString("billCode"));
                bill.setProductName(rs.getString("productName"));
                bill.setProductDesc(rs.getString("productDesc"));
                bill.setProductUnit(rs.getString("productUnit"));
                bill.setProductCount(rs.getBigDecimal("productCount"));
                bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                bill.setIsPayment(rs.getInt("isPayment"));
                bill.setProviderID(rs.getInt("providerId"));
                bill.setProviderName(rs.getString("providerName"));
                bill.setModifyBy(rs.getInt("modifyBy"));
                bill.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return bill;
    }
    //删除订单
    @Override
    public int deleteBill(Connection connection, String delid) throws SQLException {
        PreparedStatement pstm=null;
        int updatNum = 0;
        if (connection!=null){
            String sql="delete from `smbms_bill` where id=?";
            Object[] params = {delid};
           updatNum=BaseDao.executes(connection,pstm,sql,params);
           BaseDao.closeResource(null,pstm,null);
        }
        return updatNum;
    }
    //修改订单
    @Override
    public int modify(Connection connection, Bill bill) throws SQLException {
        int number = 0;
        PreparedStatement pstm = null;
        if (connection!=null){
            String sql = "update smbms_bill set productName=?,productDesc=?,productUnit=?,productCount=?,totalPrice=?,providerID=?,isPayment=?,modifyBy=?,modifyDate=? where id = ?";
            Object[] params = {bill.getProductName(),bill.getProductDesc(),bill.getProductUnit(),bill.getProductCount(),bill.getTotalPrice(),bill.getProviderID(),bill.getIsPayment(),bill.getModifyBy(),bill.getModifyDate()};
           number = BaseDao.executes(connection,pstm,sql.toString(),params);
           BaseDao.closeResource(null,pstm,null);
        }
        return number;
    }
    //根据供应商id查询订单数量
    @Override
    public int getBillCountByProvider(Connection connection, String providerId) throws SQLException {
        int billcount = 0;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if (connection!=null){
            String sql = "SELECT COUNT(1) as billCount FROM `smbms_bill` WHERE `providerID`=?";
            Object[] params = {providerId};
            rs = BaseDao.execute(connection,pstm,rs,sql,params);
            while (rs.next()){
                billcount=rs.getInt("billCount");
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return billcount;
    }

}
