package com.weidashan.service;

import com.weidashan.pojo.PmsCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-10-30
 */
public interface IPmsCategoryService extends IService<PmsCategory> {
    List<PmsCategory> getByParentId(Long parentId);
    List<PmsCategory> getAll(Long parentId);
}
