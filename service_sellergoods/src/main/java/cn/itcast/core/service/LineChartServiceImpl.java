package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderDao_Line;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.order.NewOrder;
import cn.itcast.core.pojo.order.OrderItem;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LineChartServiceImpl implements LineChartService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderDao_Line orderDao_line;
    @Autowired
    private OrderItemDao orderItemDao;

    //创建日期格式
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();

    @Override
    public Map<String,Double> findAll(String time, String userName) throws ParseException {

        //time=1查询一个星期的商品及数量
        if ("1".equals(time)) {
            //1.过去七天
            Date date = new Date();
            c.setTime(date);
            c.add(Calendar.DATE, -7);
            Date d = c.getTime();
            String startTime = format1.format(date);
            String endTime = format1.format(d);
            Map<String, Double> stringDoubleMap = selectDataByOrderdata(startTime,endTime, userName);
            return stringDoubleMap;
        }
        //time=1查询一个月的商品及数量
        if ("2".equals(time)) {
            //2.过去一月
            Date date = new Date();
            c.setTime(date);
            c.add(Calendar.MONTH, -1);
            Date m = c.getTime();
            String startTime = format1.format(date);
            String endTime = format1.format(m);
            Map<String, Double> stringDoubleMap = selectDataByOrderdata(startTime,endTime, userName);
            return stringDoubleMap;
        }
        //time=1查询一个季度的商品及数量
        if ("3".equals(time)) {
            //4.过去一年
            Date date = new Date();
            c.setTime(date);
            c.add(Calendar.YEAR, -1);
            Date y = c.getTime();
            String startTime = format1.format(date);
            String endTime = format1.format(y);
            Map<String, Double> stringDoubleMap = selectDataByOrderdata(startTime,endTime, userName);
            return stringDoubleMap;
        }
        return null;
    }

    public Map<String,Double> selectDataByOrderdata(String startTime,String endTime,String userName) throws ParseException {
        //获取中间时间

        Date parse1 = format1.parse(startTime);
        Date parse2 = format1.parse(endTime);
        List<Date> betweenDates = getDatesBetweenTwoDate(parse2, parse1);

        //定义map用于存放时间和金额
        Map<String,Double> map = new HashMap<>();
        //遍历时间
        if (betweenDates!=null&&!"".equals(betweenDates)) {
            for (Date betweenDate : betweenDates) {
                //定义一个变量用于存放当天的金额
                double money = 0.0;
                String formatDate = format1.format(betweenDate);
                List<NewOrder> dataByTime = orderDao_line.findByName(formatDate);
                if (dataByTime!=null && !"".equals(dataByTime)) {
                    for (NewOrder newOrder : dataByTime) {
                        String orderId = newOrder.getOrder_id().toString();
                        List<OrderItem> dataByName = orderDao_line.findById(orderId, userName);
                        if (dataByName!=null && !"".equals(dataByName)){
                            for (OrderItem orderItem : dataByName) {
                                String string = orderItem.getPrice().toString();
                                Double aDouble = Double.valueOf(string);
                                money = money+aDouble;
                                map.put(formatDate,money);
                            }
                        }
                    }
                }
                map.put(formatDate,money);
            }
        }
        return map;
    }

    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(beginDate);// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);// 把结束时间加入集合
        return lDate;
    }
}
