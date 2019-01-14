package cn.itcast.core.pojo.order;

import java.math.BigDecimal;
import java.util.Date;

public class NewOrder {
    /**
     * 订单id
     */
    private Long order_id;

    /**
     * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private BigDecimal payment;

    /**
     * 支付类型，1、在线支付，2、货到付款
     */
    private String payment_type;

    /**
     * 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
    private String post_fee;

    /**
     * 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价
     */
    private String status;

    /**
     * 订单创建时间
     */
    private Date create_time;

    /**
     * 订单更新时间
     */
    private Date update_time;

    /**
     * 付款时间
     */
    private Date payment_time;

    /**
     * 发货时间
     */
    private Date consign_time;

    /**
     * 交易完成时间
     */
    private Date end_time;

    /**
     * 交易关闭时间
     */
    private Date close_time;

    /**
     * 物流名称
     */
    private String shipping_name;

    /**
     * 物流单号
     */
    private String shipping_code;

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 买家留言
     */
    private String buyer_message;

    /**
     * 买家昵称
     */
    private String buyer_nick;

    /**
     * 买家是否已经评价
     */
    private String buyer_rate;

    /**
     * 收货人地区名称(省，市，县)街道
     */
    private String receiver_area_name;

    /**
     * 收货人手机
     */
    private String receiver_mobile;

    /**
     * 收货人邮编
     */
    private String receiver_zip_code;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 过期时间，定期清理
     */
    private Date expire;

    /**
     * 发票类型(普通发票，电子发票，增值税发票)
     */
    private String invoice_type;

    /**
     * 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
    private String source_type;

    /**
     * 商家ID
     */
    private String seller_id;

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPost_fee() {
        return post_fee;
    }

    public void setPost_fee(String post_fee) {
        this.post_fee = post_fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(Date payment_time) {
        this.payment_time = payment_time;
    }

    public Date getConsign_time() {
        return consign_time;
    }

    public void setConsign_time(Date consign_time) {
        this.consign_time = consign_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getClose_time() {
        return close_time;
    }

    public void setClose_time(Date close_time) {
        this.close_time = close_time;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBuyer_message() {
        return buyer_message;
    }

    public void setBuyer_message(String buyer_message) {
        this.buyer_message = buyer_message;
    }

    public String getBuyer_nick() {
        return buyer_nick;
    }

    public void setBuyer_nick(String buyer_nick) {
        this.buyer_nick = buyer_nick;
    }

    public String getBuyer_rate() {
        return buyer_rate;
    }

    public void setBuyer_rate(String buyer_rate) {
        this.buyer_rate = buyer_rate;
    }

    public String getReceiver_area_name() {
        return receiver_area_name;
    }

    public void setReceiver_area_name(String receiver_area_name) {
        this.receiver_area_name = receiver_area_name;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public String getReceiver_zip_code() {
        return receiver_zip_code;
    }

    public void setReceiver_zip_code(String receiver_zip_code) {
        this.receiver_zip_code = receiver_zip_code;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }
}
