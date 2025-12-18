package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 数据统计查询DTO
 * 创建时间：2025-12-18
 */
@Data
@ApiModel(description = "数据统计查询请求")
public class StatisticsQueryDTO {

    @ApiModelProperty(value = "统计月份（格式：YYYY-MM）", required = true, example = "2025-12")
    @NotBlank(message = "统计月份不能为空")
    @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "统计月份格式错误，应为YYYY-MM")
    private String statMonth;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小", example = "10")
    private Integer pageSize = 10;
}
