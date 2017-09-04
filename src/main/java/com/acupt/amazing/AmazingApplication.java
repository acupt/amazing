package com.acupt.amazing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by liujie on 2017/8/12.
 */
@SpringBootApplication(scanBasePackages = "com.acupt")
@EnableJpaRepositories(basePackages = "com.acupt.dao")
@EntityScan(basePackages = "com.acupt.entity")
@ServletComponentScan
public class AmazingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmazingApplication.class, args);
    }
}
