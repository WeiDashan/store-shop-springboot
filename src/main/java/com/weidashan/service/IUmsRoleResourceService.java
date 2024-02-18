package com.weidashan.service;

import com.weidashan.pojo.UmsRoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色关联资源 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-10-30
 */
public interface IUmsRoleResourceService extends IService<UmsRoleResource> {
    boolean save(Long roleId, Long[] resourceIds);
    List<Long> getByRoleId(Long roleId);
}
