package cn.itcast.core.service;


import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.seckill.SeckillGoods;

import java.util.List;

public interface SeckillService {

    public PageResult findPage(SeckillGoods seckillGoods, Integer page, Integer rows);

    public SeckillGoods findOne(Long id);

    public List<SeckillGoods> findAll();

    public SeckillGoods findById(Long id);

    void add(SeckillGoods seckillGoods);

    void update(SeckillGoods seckillGoods);
}
