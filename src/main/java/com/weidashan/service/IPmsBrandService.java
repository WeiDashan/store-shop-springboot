package com.weidashan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weidashan.pojo.PmsBrand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-10-26
 */
public interface IPmsBrandService extends IService<PmsBrand> {
    IPage<PmsBrand> page(Integer pageNo, Integer pageSize, String name);
    List<PmsBrand> getAll();
}
