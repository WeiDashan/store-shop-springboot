package com.weidashan.service;

import com.weidashan.pojo.UmsRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-10-30
 */
public interface IUmsRoleService extends IService<UmsRole> {
    List<UmsRole> list(String name);
}
