package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.OorderService;
import cn.itcast.core.service.OrderService;
import cn.itcast.core.service.SeckillService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OorderService orderService;

    @RequestMapping("/search")
    public PageResult search(@RequestBody Order order, Integer page, Integer rows) {
        //获取当前登录用户用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //将当前用户名放入卖家id字段
        order.setSellerId(userName);

        PageResult result = orderService.findPage(order, page, rows);
        return result;
    }

    @RequestMapping("/findAll")
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @RequestMapping("/findById")
    public Order findById(Long Id){
        System.out.println(Id);
        Order order = orderService.findById(Id);
        return order;
    }
}
