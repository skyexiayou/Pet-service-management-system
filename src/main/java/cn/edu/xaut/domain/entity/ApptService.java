package cn.edu.xaut.domain.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApptService {
    private Integer relId;
    private Integer apptId;
    private Integer serviceId;
    private Integer quantity;
    private BigDecimal subtotal;
}