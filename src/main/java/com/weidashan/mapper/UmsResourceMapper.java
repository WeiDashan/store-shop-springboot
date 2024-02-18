package com.weidashan.mapper;

import com.weidashan.pojo.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author weidashan
 * @since 2023-10-30
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {
    List<UmsResource> getByUserId(Long userId);
}
