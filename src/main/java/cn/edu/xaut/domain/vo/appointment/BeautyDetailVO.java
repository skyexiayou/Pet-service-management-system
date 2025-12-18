package cn.edu.xaut.domain.vo.appointment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 美容服务明细VO
 */
@Data
@ApiModel(value = "BeautyDetailVO", description = "美容服务明细")
public class BeautyDetailVO {

    @ApiModelProperty(value = "美容项目ID")
    private Integer beautyId;

    @ApiModelProperty(value = "美容项目名称")
    private String beautyName;

    @ApiModelProperty(value = "美容类型")
    private String beautyType;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "时长（分钟）")
    private Integer duration;

    @ApiModelProperty(value = "服务说明")
    private String beautyRemarks;
}
