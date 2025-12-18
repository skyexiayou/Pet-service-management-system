package cn.edu.xaut.domain.dto.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 关联预约订单创建DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "OrderFromAppointmentDTO", description = "关联预约订单创建请求")
public class OrderFromAppointmentDTO {

    @NotNull(message = "预约ID不能为空")
    @ApiModelProperty(value = "预约ID", required = true, example = "1")
    private Integer apptId;

    @ApiModelProperty(value = "用品购买列表", example = "[{\"relId\":1,\"quantity\":2}]")
    private List<ProductPurchaseDTO> products;
}
