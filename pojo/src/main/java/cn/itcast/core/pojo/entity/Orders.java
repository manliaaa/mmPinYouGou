package cn.itcast.core.pojo.entity;

import java.io.Serializable;

public class Orders implements Serializable {
    String payment;
    String create_time;

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "payment='" + payment + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }

    public String getCreate_time() {
        return create_time;
    }
}
