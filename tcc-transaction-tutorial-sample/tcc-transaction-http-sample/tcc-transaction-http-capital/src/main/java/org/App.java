package org;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *运行入口：main方法
 * 1.Spring Boot内置了一个Tomcat容器
 */


@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@ComponentScan(basePackages={"org.mengyun.tcctransaction"})
public class App  {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(App.class);
        application.setBannerMode(Mode.OFF);
        application.run(args);
    }

}
