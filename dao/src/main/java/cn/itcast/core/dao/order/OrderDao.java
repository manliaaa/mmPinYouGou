package cn.itcast.core.dao.order;

import cn.itcast.core.pojo.entity.Orders;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderQuery;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

public interface OrderDao {
    int countByExample(OrderQuery example);

    int deleteByExample(OrderQuery example);

    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderQuery example);

    Order selectByPrimaryKey(Long orderId);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderQuery example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderQuery example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    @Select("SELECT\n" +
            "\tSUM(payment) AS payment,\n" +
            "\tDATE_FORMAT(create_time,'%Y-%m-%d') AS create_time\n" +
            "FROM\n" +
            "\ttb_order\n" +
            "GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d')\n")
    List<Orders> findAll();
}