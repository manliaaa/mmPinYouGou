package cn.itcast.core.dao.order;

import cn.itcast.core.pojo.order.NewOrder;
import cn.itcast.core.pojo.order.OrderItem;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderDao_Line {
    @Select("SELECT * FROM (SELECT * from tb_order WHERE status='2') t WHERE t.create_time LIKE \"%\"#{orderTime}\"%\" ")
    List<NewOrder> findByName(String orderTime);

    @Select("SELECT * FROM tb_order_item where order_id=#{param1} AND seller_id=#{param2}")
    List<OrderItem> findById(String orderId, String userName);
}
