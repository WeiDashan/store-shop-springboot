package com.weidashan.controller;


import com.weidashan.pojo.AppCart;
import com.weidashan.service.IAppCartService;
import com.weidashan.util.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2024-02-24
 */
@RestController
@RequestMapping("/app-cart")
public class AppCartController {

    @Resource
    IAppCartService appCartService;

    @GetMapping("/getAllCartByUserId")
    ResultJson getAllCartByUserId(Long userId){
        return ResultJson.success(appCartService.getAllCartByUserId(userId),"请求成功");
    }

    @PostMapping("/addCart")
    ResultJson addCart(AppCart cart){
        return ResultJson.success(appCartService.save(cart), "保存成功");
    }

    @PostMapping("/updateProductNumByCartId")
    ResultJson updateProductNumByCartId(Long cartId, Integer productNum){
        return ResultJson.success(appCartService.updateProductNumByCartId(cartId, productNum),"修改成功");
    }

}
