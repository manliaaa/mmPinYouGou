package cn.itcast.core.service.impl;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillOrderService;
import cn.itcast.core.util.IdWorker;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
@Service
@Transactional
public class SeckillOrderServiceImpl implements SeckillOrderService{

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;


    @Override
    public void submitOrder(Long seckillId, String userId) {
        //从缓存中查询秒杀商品
        SeckillGoods seckillGoods =(SeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(seckillId);
        if(seckillGoods==null){
            throw new RuntimeException("商品不存在");
        }
        if(seckillGoods.getStockCount()<=0){
            throw new RuntimeException("商品已抢购一空");
        }
        //扣减（redis）库存
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        redisTemplate.boundHashOps("seckillGoods").put(seckillId, seckillGoods);//放回缓存
        if(seckillGoods.getStockCount()==0){//如果已经被秒光
            seckillGoodsDao.updateByPrimaryKey(seckillGoods);//同步到数据库
            redisTemplate.boundHashOps("seckillGoods").delete(seckillId);
        }
        //保存（redis）订单
        long orderId = idWorker.nextId();
        SeckillOrder seckillOrder=new SeckillOrder();
        seckillOrder.setId(orderId);
        seckillOrder.setCreateTime(new Date());
        seckillOrder.setMoney(seckillGoods.getCostPrice());//秒杀价格
        seckillOrder.setSeckillId(seckillId);
        seckillOrder.setSellerId(seckillGoods.getSellerId());
        seckillOrder.setUserId(userId);//设置用户ID
        seckillOrder.setStatus("0");//状态
        redisTemplate.boundHashOps("seckillOrder").put(userId, seckillOrder);
    }

    @Override
    public SeckillOrder  searchOrderFromRedisByUserId(String userId) {
        return (SeckillOrder) redisTemplate.boundHashOps("seckillOrder").get(userId);
    }

}
