package cn.edu.xaut.domain.dto.store;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * 门店信息DTO
 * @date 2025.12.17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "门店信息DTO")
public class StoreDTO {

    @Schema(description = "城市ID", required = true, example = "1")
    @NotNull(message = "城市ID不能为空")
    private Integer cityId;

    @Schema(description = "门店名称", required = true, example = "宠物乐园")
    @NotNull(message = "门店名称不能为空")
    private String storeName;

    @Schema(description = "门店地址", example = "北京市朝阳区建国路88号")
    private String storeAddress;

    @Schema(description = "门店电话", example = "010-12345678")
    private String storePhone;

    @Schema(description = "营业时间", example = "09:00-21:00")
    private String businessHours;
}