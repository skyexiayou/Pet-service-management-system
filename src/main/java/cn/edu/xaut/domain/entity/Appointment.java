package cn.edu.xaut.domain.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Appointment {
    private Integer apptId;
    private Integer userId;
    private Integer petId;
    private Integer storeId;
    private Integer empId;
    private Date apptTime;
    private String status;
    private Date createTime;
}
