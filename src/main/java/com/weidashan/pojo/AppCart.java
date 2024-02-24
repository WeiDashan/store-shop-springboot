package com.weidashan.pojo;

import com.weidashan.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author weidashan
 * @since 2024-02-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppCart extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 库存id
     */
    private Long stockId;

    /**
     * 库存sku描述
     */
    private String skuDetail;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品数量
     */
    private Integer productNum;

    /**
     * 商品单价
     */
    private Integer productPrice;

    /**
     * 商品图片
     */
    private String productIcon;


}
