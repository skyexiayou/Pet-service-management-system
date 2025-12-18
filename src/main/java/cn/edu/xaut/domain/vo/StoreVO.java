package cn.edu.xaut.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 门店信息VO
 * @date 2025.12.18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "门店信息VO")
public class StoreVO {

    @ApiModelProperty(value = "门店ID", example = "1")
    private Integer storeId;

    @ApiModelProperty(value = "城市ID", example = "1")
    private Integer cityId;

    @ApiModelProperty(value = "城市名称", example = "北京市")
    private String cityName;

    @ApiModelProperty(value = "门店名称", example = "宠宠之家")
    private String storeName;

    @ApiModelProperty(value = "门店地址", example = "北京市朝阳区某某路1号")
    private String storeAddress;

    @ApiModelProperty(value = "门店电话", example = "010-12345678")
    private String storePhone;

    @ApiModelProperty(value = "营业时间", example = "09:00-21:00")
    private String businessHours;
}