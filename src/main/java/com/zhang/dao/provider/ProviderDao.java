package com.zhang.dao.provider;

import com.zhang.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProviderDao {
    //列表
    public List<Provider> getProviderList(Connection connection, String proName, String proCode)throws Exception;
    //添加供应商
    public int add(Connection connection,Provider provider)throws SQLException;
    //通过provider的id显示供应商信息
    public Provider getProviderById(Connection connection,String id)throws SQLException;
    //通过proId删除Provider
    public int deleteProviderById(Connection connection,String delId)throws SQLException;
    //修改
    public int modify(Connection connection,Provider provider)throws SQLException;

}
