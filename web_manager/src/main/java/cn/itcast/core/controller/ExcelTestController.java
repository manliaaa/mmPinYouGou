package cn.itcast.core.controller;

import cn.itcast.core.ExcelUtil.ExportExcel;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.ExcelGetData;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/showExcel")
public class ExcelTestController {
    @Reference
    public ExcelGetData excelGetData;
    /**
     * 测试生成excel表格
     */
    @RequestMapping(value = "/download.do", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    @ResponseBody
    public Result downloadFile(HttpServletResponse response) {
        try {
            //获取数据库中的数据
            List<Order> orderDataToExcel = excelGetData.getOrderDataToExcel();
            ExportExcel<Order> ee= new ExportExcel<Order>();
            String[] headers = { "OrderId", "Payment", "PaymentType", "PostFee","Status",
                    "CreateTime","UpdateTime","PaymentTime","ConsignTime","EndTime","CloseTime",
                    "ShippingName","ShippingCode","UserId","BuyerMessage","BuyerNick","BuyerRate",
                    "ReceiverAreaName" ,"ReceiverMobile","ReceiverZipCode","Receiver","Expire",
                    "InvoiceType","SourceType","SellerId"};
            String fileName = "订单表";
            ee.exportExcel(headers,orderDataToExcel,fileName,response);
            return new Result(true,"导出成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"导出失败");
        }
    }
}
