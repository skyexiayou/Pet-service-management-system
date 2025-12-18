package cn.edu.xaut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PetServiceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetServiceManagementApplication.class, args);
        System.out.println("^_^   启动成功   ^_^");
    }

}
