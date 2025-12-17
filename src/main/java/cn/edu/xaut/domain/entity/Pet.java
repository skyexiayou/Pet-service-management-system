package cn.edu.xaut.domain.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Pet {
    private Integer petId;
    private Integer userId;
    private String petName;
    private String breed;
    private String gender;
    private Date birthDate;
    private String vaccineStatus;
    private String remarks;
}
