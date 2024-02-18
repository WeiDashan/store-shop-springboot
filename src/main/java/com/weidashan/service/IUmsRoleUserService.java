package com.weidashan.service;

import com.weidashan.pojo.UmsRoleUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与用户关联表 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-10-30
 */
public interface IUmsRoleUserService extends IService<UmsRoleUser> {
    boolean save(Long roleId, Long[] userIds);
    List<UmsRoleUser> getUsersByRoleId(Long roleId);
}
