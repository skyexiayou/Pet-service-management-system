package cn.edu.xaut.domain.entity.boarding;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 寄养服务表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("boarding")
@ApiModel(value = "BoardingDO", description = "寄养服务表实体类")
public class BoardingDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 寄养服务ID */
    @TableId(value = "BoardingID", type = IdType.AUTO)
    @ApiModelProperty(value = "寄养服务ID", dataType = "Integer", example = "1")
    private Integer boardingId;

    /** 服务名 */
    @TableField("BoardingName")
    @ApiModelProperty(value = "服务名", dataType = "String", example = "宠物寄养")
    private String boardingName;

    /** 类型 */
    @TableField("BoardingType")
    @ApiModelProperty(value = "类型", dataType = "String", example = "标准寄养")
    private String boardingType;

    /** 单价（每天） */
    @TableField("Price")
    @ApiModelProperty(value = "单价（每天）", dataType = "BigDecimal", example = "80.00")
    private BigDecimal price;

    /** 服务说明 */
    @TableField("BoardingRemarks")
    @ApiModelProperty(value = "服务说明", dataType = "String", example = "包含日常喂养和遛弯")
    private String boardingRemarks;
}