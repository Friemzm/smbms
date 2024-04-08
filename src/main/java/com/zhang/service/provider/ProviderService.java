package com.zhang.service.provider;

import com.zhang.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProviderService {
    //供应商列表
    public List<Provider> getProviderList(String proName, String proCode);
    //添加供应商
    public boolean add(Provider provider);
    //通过id显示供应商信息
    public Provider getProviderById(String id);
    //通过proId删除Provider
    public int deleteProviderById(String delId);
    //修改
    public boolean modify(Provider provider);
}
