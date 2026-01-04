package com.ipaas.monitoringplstformsys.module;

import com.ipaas.monitoringplstformsys.entities.NoTenBasePojo;
import lombok.Data;

@Data
public class FndConsumers extends NoTenBasePojo {
    private String id;
    private String tenantId;
    private String relationId;
    private String relationTenantId;
    private String consumerCode;
    private String consumerName;
    private String consumerKongCode;
    private String consumerKongId;
    private String consumerDesc;
    private String enableStatus;
    private String deleted;


}
