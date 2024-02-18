package com.weidashan.service;

import com.weidashan.pojo.PmsStock;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku库存表 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-11-06
 */
public interface IPmsStockService extends IService<PmsStock> {
    boolean removeByProductId(Long productId);
    List<PmsStock> getStockByProductId(Long productId);
}
