package com.weidashan.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weidashan.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author weidashan
 * @since 2024-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppUser extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称/用户名
     */
    private String nickyName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String icon;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 是否有效，1表示有效，0表示失效
     */
    private Integer active;

    /**
     * 用户密码
     */
    @JsonIgnore
    private String password;


    /**
     * 密码明文
     * */
    @TableField(exist = false)
    @JsonIgnore
    private String rawPassword;

}
