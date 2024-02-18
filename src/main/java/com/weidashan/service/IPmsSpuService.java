package com.weidashan.service;

import com.weidashan.pojo.PmsSpu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu属性表 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-11-01
 */
public interface IPmsSpuService extends IService<PmsSpu> {
    List<PmsSpu> list(Long categoryId);
    List<PmsSpu> getByCategory(Long[] categoryIds);
}
