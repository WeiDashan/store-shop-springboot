package com.weidashan.controller;


import com.weidashan.pojo.PmsSpu;
import com.weidashan.service.IPmsSpuService;
import com.weidashan.util.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * spu属性表 前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2023-11-01
 */
@RestController
@RequestMapping("/pms-spu")
public class PmsSpuController {
    @Resource
    IPmsSpuService spuService;
    @GetMapping("/list")
    ResultJson list(Long categoryId) {
        return ResultJson.success(spuService.list(categoryId));
    }
    @PostMapping("/add")
    ResultJson add(PmsSpu pmsSpu) {
        return ResultJson.success(spuService.save(pmsSpu),"添加属性成功");
    }
    @GetMapping("/getone")
    ResultJson getone(Long id) {
        return ResultJson.success(spuService.getById(id));
    }
    @PostMapping("/update")
    ResultJson update(PmsSpu pmsSpu) {
        return ResultJson.success(spuService.updateById(pmsSpu),"修改属性成功");
    }
    @PostMapping("/del")
    ResultJson del(Long id) {
        return ResultJson.success(spuService.removeById(id),"删除属性成功");
    }
}
