package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Orders;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.CodingErrorAction;
import java.util.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    //折线图后台查询
    @RequestMapping("/findAll")
    public Map<String, Object> findAll() {
        List<Orders> ordersList = orderService.findAll();
        Map<String, Object> map = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        StringBuffer ab = new StringBuffer();
        //List<Integer> list = new ArrayList<>();
        map.put("Create_time", sb);
        map.put("Payment", ab);
        for (Orders orders : ordersList) {
            sb.append(orders.getCreate_time());
            sb.append(",");
            String payment = orders.getPayment();
            System.out.println(payment);
            String[] split = payment.split("\\.");
            System.out.println(Arrays.toString(split));
            //list.add(Integer.parseInt(String.valueOf(split[0])));
            ab.append(split[0]);
            ab.append(",");
        }

        return map;
    }
}
