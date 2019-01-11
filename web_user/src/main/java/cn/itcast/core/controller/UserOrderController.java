package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserOrderController {

    @Reference
    private UserService userService;

    // 查询订单
    @RequestMapping("/search")
    public PageResult search(Integer page,Integer rows,@RequestBody Order order){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        order.setUserId(userName);
        PageResult result = userService.search(page,rows,order);
        return result;
    }


}


