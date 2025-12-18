package cn.edu.xaut.domain.vo.pet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 宠物服务记录VO
 * @date 2025.12.18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "宠物服务记录VO")
public class ServiceRecordVO {

    @Schema(description = "服务类型", example = "美容")
    private String serviceType;

    @Schema(description = "服务名称", example = "宠物洗澡")
    private String serviceName;

    @Schema(description = "服务时间", example = "2025-12-20 14:30:00")
    private Date serviceTime;

    @Schema(description = "服务状态", example = "已完成")
    private String serviceStatus;

    @Schema(description = "服务价格", example = "50.00")
    private BigDecimal servicePrice;

    @Schema(description = "服务时长（分钟）", example = "60")
    private Integer duration;

    @Schema(description = "服务说明")
    private String serviceRemarks;
}