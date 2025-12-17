package cn.edu.xaut.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    private Integer orderId;
    private Integer apptId;
    private BigDecimal totalAmount;
    private String payStatus;
    private String payMethod;
    private Date payTime;
    private Date orderCreateTime;
}