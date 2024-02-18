package com.weidashan.service;

import com.weidashan.pojo.PmsSkuValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品sku值 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-11-06
 */
public interface IPmsSkuValueService extends IService<PmsSkuValue> {
    boolean removeByProductId(Long productId);

    List<PmsSkuValue> getSkuValuesByProductId(Long productId);

}
