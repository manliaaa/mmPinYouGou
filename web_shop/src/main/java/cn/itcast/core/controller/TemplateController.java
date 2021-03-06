package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.TemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {

    @Reference
    private TemplateService templateService;

    @RequestMapping("/findOne")
    public TypeTemplate findOne(Long id) {
        TypeTemplate one = templateService.findOne(id);
        return one;
    }



    @RequestMapping("/search")
    public PageResult search(@RequestBody TypeTemplate template, Integer page, Integer rows) {
        PageResult result = templateService.findPage(template, page, rows);
        return result;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TypeTemplate template) {
        try {
            templateService.add(template);
            return new Result(true, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败!");
        }
    }
    /**
     * 根据模板id, 获取规格和对应的规格选项集合数据
     * @param id
     * @return
     */
    @RequestMapping("/findBySpecList")
    public List<Map> findBySpecList(Long id) {
        List<Map> list = templateService.findBySpecList(id);
        return list;
    }

    @RequestMapping("/update")
    public  Result update(@RequestBody TypeTemplate template) {
        try {
            templateService.update(template);
            return new Result(true, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败!");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            templateService.delete(ids);
            return new Result(true, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
    }

    @RequestMapping("/updateStatus")
    public  Result updateStatus(Long[] ids, String status) {
        try {
            if (ids != null) {
                for (Long id : ids) {
                    //1. 更改数7银行业调查++据库中商品的审核状态
                    templateService.updateStatus(id, status);

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
