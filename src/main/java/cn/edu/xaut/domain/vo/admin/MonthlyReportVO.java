package cn.edu.xaut.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 门店月报VO
 * 创建时间：2025-12-18
 */
@Data
@ApiModel(description = "门店月报数据")
public class MonthlyReportVO {

    @ApiModelProperty(value = "门店ID", example = "1")
    private Integer storeId;

    @ApiModelProperty(value = "门店名称", example = "宠物乐园总店")
    private String storeName;

    @ApiModelProperty(value = "统计月份", example = "2025-12")
    private String statMonth;

    @ApiModelProperty(value = "新增客户数", example = "15")
    private Integer newUserCount;

    @ApiModelProperty(value = "总订单数", example = "50")
    private Integer totalOrderCount;

    @ApiModelProperty(value = "美容服务订单数", example = "20")
    private Integer beautyOrderCount;

    @ApiModelProperty(value = "寄养服务订单数", example = "15")
    private Integer fosterOrderCount;

    @ApiModelProperty(value = "医疗服务订单数", example = "15")
    private Integer medicalOrderCount;

    @ApiModelProperty(value = "用品销售额", example = "5000.00")
    private BigDecimal productSales;

    @ApiModelProperty(value = "美容营收", example = "1600.00")
    private BigDecimal beautyRevenue;

    @ApiModelProperty(value = "寄养营收", example = "7500.00")
    private BigDecimal fosterRevenue;

    @ApiModelProperty(value = "医疗营收", example = "1800.00")
    private BigDecimal medicalRevenue;

    @ApiModelProperty(value = "总营收", example = "15900.00")
    private BigDecimal totalRevenue;

    @ApiModelProperty(value = "热门服务类型", example = "寄养")
    private String hotServiceType;
}
