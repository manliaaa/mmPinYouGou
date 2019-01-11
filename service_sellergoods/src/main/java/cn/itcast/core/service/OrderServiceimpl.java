package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.OorderService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceimpl implements OorderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public PageResult findPage(Order order, Integer page, Integer rows){
        PageHelper.startPage(page, rows );




        Page<Order> orderPage = (Page<Order>)orderDao.selectByExample(null);
        return new PageResult(orderPage.getTotal(), orderPage.getResult());
    }



    @Override
    public List<Order> findAll() {
        return orderDao.selectByExample(null);
    }

    @Override
    public Order findById(Long Id) {
        System.out.println(".."+Id);
        Order order = orderDao.selectByPrimaryKey(Id);
        return order;
    }


}
