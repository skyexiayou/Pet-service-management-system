package cn.edu.xaut.domain.entity;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer userId;
    private String userName;
    private String phone;
    private String address;
    private Date registerTime;
    private String email;
}
