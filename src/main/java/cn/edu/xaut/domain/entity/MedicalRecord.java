package cn.edu.xaut.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MedicalRecord {
    private Integer medicalId;
    private Integer petId;
    private Integer empId;
    private Integer storeId;
    private Date medicalTime;
    private String diagnosis;
    private String medication;
    private BigDecimal medicalFee;
    private String followUpAdvice;
}