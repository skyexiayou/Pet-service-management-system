package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * 报表查询DTO
 * @date 2025-12-19
 */
@Data
@ApiModel(description = "报表查询DTO")
public class ReportQueryDTO {

    @ApiModelProperty(value = "起始月份（格式：YYYY-MM）", required = true, example = "2025-01")
    @NotNull(message = "起始月份不能为空")
    @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "月份格式必须为YYYY-MM")
    private String startMonth;

    @ApiModelProperty(value = "终止月份（格式：YYYY-MM）", required = true, example = "2025-12")
    @NotNull(message = "终止月份不能为空")
    @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "月份格式必须为YYYY-MM")
    private String endMonth;

    @ApiModelProperty(value = "门店ID（可选，不传则查询所有门店）", example = "1")
    private Integer storeId;
}
