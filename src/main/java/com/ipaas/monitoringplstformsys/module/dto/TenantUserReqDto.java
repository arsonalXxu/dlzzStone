package com.ipaas.monitoringplstformsys.module.dto;

import com.ipaas.monitoringplstformsys.common.request.BaseQueryRequest;
import lombok.Data;

@Data
public class TenantUserReqDto extends BaseQueryRequest {

    private String tenantId;

    private String userId;

    @Override
    public void validator() {

    }
}