package com.weidashan.controller;


import com.weidashan.pojo.PmsBrand;
import com.weidashan.service.IImgService;
import com.weidashan.service.IPmsBrandService;
import com.weidashan.util.ResultJson;
import io.minio.errors.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2023-10-26
 */
@RestController
@RequestMapping("/pms-brand")
public class PmsBrandController {
    @Resource
    IPmsBrandService brandService;
    @Resource
    IImgService imgService;

    @GetMapping("/list")
    ResultJson list(Integer pageNo, Integer pageSize, String name) {
        return ResultJson.success(brandService.page(pageNo,pageSize,name));
    }
    @PostMapping("/add")
    ResultJson add(PmsBrand pmsBrand, MultipartFile file) throws ServerException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        pmsBrand.setLogo(imgService.upload(file));
        return ResultJson.success(brandService.save(pmsBrand),"添加品牌成功");
    }
    @GetMapping("/getone")
    ResultJson getone(Long id) {
        return ResultJson.success(brandService.getById(id));
    }
    @PostMapping("/update")
    ResultJson update(PmsBrand pmsBrand, MultipartFile file) throws ServerException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if(file != null && file.getSize() > 0) {
            pmsBrand.setLogo(imgService.upload(file));
        }
        return ResultJson.success(brandService.updateById(pmsBrand),"修改品牌成功");
    }
    @PostMapping("/del")
    ResultJson del(PmsBrand pmsBrand) {
        String message = pmsBrand.getActive() == 0 ? "删除品牌成功" : "恢复品牌成功";
        return ResultJson.success(brandService.updateById(pmsBrand),message);
    }
}
