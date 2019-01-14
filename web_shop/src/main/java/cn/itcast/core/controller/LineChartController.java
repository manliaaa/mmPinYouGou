package cn.itcast.core.controller;

import cn.itcast.core.service.LineChartService;
import cn.itcast.core.util.Data;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;


@Controller
@RequestMapping("/index")
public class LineChartController {
    @Reference
    private LineChartService lineChartService;

    @RequestMapping("/getData.do")
    @ResponseBody
    public Map<String,Object> getDataByFree(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        //获取时间段
        String time = request.getParameter("time");

        //获取商家名称
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        //调用service层，获取数据
        Map<String, Double> allData = lineChartService.findAll(time, userName);
        //封装折线图横坐标
        int size = allData.keySet().size();
        String[] Stringdata = new String[size];
        //封装折线图纵坐标
        double[] Moneydata = new double[size];
        Set<String> set = allData.keySet();
        int i = 0;
        for (String s : set) {
            Stringdata[i] = s;
            Double money = allData.get(s);
            Moneydata[i] = money;
            i++;
        }


        //创建map集合，用于封装数据集合
        Map<String,Object> map = new HashMap<String,Object>();

        map.put("xdata",Stringdata);

        //创建数据集合
        List<Data> list =new ArrayList<Data>();

        //
        Data data = new Data();
        data.setName("商品销售情况");
        data.setDatas(Moneydata);


        list.add(data);


        map.put("list", list);
        return map;
    }

}


