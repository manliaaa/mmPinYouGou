package cn.itcast.core.service;


import cn.itcast.core.pojo.seckill.SeckillGoods;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.Map;

@Service(timeout=3000)
public class SeckillSearchServiceImpl implements SeckillSearchService{
    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String,Object> map=new HashMap<>();
        Query query=new SimpleQuery();
        //添加查询条件
        Criteria criteria=new Criteria("seckill_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        ScoredPage<SeckillGoods> page = solrTemplate.queryForPage(query, SeckillGoods.class);
        map.put("rows", page.getContent());
        return map;
    }
}
