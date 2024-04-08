package com.zhang.dao.user;

import com.mysql.cj.util.StringUtils;
import com.zhang.dao.BaseDao;
import com.zhang.pojo.Role;
import com.zhang.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    @Override
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rst = null;
        User user = null;
        if (connection!=null) {
            String sql = "select *from smbms_user where userCode = ?";
            Object[] params = {userCode};


                rst = BaseDao.execute(connection, pstm, rst, sql, params);
                if (rst.next()){
                    user=new User();
                    user.setId(rst.getInt("id"));
                    user.setUserCode(rst.getString("userCode"));
                    user.setUserName(rst.getString("userName"));
                    user.setUserPassword(rst.getString("userPassword"));
                    user.setGender(rst.getInt("gender"));
                    user.setBirthday(rst.getDate("birthday"));
                    user.setPhone(rst.getString("phone"));
                    user.setAddress(rst.getString("address"));
                    user.setUserRole(rst.getInt("userRole"));
                    user.setCreatedBy(rst.getInt("createdBy"));
                    user.setCreationDate(rst.getTimestamp("creationDate"));
                    user.setModifyBy(rst.getInt("modifyBy"));
                    user.setModifyDate(rst.getTimestamp("modifyDate"));
                }
                BaseDao.closeResource(null,pstm,rst);
        }
        return user;
    }

    @Override
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        PreparedStatement pstm = null;
        int i = 0;
        if (connection!=null){
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object[] params = {password,id};
            i = BaseDao.executes(connection, pstm, sql, params);
            BaseDao.closeResource(null,pstm,null);
        }
        return i;
    }
    //根据用户名或者角色查询用户总数（最难）
    @Override
    public int getUserCount(Connection connection, String username, int userRole) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if (connection!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
            ArrayList<Object> list = new ArrayList<>();//存放参数

            if (!StringUtils.isNullOrEmpty(username)){
                sql.append(" and u.userName like ?");
                list.add("%"+username+"%");//index:0
            }
            if (userRole>0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);//index:1
            }
            //把list转化为数组
            Object[] params = list.toArray();

            System.out.println("UserDaoImpl-->getUserCount:"+sql.toString());//输出sql完整语句
            rs = BaseDao.execute(connection,pstm,rs,sql.toString(),params);
            if (rs.next()){
                count = rs.getInt("count");//从结果集中获得最终数量
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return count;
    }
    //用户列表
    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException {
       PreparedStatement pstm = null;
       ResultSet rs = null;
      List<User> userList = new ArrayList<>();
       if (connection!=null){
       StringBuffer sql = new StringBuffer();
       sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
        List<Object> list = new ArrayList<>();
        if (!StringUtils.isNullOrEmpty(userName)){
            sql.append(" and u.userName like ?");
            list.add("%"+userName+"%");
        }
        if (userRole>0){
            sql.append(" and u.userRole = ?");
            list.add(userRole);//index:1
        }
        //在数据库中，分页使用  limit startIndex  pageSise
        //当前页（当前页-1）*页面个数

        sql.append(" order by creationDate DESC limit ?,?");
        currentPageNo = (currentPageNo-1)*pageSize;
        list.add(currentPageNo);
        list.add(pageSize);

        Object[] params = list.toArray();
           System.out.println("sql------>"+sql.toString());
        rs = BaseDao.execute(connection,pstm,rs,sql.toString(),params);
        while (rs.next()){
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUserCode(rs.getString("userCode"));
            user.setUserName(rs.getString("userName"));
            user.setGender(rs.getInt("gender"));
            user.setBirthday(rs.getDate("birthday"));
            user.setPhone(rs.getString("phone"));
            user.setUserRole(rs.getInt("userRole"));
            user.setUserRoleName(rs.getString("userRoleName"));
            userList.add(user);
        }
        BaseDao.closeResource(null,pstm,rs);
       }
        return userList;
    }

    @Override
    public int addUser(Connection connection, User user) throws SQLException {
        PreparedStatement pstm = null;
        int updateNum=0;
        if (connection!=null){
            String sql = "insert into smbms_user (userCode,userName,userPassword," +
                    "userRole,gender,birthday,phone,address,creationDate,createdBy) " +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {user.getUserCode(), user.getUserName(), user.getUserPassword(),
                    user.getUserRole(), user.getGender(), user.getBirthday(),
                    user.getPhone(), user.getAddress(), user.getCreationDate(), user.getCreatedBy()};
           updateNum = BaseDao.executes(connection,pstm,sql,params);
           BaseDao.closeResource(null,pstm,null);
        }
        return updateNum;
    }

    @Override
    public int deletUser(Connection connection, Integer delId) throws SQLException {
        PreparedStatement pstm = null;
        int updateNum=0;
        if (connection!=null){
            String sql = "DELETE FROM `smbms_user` WHERE id=?";
            Object[] params = {delId};
            updateNum = BaseDao.executes(connection,pstm,sql,params);
            BaseDao.closeResource(null,pstm,null);
        }
        return updateNum;
    }

    @Override
    public User getUserById(Connection connection, String id) throws SQLException {
        PreparedStatement pstm = null;
        User user = new User();
        ResultSet rs = null;
        if (connection != null) {
            String sql = "select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id=? and u.userRole = r.id";
            Object[] params = {id};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
                user.setUserRoleName(rs.getString("userRoleName"));
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return user;
    }

    @Override
    public int modify(Connection connection, User user) throws Exception {
        PreparedStatement pstm = null;
        int update = 0;
        if (connection!=null){
            String sql = "update smbms_user set userName=?,"+
                    "gender=?,birthday=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=? where id = ? ";
            Object[] params = {user.getUserName(),user.getGender(),user.getBirthday(),
                    user.getPhone(),user.getAddress(),user.getUserRole(),user.getModifyBy(),
                    user.getModifyDate(),user.getId()};
            update = BaseDao.executes(connection,pstm,sql,params);
            BaseDao.closeResource(null,pstm,null);
        }
        return update;
    }

}
