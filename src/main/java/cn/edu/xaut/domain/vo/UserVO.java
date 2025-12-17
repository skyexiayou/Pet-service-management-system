package cn.edu.xaut.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {
    private Integer userId;
    private String userName;
    private String phone;
    private String address;
    private Date registerTime;
    private String email;
}