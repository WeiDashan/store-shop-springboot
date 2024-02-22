package com.weidashan.pojo;

import com.weidashan.pojo.BasePojo;
import java.time.LocalDateTime;
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
public class AppOrder extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 下单用户id
     */
    private Long userId;

    /**
     * 库存id
     */
    private Long stockId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 库存商品描述
     */
    private String skuDetail;

    /**
     * 下单数量
     */
    private Integer productNum;

    /**
     * 商品单价,单位分
     */
    private Integer productPrice;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 付款时间
     */
    private LocalDateTime payTime;

    /**
     * 0未付款，1待发货，2待收货，3待评价，4交易成功，5已取消，6退款中
     */
    private Integer orderStatus;


}
