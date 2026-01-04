package com.ipaas.monitoringplstformsys.module.vo;

import com.ipaas.monitoringplstformsys.module.ApiOwnerInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiResVo {
    private String id;
    private String apiCode;
    private String apiName;
    private String categoryId;
    private String categoryCode;
    private String categoryName;
    private List<String> businessOwners = new ArrayList<>();
    private List<String> technicalOwners = new ArrayList<>();
    private List<ApiOwnerInfo> children = new ArrayList<>();
}
