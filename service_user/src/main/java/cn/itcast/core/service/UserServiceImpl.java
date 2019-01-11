package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.dao.user.UserDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemQuery;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.activemq.command.ActiveMQQueue;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JmsTemplate jmsTemplate;

    //点对点发送, 向这个目标中发送
    @Autowired
    private ActiveMQQueue smsDestination;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${template_code}")
    private String template_code;

    @Value("${sign_name}")
    private String sign_name;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private ItemDao itemDao;


    /**
     * 订单查询
     * @param page
     * @param rows
     * @param order
     * @return
     */
    @Override
    public PageResult search(Integer page, Integer rows, Order order) {
        // 1.使用分页小助手分页
        PageHelper.startPage(page,rows);
        // 2.创建Order查询对象
        OrderQuery orderQuery = new OrderQuery();
        OrderQuery.Criteria criteria = orderQuery.createCriteria();
        // 3.判断Order不能为空
        if (order != null) {
            // 4.状态不能为空
//            if (order.getStatus() != null && !"".equals(order.getStatus())){
            String userId = order.getUserId();
            // 5.将userID添加到查询条件中
            criteria.andUserIdEqualTo(userId);
//        }
        }
        // 6.获取到Order集合对象
        Page<Order> orders = (Page<Order>)orderDao.selectByExample(orderQuery);
        List<Order> orderList = orders.getResult();
        // 7.Order集合对象不能为空，长度要大于0
        if (orderList != null && orderList.size() >0){
            // 8.遍历OrderList
            for (Order Oneorder : orderList) {
                Long orderId = Oneorder.getOrderId();
                OrderItemQuery orderItemQuery = new OrderItemQuery();
                OrderItemQuery.Criteria queryCriteria = orderItemQuery.createCriteria();
                queryCriteria.andOrderIdEqualTo(orderId);
                List<OrderItem> itemList = orderItemDao.selectByExample(orderItemQuery);


                for (OrderItem orderItem : itemList) {
                    ItemQuery itemQuery = new ItemQuery();
                    ItemQuery.Criteria criteria1 = itemQuery.createCriteria();
                    criteria1.andTitleEqualTo(orderItem.getTitle());
                    List<Item> items = itemDao.selectByExample(itemQuery);
                    for (Item item : items) {
                        orderItem.setItemSpec(item.getSpec());
                    }
                }
                Oneorder.setOrderItemList(itemList);
            }
        }
        return new PageResult(orders.getTotal(),orders.getResult());

    }





















    private Map<String,Object> search(String userName, Integer page, Integer rows) {
        // 创建Map集合对象
        Map<String,Object> map = new HashMap<>();
        // 创建Order对象
        Order order = new Order();
        order.setUserId(userName);
        // 使用分页助手，传入当前页和每页查询多少条数据
        PageHelper.startPage(page,rows);
        // 创建查询对象
        OrderQuery query = new OrderQuery();
        // 创建查询条件
        OrderQuery.Criteria criteria = query.createCriteria();
        criteria.andUserIdEqualTo(order.getUserId());
        List<Order> orderList = orderDao.selectByExample(query);
        // 判断登录用户名是否为空
        if ("anonymousUser".equals(userName)){
            return  map;
        }
        // 遍历OrderList集合
        for (Order ord : orderList) {
            List<OrderItem> orderItemList = ord.getOrderItemList();
            // 获取OrderID主键
            Long orderId = ord.getOrderId();
            OrderItem orderItem= orderItemDao.selectByPrimaryKey(orderId);

            // 判断OrderItem集合不为空且长度大于0
            if (orderItemList != null && orderItemList.size() >0 ){
               orderItemList.add(orderItem);
                ord.setOrderItemList(orderItemList);
                orderList.add(ord);
                // 存入到map集合a
                map.put("orderList",orderList);
            }
        }
        Page<Order> orderPage = (Page<Order>)orderList;
        map.put("orderPage",orderPage);
        return map;
    }








    @Override
    public void sendCode(final String phone) {

        //1. 生成随机6为数字作为验证码
        StringBuffer sb = new StringBuffer();
        for(int i = 1; i< 7; i++) {
            sb.append(new Random().nextInt(10));
        }

        final String smsCode = sb.toString();
        System.out.println(smsCode);

        //2. 手机号作为key, 验证码最为value存入redis中, 有效时间为10分钟
        redisTemplate.boundValueOps(phone).set(sb.toString(), 60*10, TimeUnit.SECONDS);

        //3. 将手机号, 验证码, 模板编号, 签名封装成Map类型的消息发送给消息服务器
        jmsTemplate.send(smsDestination, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("mobile", phone);//手机号
                message.setString("template_code", template_code);//模板编码
                message.setString("sign_name", sign_name);//签名
                Map map=new HashMap();
                map.put("code", smsCode);	//验证码
                message.setString("param", JSON.toJSONString(map));
                return (Message) message;
            }
        });

    }

    @Override
    public Boolean checkSmsCode(String phone, String smsCode) {
        //1. 判断如果手机号或者验证码为空, 直接返回false
        if (phone == null || smsCode == null || "".equals(phone) || "".equals(smsCode)) {
            return false;
        }
        //2. 根据手机号到redis中获取我们自己的验证码
        String redisSmsCode = (String)redisTemplate.boundValueOps(phone).get();

        //3. 根据页面传入的验证码和我们自己存的验证码对比是否正确
        if (smsCode.equals(redisSmsCode)) {
            return true;
        }

        return false;
    }

    @Override
    public void add(User user) {
        userDao.insertSelective(user);
    }



    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        for(int i = 1; i< 7; i++) {
            sb.append(new Random().nextInt(10));
        }

        System.out.println("======" + sb.toString());
    }
}
