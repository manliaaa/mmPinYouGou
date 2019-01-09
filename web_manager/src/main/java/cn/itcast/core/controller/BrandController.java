package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    @RequestMapping("/findAll")
    public List<Brand> findAll() {
        List<Brand> list = brandService.findAll();
        return list;
    }


    //    @RequestMapping("/findPage")
//    public PageResult findPage( Integer page, Integer rows){
//        PageResult pageResult = brandService.findPage(page,rows);
//        return pageResult;
//    }
    @RequestMapping("/add")
    public Result add(@RequestBody Brand brand) {
        try {
            brandService.add(brand);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }

    @RequestMapping("/findOne")
    public Brand findOne(Long id) {
        Brand brand = brandService.findOne(id);
        return brand;
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand) {
        try {
            brandService.update(brand);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            brandService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Brand brand, Integer page, Integer rows) {
        PageResult page1 = brandService.findPage(brand, page, rows);
        return page1;
    }

    //模块关联回显
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList() {
        return brandService.selectOptionList();
    }
    @RequestMapping("/getBankListByExcel")
    public Result uploadFile(@RequestParam(value = "file" , required = true) MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            FileOutputStream fileWriter = new FileOutputStream("D:\\"+file.getOriginalFilename());
            byte[] b = new byte[1024*10];
            int len = 0;
            while ((len = inputStream.read(b))!=-1){
                fileWriter.write(b, 0, len);
            }
            brandService.getBankListByExcel(file.getOriginalFilename());
            fileWriter.close();
            File f = new File("D:\\"+file.getOriginalFilename());
            f.delete();
            return new Result(true,"导入成功!");
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true,"导入失败!");
        }
    }



}

