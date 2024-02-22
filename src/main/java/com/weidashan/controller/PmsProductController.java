package com.weidashan.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weidashan.pojo.PmsProduct;
import com.weidashan.pojo.PmsSkuValue;
import com.weidashan.pojo.PmsSpuValue;
import com.weidashan.pojo.PmsStock;
import com.weidashan.service.*;
import com.weidashan.util.ResultJson;
import io.minio.errors.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2023-11-01
 */
@RestController
@RequestMapping("/pms-product")
public class PmsProductController {
    @Resource
    IPmsProductService productService;
    @Resource
    IPmsBrandService brandService;
    @Resource
    IPmsCategoryService categoryService;
    @Resource
    IPmsSpuService spuService;
    @Resource
    IPmsSkuService skuService;
    @Resource
    IImgService imgService;
    @Resource
    IPmsSpuValueService spuValueService;
    @Resource
    IPmsSkuValueService skuValueService;
    @Resource
    IPmsStockService stockService;
    @GetMapping("/list")
    ResultJson list(Integer pageNo, Integer pageSize) {
        System.out.println(pageNo+" "+pageSize);
        return ResultJson.success(productService.page(new Page<>(pageNo,pageSize)));
    }

    // 新增商品
    @GetMapping("/getData")
    ResultJson getData() {
        Map<String, List> map = new HashMap<>();
        map.put("brands",brandService.getAll());
        map.put("categorys",categoryService.getAll(0L));
        return ResultJson.success(map);
    }
    @GetMapping("/getAttr")
    ResultJson getAttr(Long[] categoryIds) {
        Map<String,List> map = new HashMap<>();
        map.put("spus",spuService.getByCategory(categoryIds));
        map.put("skus",skuService.getByCategory(categoryIds));
        return ResultJson.success(map);
    }
    @PostMapping("/add")
    @Transactional
    ResultJson add(PmsProduct pmsProduct, MultipartFile file, MultipartFile[] files, String[] spus, String[] skus, String[] stocks) throws ServerException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        pmsProduct.setImg(imgService.upload(file));
        List<String> piclist = new ArrayList<>();
        for(MultipartFile f : files) {
            MultipartFile file2 = new MockMultipartFile("file",f.getOriginalFilename(),f.getContentType(),f.getBytes());
            piclist.add(imgService.upload(file2));
        }

        pmsProduct.setPics(piclist.toString().replaceAll("\\[","").replaceAll("\\]",""));

        productService.save(pmsProduct);
        List<PmsSpuValue> spuValueList = new ArrayList<>();
        for(String spu : spus) {
            PmsSpuValue pmsSpuValue = JSONObject.parseObject(spu, PmsSpuValue.class);
            pmsSpuValue.setProductId(pmsProduct.getId());
            spuValueList.add(pmsSpuValue);
        }
        spuValueService.saveBatch(spuValueList);
        List<PmsSkuValue> skuValueList = new ArrayList<>();
        for(String sku : skus) {
            PmsSkuValue pmsSkuValue = JSONObject.parseObject(sku,PmsSkuValue.class);
            pmsSkuValue.setProductId(pmsProduct.getId());
            skuValueList.add(pmsSkuValue);
        }
        skuValueService.saveBatch(skuValueList);
        List<PmsStock> stockList = new ArrayList<>();
        for(String stock : stocks) {
            PmsStock pmsStock = JSONObject.parseObject(stock, PmsStock.class);
            pmsStock.setProductId(pmsProduct.getId());
            stockList.add(pmsStock);
        }
        stockService.saveBatch(stockList);
        return ResultJson.success("","添加成功");
    }

    @PostMapping("/update")
    @Transactional
    ResultJson update(Long productId, PmsProduct pmsProduct, String[] spus, String[] skus, String[] stocks){
        //更新product
        pmsProduct.setId(productId);
        productService.updateById(pmsProduct);

        //更新spu
        List<PmsSpuValue> spuValueList = new ArrayList<>();
        for(String spu : spus) {
            PmsSpuValue pmsSpuValue = JSONObject.parseObject(spu, PmsSpuValue.class);
            pmsSpuValue.setProductId(productId);
            spuValueList.add(pmsSpuValue);
        }
        spuValueService.removeByProductId(productId);
        spuValueService.saveBatch(spuValueList);

        //更新sku
        List<PmsSkuValue> skuValueList = new ArrayList<>();
        for(String sku : skus) {
            PmsSkuValue pmsSkuValue = JSONObject.parseObject(sku,PmsSkuValue.class);
            pmsSkuValue.setProductId(productId);
            skuValueList.add(pmsSkuValue);
        }
        skuValueService.removeByProductId(productId);
        skuValueService.saveBatch(skuValueList);

        //更新stocks
        List<PmsStock> stockList = new ArrayList<>();
        for(String stock : stocks) {
            PmsStock pmsStock = JSONObject.parseObject(stock, PmsStock.class);
            pmsStock.setProductId(productId);
            stockList.add(pmsStock);
        }
        stockService.removeByProductId(productId);
        stockService.saveBatch(stockList);
        return ResultJson.success("","修改成功");
    }

    @PostMapping("/del")
    ResultJson del(PmsProduct pmsProduct) {
        // 删除(product)id -> pms_product pms_sku_value pms_spu_value pms_stock
//        Long productId = pmsProduct.getId();
//        productService.removeById(productId);
//        skuValueService.removeById(productId);
//        spuValueService.removeByProductId(productId);
//        stockService.removeByProductId(productId);
        String message = pmsProduct.getActive() == 0 ? "删除用户成功" : "恢复用户成功";
        return ResultJson.success(productService.updateById(pmsProduct),message);
    }

    //修改商品
    @PostMapping("/getOne")
    ResultJson getOne(Long id) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("getOne product_id: "+id);
        map.put("brands",brandService.getAll());
        map.put("categorys",categoryService.getAll(0L));
        PmsProduct pmsProduct = productService.getById(id);
        map.put("pmsProduct", pmsProduct);
        map.put("pmsSpuValue", spuValueService.getSpuValuesByProductId(id));
        map.put("pmsSkuValue", skuValueService.getSkuValuesByProductId(id));
        String[] categoryStrIds = pmsProduct.getCategoryId().split(",");
        Long[] categoryIds = new Long[categoryStrIds.length];
        for(int i=0;i<categoryStrIds.length;i++){
            categoryIds[i] = Long.parseLong(categoryStrIds[i]);
        };
        map.put("skus",skuService.getByCategory(categoryIds));
        map.put("stock", stockService.getStockByProductId(id));
        return ResultJson.success(map);
    }
}
