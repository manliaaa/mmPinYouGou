package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Orders;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

import java.util.List;

public interface OrderService {

    public List<Order> findAll1();

    public void add(Order order);

    public Order findOne(Long id);

    public PageResult findPage(Order order, Integer page, Integer rows);


    public void update(Order order);

    public void delete(Long[] ids);

    public PayLog getPayLogByUserName(String userName);

    public void updatePayStatus(String userName);
    //折线图后台查询
    List<Orders> findAll();
}
