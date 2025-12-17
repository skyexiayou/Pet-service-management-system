package cn.edu.xaut.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EmployeeDTO {
    private Integer storeId;
    private String empName;
    private String position;
    private String empPhone;
    private Date entryTime;
    private BigDecimal salary;
}