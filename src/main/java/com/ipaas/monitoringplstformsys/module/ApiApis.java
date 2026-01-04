package com.ipaas.monitoringplstformsys.module;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ipaas.monitoringplstformsys.common.annotation.DataFrom;
import com.ipaas.monitoringplstformsys.entities.NoTenBasePojo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: 李绍刚
 * @since: 2021/6/1
 * @history: 2021/6/1 created by 李绍刚
 */

@Data
@TableName(value = "API_APIS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiApis extends NoTenBasePojo {

    @TableField(exist = false)
    public static final String FIELD_ID = "ID";
    @TableField(exist = false)
    public static final String FIELD_API_NAME = "API_NAME";
    @TableField(exist = false)
    public static final String FIELD_TENANT_ID = "TENANT_ID";
    @TableField(exist = false)
    public static final String FIELD_CREATION_DATE = "CREATION_DATE";
    @TableField(exist = false)
    public static final String FIELD_CREATION_NAME = "CREATION_NAME";
    @TableField(exist = false)
    public static final String FIELD_API_CODE = "API_CODE";
    @TableField(exist = false)
    public static final String FIELD_CATEGORY_NAME = "CATEGORY_NAME";
    @TableField(exist = false)
    public static final String FIELD_API_PORTAL_STATUS = "API_PORTAL_STATUS";
    @TableField(exist = false)
    public static final String FIELD_API_DESC = "API_DESC";

    /**
     * 主键
     */
    @TableId
    private String id;

    private String innerAclId;
    private String aclId;

    private String accessControl;

    /**
     * api编码
     */
    private String apiCode;

    /**
     * api名称
     */
    private String apiName;

    /**
     * api描述
     */
    private String apiDesc;

    /**
     * api分类id
     */
    private String categoryId;

    /**
     * api协议(http/https)
     */
    private String apiProtocol;

    /**
     * api请求方式(post/get)
     */
    private String apiMethod;

    private String apiPath;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 创建类型(defineConfig/importFile/apiFlow)
     */
    private String createType;

    @TableField(exist = false)
    @DataFrom(column = "value", table = "FND_LOV_VALUE", where = "code=#createType")
    private String value;
    /**
     * api类别编码
     */
    @TableField(exist = false)
    @DataFrom(column = "category_code", table = "API_CATEGORIES", where = "id=#categoryId")
    private String categoryCode;

    /**
     * api类别名称
     */
    @TableField(exist = false)
    @DataFrom(column = "category_name", table = "API_CATEGORIES", where = "id=#categoryId")
    private String categoryName;

    /**
     * api分类名称
     */
    @TableField(exist = false)
    private String classificationName;

    @TableField(exist = false)
    private String classificationId;

    @TableField(exist = false)
    private String hostName;

    /**
     * 此API是否订阅，1为是，其余为否
     */
    @TableField(exist = false)
    private Integer isNotSubscribe;

    /**
     * api图标
     */
    private String apiIcon;

    /**
     * 业务关键字启用状态
     */
    private String businessKeywordStatus;

    /**
     * 数据脱敏状态
     * ENABLED / DISABLED
     */
    private String dataDesensitizationStatus;
    /**
     * 脱敏json串
     */
    private String dataDesensitizationRuleJson;
    /**
     * 代理型api关联upstream
     */
    private String upstreamCode;
    /**
     * api多级分类编码
     */
    private String classificationCode;
    /**
     * api_version 的 config_type 触发方式
     */
    @TableField(exist = false)
    private String configTypeMeaning;

    @TableField(exist = false)
    @DataFrom(column = "tenant_name", table = "FND_TENANTS", where = "id=#tenantId")
    private String tenantName;

    @TableField(exist = false)
    private String logoUrl;
    /**
     * API自定义图标文本
     */
    private String apiIconText;
    /**
     * API自定义图标颜色
     */
    private String apiIconColor;
    /**
     * api内容
     */
    private String apiContent;
    /**
     * 创建人
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 创建人头像
     */
    @TableField(exist = false)
    private String headPortrait;
    /**
     * api市场状态
     */
    private String apiMarketStatus;
    /**
     * api门户状态
     */
    private String apiPortalStatus;
    /**
     * 租户门户上架状态
     */
    private String tenantPortalStatus;
    /**
     * 是否允许重试配置
     */
    private String retryStatus;

    private String openStatus;

    /**
     * 应用场景id
     */
    @TableField(exist = false)
    private String appSceneId;
    /**
     * 应用场景编码
     */
    @TableField(exist = false)
    private String appSceneCode;
    /**
     * 应用场景名称
     */
    @TableField(exist = false)
    private String appSceneName;
    /**
     * 应用场景&API关联关系id
     */
    @TableField(exist = false)
    private String appSceneRelationId;

    /**
     * 环境名称
     */
    @TableField(exist = false)
    private String envName;

    @TableField(exist = false)
    private String envId;

}
