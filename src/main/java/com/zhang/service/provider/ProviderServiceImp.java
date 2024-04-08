package com.zhang.service.provider;

import com.zhang.dao.BaseDao;
import com.zhang.dao.bill.BillDao;
import com.zhang.dao.bill.BillDaoImp;
import com.zhang.dao.provider.ProviderDao;
import com.zhang.dao.provider.ProviderDaoImp;
import com.zhang.pojo.Provider;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;

public class ProviderServiceImp implements ProviderService{
    private ProviderDao providerDao;
    private BillDao billDao;
    public ProviderServiceImp(){
        providerDao = new ProviderDaoImp();
        billDao = new BillDaoImp();
    }
    //提供者列表
    @Override
    public List<Provider> getProviderList(String proName, String proCode) {
        Connection connection = null;
        List<Provider> providerList = null;
        System.out.println("query proName----->"+proName);
        System.out.println("query proCode----->"+proCode);
        try {
            connection = BaseDao.getConnection();
            providerList = providerDao.getProviderList(connection,proName,proCode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return providerList;
    }
    //添加供应商
    @Override
    public boolean add(Provider provider) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection= BaseDao.getConnection();
            connection.setAutoCommit(false);
            int update = providerDao.add(connection,provider);
            if (update>0){
                flag = true;
                connection.commit();
                System.out.println("提供者添加成功");
            }else {
                System.out.println("提供者添加失败");
            }
        } catch (SQLException throwables) {
            System.out.println("失败了回滚");
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }
    //通过id显示提供者信息
    @Override
    public Provider getProviderById(String id) {
        Connection connection = null;
        Provider provider = null;
        try {
            connection = BaseDao.getConnection();
            provider = providerDao.getProviderById(connection,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return provider;
    }

    @Override
    public int deleteProviderById(String delId) {
        int billCount = -1;
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
           connection.setAutoCommit(false);
           billCount = billDao.getBillCountByProvider(connection,delId);
            if (billCount==0){
                providerDao.deleteProviderById(connection,delId);
            }
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            billCount=-1;
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }finally {
            BaseDao.closeResource(connection ,null,null);
        }
        return billCount;
    }

    @Override
    public boolean modify(Provider provider) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
           int num = providerDao.modify(connection,provider);
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
