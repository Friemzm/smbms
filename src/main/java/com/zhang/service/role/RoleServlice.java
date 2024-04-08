package com.zhang.service.role;

import com.zhang.pojo.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleServlice {
    //获取角色列表
    public List<Role> getRoleList();
}
