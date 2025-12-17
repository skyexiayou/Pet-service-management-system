package cn.edu.xaut.domain.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class FosterRecord {
    private Integer fosterId;
    private Integer petId;
    private Integer storeId;
    private Integer empId;
    private Date startDate;
    private Date endDate;
    private BigDecimal fosterFee;
    private String fosterStatus;
    private String fosterRemarks;
    private String dailyStatus;
}