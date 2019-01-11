package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Reference
    private SeckillService seckillService;

    @RequestMapping("/search")
    public PageResult search(@RequestBody SeckillGoods SeckillGoods, Integer page, Integer rows) {
        //获取当前登录用户用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //将当前用户名放入卖家id字段
        SeckillGoods.setSellerId(userName);

        PageResult result = seckillService.findPage(SeckillGoods, page, rows);
        return result;
    }

    @RequestMapping("/findAll")
    public List<SeckillGoods> findAll() {
        return seckillService.findAll();
    }

    @RequestMapping("/findById")
    public SeckillGoods findById(Long id){
        return seckillService.findById(id);
    }
}
