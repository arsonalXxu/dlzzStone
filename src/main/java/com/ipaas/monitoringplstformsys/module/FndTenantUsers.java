package com.ipaas.monitoringplstformsys.module;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ipaas.monitoringplstformsys.entities.NoTenBasePojo;
import lombok.Data;


@Data
@TableName(value = "FND_TENANT_USERS")
public class FndTenantUsers extends NoTenBasePojo {

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 是否禁用状态
     */
    private String enableStatus;

    @TableField(exist = false)
    private String userAccount;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String phone;

    @TableField(exist = false)
    private String email;

    @TableField(exist = false)
    private String headPortrait;

    @TableField(exist = false)
    private String userPassword;

    @TableField(exist = false)
    private String userEnableStatus;
}
