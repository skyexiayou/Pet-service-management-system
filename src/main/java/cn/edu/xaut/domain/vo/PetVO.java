package cn.edu.xaut.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class PetVO {
    private Integer petId;
    private String petName;
    private String breed;
    private String gender;
    private Date birthDate;
    private String vaccineStatus;
    private String remarks;
    private UserVO user;
}