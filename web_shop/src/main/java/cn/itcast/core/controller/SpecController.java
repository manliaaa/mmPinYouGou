package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.service.SpecService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 规格管理
 */
@RestController
@RequestMapping("/specification")
public class SpecController {

    @Reference
    private SpecService specService;

    /**
     * 规格高级查询
     * @param spec
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Specification spec, Integer page, Integer rows) {
        PageResult result = specService.findPage(spec, page, rows);
        return result;

    }

    /**
     * 添加保存规格
     * @param spec
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody SpecEntity spec){
        try {
            specService.add(spec);
            return new Result(true, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败!");
        }
    }

    /**
     * 规格回显
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public SpecEntity findOne(Long id) {
        SpecEntity one = specService.findOne(id);
        return one;
    }

    /**
     * 规格保存修改
     * @param specEntity
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody SpecEntity specEntity) {
        try {
            specService.update(specEntity);
            return new Result(true, "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败!");
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            specService.delete(ids);
            return new Result(true, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList() {
        List<Map> maps = specService.selectOptionList();
        return maps;
    }

    @RequestMapping("/updateStatus")
    public  Result updateStatus(Long[] ids, String status) {
        try {
            if (ids != null) {
                for (Long id : ids) {
                    //1. 更改数7银行业调查++据库中商品的审核状态
                    specService.updateStatus(id, status);

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
