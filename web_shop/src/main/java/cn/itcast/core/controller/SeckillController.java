package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
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

import java.text.SimpleDateFormat;
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

    @RequestMapping("/save")
    public Result add(@RequestBody SeckillGoods seckillGoods) {
        System.out.println(seckillGoods.getStartTime());
        try {
            seckillService.add(seckillGoods);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody SeckillGoods seckillGoods) {
        try {
            seckillService.update(seckillGoods);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }
}
