package com.weidashan.controller;


import com.weidashan.service.IUmsResourceService;
import com.weidashan.util.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2023-10-30
 */
@RestController
@RequestMapping("/ums-resource")
public class UmsResourceController {
    @Resource
    IUmsResourceService resourceService;
    @GetMapping("/list")
    ResultJson list() {
        return ResultJson.success(resourceService.getResource(0L));
    }
}
