package com.zhang.dao.bill;

import com.zhang.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BillDao {

    //添加订单
    public int aad(Connection connection, Bill bill)throws SQLException;
    //通过查询条件获取供应商列表-模糊查询-getBillList
    public List<Bill> getBillList(Connection connection,Bill bill)throws SQLException;
    //通过billid获取bill
    public Bill getBillByID(Connection connection,String id)throws SQLException;
    //通过id删除bill
    public int deleteBill(Connection connection,String delid)throws SQLException;
    //更改bill信息
    public int modify(Connection connection,Bill bill)throws SQLException;
    //根据供应商id查询订单数量
    public int getBillCountByProvider(Connection connection,String providerId)throws SQLException;


}
