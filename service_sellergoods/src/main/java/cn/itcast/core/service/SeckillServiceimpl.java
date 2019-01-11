package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import cn.itcast.core.service.SeckillService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SeckillServiceimpl implements SeckillService{

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    @Override
    public PageResult findPage(SeckillGoods seckillGoods, Integer page, Integer rows){
        PageHelper.startPage(page, rows );

        SeckillGoodsQuery query = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = query.createCriteria();

        if (seckillGoods != null) {

            if (seckillGoods.getTitle() != null && !"".equals(seckillGoods.getTitle())) {
                criteria.andTitleLike("%"+seckillGoods.getTitle()+"%");
            }
            if (seckillGoods.getSellerId() != null && !"".equals(seckillGoods.getSellerId())
                    && !"admin".equals(seckillGoods.getSellerId()) && !"wc".equals(seckillGoods.getSellerId())) {
                criteria.andSellerIdEqualTo(seckillGoods.getSellerId());
            }
        }
        Page<SeckillGoods> seckillGoodsPage = (Page<SeckillGoods>)seckillGoodsDao.selectByExample(query);
        return new PageResult(seckillGoodsPage.getTotal(), seckillGoodsPage.getResult());
    }

    @Override
    public SeckillGoods findOne(Long id) {
        return seckillGoodsDao.selectByPrimaryKey(id);
    }

    @Override
    public List<SeckillGoods> findAll() {
        return seckillGoodsDao.selectByExample(null);
    }

    @Override
    public SeckillGoods findById(Long id) {

        return seckillGoodsDao.selectByPrimaryKey(id);
    }

}
