package cn.edu.xaut.domain.vo.petproduct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 宠物用品VO
 * @date 2025.12.18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "宠物用品VO")
public class PetProductVO {
    
    @ApiModelProperty(value = "商品ID", example = "1")
    private Integer productId;
    
    @ApiModelProperty(value = "商品名称", example = "狗粮")
    private String productName;
    
    @ApiModelProperty(value = "商品类型", example = "食品")
    private String productType;
    
    @ApiModelProperty(value = "供应商", example = "宠物用品公司")
    private String supplier;
}