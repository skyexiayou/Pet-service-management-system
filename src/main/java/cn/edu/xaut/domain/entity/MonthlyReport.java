package cn.edu.xaut.domain.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MonthlyReport {
    private Integer reportId;
    private Integer storeId;
    private String reportMonth;
    private Integer serviceCount;
    private BigDecimal orderTotal;
    private BigDecimal fosterTotal;
    private BigDecimal medicalTotal;
    private Integer newUserCount;
    private Date createTime;
    private String remarks;
}