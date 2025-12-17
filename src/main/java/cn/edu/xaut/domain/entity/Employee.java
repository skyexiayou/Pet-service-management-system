package cn.edu.xaut.domain.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Employee {
    private Integer empId;
    private Integer storeId;
    private String empName;
    private String position;
    private String empPhone;
    private Date entryTime;
    private BigDecimal salary;
}
