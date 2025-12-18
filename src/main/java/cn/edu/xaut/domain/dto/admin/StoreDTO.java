package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 门店DTO
 * @date 2025-12-18
 */
@Data
@ApiModel(description = "门店DTO")
public class StoreDTO {

    @ApiModelProperty(value = "城市ID", required = true, example = "1")
    @NotNull(message = "城市ID不能为空")
    private Integer cityId;

    @ApiModelProperty(value = "门店名称", required = true, example = "宠物乐园总店")
    @NotBlank(message = "门店名称不能为空")
    @Size(max = 100, message = "门店名称长度不能超过100个字符")
    private String storeName;

    @ApiModelProperty(value = "门店地址", required = true, example = "朝阳区建国路88号")
    @NotBlank(message = "门店地址不能为空")
    @Size(max = 200, message = "门店地址长度不能超过200个字符")
    private String storeAddress;

    @ApiModelProperty(value = "门店电话", required = true, example = "010-12345678")
    @NotBlank(message = "门店电话不能为空")
    @Size(max = 20, message = "门店电话长度不能超过20个字符")
    private String storePhone;

    @ApiModelProperty(value = "营业时间", example = "09:00-21:00", notes = "格式：HH:mm-HH:mm")
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$", message = "营业时间格式不正确，应为HH:mm-HH:mm")
    private String businessHours;
}
