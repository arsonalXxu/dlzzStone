package com.ipaas.monitoringplstformsys.module;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiOwnerInfo {
    private String factory;
    private List<String> businessOwners = new ArrayList<>();
    private List<String> technicalOwners = new ArrayList<>();
}
