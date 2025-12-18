package cn.edu.xaut.domain.entity.medical;

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
 * 医疗服务表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("medical")
@ApiModel(value = "MedicalDO", description = "医疗服务表实体类")
public class MedicalDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 医疗服务ID */
    @TableId(value = "MedID", type = IdType.AUTO)
    @ApiModelProperty(value = "医疗服务ID", dataType = "Integer", example = "1")
    private Integer medId;

    /** 服务名 */
    @TableField("MedName")
    @ApiModelProperty(value = "服务名", dataType = "String", example = "宠物疫苗接种")
    private String medName;

    /** 类型 */
    @TableField("MedType")
    @ApiModelProperty(value = "类型", dataType = "String", example = "疫苗")
    private String medType;

    /** 单价 */
    @TableField("Price")
    @ApiModelProperty(value = "单价", dataType = "BigDecimal", example = "150.00")
    private BigDecimal price;

    /** 时长（单位：分钟） */
    @TableField("Duration")
    @ApiModelProperty(value = "时长（单位：分钟）", dataType = "Integer", example = "30")
    private Integer duration;

    /** 服务说明 */
    @TableField("MedRemarks")
    @ApiModelProperty(value = "服务说明", dataType = "String", example = "包含疫苗接种和观察")
    private String medRemarks;
}