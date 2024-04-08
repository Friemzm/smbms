package com.zhang.service.user;

import com.zhang.pojo.User;

import java.util.List;

public interface UserService {
    //用户登录
    public User login(String userCode,String password);
    //修改当前用户密码
    public boolean updatePwd(int id, String password);
    //查询记录数
    public int getUserCount(String username,int userRole);
    //根据条件查询用户列表
    public List<User> getUserList(String queryUserName,int queryUserRole,int currentPageNo,int pageSize);
    //增加用户信息
    public Boolean addUser(User user);
    //通过用户id删除用户信息
    public Boolean deletUser(Integer delId);
    //通过userid得到当前用户
    public User getUserById(String id);
    //修改用户信息
    public Boolean modify(User user);
    //根据用户编码，判断用户是否存在
    public User selectUserCodeExist(String userCode);

}
