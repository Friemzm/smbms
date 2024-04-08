package com.zhang.dao.role;

import com.zhang.dao.BaseDao;
import com.zhang.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDapImp implements RoleDao{
    //获取角色列表
    @Override
    public List<Role> getRoleList(Connection connection) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Role> rolesList = new ArrayList<Role>();
        if (connection!=null){
            String sql = "select * from smbms_role";
            Object[] params = {};
            rs = BaseDao.execute(connection,pstm,rs,sql,params);
            while (rs.next()){
                Role _role = new Role();
                _role.setId(rs.getInt("id"));
                _role.setRoleCode(rs.getString("roleCode"));
                _role.setRoleName(rs.getString("roleName"));
                rolesList.add(_role);
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return rolesList;
    }
}
