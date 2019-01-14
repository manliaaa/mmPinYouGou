package cn.itcast.core.controller;

import cn.itcast.core.ExcelUtil.ExportExcel;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.service.ExcelGetData;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/showExcel")
public class ExcelGoodsController {
    @Reference
    private ExcelGetData excelGetData;

    @RequestMapping(value = "/goods.do",method = RequestMethod.GET)
    public Result downloadFile(HttpServletResponse response){
        try {
            //获取数据库中的数据
            List<Goods> goodsDataToExcel = excelGetData.getGoodsDataToExcel();
            ExportExcel<Goods> ee= new ExportExcel<Goods>();
            String[] headers = {"getId","SellerId","GoodsName","DefaultItemId","AuditStatus",
                    "IsMarketable","BrandId","Caption","Category1Id","Category2Id",
                    "Category3Id","SmallPic","Price","TypeTemplateId","IsEnableSpec",
                    "IsDelete"};
            String fileName = "商品表";
            ee.exportExcel(headers,goodsDataToExcel,fileName,response);
            return new Result(true,"商品导出成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"商品导出失败");
        }
    }
}
