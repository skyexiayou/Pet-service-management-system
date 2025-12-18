package cn.edu.xaut.domain.vo.order;

import cn.edu.xaut.domain.vo.appointment.BeautyDetailVO;
import cn.edu.xaut.domain.vo.appointment.FosterDetailVO;
import cn.edu.xaut.domain.vo.appointment.MedicalDetailVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单详情VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "OrderDetailVO", description = "订单详情")
public class OrderDetailVO {

    // 订单基本信息
    @ApiModelProperty(value = "订单ID", example = "1")
    private Integer orderId;

    @ApiModelProperty(value = "预约ID", example = "1")
    private Integer apptId;

    @ApiModelProperty(value = "订单总金额", example = "500.00")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "支付状态", example = "已支付")
    private String payStatus;

    @ApiModelProperty(value = "支付方式", example = "WECHAT")
    private String payMethod;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "支付时间", example = "2025-12-18 14:30:00")
    private Date payTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "订单创建时间", example = "2025-12-18 14:00:00")
    private Date orderCreateTime;

    // 服务明细
    @ApiModelProperty(value = "美容服务明细")
    private List<BeautyDetailVO> beautyServices;

    @ApiModelProperty(value = "寄养服务明细")
    private FosterDetailVO fosterService;

    @ApiModelProperty(value = "医疗服务明细")
    private MedicalDetailVO medicalService;

    // 用品明细
    @ApiModelProperty(value = "用品明细")
    private List<ProductDetailVO> products;
}
