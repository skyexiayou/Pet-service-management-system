package cn.edu.xaut.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 月度报表汇总VO
 * @date 2025-12-19
 */
@Data
@ApiModel(description = "月度报表汇总VO")
public class MonthlyReportSummaryVO {

    @ApiModelProperty(value = "查询起始月份", example = "2025-01")
    private String startMonth;

    @ApiModelProperty(value = "查询终止月份", example = "2025-12")
    private String endMonth;

    @ApiModelProperty(value = "累计新增客户数", example = "150")
    private Integer totalNewUserCount;

    @ApiModelProperty(value = "累计总订单数", example = "500")
    private Integer totalOrderCount;

    @ApiModelProperty(value = "累计美容服务订单数", example = "200")
    private Integer totalBeautyOrderCount;

    @ApiModelProperty(value = "累计寄养服务订单数", example = "150")
    private Integer totalFosterOrderCount;

    @ApiModelProperty(value = "累计医疗服务订单数", example = "150")
    private Integer totalMedicalOrderCount;

    @ApiModelProperty(value = "累计用品销售额", example = "50000.00")
    private BigDecimal totalProductSales;

    @ApiModelProperty(value = "累计美容营收", example = "16000.00")
    private BigDecimal totalBeautyRevenue;

    @ApiModelProperty(value = "累计寄养营收", example = "75000.00")
    private BigDecimal totalFosterRevenue;

    @ApiModelProperty(value = "累计医疗营收", example = "18000.00")
    private BigDecimal totalMedicalRevenue;

    @ApiModelProperty(value = "累计总营收", example = "159000.00")
    private BigDecimal totalRevenue;

    @ApiModelProperty(value = "各月明细列表")
    private List<MonthlyReportVO> monthlyDetails;
}
