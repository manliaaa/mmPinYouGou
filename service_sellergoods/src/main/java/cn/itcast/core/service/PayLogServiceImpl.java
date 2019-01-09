package cn.itcast.core.service;

import cn.itcast.core.dao.log.PayLogDao;
import cn.itcast.core.pojo.log.PayLog;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PayLogServiceImpl implements PayLogService {
    @Autowired
    private PayLogDao payLogDao;
    //折线图实现
    @Override
    public List<PayLog> findtu() {
        List<PayLog> payLogList = payLogDao.selectByExample(null);
        return payLogList;
    }
}
