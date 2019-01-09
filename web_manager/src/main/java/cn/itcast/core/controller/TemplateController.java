package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.TemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {

    @Reference
    private TemplateService templateService;

    @RequestMapping("/search")
    public PageResult search(@RequestBody TypeTemplate template, Integer page, Integer rows) {
        PageResult result = templateService.findPage(template, page, rows);
        return result;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TypeTemplate template) {
        try {
            templateService.add(template);
            return new Result(true, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败!");
        }
    }

    @RequestMapping("/findOne")
    public TypeTemplate findOne(Long id) {
        TypeTemplate one = templateService.findOne(id);
        return one;
    }

    @RequestMapping("/update")
    public  Result update(@RequestBody TypeTemplate template) {
        try {
            templateService.update(template);
            return new Result(true, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败!");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            templateService.delete(ids);
            return new Result(true, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
    }
    //excel导入
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
            templateService.getBankListByExcel(file.getOriginalFilename());
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
