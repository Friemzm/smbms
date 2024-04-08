package com.zhang.service.bill;

import com.zhang.dao.BaseDao;
import com.zhang.dao.bill.BillDao;
import com.zhang.dao.bill.BillDaoImp;
import com.zhang.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillServiceImp implements BillService{
    private BillDao billDao;
    public BillServiceImp(){
        billDao =  new BillDaoImp();
    }
    //添加订单
    @Override
    public Boolean add(Bill bill) {
        Boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            int update = billDao.aad(connection,bill);
            connection.commit();
            if (update>0){
                flag = true;
                connection.commit();
                System.out.println("add success");
            }else {
                System.out.println("add failed!!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                System.out.println("rollback==================");
                connection.rollback();//失败就回滚
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }
    //通过查询条件获取供应商列表-模糊查询-getBillList
    @Override
    public List<Bill> getBillList(Bill bill) {
        List<Bill> billList = new ArrayList<Bill>();
        Connection connection = null;
        System.out.println("query productName ---- > " + bill.getProductName());
        System.out.println("query providerId ---- > " + bill.getProviderID());
        System.out.println("query isPayment ---- > " + bill.getIsPayment());
        try {
            connection = BaseDao.getConnection();
            billList = billDao.getBillList(connection,bill);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return billList;
    }
    //通过billid获取bill
    @Override
    public Bill getBillByID(String id) {
        Connection connection = null;
        Bill bill = new Bill();

        try {
            connection = BaseDao.getConnection();
            bill = billDao.getBillByID(connection,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return bill;
    }
    //通过billid获取删除Bill
    @Override
    public Boolean deleteBill(String delid) {
        Connection connection=null;
        boolean flag = false;
        int num = 0;//受影响行数
        try {
            connection= BaseDao.getConnection();
            num=billDao.deleteBill(connection,delid);
            if (num>0){
                flag=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }
    //更改bill信息
    @Override
    public Boolean modify(Bill bill) {
        boolean flag = false;
        Connection connection = null;

        try {
            connection=BaseDao.getConnection();
            int num=billDao.modify(connection,bill);
            if (num>0){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }


}
