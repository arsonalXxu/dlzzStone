package com.ipaas.monitoringplstformsys.module;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ipaas.monitoringplstformsys.entities.NoTenBasePojo;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "API_CATEGORIES")
public class ApiCategories extends NoTenBasePojo {


    private String id;

    private String categoryCode;

    private String categoryName;

    private String categoryDesc;

    /**
     * 禁用状态
     */
    private String enableStatus;

    private String groupCode;

    /**
     * 分类类型
     */
    private String categoryType;

    private String categoryGroupId;


    /**
     * 代理透传前缀
     */
    private String proxyPrefix;

    /**
     * 应用系统管理员
     */
    private String adminUsers;

    private String sourceId;

    String owner;


    String createdBy;


    String lastUpdatedBy;


    Date creationDate;


    Date lastUpdateDate;


    Integer objectVersionNumber;


    String tenantId;


    String userOperationId;
}
