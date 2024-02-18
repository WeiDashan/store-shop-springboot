package com.weidashan.service;

import com.weidashan.pojo.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-10-30
 */
public interface IUmsResourceService extends IService<UmsResource> {
    List<UmsResource> getResource(Long parentId);
    List<Long> getLast();
    List<UmsResource> getByUserId(Long userId);
}
