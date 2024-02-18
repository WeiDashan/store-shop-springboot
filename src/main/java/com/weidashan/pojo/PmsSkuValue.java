package com.weidashan.pojo;

import com.weidashan.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品sku值
 * </p>
 *
 * @author weidashan
 * @since 2023-11-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PmsSkuValue extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 关联商品id
     */
    private Long productId;

    /**
     * 属性名
     */
    private String name;

    /**
     * 属性值
     */
    private String value;


}
