package cn.edu.xaut.domain.dto.diagnosis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * 保存诊断和处方的DTO
 */
@Data
@ApiModel(description = "保存诊断和处方的请求数据")
public class DiagnosisSaveDTO {

    @NotNull(message = "预约ID不能为空")
    @ApiModelProperty(value = "预约ID", required = true)
    private Integer apptId;

    @NotEmpty(message = "病情描述不能为空")
    @ApiModelProperty(value = "病情描述", required = true)
    private String diagnoseDesc;

    @NotEmpty(message = "诊断结果不能为空")
    @ApiModelProperty(value = "诊断结果", required = true)
    private String diagnosis;

    @NotEmpty(message = "药品列表不能为空")
    @ApiModelProperty(value = "已选药品列表", required = true)
    private List<PrescriptionDrugDTO> drugs;

    /**
     * 处方药品DTO
     */
    @Data
    public static class PrescriptionDrugDTO {
        @NotNull(message = "药品ID不能为空")
        @ApiModelProperty(value = "药品ID", required = true)
        private Integer drugId;

        @NotNull(message = "每次用量不能为空")
        @ApiModelProperty(value = "每次用量", required = true)
        private Double dosePerTime;

        @NotNull(message = "每日次数不能为空")
        @ApiModelProperty(value = "每日次数", required = true)
        private Integer frequencyPerDay;

        @NotNull(message = "开具数量不能为空")
        @ApiModelProperty(value = "开具数量（盒）", required = true)
        private Integer prescriptionNum;
    }
}