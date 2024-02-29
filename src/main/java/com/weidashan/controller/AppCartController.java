package com.weidashan.controller;


import com.weidashan.pojo.AppCart;
import com.weidashan.pojo.AppOrder;
import com.weidashan.pojo.PmsStock;
import com.weidashan.service.IAppCartService;
import com.weidashan.service.IAppOrderService;
import com.weidashan.service.IPmsStockService;
import com.weidashan.util.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

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
    @Resource
    IPmsStockService stockService;

    @Resource
    IAppOrderService orderService;

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
    @PostMapping("/delCartById")
    ResultJson delCartById(Long id){
        return ResultJson.success(appCartService.removeById(id),"移出成功");
    }
    @PostMapping("/orderByCarts")
    ResultJson orderByCarts(AppCart appCart){

        PmsStock stock = stockService.getById(appCart.getStockId());
        AppOrder appOrder = new AppOrder();
        appOrder.setUserId(appCart.getUserId());
        appOrder.setStockId(appCart.getStockId());
        appOrder.setProductId(appCart.getProductId());
        appOrder.setSkuDetail(stock.getSkuList());
        appOrder.setProductNum(appCart.getProductNum());
        appOrder.setProductPrice(appCart.getProductPrice());
        appOrder.setCreateTime(LocalDateTime.now());
        appOrder.setProductName(appCart.getProductName());
        appOrder.setProductIcon(appCart.getProductIcon());
        appCartService.removeById(appCart.getId());

        return ResultJson.success(orderService.save(appOrder),"下单成功");
    }

}
