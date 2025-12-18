package cn.edu.xaut.domain.dto.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

/**
 * 预约信息DTO
 * @date 2025.12.17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "预约信息DTO")
public class AppointmentDTO {

    @Schema(description = "用户ID", required = true, example = "1")
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @Schema(description = "宠物ID", required = true, example = "1")
    @NotNull(message = "宠物ID不能为空")
    private Integer petId;

    @Schema(description = "门店ID", required = true, example = "1")
    @NotNull(message = "门店ID不能为空")
    private Integer storeId;

    @Schema(description = "员工ID", required = true, example = "1")
    @NotNull(message = "员工ID不能为空")
    private Integer empId;

    @Schema(description = "预约时间", required = true)
    @NotNull(message = "预约时间不能为空")
    private Date apptTime;

    @Schema(description = "预约状态", example = "pending", allowableValues = {"pending", "confirmed", "completed", "cancelled"})
    private String apptStatus;
}