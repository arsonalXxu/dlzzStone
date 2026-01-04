package com.ipaas.monitoringplstformsys.module.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiOwnerDTO {
    private String id;
    private String apiCode;
    private List<String> businessOwners; // 业务负责人ID列表
    private List<String> technicalOwners; // 技术负责人ID列表

    // children:[{factory:"123", businessOwners:[], technicalOnwers:[]},....]
    private List<ApiOwnerChildDTO> children = new ArrayList<>();

}
