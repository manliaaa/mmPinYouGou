package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.item.ItemCat;

import java.util.List;

public interface ItemCatService {

    public List<ItemCat> findByParentId(Long parentId);

    public PageResult findPage(ItemCat itemCat, Integer page, Integer rows);

    public ItemCat findOne(Long id);

    public List<ItemCat> findAll();

    public void  updateStatus(Long id, String  status);

    void getBankListByExcel(String originalFilename);
}
