package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 城市DTO
 * @date 2025-12-18
 */
@Data
@ApiModel(description = "城市DTO")
public class CityDTO {

    @ApiModelProperty(value = "省份", required = true, example = "北京市")
    @NotBlank(message = "省份不能为空")
    @Size(max = 50, message = "省份长度不能超过50个字符")
    private String province;

    @ApiModelProperty(value = "城市名称", required = true, example = "北京")
    @NotBlank(message = "城市名称不能为空")
    @Size(max = 50, message = "城市名称长度不能超过50个字符")
    private String cityName;

    @ApiModelProperty(value = "邮政编码", example = "100000")
    @Size(max = 6, message = "邮政编码长度不能超过6个字符")
    private String zipCode;
}
