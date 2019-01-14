package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.pojo.entity.Orders;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;
import java.util.Map;

@Service
public class OrderSearchServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public List<Order> findAll1() {
        List<Order> orders = orderDao.selectByExample(null);
        return orders;
    }

    @Override
    public void add(Order order) {
        //带selective这样的方法, 在拼接sql语句的时候会判断传入的参数里面的属性是否为null, 如果为null
        //不参数拼接sql语句会使sql缩短增加传输效率和执行效率
        orderDao.insertSelective(order);
    }

    @Override
    public Order findOne(Long id) {
        return orderDao.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(Order order, Integer page, Integer rows) {
        //使用分页助手, 传入当前页和每页查询多少条数据
        PageHelper.startPage(page, rows);
        //创建查询对象
        OrderQuery orderQuery = new OrderQuery();
        //创建where查询条件
        OrderQuery.Criteria criteria = orderQuery.createCriteria();

        //使用分页助手的page对象接收查询到的数据, page对象继承了ArrayList所以可以接收查询到的结果集数据.
        Page<Order> orders = (Page<Order>)orderDao.selectByExample(orderQuery);
        return new PageResult(orders.getTotal(), orders.getResult());
    }


    @Override
    public void update(Order order) {
        //带selective的, 就是传入的修改对象中的属性如果为null, 不参与拼接sql语句进行修改, 这个是根据查询条件修改
        //brandDao.updateByExampleSelective(修改的品牌对象, 查询条件对象 )
        //不带selective的修改, 不管传入的品牌对象是否为null都参数拼接sql语句进行修改, 如果品牌对象中有属性为null
        //则修改完后数据库中对应的值也会是null, 根据查询条件修改
        //brandDao.updateByExample(修改的品牌对象, 查询条件对象)

        //根据主键修改, 不管传入参数是否为null, 都会直接修改, 如果属性为null则数据库中对应的字段值修改成null
        //brandDao.updateByPrimaryKey()

        orderDao.updateByPrimaryKeySelective(order);
    }

/*    @Override
    public List<Map> selectOptionList() {
        return orderDao.selectOptionList();
    }*/

    @Override
    public void delete(Long[] ids) {
        if (ids != null) {
            for(Long id : ids){
                orderDao.deleteByPrimaryKey(id);
            }
        }

    }

    @Override
    public PayLog getPayLogByUserName(String userName) {
        return null;
    }

    @Override
    public void updatePayStatus(String userName) {

    }

    @Override
    public List<Orders> findAll() {
        return null;
    }

}
