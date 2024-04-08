package com.zhang.service.user;

import com.zhang.dao.BaseDao;
import com.zhang.dao.user.UserDao;
import com.zhang.dao.user.UserDaoImpl;
import com.zhang.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImp implements UserService{
    //业务层都会调用Dao层，所以需要引入Dao层
    private UserDao userDao;
    public UserServiceImp(){
        userDao = new UserDaoImpl();
    }

    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;
        //通过业务层调用对应的具体的数据库操作
        try {
            connection = BaseDao.getConnection();
          user = userDao.getLoginUser(connection,userCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return user;
    }
    //修改当前用户密码
    @Override
    public boolean updatePwd(int id, String password) {
        Connection connection = null;
        boolean flag = false;
        try {
             connection = BaseDao.getConnection();
            if (userDao.updatePwd(connection,id,password)>0){
                flag = true; }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }
    //查询记录数
    @Override
    public int getUserCount(String username, int userRole) {
        Connection connection = null;
        int count = 0;
        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection, username, userRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return count;
    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;
        System.out.println("queryUserName------"+queryUserName);
        System.out.println("queryUserRole------"+queryUserRole);
        System.out.println("currentPageNo------"+currentPageNo);
        System.out.println("pageSize-----------"+pageSize);
        try {
            connection = BaseDao.getConnection();
           userList = userDao.getUserList(connection,queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return userList;
    }


    @Override
    public Boolean addUser(User user) {
        Boolean flag = false;
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务
            int updateRows = userDao.addUser(connection,user);
            connection.commit();

            if (updateRows > 0){
                flag = true;
                System.out.println("add success!");
            }else {
                System.out.println("add failed!");
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
    //通过用户id删除用户信息
    @Override
    public Boolean deletUser(Integer delId) {
        Connection connection = null;
        Boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            int deleteRows = userDao.deletUser(connection,delId);
            if (deleteRows>0){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }
    //通过userid得到当前用户
    @Override
    public User getUserById(String id) {
        User user = new User();
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
            user = userDao.getUserById(connection,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user;
    }

    @Override
    public Boolean modify(User user) {
        Connection connection = null;
        Boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务
            int updateNum = userDao.modify(connection,user);
            connection.commit();//提交事务
            if (updateNum>0){
                flag = true;
                System.out.println("用户修改成功！");
            }else {
                System.out.println("用户修改失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            //若抛出异常，则说明修改失败需要回滚
            System.out.println("修改失败，回滚事务");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }

    @Override
    public User selectUserCodeExist(String userCode) {
        User user = new User();
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(connection,userCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return user;
    }

   /* @Test
    public void Test(){
        System.out.println(new UserServiceImp().getUserCount(null, 0));
    }*/
}
