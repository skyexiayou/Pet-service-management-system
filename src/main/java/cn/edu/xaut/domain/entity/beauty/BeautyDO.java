package cn.edu.xaut.domain.entity.beauty;

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
 * 美容项目表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("beauty")
@ApiModel(value = "BeautyDO", description = "美容项目表实体类")
public class BeautyDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 美容项目ID */
    @TableId(value = "BeautyID", type = IdType.AUTO)
    @ApiModelProperty(value = "美容项目ID", dataType = "Integer", example = "1")
    private Integer beautyId;

    /** 服务名 */
    @TableField("BeautyName")
    @ApiModelProperty(value = "服务名", dataType = "String", example = "宠物洗澡")
    private String beautyName;

    /** 类型 */
    @TableField("BeautyType")
    @ApiModelProperty(value = "类型", dataType = "String", example = "美容")
    private String beautyType;

    /** 单价 */
    @TableField("Price")
    @ApiModelProperty(value = "单价", dataType = "BigDecimal", example = "100.00")
    private BigDecimal price;

    /** 时长（单位：分钟） */
    @TableField("Duration")
    @ApiModelProperty(value = "时长（单位：分钟）", dataType = "Integer", example = "60")
    private Integer duration;

    /** 服务说明 */
    @TableField("BeautyRemarks")
    @ApiModelProperty(value = "服务说明", dataType = "String", example = "包含洗澡、吹干、梳理毛发")
    private String beautyRemarks;
}