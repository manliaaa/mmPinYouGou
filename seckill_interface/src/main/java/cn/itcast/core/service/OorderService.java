package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.order.Order;

import java.util.List;

public interface OorderService {

    public PageResult findPage(Order order, Integer page, Integer rows);


    public List<Order> findAll();

    public Order findById(Long id);
}
