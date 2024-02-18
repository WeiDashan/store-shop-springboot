package com.weidashan.service;

import com.weidashan.pojo.PmsSpuValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品spu值 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-11-06
 */
public interface IPmsSpuValueService extends IService<PmsSpuValue> {
    boolean removeByProductId(Long productId);

    List<PmsSpuValue> getSpuValuesByProductId(Long productId);


}
