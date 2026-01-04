package com.ipaas.monitoringplstformsys.module;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ipaas.monitoringplstformsys.entities.NoTenBasePojo;
import lombok.Data;

@Data
@TableName(value = "api_user_relation")
public class ApiUserRelation extends NoTenBasePojo {
    private String id;
    private String apiId;
    private String apiCode;
    private String userAccount;
    private String roleType;
    // 增加工厂
    private String factory;
}
