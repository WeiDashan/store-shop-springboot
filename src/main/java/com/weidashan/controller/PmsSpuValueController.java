package com.weidashan.controller;


import com.weidashan.service.IPmsSpuValueService;
import com.weidashan.util.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商品spu值 前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2023-11-06
 */
@RestController
@RequestMapping("/pms-spu-value")
public class PmsSpuValueController {

    @Resource
    IPmsSpuValueService spuValueService;

    @GetMapping("/getSpuValuesByProductId")
    ResultJson getSpuValuesByProductId(Long productId){
        return ResultJson.success(spuValueService.getSpuValuesByProductId(productId), "请求成功");
    }

}
