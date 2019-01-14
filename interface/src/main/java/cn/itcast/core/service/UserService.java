package cn.itcast.core.service;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.user.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface UserService {

    public void sendCode(String phone);

    public Boolean checkSmsCode(String phone , String smsCode);

    public  void  add(User user);


    // 查询订单并分页
    public PageResult search(Integer page, Integer rows,Order Order);



    // 查询未付款订单并分页
    public PageResult searchStatus(Integer page,Integer rows,Order order);

    //地址管理
    public List<Address> addresses(Address address);
}
