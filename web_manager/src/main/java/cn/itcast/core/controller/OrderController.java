package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.BrandService;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
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

    @RequestMapping("/findAll1")
    public List<Order> findAll1() {
        List<Order> list = orderService.findAll1();
        return list;
    }

    /**
     * 品牌分页查询
     * @param page  当前页
     * @param rows  每页查询多少条数据
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(Integer page, Integer rows) {
        PageResult result = orderService.findPage(null, page, rows);
        return result;
    }

    /**
     * 添加品牌数据
     * @param brand
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody  Order order) {
        try {
            orderService.add(order);
            return new Result(true, "添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败!");
        }
    }

    /**
     * 根据id查询品牌对象
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Order findOne(Long id) {
        Order one = orderService.findOne(id);
        return one;
    }

    /**
     * 保存修改
     * @param brand
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Order order) {
        try {
            orderService.update(order);
            return new Result(true, "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败!");
        }
    }

    /**
     * 删除选中的数据
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            orderService.delete(ids);
            return new Result(true, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Order order, Integer page, Integer rows) {
        PageResult result = orderService.findPage(order, page, rows);
        return result;
    }

    /**
     * 获取模板下拉选择框所需要的数据
     * [{id:1,text:xxx},{id:2, text: asdfasdfsa}]
     * @return
     */
/*    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        List<Map> list = orderService.selectOptionList();
        return list;
    }*/
}
