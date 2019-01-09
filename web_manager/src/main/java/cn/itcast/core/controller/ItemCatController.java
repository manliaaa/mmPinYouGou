package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService catService;

    /**
     * 根据父级id查询它对应的子集数据
     *
     * @param parentId 父级id
     * @return
     */
    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId) {
        List<ItemCat> list = catService.findByParentId(parentId);
        return list;
    }
    //excel导入
    @RequestMapping("/getBankListByExcel")
    public Result uploadFile(@RequestParam(value = "file", required = true) MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            FileOutputStream fileWriter = new FileOutputStream("D:\\" + file.getOriginalFilename());
            byte[] b = new byte[1024 * 10];
            int len = 0;
            while ((len = inputStream.read(b)) != -1) {
                fileWriter.write(b, 0, len);
            }
            catService.getBankListByExcel(file.getOriginalFilename());
            fileWriter.close();
            File f = new File("D:\\" + file.getOriginalFilename());
            f.delete();
            return new Result(true, "导入成功!");
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true, "导入失败!");
        }
    }
}