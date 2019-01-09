package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatQuery;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.util.Constants;
import cn.itcast.core.util.ImportExcelUtil;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatDao catDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        //1. 查询所有分类数据
        List<ItemCat> itemAllList = catDao.selectByExample(null);
        //2. 将所有分类数据以分类名称为key, 对应的模板id为value, 存入redis中
        for (ItemCat itemCat : itemAllList) {
            redisTemplate.boundHashOps(Constants.CATEGORY_LIST_REDIS).put(itemCat.getName(), itemCat.getTypeId());
        }

        //3. 到数据库中查询数据返回到页面展示
        ItemCatQuery query = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = query.createCriteria();
        //根据父级id查询
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> itemCats = catDao.selectByExample(query);
        return itemCats;
    }

    @Override
    public ItemCat findOne(Long id) {
        return catDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return catDao.selectByExample(null);
    }

    @Override
    public void getBankListByExcel(String path) {
        String filepath = "D:\\" + path;
        File file = new File(filepath);
        List<List<Object>> list = null;
        try {
            FileInputStream inputStream = new FileInputStream(new File(filepath));
            list = ImportExcelUtil.getBankListByExcel(inputStream, filepath);
            System.out.println(list);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            ItemCat itemCat = new ItemCat();
                itemCat.setParentId(Long.parseLong(String.valueOf(list.get(i).get(1))));
                itemCat.setName(String.valueOf(list.get(i).get(2)));
            if ((list.get(i).size()==4)){
                itemCat.setTypeId(Long.parseLong(String.valueOf(list.get(i).get(3))));
            }
            catDao.insertSelective(itemCat);
        }
    }
}
