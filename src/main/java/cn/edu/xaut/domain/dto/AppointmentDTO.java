package cn.edu.xaut.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AppointmentDTO {
    private Integer userId;
    private Integer petId;
    private Integer storeId;
    private Integer empId;
    private Date apptTime;
    private String apptStatus;
}