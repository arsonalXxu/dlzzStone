package com.ipaas.monitoringplstformsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ipaas.monitoringplstformsys", "com.definesys.deipaas.template"})
@EnableScheduling
public class MonitoringPlstformSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringPlstformSysApplication.class, args);
    }

}
