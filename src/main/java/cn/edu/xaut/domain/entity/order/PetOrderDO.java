package cn.edu.xaut.domain.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 宠物订单实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pet_order")
@Schema(description = "宠物订单实体类")
public class PetOrderDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    @TableId(value = "OrderID", type = IdType.AUTO)
    @Schema(description = "订单ID", example = "1")
    private Integer orderId;

    /** 订单编号 */
    @TableField("OrderNo")
    @Schema(description = "订单编号", example = "ORD20230101000001")
    private String orderNo;

    /** 用户ID */
    @TableField("UserID")
    @Schema(description = "用户ID", example = "1")
    private Integer userId;

    /** 宠物ID */
    @TableField("PetID")
    @Schema(description = "宠物ID", example = "1")
    private Integer petId;

    /** 门店ID */
    @TableField("StoreID")
    @Schema(description = "门店ID", example = "1")
    private Integer storeId;

    /** 处方ID */
    @TableField("prescription_id")
    @Schema(description = "处方ID", example = "1")
    private Integer prescriptionId;

    /** 订单类型（药品订单/服务订单） */
    @TableField("OrderType")
    @Schema(description = "订单类型", example = "药品订单")
    private String orderType;

    /** 订单状态（待支付/已支付/已取消/已退款） */
    @TableField("OrderStatus")
    @Schema(description = "订单状态", example = "待支付")
    private String orderStatus;

    /** 订单总金额 */
    @TableField("TotalAmount")
    @Schema(description = "订单总金额", example = "200.00")
    private BigDecimal totalAmount;

    /** 创建时间 */
    @TableField("CreateTime")
    @Schema(description = "创建时间", example = "2023-01-01 10:00:00")
    private LocalDateTime createTime;



    /** 备注 */
    @TableField("Remark")
    @Schema(description = "备注", example = "请尽快发货")
    private String remark;

    // ========== 非数据库字段，用于联表查询 ==========

    /** 用户名称 */
    @TableField(exist = false)
    @Schema(description = "用户名称")
    private String userName;

    /** 宠物名称 */
    @TableField(exist = false)
    @Schema(description = "宠物名称")
    private String petName;

    /** 门店名称 */
    @TableField(exist = false)
    @Schema(description = "门店名称")
    private String storeName;

    /** 处方编号 */
    @TableField(exist = false)
    @Schema(description = "处方编号")
    private String prescriptionNo;
}
