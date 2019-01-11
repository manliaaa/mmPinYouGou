package cn.itcast.core.controller;

import cn.itcast.core.service.SeckillSearchService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/seckillsearch")
public class SeckillSearchController
{
    @Reference
    private SeckillSearchService seckillSearchService;

    @RequestMapping("/search")
    public Map<String, Object> search(@RequestBody Map searchMap ){
        return  seckillSearchService.search(searchMap);
    }
}
