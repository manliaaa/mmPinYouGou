package cn.itcast.core.controller;

import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.pojo.entity.CatEntity;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ContentService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 展示广告
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    /**
     * 根据分类id查询广告集合
     *
     * @param categoryId
     * @return
     */
    @RequestMapping("/findByCategoryId")
    public List<Content> findByCategoryId(Long categoryId) {
        return contentService.findByCategoryIdFromRedis(categoryId);
    }

    @RequestMapping("/findHome")
    public List<Content> findHome(Long categoryId) {
        return contentService.findHome(categoryId);
    }


    @RequestMapping("/findByParent")
    public List<ItemCat> findByParent() {
        return contentService.findByParent();
    }


}
