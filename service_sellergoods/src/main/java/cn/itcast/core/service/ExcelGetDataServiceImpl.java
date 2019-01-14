package cn.itcast.core.service;

import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.order.OrderDao;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.order.Order;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
@Service
public class ExcelGetDataServiceImpl implements ExcelGetData {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodsDao goodsDao;
    @Override
    public List<Order> getOrderDataToExcel() {
        List<Order> orderList = new ArrayList<Order>();
        List<Order> orders = orderDao.selectByExample(null);
        for (Order order : orders) {
            Order orderValue = new Order(order.getOrderId(),order.getPayment(),order.getPaymentType(),
                    order.getPostFee(),order.getStatus(),order.getCreateTime(),order.getUpdateTime(),
                    order.getPaymentTime(),order.getConsignTime(),order.getEndTime(),order.getCloseTime(),
                    order.getShippingName(),order.getShippingCode(),order.getUserId(),order.getBuyerMessage(),
                    order.getBuyerNick(),order.getBuyerRate(),order.getReceiverAreaName(),order.getReceiverMobile(),
                    order.getReceiverZipCode(),order.getReceiver(),order.getExpire(),order.getInvoiceType(),order.getSourceType(),
                    order.getSellerId());
            orderList.add(orderValue);
        }
        return orderList;
    }

    @Override
    public List<Goods> getGoodsDataToExcel() {
        List<Goods> goodsList = new ArrayList<>();
        List<Goods> goods = goodsDao.selectByExample(null);
        for (Goods good : goods) {
            Goods goodsValue = new Goods(good.getId(),good.getSellerId(),good.getGoodsName(),
                    good.getDefaultItemId(),good.getAuditStatus(),good.getIsMarketable(),good.getBrandId(),
                    good.getCaption(),good.getCategory1Id(),good.getCategory2Id(),good.getCategory3Id(),
                    good.getSmallPic(),good.getPrice(),good.getTypeTemplateId(),good.getIsEnableSpec(),
                    good.getIsDelete());
            goodsList.add(goodsValue);
        }
        return goodsList;
    }
}
