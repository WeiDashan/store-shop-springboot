package com.weidashan.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author weidashan
 * @since 2023-10-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UmsUser extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String nickyName;


    @JsonIgnore
    private String password;

    private String phone;

    private String email;

    private Integer active;

    /**
     * 用户头像
     */
    private String icon;

    /**
     * 密码明文
     * */
    @TableField(exist = false)
    @JsonIgnore
    private String rawPassword;


}
