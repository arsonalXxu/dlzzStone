package com.ipaas.monitoringplstformsys.module;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ipaas.monitoringplstformsys.entities.NoTenBasePojo;
import lombok.Data;


@Data
@TableName(value = "FND_TENANT_ADMINS")
public class FndTenantAdmins extends NoTenBasePojo {

    /**
     * 主键     primary key
     */
    @TableId
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 租户
     */
    private String tenantId;


}
