package cn.itcast.core.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface LineChartService {
    public Map<String,Double> findAll(String time, String userName) throws ParseException;
}
