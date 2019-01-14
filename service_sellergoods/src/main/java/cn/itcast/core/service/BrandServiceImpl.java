package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemQuery;
import cn.itcast.core.util.ImportExcelUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private JmsTemplate jmsTemplate;

    //用于商品上架
    @Autowired
    private ActiveMQTopic topicPageAndSolrDestination;

    //用于商品下架
    @Autowired
    private ActiveMQQueue queueSolrDeleteDestination;

    @Override
    public List<Brand> findAll() {
        List<Brand> brands = brandDao.selectByExample(null);
        return brands;
    }

    @Override
    public PageResult findPage(Brand brand, Integer page, Integer rows) {
        //使用分页助手, 传入当前页和每页查询多少条数据
        PageHelper.startPage(page, rows);
        //创建查询对象
        BrandQuery brandQuery = new BrandQuery();
        //创建where查询条件
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        if (brand != null) {
            if (brand.getName() != null && !"".equals(brand.getName())) {
                criteria.andNameLike("%" + brand.getName() + "%");
            }
            if (brand.getFirstChar() != null && !"".equals(brand.getFirstChar())) {
                criteria.andFirstCharLike("%" + brand.getFirstChar() + "%");
            }
            if (brand.getAuditStatus() != null && !"".equals(brand.getAuditStatus())) {
                criteria.andAuditStatusLike("%"+brand.getAuditStatus()+"%");
            }
        }
        //使用分页助手的page对象接收查询到的数据, page对象继承了ArrayList所以可以接收查询到的结果集数据.
        Page<Brand> brandList = (Page<Brand>) brandDao.selectByExample(brandQuery);
        System.out.println();
        return new PageResult(brandList.getTotal(), brandList.getResult());
    }

    @Override
    public void add(Brand brand) {
        //带selective这样的方法, 在拼接sql语句的时候会判断传入的参数里面的属性是否为null, 如果为null
        //不参数拼接sql语句会使sql缩短增加传输效率和执行效率
        brandDao.insertSelective(brand);
    }

    @Override
    public Brand findOne(Long id) {
        return brandDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(Brand brand) {
        //带selective的, 就是传入的修改对象中的属性如果为null, 不参与拼接sql语句进行修改, 这个是根据查询条件修改
        //brandDao.updateByExampleSelective(修改的品牌对象, 查询条件对象 )
        //不带selective的修改, 不管传入的品牌对象是否为null都参数拼接sql语句进行修改, 如果品牌对象中有属性为null
        //则修改完后数据库中对应的值也会是null, 根据查询条件修改
        //brandDao.updateByExample(修改的品牌对象, 查询条件对象)

        //根据主键修改, 不管传入参数是否为null, 都会直接修改, 如果属性为null则数据库中对应的字段值修改成null
        //brandDao.updateByPrimaryKey()

        brandDao.updateByPrimaryKeySelective(brand);
    }

    @Override
    public List<Map> selectOptionList() {
        return brandDao.selectOptionList();
    }
    //excel导入
    @Override
    public void getBankListByExcel(String path) {
        String filepath = "D:\\" + path;
//        File file = new File(filepath);
        List<List<Object>> list = null;
        try {
            FileInputStream inputStream = new FileInputStream(new File(filepath));
            list = ImportExcelUtil.getBankListByExcel(inputStream, filepath);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            Brand brand = new Brand();
                brand.setName(String.valueOf(list.get(i).get(1)));
            if ((list.get(i).size()==3)){
                brand.setFirstChar(String.valueOf(list.get(i).get(2)));
            }
            brandDao.insertSelective(brand);
        }

    }



    @Override
    public void delete(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                brandDao.deleteByPrimaryKey(id);
            }
        }

    }

    @Override
    public void updateStatus(final Long id, String status) {
        /**
         * 根据商品id改变数据库中商品的上架状态
         */
        //1. 修改商品状态
        Brand brand = new Brand();
        brand.setId(id);
        brand.setAuditStatus(status);
        brandDao.updateByPrimaryKeySelective(brand);

        //2. 修改库存状态
/*        Item item = new Item();
        item.setStatus(status);

        ItemQuery query = new ItemQuery();
        ItemQuery.Criteria criteria = query.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        itemDao.updateByExampleSelective(item, query);*/


        /**
         * 判断如果审核通过, 将商品id作为消息发送给消息服务器
         */
/*        if ("1".equals(status)) {
            //发送消息, 第一个参数是发送到的队列, 第二个参数是一个接口, 定义发送的内容
            jmsTemplate.send(topicPageAndSolrDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage textMessage = session.createTextMessage(String.valueOf(id));
                    return textMessage;
                }
            }
            );
        }*/
    }

}
