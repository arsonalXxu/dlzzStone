package com.ipaas.monitoringplstformsys.module.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiOwnerChildDTO {

    private String factory;                  // 工厂
    private List<String> businessOwners;     // 业务负责人
    private List<String> technicalOwners;    // 技术负责人
}
