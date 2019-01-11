package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.Orders;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrderService {

    public void add(Order order);

    public PayLog getPayLogByUserName(String userName);

    public void updatePayStatus(String userName);
    //折线图后台查询
    List<Orders> findAll();
}
