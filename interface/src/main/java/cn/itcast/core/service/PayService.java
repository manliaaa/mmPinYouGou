package cn.itcast.core.service;

import java.util.Map;

public interface PayService {

    public Map createNative(String outTradeNo, String totolFee);

    public Map<String, String> queryPayStatus(String out_trade_no);
    //支付订单超时关闭
    public Map<String, String> PayStatus(String out_trade_no);
}
