package com.ipaas.monitoringplstformsys.module.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipaas.monitoringplstformsys.common.request.BasePageQueryReqVo;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class ApiPageReqDto extends BasePageQueryReqVo {

    private String searchType;

    @JsonIgnore
    private String searchSql;

    private String createdBy;

    private List<String> createTypeList = Collections.emptyList();
}
