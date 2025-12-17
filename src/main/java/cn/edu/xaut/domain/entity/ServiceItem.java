package cn.edu.xaut.domain.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ServiceItem {
    private Integer serviceId;
    private String serviceName;
    private String serviceType;
    private BigDecimal price;
    private Integer duration;
    private String serviceRemarks;
}