package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类管理
 *
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService catService;

    /**
     * 根据父级id查询它对应的子集数据
     * @param parentId   父级id
     * @return
     */
    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId) {
        List<ItemCat> list = catService.findByParentId(parentId);
        //System.out.println("yi"+list);
        return list;
    }


    @RequestMapping("/findPage")
    public PageResult findPage(Integer page, Integer rows) {
        PageResult result = catService.findPage(null, page, rows);
        return result;
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody ItemCat itemCat, Integer page, Integer rows) {
        PageResult result = catService.findPage(itemCat, page, rows);
        return result;
    }

    @RequestMapping("/findOne")
    public ItemCat findOne(Long id) {
        return catService.findOne(id);
    }

    @RequestMapping("/findAll")
    public  List<ItemCat> findAll() {
        return catService.findAll();
    }

    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            if (ids != null) {
                for (Long id : ids) {
                    //1. 更改数7银行业调查++据库中商品的审核状态
                    catService.updateStatus(id, status);

                    //2. 判断商品审核状态是否为1, 审核通过
//                    if ("1".equals(status)) {
//                        //3. 根据商品id, 获取商品详细数据, 放入solr索引库中供前台系统搜索使用
//                        solrManagerService.saveItemToSolr(id);
//                        //4. 根据商品id, 获取商品详细数据, 通过数据和模板生成商品详情页面
//                        Map<String, Object> goodsMap = cmsService.findGoodsData(id);
//                        cmsService.createStaticPage(goodsMap, id);
//                    }
                }
            }
            return new Result(true, "状态修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "状态修改失败!");
        }
    }

}
