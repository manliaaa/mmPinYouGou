package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillOrder;

public interface SeckillOrderService {

    /**
     * 提交订单
     * @param seckillId
     * @param userId
     */
    public void submitOrder(Long seckillId, String userId);

    /**
     * 根据用户名查询秒杀订单
     * @param userId
     */
    public SeckillOrder searchOrderFromRedisByUserId(String userId);

}
