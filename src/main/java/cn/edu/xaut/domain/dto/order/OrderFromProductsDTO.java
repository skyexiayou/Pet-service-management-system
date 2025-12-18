package cn.edu.xaut.domain.dto.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 纯用品购买订单创建DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "OrderFromProductsDTO", description = "纯用品购买订单创建请求")
public class OrderFromProductsDTO {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true, example = "1")
    private Integer userId;

    @NotNull(message = "门店ID不能为空")
    @ApiModelProperty(value = "门店ID", required = true, example = "1")
    private Integer storeId;

    @NotEmpty(message = "用品列表不能为空")
    @ApiModelProperty(value = "用品购买列表", required = true, example = "[{\"relId\":1,\"quantity\":2}]")
    private List<ProductPurchaseDTO> products;
}
