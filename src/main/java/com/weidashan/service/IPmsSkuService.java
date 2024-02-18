package com.weidashan.service;

import com.weidashan.pojo.PmsSku;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku属性表 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-11-01
 */
public interface IPmsSkuService extends IService<PmsSku> {
    List<PmsSku> list(Long categoryId);
    List<PmsSku> getByCategory(Long[] categoryIds);
}
