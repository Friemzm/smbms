package com.zhang.service.bill;

import com.zhang.pojo.Bill;

import java.util.List;

public interface BillService {
    //添加订单
    public Boolean add(Bill bill);
    //通过查询条件获取供应商列表-模糊查询-getBillList
    public List<Bill>getBillList(Bill bill);
    //通过billid获取bill
    public Bill getBillByID(String id);
    //通过billid删除bill
    public Boolean deleteBill(String delid);
    //更改bill信息
    public Boolean modify(Bill bill);

}
