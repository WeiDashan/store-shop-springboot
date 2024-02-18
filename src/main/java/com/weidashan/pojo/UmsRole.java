package com.weidashan.pojo;

import com.weidashan.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author weidashan
 * @since 2023-10-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UmsRole extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名
     */
    private String name;

    /**
     * 是否有效
     */
    private Integer active;


}
