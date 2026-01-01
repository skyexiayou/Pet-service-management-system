package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品创建DTO（包含门店关联信息）
 */
@Data
@ApiModel(description = "商品创建DTO")
public class ProductCreateDTO {

    @ApiModelProperty(value = "商品名称", required = true, example = "狗粮")
    @NotBlank(message = "商品名称不能为空")
    @Size(max = 100, message = "商品名称长度不能超过100个字符")
    private String productName;

    @ApiModelProperty(value = "商品类型", required = true, example = "食品")
    @NotBlank(message = "商品类型不能为空")
    @Size(max = 50, message = "商品类型长度不能超过50个字符")
    private String productType;

    @ApiModelProperty(value = "供应商", example = "XX宠物用品公司")
    @Size(max = 100, message = "供应商名称长度不能超过100个字符")
    private String supplier;

    @ApiModelProperty(value = "门店ID", required = true, example = "1")
    @NotNull(message = "门店ID不能为空")
    private Integer storeId;

    @ApiModelProperty(value = "价格", required = true, example = "99.00")
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    private BigDecimal price;

    @ApiModelProperty(value = "库存数量", required = true, example = "100")
    @NotNull(message = "库存不能为空")
    @Min(value = 0, message = "库存不能为负数")
    private Integer storeStock;

    @ApiModelProperty(value = "上架状态（在售/下架/缺货）", example = "在售")
    private String shelfStatus = "在售";
}
