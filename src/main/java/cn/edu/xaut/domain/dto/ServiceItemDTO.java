package cn.edu.xaut.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceItemDTO {
    private String serviceName;
    private String serviceType;
    private BigDecimal price;
    private Integer duration;
    private String serviceRemarks;
}