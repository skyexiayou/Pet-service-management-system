package cn.edu.xaut.domain.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工信息DTO
 * @date 2025.12.17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "员工信息DTO")
public class EmployeeDTO {

    @Schema(description = "门店ID", required = true, example = "1")
    @NotNull(message = "门店ID不能为空")
    private Integer storeId;

    @Schema(description = "员工姓名", required = true, example = "张三")
    @NotNull(message = "员工姓名不能为空")
    private String empName;

    @Schema(description = "职位", example = "店长", allowableValues = {"店长", "美容师", "驯导师", "收银员"})
    private String position;

    @Schema(description = "联系电话", example = "13800138000")
    private String empPhone;

    @Schema(description = "入职时间")
    private Date entryTime;

    @Schema(description = "薪资", example = "5000.00")
    @Min(value = 0, message = "薪资不能为负数")
    private BigDecimal salary;
}