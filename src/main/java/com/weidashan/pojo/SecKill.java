package com.weidashan.pojo;

import com.weidashan.pojo.BasePojo;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 秒杀表
 * </p>
 *
 * @author weidashan
 * @since 2024-02-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecKill extends BasePojo {

    private static final long serialVersionUID = 1L;

    /**
     * 库存id
     */
    private Long stockId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 秒杀开始时间
     */
    private LocalDateTime startTime;

    /**
     * 秒杀结束时间
     */
    private LocalDateTime endTime;

    /**
     * 秒杀剩余数量
     */
    private Integer saleAmount;

    /**
     * 秒杀未支付数量
     */
    private Integer lockAmount;

    /**
     * 秒杀完成数量
     */
    private Integer successAmount;

    /**
     * 1有效 0无效
     */
    private Integer active;

}
