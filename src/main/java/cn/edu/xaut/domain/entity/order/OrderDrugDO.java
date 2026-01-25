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

/**
 * 订单药品明细实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_drug")
@Schema(description = "订单药品明细实体类")
public class OrderDrugDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    @TableId(value = "RelID", type = IdType.AUTO)
    @Schema(description = "明细ID", example = "1")
    private Integer relId;

    /** 订单ID */
    @TableField("OrderID")
    @Schema(description = "订单ID", example = "1")
    private Integer orderId;

    /** 药品ID */
    @TableField("DrugID")
    @Schema(description = "药品ID", example = "1")
    private Integer drugId;

    /** 药品名称 (非数据库字段) */
    @TableField(exist = false)
    @Schema(description = "药品名称", example = "阿莫西林")
    private String drugName;

    /** 药品规格 (非数据库字段) */
    @TableField(exist = false)
    @Schema(description = "药品规格", example = "100mg/片")
    private String drugSpec;

    /** 药品单价 */
    @TableField("UnitPrice")
    @Schema(description = "药品单价", example = "50.00")
    private BigDecimal price;

    /** 数量 */
    @TableField("DrugNum")
    @Schema(description = "数量", example = "5")
    private Integer quantity;

    /** 金额 */
    @TableField("Subtotal")
    @Schema(description = "金额", example = "250.00")
    private BigDecimal amount;

    /** 是否处方药（0-否，1-是）(非数据库字段，需确认是否在表中) */
    @TableField(exist = false)
    @Schema(description = "是否处方药", example = "1")
    private Integer isPrescription;
}
