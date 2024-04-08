package com.zhang.dao.provider;

import com.mysql.cj.util.StringUtils;
import com.zhang.dao.BaseDao;
import com.zhang.pojo.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderDaoImp implements ProviderDao{
    //提供者列表
    @Override
    public List<Provider> getProviderList(Connection connection, String proName, String proCode) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
       List<Provider> providerList = new ArrayList<Provider>();
        if (connection!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("select * from smbms_provider where 1=1");
            List<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(proName)){
                sql.append(" and proName like ?");
                list.add("%"+proName+"%");
            }
            if (!StringUtils.isNullOrEmpty(proCode)){
                sql.append(" and proCode like ?");
                list.add("%"+proCode+"%");
            }
            Object[] params = list.toArray();
            System.out.println("sql-------->"+sql.toString());
            rs=BaseDao.execute(connection,pstm,rs,sql.toString(),params);
            while (rs.next()){
                Provider _provider = new Provider();
                _provider.setId(rs.getInt("id"));
                _provider.setProCode(rs.getString("proCode"));
                _provider.setProName(rs.getString("proName"));
                _provider.setProDesc(rs.getString("proDesc"));
                _provider.setProContact(rs.getString("proContact"));
                _provider.setProPhone(rs.getString("proPhone"));
                _provider.setProAddress(rs.getString("proAddress"));
                _provider.setProFax(rs.getString("proFax"));
                _provider.setCreationDate(rs.getDate("creationDate"));
                providerList.add(_provider);
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return providerList;
    }

    @Override
    public int add(Connection connection, Provider provider) throws SQLException {
        int num = 0;
        PreparedStatement pstm=null;

        if (connection!=null){
            String sql = "insert into smbms_provider(proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy, creationDate) \n" +
                    "VALUES(?,?,?,?,?,?,?,?,?)";
            Object[] params = {provider.getProCode(),provider.getProName(),provider.getProDesc(),provider.getProContact(),provider.getProPhone(),provider.getProAddress(),provider.getProFax(),provider.getCreatedBy(),provider.getCreationDate()};
           num = BaseDao.executes(connection,pstm,sql,params);
        }
        BaseDao.closeResource(null,pstm,null);
        return num;
    }
    //通过id显示供应商信息
    @Override
    public Provider getProviderById(Connection connection, String id) throws SQLException {
        Provider provider1 = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if (connection!=null){
            String sql = "select * from smbms_provider where id=?";
            Object[] params = {id};
            rs = BaseDao.execute(connection,pstm,rs,sql.toString(),params);
            if (rs.next()){
                provider1 = new Provider();
                provider1.setId(rs.getInt("id"));
                provider1.setProCode(rs.getString("proCode"));
                provider1.setProName(rs.getString("proName"));
                provider1.setProDesc(rs.getString("proDesc"));
                provider1.setProContact(rs.getString("proContact"));
                provider1.setProPhone(rs.getString("proPhone"));
                provider1.setProAddress(rs.getString("proAddress"));
                provider1.setProFax(rs.getString("proFax"));
                provider1.setCreatedBy(rs.getInt("createdBy"));
                provider1.setCreationDate(rs.getTimestamp("creationDate"));
                provider1.setModifyBy(rs.getInt("modifyBy"));
                provider1.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return provider1;
    }

    @Override
    public int deleteProviderById(Connection connection, String delId) throws SQLException {
        int update = 0;
        PreparedStatement pstm = null;
        if (connection!=null){
            String sql ="delete from smbms_provider where id=?";
            Object[] params = {delId};
           update = BaseDao.executes(connection,pstm,sql.toString(),params);
            BaseDao.closeResource(null,pstm,null);
        }
        return update;
    }

    @Override
    public int modify(Connection connection, Provider provider) throws SQLException {
        PreparedStatement pstm = null;
        int up = 0;
        if (connection!=null){
            String sql = "update smbms_provider set proCode=?,proName=?,proContact=?,proPhone=?,proAddress=?,proFax=?,proDesc=?,modifyBy=?,modifyDate=? where id = ?";
            Object[] params = {provider.getProCode(),provider.getProName(),provider.getProContact(),provider.getProPhone(),provider.getProAddress(),provider.getProFax(),provider.getProDesc(),provider.getModifyBy(),provider.getModifyDate(),provider.getId()};
            up = BaseDao.executes(connection,pstm,sql.toString(),params);
            BaseDao.closeResource(null,pstm,null);
        }
        return up;
    }

}
