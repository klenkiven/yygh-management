package xyz.klenkiven.yygh.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author ：klenkiven
 * @date ：2021/4/8 18:12
 */
@SpringBootApplication
@ComponentScan(basePackages = "xyz.klenkiven")
public class ServiceHospApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class);
    }

}