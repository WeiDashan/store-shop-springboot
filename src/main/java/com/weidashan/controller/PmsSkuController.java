package com.weidashan.controller;


import com.weidashan.pojo.PmsSku;
import com.weidashan.service.IPmsSkuService;
import com.weidashan.util.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * sku属性表 前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2023-11-01
 */
@RestController
@RequestMapping("/pms-sku")
public class PmsSkuController {
    @Resource
    IPmsSkuService skuService;
    @GetMapping("/list")
    ResultJson list(Long categoryId) {
        return ResultJson.success(skuService.list(categoryId));
    }
    @PostMapping("/add")
    ResultJson add(PmsSku pmsSku) {
        return ResultJson.success(skuService.save(pmsSku),"添加sku成功");
    }
    @GetMapping("/getone")
    ResultJson getone(Long id) {
        return ResultJson.success(skuService.getById(id));
    }
    @PostMapping("/update")
    ResultJson update(PmsSku pmsSku) {
        return ResultJson.success(skuService.updateById(pmsSku),"修改sku成功");
    }
    @PostMapping("/del")
    ResultJson del(Long id) {
        return ResultJson.success(skuService.removeById(id),"删除sku成功");
    }
}
