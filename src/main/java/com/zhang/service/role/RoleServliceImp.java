package com.zhang.service.role;

import com.zhang.dao.BaseDao;
import com.zhang.dao.role.RoleDao;
import com.zhang.dao.role.RoleDapImp;
import com.zhang.pojo.Role;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleServliceImp implements RoleServlice{
    //引入Dao
    private RoleDao roleDao;
    public RoleServliceImp(){
      roleDao = new RoleDapImp();
    }
    @Override
    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;

        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return roleList;
    }
   /* @Test
    public void test(){
        for (Role role : new RoleServliceImp().getRoleList()) {
            System.out.println(role.getRoleName());
        }
    }*/
}
