package cn.edu.xaut.domain.dto.serviceitem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 服务项目DTO
 * @date 2025.12.17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "服务项目DTO")
public class ServiceItemDTO {

    @Schema(description = "服务名称", required = true, example = "宠物美容")
    @NotNull(message = "服务名称不能为空")
    private String serviceName;

    @Schema(description = "服务类型", example = "美容", allowableValues = {"美容", "医疗", "驯导", "寄养"})
    private String serviceType;

    @Schema(description = "服务价格", required = true, example = "100.00")
    @NotNull(message = "服务价格不能为空")
    @Min(value = 0, message = "服务价格不能为负数")
    private BigDecimal price;

    @Schema(description = "服务时长（分钟）", example = "60")
    @Min(value = 1, message = "服务时长不能小于1分钟")
    private Integer duration;

    @Schema(description = "服务备注")
    private String serviceRemarks;
}