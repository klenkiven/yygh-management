package xyz.klenkiven.yygh.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户服务程序
 * @author klenkiven
 */
@SpringBootApplication
@ComponentScan(basePackages = {"xyz.klenkiven"})
@EnableFeignClients(basePackages = {"xyz.klenkiven"})
public class ServiceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class);
    }
}
