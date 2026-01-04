package com.ipaas.monitoringplstformsys.common.constant;


import com.ipaas.monitoringplstformsys.common.exception.base.BaseExceptionEnumInterface;
import com.ipaas.monitoringplstformsys.config.InternationalizationConfig;

public enum DeipaasExceptionEnum implements BaseExceptionEnumInterface {

    USER_NOT_BUY_THIS_SERVICE("7001","你尚未购买此服务，故不能评论"),

    DATABASE_FAIL("500", "{0}"),

    COMMON_NOT_NULL("COMM_1000", "{0}不能为空，请确认！"),
    COMMON_LENGTH_MIN("COMM_1001", "{0}超过最小长度限制，请确认！"),
    COMMON_LENGTH_MAX("COMM_1002", "{0}超过长度限制，请确认！"),
    COMMON_LENGTH_MESSAGE_MIN("COMM_1001", "{0}"),
    COMMON_LENGTH_MESSAGE_MAX("COMM_1002", "{0}"),

    // 用户管理
    // =================================================================
    USER_IS_NOT_EXIST("50001", "用户不存在"),
    USER_NAME_EMPTY("50001", "名称为空"),
    USER_ACCOUNT_EMPTY("50001", "账号为空"),
    USER_PWD_EMPTY("50001", "密码为空"),
    USER_SSO_ERROR("USER_SSO_ERROR", "{0}"),
    USER_SSO_INTERFACE_ERROR("50001", "单点登录接口地址出现异常，请检查"),
    USER_NOT_SYNC("50001", "用户尚未同步"),

    USER_ACCOUNT_IS_APPLY("50001", "该账号正在审批中"),
    USER_ACCOUNT_IS_REFUSED("50001", "账号不存在"),

    PHONE_ALREADY_EXISTED("50001", "该手机号已被其他用户注册"),
    PHONE_MODIFY_ERROR("50001", "手机号修改失败，请重新修改"),
    EMAIL_MODIFY_ERROR("50001", "邮箱修改失败，请重新修改"),

    PHONE_ERROR("50001", "手机号未绑定或重复绑定"),
    EMAIL_ALREADY_EXISTED("50001", "该邮箱已被其他用户注册"),

    PHONE_ALREADY_REGISTERED("50001", "该用户绑定的手机号已被其他用户使用，请修改后重新提交申请"),

    EMAIL_ALREADY_REGISTERED("50001", "该用户绑定的邮箱已被其他用户使用，请修改后重新提交申请"),

    USER_IS_DISABLE("50003", "该用户已被禁用，请联系系统管理员"),

    USER_PASSWORD_IS_WRONG("60001", "用户名或密码错误"),

    USER_IS_NOT_SYSADMIN("60001", "仅超级管理员登录"),

    SYSTEM_PERMISSION_DENIED("4000", "权限不足,请联系系统管理员"),
    INVALID_EMAIL_AUTH("50001", "保存失败，请先完善邮件服务器的配置"),
    INVALID_SMS_AUTH("50001", "保存失败，请先完善短信服务器的配置"),
    INVALID_SMS_OR_EMAIL_AUTH("50001", "保存失败，请先完善短信和邮件服务器的配置"),
    CANNOT_BE_CHANGED("50001", "已被注册为内部用户，不可更改"),

    USER_NOT_LOGIN("50001", "用户未登录，请登录再做操作"),
    LOGIN_AND_TRY_AGAIN("50001", "请登录后重试"),
    // 文件上传
    FILE_NAME_IS_INVALID("2004", "文件名称异常"),
    FILE_PATH_IS_INVALID("FILE_PATH_IS_INVALID", "文件路径不合法"),
    WSDL_PATH_IS_INVALID("WSDL_PATH_IS_INVALID", "wsdl地址不合法，需要以「?wsdl」结尾"),
    FILE_FORMAT_IS_INVALID("2005", "文件格式错误, 仅支持「{0}」类型"),
    JAVA_COMPILE_CONTENT_IS_INVALID("JAVA_COMPILE_CONTENT_IS_INVALID", "「{0}」文件使用了「{1}」，存在安全隐患，请检查"),
    // 平台管理
    PLAT_FORM_NAME_EMPTY("1", "平台名称为空"),
    TENANT_NAME_EMPTY("1", "租户名称为空"),
    TENANT_HOST_EMPTY("1", "租户域名为空"),
    TENANT_NAME_REPEAT("1", "租户名称重复"),
    TENANT_ADMIN("1", "租户管理员不能移出"),

    TENANT_ID_EMPTY("1", "租户id为空"),

    // 用户管理
    PWD_LENGTH_LESS("1", "密码长度不足"),
    PWD_LENGTH_LESS_MESSAGE("PWD_LENGTH_LESS_MESSAGE", "密码长度不足，请输入至少{0}位密码"),
    PWD_INVALID("PWD_INVALID", "密码不合法，需要包含「{0}」"),
    TENANT_ACCOUNT_REPEAT("1", "账号重复"),
    HMAC_AUTH_ACCOUNT_REPEAT("1", "账号在当前网关配置中重复，请重新设置"),
    COMPANY_ACCOUNT_REPEAT("1", "该公司已被注册"),
    TENANT_ACCOUNT_CONTAINS_BLANK("1", "用户账号包含空格"),

    //菜单管理
    MENU_NO_EXIT("500040", "菜单不存在"),
    MENU_CODE_IS_EXIT("500040", "菜单编码已经存在"),
    MENU_NO_DISABLE("500041", "菜单不能被禁用"),
    MENU_SYSTEM_NO_DELETE("500042", "菜单不能被删除"),


    //API分类
    CATEGORY_CODE_EMPTY("1", "应用系统编码为空"),
    CATEGORY_NAME_EMPTY("1", "应用系统名称为空"),
    CATEGORY_NAME_REPEAT("1", "应用系统名称重复"),
    CATEGORY_CODE_REPEAT("1", "应用系统编码重复"),
    CATEGORY_NOT_EXIST("500010", "该api应用系统不存在"),
    CATEGORY_EXIST("500010", "该api应用系统已存在"),
    CATEGORY_DISABLED("500010", "该api应用系统已被禁用"),
    SEARCH_EXIST("1", "查询器名称重复"),
    SEARCH_EMPTY("1", "查询器名称至少需要输入一位非空格字符"),

    //常用模板
    TEM_NAME_REPEAT("1", "模板名称重复"),
    TEM_CODE_REPEAT("1", "模板编码重复"),


    //角色
    ROLE_NAME_EMPTY("1", "角色名称为空"),
    ROLE_NAME_REPEAT("1", "角色名称重复"),
    ROLE_USER_EXIST("1", "用户已存在"),

    //消费者
    CONSUMER_NAME_EMPTY("1", "消费者名称为空"),
    CONSUMER_NAME_REPEAT("1", "消费者名称重复"),
    CONSUMER_CODE_EMPTY("1", "消费者编码为空"),
    CONSUMER_CODE_REPEAT("1", "消费者编码重复"),
    CONSUMER_USER_EMPTYT("1", "消费者用户ID为空"),

    CATEGORY_USER_EMPTYT("1", "应用系统用户ID为空"),

    //值列表
    LOV_TYPE_ERROR("1", "参数有误"),
    LOV_TYPE_CODE_REPEAT("1", "该code已存在"),

    //API需求管理
    API_DEMAND_NOT_EXIST("500040", "该数据已被删除，请刷新列表"),
    DEMAND_PAGE_NOT_EXIST("500020", "没有相应的API需求"),
    //API设计
    API_DESIGN_NOT_EXIST("500041", "该API设计不存在，请刷新页面"),
    SOAP_TENANT_API("1", "此API来自其他租户，无法解析，请重新选择！"),
    //   请求头规范
    API_REQA_NOT_EXIT("5000007", "该请求头规范不存在，请刷新页面"),
    APIREQH_NAME_REPEAT("1", "Header名称重复"),
    NOT_DELETE_TRACEID("50008", "traceId为系统默认日志追踪字段，不支持删除"),
    // API管理
    API_VERSION_IS_NOT_EXIST("50000", "该API版本已经不存在"),
    API_TEMPLATES_SAVED_ERROR("5000008", "生成API,非第一次暂存,请检查"),
    API_NOT_EXIST("5000008", "该API不存在，请刷新页面"),
    API_NOT_EXIST_IN_API_STORE("5000008", "APIStore不存在该API"),
    MK_API_NOT_EXIST("5000008", "当前API已被公开市场引用，请联系市场管理员处理后重试"),
    CONSUMER_NOT_EXIST("5000008", "该用户消费者不存在"),
    API_ROUTE_CODE_NOT_EXIST("5000008", "该API路由信息不存在，请先上架或查询改网关路由信息是否正确"),
    API_CODE_IS_EXIST("500007", "API编码不能重复"),
    API_PROXY_CODE_IS_EXIST("500007", "第{0}行的API编码重复"),
    API_PATH_IS_EXIST("500007", "该应用系统下已存在此path"),
    API_PROXY_PATH_IS_EXIST("500007", "第{0}行的Path重复"),
    API_NAME_IS_EXIST("500007", "API名称不能重复"),
    API_NAME_IS_REPEAT("500007", "API名称重复，请检查"),
    API_NAME_CONTAIN_BLANK("500007", "API名称不能含有空格"),
    API_PROXY_NAME_IS_EXIST("500007", "第{0}行的API名称重复"),
    API_COUNT_LIMIT("50007", "该租户API总数已超出限制，无法继续创建API，请删除部分API或联系管理员提高限额。"),
    TEMP_API_VERSION_IS_EXIST("500009", "已存在API版本，无法暂存"),
    SAVE_API_VERSION_IS_EXIST("500009", "已存在API版本，无法保存"),
    API_HAS_RUNNING_VERSION("500010", "该API已存在{0}个版本正在运行，不允许删除"),
    VERSION_ID_ERROR("500012", "请传入正确的版本号"),
    API_AUTH_REPEAT("1", "此api已授权给该消费者"),
    VERSION_ID_EMPTY("500001", "版本id为必填项"),
    VERSION_NOT_EXIST("500002", "该api版本不存在"),
    AI_DEPLOY_ERROR("500012", "AI API 暂不支持发布至此环境"),
    VERSION_IS_TEMP_STATUS("500002", "该api版本是暂存状态，不可发布！"),
    VERSION_NOT_RUNNING("500003", "该api版本未部署运行"),
    VERSION_IS_PUBLIC_UN_DEPLOY("500003", "该版本属于公开版本，暂时无法下线，请先下架"),
    VERSION_IS_RUNNING("500003", "该api版本已部署运行"),
    VERSION_CAN_NOT_DELETE("500004", "该版本属于公开版本，暂时无法删除，请先下架"),
    VERSION_IS_PUBLIC("500005", "该版本已是公开状态"),
    VERSION_IS_PROTECTED("500006", "该版本已是私密状态"),
    VERSION_IS_SAVED("500007", "该版本已保存"),
    METHOD_IDENTITY_ERROR("4000002", "数据校验异常"),
    API_DEPLOY_VERSION_ERROR("500008", "API发布时出现错误"),
    API_GENERATE_CONFIG_XML_ERROR("500008", "生成API配置文件时出现错误"),
    API_VERSION_COVER_NUM_ILLEGAL("500009", "该版本已存在或不合法，不能覆盖！"),
    API_IMPORT_FILE_ISNULL("500010", "导入文件不能为空"),
    API_IMPORT_FILE_ERROR("500010", "文件解析错误"),
    API_DATA_CHANNEL_NO_SUPPORT_ERROR("500010", "所选API不满足{0}要求，请检查后重试"),

    API_VERSION_IS_RUNNING("500011", "操作失败，原API已发布，不允许删除"),
    ENV_REPEAT_ERROR("500010", "关联环境不能重复"),

    RAISE_ERROR_NAME_IS_EXIST("500009", "抛出异常节点type不能重复，请重新设置！"),
    RAISE_ERROR_TYPE_IS_EXIST("500009", "抛出异常节点的异常类型必须是编排中不存在的类型，支持自定义！"),
    API_PROXY_TYPE_CAN_NOT_SAVE("50000011", "已存在保存版本，不支持暂存"),
    VERSION_NOT_IS_EMPTY("500002", "api版本参数为空"),
    MK_DOCUMENT_NOT_IS_EXIST("500002", "该API版本的接口文档不存在"),
    MK_AUTHORITY_VALID_DATE("500002", "当前日期已失效，请选择有效日期"),
    // 服务编排
    REQEUST_BODY_WRONG_FORMAT("500011", "请求body格式错误"),
    INTERNAL_EXCEPTION("500000", "服务器不给力，请稍后再试"),
    API_LAYOUT_CONFIG_ERROR("500011", "编排数据错误"),
    API_SQL_ERROR("500012", "SQL错误，请输入正确的SQL"),
    DB_CONFIG_ERROR("500013", "数据库连接信息不可用，请检查"),

    PROCEDURE_ERROR("500013", "请输入3个或3个以上查询字符"),

    DB_ERROR("500013", "不合法的数据库连接，请修改连接信息"),
    API_CONFIG_TYPE_NULL("500014", "编排类型为空或为非法类型，请检查！"),
    API_VERSION_CONFIG_TYPE_ENV_IS_RUNNING("500015", "该编排类型已存在运行版本{0}，不允许多版本发布，请确认！"),
    API_VERSION_CONFIG_TYPE_IS_RUNNING("500016", "该编排类型在{0}环境存在运行版本，不允许多版本发布，请确认！"),
    DEPLOY_EXCEPTION("500000", "发布失败！原因：{0}"),
    DEPLOY_SYNC_EXTERNAL_SERVICE_EXCEPTION("500000", "同步编排服务失败！原因：{0}"),
    DATA_CHANNEL_SUBSCRIBER_RETRY_EXCEPTION("500000", "重试失败！原因：{0}"),
    DEPLOY_DEV_NOT_EXIST_HTTP_CONNECT("500000", "HTTP连接器中没有配置部署环境相关的信息，请检查！"),
    DEPLOY_ENV_NOT_EXIST_DATASOURCE("500000", "配置部署环境没有数据源相关的信息，请检查！"),
    // 环境
    ENV_NOT_EXIST("500013", "该环境不存在"),
    ENV_NAME_REPEAT("1", "环境名称重复"),
    GW_NAME_REPEAT("1", "网关名称重复"),
    ENV_CODE_REPEAT("1", "环境编码重复"),
    GW_ADMIN_URL_REPEAT("1", "网关管理地址已存在"),
    GW_REPEAT("1", "该租户环境下已存在此网关地址，请勿重复添加"),

    GW_VERSION_REPEAT("1", "与已有网关节点版本不一致，请检查"),

    ENV_FORMAL_EXIST("1", "当前租户已存在正式环境，不可重复创建"),
    ENV_EXIST_RUNNING_API("1", "当前环境存在API运行版本，无法禁用"),
    GW_SERVERS_NOT_EXIST("500014", "服务器不存在，请检查配置！"),
    ENV_NOT_FOUND("500013", "未找到{0}的代理地址，请检查"),
    //测试MQ连接
    MQ_CONFIG_NOTNULL_ERROR("MQ_CONFIG_NOTNULL_ERROR", "MQ连接信息不能为空，请检查"),
    MQ_IP_OR_HOST_ERROR("MQ_IP_OR_HOST_ERROR", "无法连接到{0}，请检查"),
    MQ_NOT_CONFIG_ERROR("MQ_NOT_CONFIG_ERROR", "当前环境未配置关联的消息队列，请检查"),
    ROCKETMQ_ACCESS_KEY_SECRET_KEY_ERROR("ROCKETMQ_ACCESS_KEY_SECRET_KEY_ERROR", "ACCESS_KEY OR SECRET_KEY ERROR，请检查"),
    //数据源
    DS_NAME_REPEAT("1", "数据源名称重复"),
    DS_CODE_REPEAT("1", "数据源编码重复"),
    DS_CONFiG_REPEAT("1", "环境不允许重复"),
    DS_CONFiG_ORACLE_ERROR("1", "Oracle的Instance Name和Service Name必填一个"),
    DS_CONFiG_NOT_EXIST("1", "数据源不存在"),
    CUSTOM_RESOURCE_CONFiG_ERROR("1", "配置数据源JSON错误，请确认！"),
    CUSTOM_RESOURCE_CONFiG_ENV_IS_EMPTY("1", "配置数据源环境配置为空，请确认！"),
    CUSTOM_RESOURCE_CONFiG_ENV_NOT_EXIST("1", "配置数据源未配置需要部署的环境配置，请确认！"),
    DB_DRIVER_NOT_FOUND("1", "数据库驱动未找到，请确认！"),
    // json解析
    JSON_ERROR("500020", "JSON格式错误"),

    //网关
    KEY_AUTH_NOT_USED("400011", "操作失败，请先手动开启插件管理KeyAuth开关"),
    ROUTE_KEY_AUTH_NOT_USED("400011", "操作失败，请先在路由上添加有效的授权认证插件配置"),
    MONITOR_PORT_ERROR("400012", "监控端口填写错误，请检查"),
    CREATE_SERVICE_ERROR("500011", "kong:创建service失败"),
    WEIGHT_EMPTY("1", "权重为空"),
    CREATE_GROUP_ERROR("500011", "kong:创建服务器组失败"),
    ADD_SERVER_ERROR("500011", "kong:添加服务器失败"),
    ADD_CONSUMER_ERROR("500011", "kong:添加消费者失败"),
    ADD_KEY_ERROR("500011", "kong:创建key-auth失败"),
    IP_EMPTY_ERROR("500011", "IP白名单和IP黑名单不能均为空"),
    CONFIG_REPEAT_ERROR("500011", "此对象已存在该插件配置，请检查"),
    ADD_BASIC_AUTH_ERROR("500011", "kong:创建basic-auth失败"),
    SERVICE_NOT_FOUND("500011", "不存在相应service，请检查"),
    CONSUMER_NOT_FOUND("500011", "kong:不存在相应consumer，请检查"),
    KONG_NOT_FOUND("500011", "kong:未同步到kong，请检查"),
    //    KONG_SYN_FAIL("500011", "kong同步失败，管理地址：{0}，请检查"),
    KONG_SYN_FAIL("500011", "网关服务不可用，操作失败，请检查"),
    KONG_ERROR("500014", "网关操作失败，{0}"),
    NODE_HAS_EXIST("500014", "该租户环境已存在此节点配置，请勿重复添加"),
    JWT_KEY_UNIQUE("500014", "Key在当前网关配置中重复，请重新设置"),
    JWT_INVALID_PUBLIC_KEY_ERROR("500014", "公钥格式错误，请输入PEM格式"),
    OAUTH2_KEY_UNIQUE("500014", "client_id在当前网关配置中重复，请重新设置"),
    KONG_ADMIN_FAIL("500011", "网关管理地址不可用，请检查"),
    KONG_URL_FAIL("500011", "不合法的网关连接，请修改连接信息"),
    ENV_KONG_ADMIN_FAIL("500011", "{0}环境网关管理地址不可用，请检查"),
    ROUTE_NAME_REPEAT("1", "路由名称重复"),
    ROUTE_PATH_REPEAT("1", "路由path重复"),
    GROUP_NAME_REPEAT("1", "服务器组名称重复"),
    SERVER_REPEAT("1", "服务器已存在"),
    CREATE_ROUTE_ERROR("500011", "kong:创建路由失败"),
    CERTIFICATE_NAME_REPEAT("1", "证书名称重复"),
    CERTIFICATE_CODE_REPEAT("1", "证书编码重复"),
    CERTIFICATE_DATE_OVERDUE("1", "证书已过期，请上传有效的证书"),
    CERTIFICATE_CONTENT_ERROR("1", "不合法的证书"),
    CERTIFICATE_DOMAIN_NAME_REPEAT("1", "域名重复，该环境下已有相同域名的证书"),
    ADD_CERTIFICATE_ERROR("500011", "添加失败，请检查证书合法性"),
    UPDATE_ROUTE_ERROR("500011", "kong:修改路由失败"),
    GW_CONFIG_NULL("500012", "网关配置为空，无法同步到网关"),
    ALLOW_EXIST("500013", "已有白名单配置，不允许添加黑名单"),
    DENY_EXIST("500013", "已有黑名单配置，不允许添加白名单"),
    DEFAULT_ROUTE("500011", "默认路由不可删除"),
    NO_HMAC("500014", "此消费者没有数字签名密钥"),
    PLUGIN_NOT_FOUND("500011", "不存在相应plugin，请检查"),
    PLUGIN_DISABLED("500011", "插件已关闭，请联系平台管理员"),
    PLUGIN_EXIT("500011", "此对象已存在该插件配置，请检查"),
    PROXY_NOT_PASS("500011", "代理型API暂不支持此操作"),
    TYPE_NOT_NULL("500011", "{0}类型不可为空，请检查"),
    GRAY_SERVICE_GROUP_NOT_FOUND("500011", "未找到灰度upstream，请检查"),
    GRAY_DEFAULT_UPSTREAM_NOT_FOUND("500011", "未找到应用对象对应Upstream，请检查"),
    CONSUMER_NOT_FOUND_WITH_NAME("500011", "kong:不存在消费者 {0} 相应consumerId，请检查"),
    PROXY_API_PATH_ERROR("500011", "代理地址格式错误"),
    PROXY_API_PATH_PROTOCOL_TYPE_DIFFERENT("500011", "同一环境下的协议类型不一致，请检查"),

    //验证码
    SEND_NOTICE_FAIL("80000", "发送失败"),
    NO_PHONE_FAIL("80000", "未配置短信服务器，请联系管理员"),
    NO_EMAIL_FAIL("80000", "未配置邮件服务器，请联系管理员"),
    NO_WECHAT_FAIL("80000", "未配置企业微信服务器，请联系管理员"),
    NO_DING_FAIL("80000", "未配置钉钉服务器，请联系管理员"),
    NO_FEISHU_FAIL("80000", "未配置飞书服务器，请联系管理员"),
    VERIFICATIONCODE_EMPTY("50008", "验证码不能为空"),
    CREATE_TEMPLATE_FAIL("80000", "模板生成失败"),
    REGISTER_PHONE_EXIST("50010", "该手机号已被注册"),
    REGISTER_EMAIL_EXIST("50010", "该邮箱已被注册"),
    VERIFICATION_CODE_INVALID("50010", "最近60s内已发送，请稍后重试"),
    REGISTER_FAIL("50010", "注册失败"),
    INVALID_USER("60000", "用户不存在"),
    TOKEN_IS_NULL("60002", "token不存在"),
    TOKEN_IS_INVALID("60003", "登录token已过期"),

    VERIFICATION_CODE_IS_INVALID("50005", "验证码失效"),
    VERIFICATION_CODE_IS_ERROR("50005", "验证码错误"),
    VERIFICATION_TYPE_NOT_SUPPORTED("50005", "不支持验证类型「{0}」!"),
    PHONE_VERIFICATION_CODE_IS_INVALID("50005", "手机验证码失效"),
    PHONE_VERIFICATION_CODE_IS_FAIL_INVALID("50005", "手机验证码错误次数过多，请重新获取"),
    PHONE_VERIFICATION_CODE_IS_ERROR("50005", "手机验证码错误"),
    VERIFICATION_CODE_OR_USERNAME_IS_ERROR("50005", "用户名或验证码错误"),
    CAPTCHA_VERIFICATION_CODE_IS_ERROR("50005", "图形验证码错误"),

    CAPTCHA_VERIFICATION_CODE_IS_INVALID("50005", "图形验证码失效"),
    EMAIL_VERIFICATION_CODE_IS_INVALID("50005", "邮箱验证码失效"),
    EMAIL_VERIFICATION_CODE_IS_ERROR("50005", "邮箱验证码错误"),
    PHONE_IS_BIND("50005", "该手机号已被绑定"),
    EMAIL_IS_BIND("50005", "该邮箱已被绑定"),
    PHONE_MISMATCH("50005", "手机号与账号不匹配！"),
    EMAIL_MISMATCH("50005", "邮箱与账号不匹配！"),
    VERIFICATION_FAILED("50005", "账号不存在或无法通过所选方式进行验证"),

    EMAIL_IS_DUPLICATE("60004", "邮箱重复"),
    PHONE_IS_DUPLICATE("60005", "手机号重复"),
    EMAIL_EMPTY("50007", "邮箱不能为空，请确认"),
    PPHONE_EMPTY("50007", "手机号不能为空，请确认"),
    TOKEN_IS_ERROR("60006", "token异常"),
    OLD_PWD_EMPTY("50015", "原密码不能为空"),
    NEW_PWD_EMPTY("50016", "新密码不能为空"),
    NEW_PWD_INVALID("NEW_PWD_INVALID", "新密码解密异常"),
    PWD_DECRYPT_INVALID("PWD_INVALID", "密码解密异常"),
    USER_PASSWORD_IS_EXPIRE("60002", "当前密码已超出有效期，请及时修改密码"),
    PARAM_CHECK_IS_ERROR("50017", "参数校验异常！"),
    OLD_PWD_ERROR("50016", "原密码错误"),
    EMAIL_CONFIG_ERROR("50017", "邮件服务器配置信息不全，请检查！"),
    PHONE_CONFIG_ERROR("50019", "短信服务器配置信息不全，请检查！"),
    PHONE_CALL_ERROR("PHONE_CALL_ERROR", "短信接口调用异常，请联系管理员，错误信息：{0}"),
    WECHAT_CONFIG_ERROR("50016", "企业微信配置信息不全，请检查！"),
    DING_CONFIG_ERROR("50016", "钉钉配置信息不全，请检查！"),
    FEISHU_CONFIG_ERROR("50016", "飞书配置信息不全，请检查！"),

    //Api市场
    MK_API_PARAMETER_EMPTY("500012", "参数为空，请联系管理员"),
    MK_API_ENV_NO_EXIST("500012", "当前租户无正式环境，请联系管理员"),
    MK_API_INVOKE_APPLY_REPEAT("500012", "审批中，请勿重复申请"),
    MK_API_IS_APPROVED("500012", "该消费者已有调用权限，无需申请"),
    MK_API_PORTAL_NOT_OPEN("500012", "门户授权未打开，无法申请调用"),
    MK_API_APPLY_REPEAT("500012", "已审批，请勿重复审批"),
    MK_INSUFFICIENT_POINTS("5000000", "金额不足，购买失败"),
    PACKAGES_ARE_ASSOCIATED_WITH_APIS("5000000", "该套餐已被API引用，不可删除"),
    API_NO_CHANGE_PLAN("500000", "该API不存在该套餐"),

    EXIST_OTHER_PACKAGES("500000", "已存在其他计费类型的消费记录，不能购买此类型套餐"),
    SERVICE_NO_CHANGE_PLAN("500000", "该服务不存在该套餐"),
    //审批至最后节点时权限因其他原因变更 直接返回操作成功
    MK_API_SUCCESS("500012", "操作成功"),
    MK_API_NO_EXIST("500012", "API无公开信息"),
    //服务器资源
    API_SERVER_RESOURCES_NAME_REPEAT("500013", "服务器名称不能重复"),
    API_SERVER_RESOURCES_HOST_REPEAT("500013", "服务器地址不能重复"),

    //平台/系统自开发资源
    FILE_EXIST("1", "当前文件名与同一用途下已有文件名重复，请修改后重新上传"),
    CODE_EXIST("1", "资源编码不能重复"),
    NAME_CONTAIN_CHINESE("1", "自开发资源文件名不允许含有中文"),

    NAME_EXIST("1", "资源名称不能重复"),
    FILE_NOT_EXIST("500015", "文件不存在"),
    FILE_ALREADY_EXIST("500015", "与同一文件夹下已有文件夹/文件名称重复，请检查"),
    FILE_NOT_ALLOW_MODIFY("500015", "此文件不允许操作"),
    FILE_NOT_ALLOW_TEMPLATE("500015", "项目名称不允许为默认名称，请修改"),
    FILE_NAME_IS_EMPTY("FILE_NAME_IS_EMPTY", "项目名称不允许为空，请检查"),
    FILE_NAME_NORM_ERROR("FILE_NAME_NORM_ERROR", "项目名称不合法，请输入字母、数字、下划线组合的编码"),
    FILE_NAME_NOT_ALLOW("500015", "文件名称仅支持字母、数字、下划线或.和-"),
    FILE_NAME_LENGTH_LIMIT("500015", "文件/文件夹名称长度限制200字符"),

    //es
    SEARCH_FAIL("50044", "查询失败"),
    ALIA_IS_NOT_EXIST("50044", "暂无归档日志数据！"),
    ES_CONFIG_ERROR("500", "elasticsearch配置异常，请检查！"),
    DURATION_ERROR("50044", "查询量级超出ES上限，建议缩短查询时间"),

    //自定义组件
    CUSTOM_CONNECTOR_CODE_REPEAT("500017", "组件编码重复"),
    CUSTOM_CONNECTOR_NAME_REPEAT("500017", "组件名称重复"),
    CUSTOM_CONNECTOR_NAME_AND_CODE_REPEAT("500017", "className和组件名称不能同时重复"),
    CUSTOM_CONNECTOR_CLASSNAME_REPEAT("500017", "className不能重复"),
    //编排类型管理
    CONFIG_TYPES_CODE_REPEAT("500017", "编排类型编码不能重复"),
    CONFIG_TYPES_NAME_REPEAT("500017", "编排类型名称不能重复"),
    //编排模板管理
    CONFIG_TEMPLATES_CODE_REPEAT("500017", "编排模板编码不能重复"),
    CONFIG_TEMPLATES_NAME_REPEAT("500017", "编排模板名称不能重复"),
    //资源管理
    RESOURCE_TYPE_CODE_REPEAT("500017", "配置资源编码不能重复"),
    RESOURCE_TYPE_NAME_REPEAT("500017", "配置资源名称不能重复"),
    //自定义组件
    RESOURCE_CONFIG_CODE_REPEAT("500017", "该类型下配置资源编码重复"),
    RESOURCE_CONFIG_NAME_REPEAT("500017", "该类型下配置资源名称重复"),
    RESOURCE_CONFIG_REPEAT("500017", "连接信息环境不可重复"),
    RESOURCE_CONFIG_TABLE_NULL("500017", "表信息为空，请检查！"),
    RESOURCE_CONFIG_DATASOURCE_NULL("500017", "数据源信息为空，请检查！"),
    RESOURCE_NOT_CHOOSE("500017", "请先选择数据源！"),
    APP_ID_NOT_CHOOSE("500017", "请先选择应用！"),
    //定时任务
    DATE_MORE_THAN_YEAR("50018", "定时任务最大支持未来一年内的时间，请重新设定时间"),
    TASK_STATUS_IS_ING("50018", "原任务已在执行中，无法编辑"),
    TASK_STATUS_IS_FINISH("50018", "原任务已执行完成，无法编辑"),
    //忘记密码
    NO_PHONE("500017", "该账号未绑定手机号"),
    NO_EMAIL("500017", "该账号未绑定邮箱"),
    NO_BIND_PHONE("500017", "未绑定手机号，请先完成绑定"),
    NO_BIND_EMAIL("500017", "未绑定邮箱，请先完成绑定"),
    //申请租户
    TENANT_USER_IS_EXIST("500017", "当前用户已在此租户内"),
    APPLY_JOIN_TENANT_REPEAT("500017", "申请正在处理中，请勿重复申请"),
    HEADER_NOT_ALLOW("500014", "{0}为关键字，不允许配置为headers"),
    //API标签
    API_TAG_NAME_REPEAT("500019", "标签名称不能重复"),
    //服务器资源
    SERVER_RESOURCE_CONNECT_ERROR("500", "服务器无法连接，请检查连接信息并确保已完成连接授权"),
    //审计日志
    AUDIT_LOGS_NOT_OPEN("500020", "该节点没有开启日志设置"),
    AUDIT_LOGS_JSON_ONT_EXIST("500020", "配置丢失，请联系管理员"),
    //重试
    DEBUGGING_SOAP_BODY_TYPE_NOT_XML("500020", "SOAP协议请求的body请选择xml格式"),
    //错误示例重试
    RETRY_INSTANCE_EXCEPTION("500020", "当前实例异常，不可重试"),
    RETRY_INSTANCE_NOT_EXIST("500020", "实例不存在，请重试"),
    RETRY_INSTANCE_IS_SUCCESS("500020", "该实例已重试成功，不可继续重试"),
    RETRY_API_VERSION_IS_ONT_EXIST("500020", "对应API版本不存在，不可重试"),
    //API函数
    API_FUNCTION_NAME_REPEAT("500017", "函数名称不能重复"),
    //连接器api code为空
    API_CONNECTOR_CODE__EMPTY("500021", "参数为空，请联系管理员"),
    //日志模板列表名称重复
    LOG_LIST_TEMPLATE_NAME_REPEAT("500022", "列表名称重复"),
    //预警时间
    STRATEGY_START_TIME_CURRENT("500021", "预警开始时间必须大于当前时间"),
    STRATEGY_START_TIME_END("500021", "预警结束时间必须大于开始时间"),
    STRATEGY_NOTICE_TYPE_EMPTY("500021", "预警通知方式不能为空, 请检查"),
    STRATEGY_NOTICE_API_URL_EMPTY("500021", "预警通知方式为自定义外部接口, 外部接口地址不能为空, 请检查"),
    STRATEGY_NOTICE_API_URL_WRONG_FORMAT("500021", "外部接口地址格式错误, 请检查"),
    STRATEGY_NOTICE_API_URL_LENGTH_ERROR("500021", "外部接口地址长度不能超过{0}, 请检查"),
    STRATEGY_NOTICE_API_URL_EDIT_ERROR("500021", "预警通知方式为自定义外部接口且选择租户定义时, 外部接口地址不能编辑, 请检查"),
    STRATEGY_PLUGIN_ERROR("500021", "{0}已启用API预警插件，请关闭后重试"),
    STRATEGY_DISABLED_ERROR("500021", "当前租户存在已启用的预警策略，请禁用后重试"),
    CALL_LINK_TRACING_PLUGIN_ERROR("500021", "{0}已启用调用链路追踪插件，请关闭后重试"),
    // 异常知识库
    EXCEPTION_CODE_REPEAT("500022", "异常码重复"),
    EXCEPTION_NAME_REPEAT("500022", "异常名称重复"),
    EXCEPTION_ID_EMPTY("500020", "参数ID为空"),
    EXCEPTION_NOT_EXIST("500020", "异常知识库不存在"),
    //审批流程
    PROCESS_CODE_REPEAT("500022", "流程编码重复"),
    PROCESS_NODE_NOT_ENOUGH("500021", "流程节点不够"),
    PROCESS_ID_NOT_EXIST("500020", "流程ID为空"),
    PROCESS_NAME_NOT_EXIST("500020", "流程名称至少需要输入一位非空格字符"),
    NODE_ID_NOT_EXIST("500020", "节点ID为空"),
    NODE_NAME_REPEAT("500022", "节点名称重复"),
    RELATED_PROCESS_EXIST("500020", "该流程存在关联业务，无法{0}"),
    // 授权码申请
    RELATED_PROCESS_IS_OFF("500020", "环境关联流程未开启，不可申请"),
    DEADLINE_TIME_OUT("500020", "当前时间已超过授权码过期时间，请检查"),
    //标准规范维护
    SHARE_URL_CLOSE("500020", "此标准规范已关闭分享链接，请联系管理员"),
    PAGE_NOT_EXIST("500020", "没有相应的标准规范页"),
    CANT_DISABLE("500020", "该标准规范为平台默认数据，不支持禁用"),
    CANT_DELETE("500020", "该标准规范为平台默认数据，不支持删除"),
    //快速开发管理
    API_FAST_CODE_REPEAT("500022", "快速开发编码重复"),
    API_FAST_NAME_REPEAT("500022", "快速开发名称重复"),
    ID_NOT_EXIST("500020", "ID为空"),
    // 产品软件授权信息
    PRODUCT_NAME_REPEAT("500022", "产品名称重复"),

    // pom文件解析
    POM_CONTENT_ERROR("500019", "pom文件内容错误"),
    POM_CONTENT_VERSION_ERROR("500019", "pom文件内容错误,version不存在"),
    POM_CONTENT_ARTIFACT_ID_ERROR("500019", "pom文件内容错误,artifactId不存在"),
    POM_CONTENT_GROUP_ID_ERROR("500019", "pom文件内容错误,groupId不存在"),

    //市场分类
    API_CLASS_NAME_EMPTY("50001", "市场分类名称不能为空，请检查"),
    API_CLASS_NAME_REPEAT("50001", "市场分类名称重复，请检查"),
    //公开市场-用户组管理
    USER_GROUP_NAME_EMPTY("50001", "用户组名称不能为空,请检查"),
    USER_GROUP_NAME_REPEAT("50001", "用户组名称重复，请检查"),
    USER_FAVORITE_REPEAT("50001", "请勿重复收藏"),
    USER_UN_FAVORITE_REPEAT("50001", "请勿重复取消收藏"),
    //公开市场
    API_MARKET_CONFIG_NOT_WHITESPACE("50001", "访问地址至少输入一个非空字符"),
    API_IS_LOADED_SHELVES("50000", "此版本在公开市场已存在，不能删除"),
    //归档
    ARCHIVE_TYPE_ERROR("50021", "归档类型错误"),
    API_DOCUMENT_IS_PUBLIC_ERROR("500055", "公开申请API暂不支持定时调度和监听类型"),
    API_AUTH_DISABLED("500055", "请启用认证策略和授权策略中消费者授权功能后重试"),

    //业务状态
    BIZ_STATE_NULL_ERROR("500022", "业务状态名称不能为空，请确认！"),
    BIZ_STATE_REPETITION_ERROR("500022", "业务状态名称重复"),
    /**
     * 请输入租户编码
     */
    TENANT_CODE_IS_NOT_NULL("TENANT_CODE_IS_NOT_NULL", "请输入租户编码"),
    /**
     * 租户编码已存在
     */
    TENANT_CODE_IS_EXIST("TENANT_CODE_IS_EXIST", "租户编码已存在"),
    /**
     * 租户编码长度最多200字符
     */
    TENANT_CODE_LENGTH_ERROR("TENANT_CODE_LENGTH_ERROR", "租户编码长度最多200字符"),
    /**
     * 租户预警通知接口必须以"HTTP://"或"HTTPS://"开头
     */
    TENANT_EARLY_WARNING_ADDRESS_FORMAT_ERROR("500039", "租户预警通知接口格式错误, 请检查"),
    /**
     * 租户预警通知接口不能为NULL
     */
    TENANT_EARLY_WARNING_ADDRESS_NOT_NULL_ERROR("500039", "租户预警地址长度不能为NULL, 请检查"),
    /**
     * 请输入字母、数字、下划线组合的编码
     */
    TENANT_CODE_NORM_ERROR("TENANT_CODE_NORM_ERROR", "请输入字母、数字、下划线组合的编码"),
    BIZ_STATE_INFO_REPETITION_ERROR("500022", "业务状态重复"),

    // 快速开发模板
    FAST_TEMP_DATA_NULL("500030", "未找到对应的快速开发模板！"),
    FAST_TEMP_PARAM_NULL("500031", "传入参数为空"),
    FAST_TEMP_TEMP_NULL("500032", "模板数据为空，请检查！"),
    FAST_CONFIG_NULL("500033", "快速开发参数为空，请检查！"),
    OPERATION_ERROR("500034", "快速开发操作类型有误，请检查！"),
    FAST_CODE_NULL("500035", "模板找不到快速开发编码，请检查！"),
    PARAM_NOT_FOUND("500036", "未找到对应的参数结构！"),
    /**
     * 请填写插入/更新字段
     */
    INSERT_OR_UPDATE("INSERT_OR_UPDATE", "操作类型含插入或更新,需选择「插入/更新字段」"),

    UPDATE_FAILE("UPDATE_FAILE", "更新数据库失败！"),

    /**
     * 调试相关
     */
    DEBUGGING_ERROR("500023", "单步调试失败，请重新发布保证配置为最新版本！"),
    DEBUGGING_STEP_TOO_LONG("500023", "单步调试目前最大支持一万步！"),
    DEBUGGING_INSTANCE_ONLY_KONG("500023", "单步调试失败，请检查网关配置！"),

    //授权码校验
    API_AUTHORIZATION_CODE_ERROR("500023", "当前授权码不满足{0}在{1}的{2}权限，请检查"),
    API_AUTHORIZATION_EXIST("500023", "你已获取{0}在{1}的{2}操作权限,请勿重复申请"),
    API_AUTHORIZATION_PENDING("500023", "{0}在{1}的{2}操作权限正在申请中,请勿重复申请"),
    API_AUTHORIZATION_NULL("500023", "该租户在{0}已开启API运维审批，请输入授权码"),
    API_AUTHORIZATION_TIMEOUT("500023", "{0}已超过授权码过期时间，无法继续操作"),
    API_APPROVE_FAILED("500023", "该待办关联API:{0}已被删除，无法进行同意操作"),
    API_APPROVE_STATUS_FAILED("500023", "该待办已被处理完成"),
    API_APPROVE_INFO_FAILED("510023", "API已被删除或环境异常，请检查后重试"),
    DATA_CHANNEL_APPROVE_INFO_FAILED("510023", "数据频道已被删除或环境异常，请检查后重试"),
    DATA_CHANNEL_CONSUMER_REPEAT("510023","当前消费者已获取授权，请勿重复申请"),
    DATA_CHANNEL_CONSUMER_APPLY("510023","当前消费者申请信息正在处理，请勿重复申请"),

    DATA_CHANNEL_CONSUMER_HANDLE("510023","当前消费者变更信息正在处理，请勿重复申请"),

    PORTAL_AUTHORIZATION_IS_NOT_OPEN("500023", "门户授权未打开，无法申请"),

    DATA_CHANNEL_DELETE_ERROR("500023", "请先删除所有订阅者"),

    DATA_DISTRIBUTION_DISABLED("500023", "数据分发插件已关闭，请先开启"),

    PUBLISH_DATA_CHANNEL_CONNECTION_ERROR("500023", "插件中心未配置所选环境的MQ资源，请联系管理员"),

    PUBLISH_DATA_CHANNEL_DATASOURCE_ERROR("500023", "请先配置数据源"),


    SUBSCRIBER_API_ADDRESS_EMPTY("500023", "请先维护接口信息"),
    API_VERSION_APPROVE_FAILED("500023", "该待办关联API:{0}版本已被下线，无法进行同意操作"),
    // 应用连接器审批有误
    DEV_CENTER_MANAGER_APPROVE_ERROR("500023", "审批类型错误，请重试"),
    //并行组件
    BRANCHES_NUM_ERROR("500024", "{0}：并行分支数量不能小于2，请检查！"),
    BRANCHES_CONTENT_ERROR("500024", "{0}：并行分支子节点不能为空，请检查！"),

    SERVICE_LOGPATH_ERROR("500036", "连接失败，请重试"),
    TOTAL_LENGTH_MAX("500037", "日志文件路径总长度不能超过{0}字符"),
    QUERY_MAX_EXCEED_ERROR("500039", "查询量级超出ES上限，建议缩短查询时间"),

    //微服务相关
    CONFIG_NOT_EXIST("500038", "请先完成微服务集成配置"),
    LAST_CONFIG("500038", "该配置为当前租户最后一条配置信息，请先关闭插件"),
    ENV_CONFIG_EXIST("500038", "该环境下已存在微服务集成配置，请勿重复添加"),
    ENV_CONFIG_ERROR("500038", "不合法的微服务集成连接，请修改连接信息"),
    ENV_LOGGING_ERROR("500038", "调用logging服务失败，原因：{0}"),

    //apaas插件相关
    CONFIG_CONNECTION_NOT_EXIST("500050", "请先完成得帆云aPaaS连接配置"),

    //MDM插件相关
    MDM_CONFIG_CONNECTION_NOT_EXIST("500050", "请先完成得帆云MDM连接配置"),

    //openApi开发平台
    OPEN_API_INTERFACE_IS_IS_DISABLE("500000", "开放平台API插件未开启"),

    //添加控制台部署应用配置信息判断
    APP_CONNECT_CONFIG_NOT_EXIST("500053", "该部署应用配置信息不存在"),
    APP_CONNECT_CONFIG_APPNAME("500051", "该名称与同一类型下已有数据重复，请检查"),
    APP_CONNECT_CONFIG_PORT("500052", "地址：端口号信息与同一类型下已有数据重复，请检查"),
    APP_CONNECT_CONFIG_ISEXIT("500057", "部署应用名称至少需要输入一位非空格字符"),

    LIMIT_CHARACTER("LIMIT_CHARACTER", "参数名限制为200字符！"),
    /**
     * 数字框不能为空
     */
    NUMBER_BOX_ERROR("NUMBER_BOX_ERROR", "数字框不能为空"),
    /**
     * 数字框不能超过15位
     */
    NUMBER_BIT_ERROR("NUMBER_BIT_ERROR", "数字框不能超过15位"),
    /**
     * 数字框应为正整数
     */
    NUMBER_INTEGER_ERROR("NUMBER_INTEGER_ERROR", "数字框应为正整数"),
    /**
     * 数字框范围错误
     */
    NUMBER_RANGE_ERROR("NUMBER_RANGE_ERROR", "数字框范围错误"),
    /**
     * 脱敏状态不能为空
     */
    DESENSITIZATION_STATUS_CANNOT_BE_NULL("DESENSITIZATION_STATUS_CANNOT_BE_NULL", "脱敏状态不能为空"),

    /**
     * 没有在%s服务组中找到%s环境的服务器信息，请检查
     */
    NOT_GRAYSCALE_SERVER("NOT_GRAYSCALE_SERVER", "没有在{0}中找到「{1}」环境的服务器信息，请检查"),
    /**
     * 该API没有有效的灰度发布插件配置，请检查
     */
    NOT_EFFECTIVE_GRAYSCALE_PLUGIN("NOT_EFFECTIVE_GRAYSCALE_PLUGIN", "该API没有有效的灰度发布插件配置，请检查"),
    /**
     * API_IS_RUN
     */
    API_IS_RUN("API_IS_RUN", "该API已在「{0}」{1}中运行，请检查"),
    API_IS_DEPLOY("API_IS_RUN", "该API正在「{0}」环境的{1}类型中部署，请稍候"),

    APPLICATION_SYSTEM_MANAGER_IS_NOT_ERROR("APPLICATION_SYSTEM_MANAGER_IS_NOT_ERROR", "请选择应用系统负责人"),

    WSDL_PARSE_ERROR("500038", "获取地址失败，请输入URL！"),
    OPENAPI_FILE_ERROR("OPENAPI_FILE_ERROR", "文件解析异常，请检查文件内容"),


    /**
     * 代码示例授权认证方式不存在
     */
    AUTHORIZATION_TYPE_NOT_EXIST("500038", "授权认证类型不存在"),

    /**
     * 页面表格字段显示顺序、冻结和数据排序： 未找到表格默认配置
     */
    TABLE_DEFAULT_CONFIG_NOT_FOUND("TABLE_DEFAULT_CONFIG_NOT_FOUND", "未找到表格默认配置，请联系系统管理员"),

    API_KEYWORD_NAME_REPEAT("API_KEYWORD_NAME_REPEAT", "同一来源下关键字名称不能重复"),

    /**
     * 请填写插件内容
     */
    PLUGIN_CONTENT_IS_EMPTY("PLUGIN_CONTENT_IS_EMPTY", "请填写插件内容"),
    /**
     * 秘钥不能为空
     */
    KEY_CANNOT_BE_EMPTY("KEY_CANNOT_BE_EMPTY", "秘钥不能为空"),
    /**
     * 参数名不能为空
     */
    PARAMETER_NAME_CANNOT_BE_EMPTY("PARAMETER_NAME_CANNOT_BE_EMPTY", "参数名不能为空"),
    /**
     * 算法不能为空
     */
    ALGORITHM_CANNOT_BE_EMPTY("ALGORITHM_CANNOT_BE_EMPTY", "算法不能为空"),

    /**
     * api导出pdf文档
     */
    API_EXPORT_DOCUMENT_PDF_ERROR("500000", "导出pdf文档失败，请联系管理员"),
    /**
     * api导出word文档
     */
    API_EXPORT_DOCUMENT_WORD_ERROR("500000", "导出word文档失败，请联系管理员"),

    API_EXPORT_DOCUMENT_FONT_LOAD_ERROR("500000", "字体文件加载失败，导出pdf文档失败！"),

    /**
     * 登陆失败五次，账号锁定10分钟，请稍后再试
     */
    ACCOUNT_LOCK_LOGIN("ACCOUNT_LOCK_LOGIN", "连续错误{0}次，账号禁止登录{1}min"),
    // 定时任务相关
    POWER_JOB_CONFIG_ENV_REPEAT_ERROR("500051", "该环境已添加过定时任务配置信息，请检查"),

    POWER_JOB_CONFIG_USER_NAME_REPEAT_ERROR("500052", "该账号已绑定过定时任务配置信息，请检查"),
    POWER_JOB_TASK_CODE_REPEAT_ERROR("500054", "该任务编码已存在，请检查"),
    POWER_JOB_NO_ENV_ERROR("POWER_JOB_NO_ENV_ERROR", "没有可用环境，请检查"),

    /**
     * 单点登录参数校验
     */
    SSO_TOKEN_END_POINT_ERROR("500038", "Token地址请求错误，请联系管理员"),
    SSO_TOKEN_INTROSPECTION_ENDPOINT_ERROR("500038", "Token验证地址请求错误，请联系管理员"),
    SSO_SIGN_VERIFICATION_SECRET_KEY_ERROR("500038", "签验密钥无效，请联系管理员"),
    SSO_CAS_SERVER_VALIDATE_URL_ERROR("500038", "ST校验地址请求错误，请联系管理员"),
    CONSOLE_LOG_ERROR("CONSOLE_LOG_ERROR", "连接失败： {0}，请检查"),
    LOGIN_ONLY_SYSADMIN("LOGIN_ONLY_SYSADMIN","该地址仅超级管理员登录"),
    /**
     * 分类编码重复，请检查
     */
    THE_CLASSIFICATION_CODE_ALREADY_EXISTS("THE_CLASSIFICATION_CODE_ALREADY_EXISTS", "分类编码重复，请检查"),
    /**
     * 上级分类不能为自己或者自己下级分类
     */
    A_PARENT_CLASS_CANNOT_BE_ITSELF_OR_A_SUBCLASS("A_PARENT_CLASS_CANNOT_BE_ITSELF_OR_A_SUBCLASS", "上级分类不能为自己或者自己下级分类"),
    /**
     * 该分类存在下级分类，无法删除
     */
    THIS_CATEGORY_HAS_SUB_CATEGORIES_AND_CANNOT_BE_DELETED("THIS_CATEGORY_HAS_SUB_CATEGORIES_AND_CANNOT_BE_DELETED", "该分类存在下级分类，无法删除"),
    /**
     * 该分类不存在
     */
    THE_CATEGORY_DOES_NOT_EXIST("THE_CATEGORY_DOES_NOT_EXIST", "该分类不存在"),
    /**
     * 该分类存在关联API，无法删除
     */
    THERE_IS_AN_ASSOCIATED_API_FOR_THIS_CATEGORY_AND_CANNOT_BE_DELETED("THERE_IS_AN_ASSOCIATED_API_FOR_THIS_CATEGORY_AND_CANNOT_BE_DELETED", "该分类存在关联API，无法删除"),
    /**
     * 该上级分类不存在
     */
    THE_PARENT_CATEGORY_DOES_NOT_EXIST("THE_PARENT_CATEGORY_DOES_NOT_EXIST", "该上级分类不存在"),
    /**
     * 该上级分类是禁用状态
     */
    THE_PARENT_CATEGORY_IS_DISABLED("THE_PARENT_CATEGORY_IS_DISABLED", "该上级分类是禁用状态"),
    /**
     * 平台只支持二级分类
     */
    THE_PLATFORM_PARENT_CATEGORY_HASPARENT("THE_PLATFORM_PARENT_CATEGORY_HASPARENT", "平台仅支持至二级分类"),

    /**
     * 产品未检测到合法的license授权，请检查
     */
    THE_PRODUCT_DOES_NOT_DETECT_A_VALID_LICENSE_AUTHORIZATION_PLEASE_CHECK("THE_PRODUCT_DOES_NOT_DETECT_A_VALID_LICENSE_AUTHORIZATION_PLEASE_CHECK", "产品未检测到合法的license授权，请检查"),
    /**
     * 授权文件已过期，请检查
     */
    THE_AUTHORIZATION_FILE_HAS_EXPIRED_PLEASE_CHECK("THE_AUTHORIZATION_FILE_HAS_EXPIRED_PLEASE_CHECK", "授权文件已过期，请检查"),
    /**
     * 该授权已上传，请勿重复操作
     */
    THE_AUTHORIZATION_HAS_BEEN_UPLOADED_PLEASE_DO_NOT_REPEAT_THE_OPERATION("THE_AUTHORIZATION_HAS_BEEN_UPLOADED_PLEASE_DO_NOT_REPEAT_THE_OPERATION", "该授权已上传，请勿重复操作"),
    /**
     * 授权信息已过期，请检查
     */
    AUTHORIZATION_INFORMATION_HAS_EXPIRED_PLEASE_CHECK("AUTHORIZATION_INFORMATION_HAS_EXPIRED_PLEASE_CHECK", "授权信息已过期，请检查"),
    CPU_CORE_NUM_EXCEEDS_LICENSE_LIMIT("CPU_CORE_NUM_EXCEEDS_LICENSE_LIMIT", "CPU核心数已超过license额度限制，请重新设置或购买额度"),
    API_NUM_EXCEEDS_LICENSE_LIMIT("API_NUM_EXCEEDS_LICENSE_LIMIT", "API数量已超过license额度限制，请重新设置或购买额度"),

    /**
     * API文档
     */
    API_DOCUMENT_IS_RESOLVE_ERROR("500055", "API文档暂不支持定时调度和监听类型"),
    API_REQ_DOCUMENT_NAME_IS_EXIST("500056", "请求示例名称已存在"),
    API_RESP_DOCUMENT_NAME_IS_EXIST("500056", "响应示例名称已存在"),
    API_EXAMPLE_NAME_IS_EMPTY("500056", "示例名称不能为空"),
    API_MARKET_STATUS("API_MARKET_STATUS", "该API已被下架"),
    SERVICE_MARKET_STATUS("SERVICE_MARKET_STATUS", "该服务已被下架"),
    API_MARKET_PLUGIN_DISABLED("API_MARKET_PLUGIN_DISABLED", "未开启API公开市场插件，请检查"),
    API_MARKET_PLUGIN_CONFIG_NOT_EXIST("API_MARKET_PLUGIN_CONFIG_NOT_EXIST", "API公开市场插件配置信息不存在，请检查"),
    API_MARKET_CONFIG_TYPE_ERROR("API_MARKET_CONFIG_TYPE_ERROR", "仅支持HTTP接口、WebService接口上架，请检查当前版本接口类型"),
    API_MARKET_DELETE_ERROR("API_MARKET_CONFIG_TYPE_ERROR", "当前API存在已上架版本，暂时无法删除，请先下架"),

    PLATFORM_API_MARKET_OPEN_ERROR("500", "配置信息校验未通过，请检查配置信息后重试！"),

    AUTHORITY_REPEAT("AUTHORITY_REPEAT", "重复申请"),
    API_MARKET_VERSION_DEPLOY("API_MARKET_VERSION_DEPLOY", "该版本已上架，暂时无法删除，请先下架"),
    /**
     * 创建快速开发JSON格式出错
     */
    FAST_JSON_TRANSITION_ERROR("JSON_TRANSITION_ERROR", "请输入正确的JSON格式"),

    //错误码规范相关
    ERROR_CODE_NAME_REPEAT_ERROR("ERROR_CODE_NAME_REPEAT_ERROR", "错误码名称重复"),
    ERROR_CODE_REPEAT_ERROR("ERROR_CODE_REPEAT_ERROR", "错误码重复"),

    //错误码映射相关
    ERROR_CODE_MAPPING_CONDITIONS_ID_ERROR("500055", "映射条件Id不存在"),
    GATEWAY_PLUGIN_NOT_EXIST_ERROR("500055", "操作失败，该网关不存在插件“{0}”"),

    //测试ES连接
    ES_IP_OR_HOST_ERROR("ES_IP_OR_HOST_ERROR", "{0}连接信息存在异常，请检查"),
    ES_CONNECT_NOTNULL_ERROR("ES_LOG_NOTNULL_ERROR", "日志存储库连接信息不能为空，请检查"),
    ES_CONNECT_INFO_WRONG("ES_BODY_NOTNULL_ERROR", "{0}不是合法的日志存储库连接，请检查"),
    ES_HTTP_ERROR("ES_HTTP_ERROR", "第{0}条，只支持HTTP请求，请检查"),
    ES_ENV_ID_NOT_NULL("ES_ENV_ID_NOT_NULL", "「{0}」没有配置日志存储库信息，请前往环境管理菜单"),
    ES_ENV_NOT_EXIST("ES_ENV_ID_NOT_NULL", "没有配置环境信息，请前往环境管理菜单"),
    ES_INDEX_NOT_EXIST("ES_INDEX_NOT_EXIST", "请初始化索引！"),
    ES_AUTHORIZATION_ERROR("ES_AUTHORIZATION_ERROR", "{0}用户名密码不正确，请检查"),
    TRANS_EXCEPTION("500", "数据转换失败"),
    SYSTEM_ERROR("500", "系统出现异常:{0}"),
    SERVER_GROUP_NOT_EXIST("500", "该租户下没有关联的服务器"),
    KONG_LOG_PLUGIN_UPDATE_ERROR("KONG_LOG_PLUGIN_UPDATE_ERROR", "以下网关Service上的日志插件更新失败: {0}，请检查"),

    /**
     * 配置资源
     */
    NONE_RESOURCE_CONFIG("NONE_RESOURCE_CONFIG", "此类配置资源暂不支持单独发布，可直接在API中引用"),
    RESOURCE_IS_USING("RESOURCE_IS_USING", "该配置资源已被运行中的API引用，无法下线"),
    RESOURCE_IS_USING_NOT_DISABLED("RESOURCE_IS_USING", "该配置资源已被运行中的API引用，无法禁用"),
    RESOURCE_IS_USING_NOT_DELETE("RESOURCE_IS_USING", "该配置资源已被运行中的API引用，无法删除"),
    RESOURCE_IS_USING_NOT_DELETE_EXTERNAL("500", "该配置资源已被使用，无法删除"),
    RESOURCE_TYPE_CANNOT_CREATE("RESOURCE_TYPE_CANNOT_CREATE", "该配置资源不允许创建"),

    /**
     * 平台关闭webhook失败
     */
    PLATFORM_WEBHOOK_DEISABLE_ERROR("500", "{0}存在运行中的Webhook任务，无法关闭"),
    /**
     * 系统关闭webhook失败
     */
    SYSYTEM_WEBHOOK_DEISABLE_ERROR("500", "当前租户存在运行中的Webhook任务，无法关闭"),
    /**
     * 系统webhook插件抽屉测试连接
     */
    SYSTEM_WEBHOOK_TEST_CONNECT_ERROR("500", "「 {0} 」连接信息存在异常，请检查"),
    /**
     * 系统webhook插件抽屉webhook服务连接
     */
    SYSTEM_WEBHOOK_CONNECT_ERROR("500", "「 {0} 」不是合法的Webhook连接，请检查"),
    /**
     * 系统webhook插件抽屉添加配置端口路径重复
     */
    SYSTEM_WEBHOOK_CONGIF_URL_PORT_REPEAT("500", "Webhook连接地址和端口与已有数据重复，请检查"),
    /**
     * 系统webhook插件抽屉添加配置名称重复
     */
    SYSTEM_WEBHOOK_CONGIF_NAME_REPEAT("500", "Webhook连接名称与已有数据重复，请检查"),
    /**
     * 删除webhook，该Webhook正在运行中，无法删除
     */
    WEBHOOK_DELETE_ERROR("500", "该Webhook正在运行中，无法删除"),
    /**
     * 发布webhook,webhook发布败
     */
    WEBHOOK_PUBLISH_FAIL("500", "Webhook发布失败，{0}"),
    /**
     * 发布webhook,webhook发布调用错误
     */
    WEBHOOK_CONFIG_ERROR("500", "Webhook参数错误"),
    /**
     * 下线webhook,webhook下线失败
     */
    WEBHOOK_OFFLINE_FAIL("500", "Webhook下线失败，{0}"),
    WEBHOOK_API_NOT_EXITS("500", "关联api不存在"),
    WEBHOOK_SERVICE_NOT_EXITS("500", "关联应用系统不存在"),
    MICRO_SERVICE_API_DOCUMENT_ERROR("MICRO_SERVICE_API_DOCUMENT_ERROR", "获取失败，请先配置微服务API路径"),
    MICRO_SERVICE_API_DOCUMENT_URI_ERROR("MICRO_SERVICE_API_DOCUMENT_URI_ERROR", "获取失败，微服务API路径配置错误"),

    POWER_JOB_PLUGIN_DISABLE_ERROR("500", "{0}定时调度任务执行中，无法关闭"),
    /**
     * 只支持下载编排型
     */
    ONLY_SUPPORTS_DOWNLOAD_LAYOUT("ONLY_SUPPORTS_DOWNLOAD_LAYOUT", "只支持下载编排型"),
    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_FAILED("FILE_DOWNLOAD_FAILED", "文件下载失败"),
    /**
     * 获取模板错误
     */
    GET_TEMPLATE_ERROR("GET_TEMPLATE_ERROR", "获取模板错误"),
    /**
     * 生成pom文件错误
     */
    GENERATE_POM_FILE_ERROR("GENERATE_POM_FILE_ERROR", "生成pom文件错误"),
    /**
     * 文件夹添加错误
     */
    NEW_FOLDER_ERROR("NEW_FOLDER_ERROR", "文件夹添加错误"),
    /**
     * 平台信息丢失
     */
    PLATFORM_IS_NULL("PLATFORM_IS_NULL", "平台信息丢失,请检查"),
    /**
     * 模板状态不能为空
     */
    TEMPLATE_STATUS_CANNOT_BE_EMPTY("TEMPLATE_STATUS_CANNOT_BE_EMPTY", "模板状态不能为空"),
    /**
     * 模板渲染失败
     */
    TEMPLATE_RENDERING_FAILED("TEMPLATE_RENDERING_FAILED", "模板渲染失败"),
    ES_COLLECT_DATA_ERROR("500", "聚合数据时出错，请检查！"),

    /**
     * 请上传.xls或.xlsx后缀的文件
     */
    XLS_OR_XLSX_FILE("XLS_OR_XLSX_FILE", "请上传.xls或.xlsx后缀的文件"),
    /**
     * 数据不能为空
     */
    DATA_CANNOT_BE_EMPTY("DATA_CANNOT_BE_EMPTY", "数据不能为空"),

    /**
     * 已超过单次导入上限，请检查
     */
    SINGLE_IMPORT_LIMIT_EXCEEDED("SINGLE_IMPORT_LIMIT_EXCEEDED", "已超过单次导入上限，请检查"),
    /**
     * 表头字段无法匹配，请检查
     */
    HEADER_FIELDS_CANNOT_BE_MATCHED("HEADER_FIELDS_CANNOT_BE_MATCHED", "表头字段无法匹配，请检查"),
    /**
     * 该数据已失效
     */
    THE_DATA_HAS_EXPIRED("THE_DATA_HAS_EXPIRED", "该数据已失效"),
    /**
     * 失败数据不能为空
     */
    FAILURE_DATA_CANNOT_BE_EMPTY("FAILURE_DATA_CANNOT_BE_EMPTY", "失败数据不能为空"),
    /**
     * 该系统编码规范不存在
     */
    THE_SYSTEM_ENCODING_RULE_DOES_NOT_EXIST("THE_SYSTEM_ENCODING_RULE_DOES_NOT_EXIST", "该系统编码规范不存在"),
    /**
     * 「XX应用系统1」「XX应用系统2」
     */
    FAIL_ALREADY_EXISTS("FAIL_ALREADY_EXISTS", "应用系统{0}存在已启用的配置，请检查"),
    /**
     * 编码规范不能为空
     */
    ENCODING_RULES_CANNOT_BE_EMPTY("ENCODING_RULES_CANNOT_BE_EMPTY", "编码规范不能为空"),
    /**
     * 编码规范不能为空
     */
    CATEGORY_RULES_CANNOT_BE_EMPTY("CATEGORY_RULES_CANNOT_BE_EMPTY", "该系统未配置编码规范，请前往“规范管理-编码规范”完成配置"),
    /**
     * 请输入值
     */
    PLEASE_INPUT_THE_VALUE("PLEASE_INPUT_THE_VALUE", "请输入值"),
    /**
     * 格式效验
     */
    FORMAT_VALIDATION("FORMAT_VALIDATION", "{0}"),
    /**
     * 启用失败：应用系统xxx已存在配置
     */
    CONFIGURATION_ALREADY_EXISTS("CONFIGURATION_ALREADY_EXISTS", "应用系统{0}存在已启用的配置，请检查"),
    /**
     * id地址格式错误
     */
    IP_ADDR_IS_ERROR("IP_ADDR_IS_ERROR", "ip地址格式有误，请重新输入！"),
    /**
     * url错误
     */
    URL_ERROR("URL_ERROR", "url错误"),
    SAP_CONNECT_ERROR("SAP_CONNECT_ERROR", "不合法的连接信息，请修改后重试@{0}"),
    MQ_CONNECT_ERROR("MQ_CONNECT_ERROR", "{0}@{1}"),

    AI_MODEL_CONNECT_ERROR("AI_MODEL_CONNECT_ERROR", "测试连接失败@{0}"),
    ES_CONNECT_ERROR("ES_CONNECT_ERROR", "不合法的连接信息，请修改后重试@{0}"),
    FTP_CONNECT_ERROR("FTP_CONNECT_ERROR", "{0}@{1}"),
    GET_DATASOURCE_INFO_FAILED("MQ_CONNECT_ERROR", "获取信息错误，请确认数据源正常后重试"),

    CANT_DELETE_APP_CASE_HAVE_OPERATION("500020", "当前分类下存在连接器，无法删除，请先删除子连接器"),

    APP_AUTH_NAME_REPEAT("APP_AUTH_NAME_REPEAT","同一连接器下参数不能重复"),

    APP_CODE_OR_NAME_REPEAT("500017", "连接器编码或名称不能重复"),
    /**
     * 模板渲染异常
     */
    EXCEPTION_OCCURRED_IN_CREATING_CONFIGURATION_RESOURCE_TEMPLATE("EXCEPTION_OCCURRED_IN_CREATING_CONFIGURATION_RESOURCE_TEMPLATE", "模板渲染异常"),
    /**
     * 该连接器不存在
     */
    THE_CONNECTOR_DOES_NOT_EXIST("THE_CONNECTOR_DOES_NOT_EXIST", "该连接器不存在"),
    /**
     * 该事件不存在
     */
    THE_EVENT_DOES_NOT_EXIST("THE_EVENT_DOES_NOT_EXIST","该事件不存在"),
    /**
     * 分组名称重复
     */
    DUPLICATE_GROUP_NAME_IN_CURRENT_CONNECTOR("DUPLICATE_GROUP_NAME_IN_CURRENT_CONNECTOR", "分组名称重复"),
    /**
     * 当前分组内编码重复
     */
    DUPLICATE_CODE_IN_CURRENT_GROUP("DUPLICATE_CODE_IN_CURRENT_GROUP", "当前分组内编码重复"),
    /**
     * 应用连接器操作编码重复
     */
    DUPLICATE_CODE_IN_API_APP("DUPLICATE_CODE_IN_API_APP", "应用连接器操作编码重复"),
    /**
     * {0}编码重复
     */
    DUPLICATE_CODE_IN_API_APP_EVENT("DUPLICATE_CODE_IN_API_APP_EVENT","{0}编码重复"),
    /**
     * 当前分组内名称重复
     */
    DUPLICATE_NAME_IN_CURRENT_GROUP("DUPLICATE_NAME_IN_CURRENT_GROUP", "当前分组内名称重复"),
    /**
     * 当前子连接器内操作名称重复
     */
    DUPLICATE_NAME_IN_CURRENT_OPERATION("DUPLICATE_NAME_IN_CURRENT_OPERATION", "当前子连接器内操作名称重复"),
    /**
     * 当前{0}下存在连接器，无法删除，请先删除子连接器！
     */
    PLEASE_DELETE_THE_SUB_CONNECTOR_FIRST("PLEASE_DELETE_THE_SUB_CONNECTOR_FIRST", "当前{0}下存在连接器，无法删除，请先删除子连接器！"),
    /**
     * 当前子连接器下存在操作，无法删除，请先删除操作！
     */
    PLEASE_DELETE_THE_SUB_CONNECTOR_FIRST_HANDLE("PLEASE_DELETE_THE_SUB_CONNECTOR_FIRST", "当前子连接器下存在操作，无法删除，请先删除操作！"),
    /**
     * 暂无数据源监听配置，请联系租户管理员前往配置资源-监听订阅配置完成添加
     */
    PLEASE_CONTACT_TENANT_ADMIN_TO_ADD_SUBSCRIBER("PLEASE_CONTACT_TENANT_ADMIN_TO_ADD_SUBSCRIBER","暂无数据源监听配置，请联系租户管理员前往配置资源-监听订阅配置完成添加"),
    /**
     * 当前{0}下存在{1}，无法删除，请先删除{2}！
     */
    PLEASE_DELETE_THE_SUB_CONNECTOR_FIRST_EVENT("PLEASE_DELETE_THE_SUB_CONNECTOR_FIRST_EVENT","当前{0}下存在{1}，无法删除，请先删除{2}！"),
    /**
     * FILE_DOWNLOAD_FAILED_401
     */
    FILE_DOWNLOAD_FAILED_401("soapFlowWSDL401", "请输入正确的账号密码"),
    /**
     * 该配置资源不存在
     */
    THE_CONFIGURATION_RESOURCE_DOES_NOT_EXIST("THE_CONFIGURATION_RESOURCE_DOES_NOT_EXIST", "该配置资源不存在"),
    CONFIGURATION_RESOURCE_DOES_NOT_EXIST("THE_CONFIGURATION_RESOURCE_DOES_NOT_EXIST", "该数据源已不存在，请重新选择"),
    /**
     * 该策略类型不存在
     */
    THE_POLICY_TYPE_DOES_NOT_EXIST("THE_POLICY_TYPE_DOES_NOT_EXIST", "该策略类型不存在"),
    /**
     * 该策略不存在
     */
    THE_POLICY_PARAM_DOES_NOT_EXIST("THE_POLICY_PARAM_DOES_NOT_EXIST", "该策略不存在"),
    /**
     * 该策略存在关联应用对象，无法删除
     */
    THERE_ARE_ASSOCIATED_APPLICATION_OBJECTS_FOR_THIS_POLICY("THERE_ARE_ASSOCIATED_APPLICATION_OBJECTS_FOR_THIS_POLICY", "该策略存在关联应用对象，无法删除"),
    /**
     * 消费者冲突
     */
    CONSUMER_DUPLICATION("CONSUMER_DUPLICATION", "「{0}」和「{1}」内部应用消费者重复，请检查"),
    /**
     * 当前API所属应用系统已绑定XX策略，请解绑后重试
     */
    THE_APPLICATION_SYSTEM_TO_WHICH_THE_CURRENT_API_BELONGS_HAS_BOUND_XX_POLICY("THE_APPLICATION_SYSTEM_TO_WHICH_THE_CURRENT_API_BELONGS_HAS_BOUND_XX_POLICY", "当前API所属应用系统已绑定{0}，请解绑后重试"),
    /**
     * 当前应用系统下存在API已单独绑定XX策略，请解绑后重试
     */
    THERE_IS_AN_API_THAT_HAS_BEEN_SEPARATELY_BOUND_TO_THE_XX_POLICY_IN_THE_CURRENT_APPLICATION_SYSTEM("THERE_IS_AN_API_THAT_HAS_BEEN_SEPARATELY_BOUND_TO_THE_XX_POLICY_IN_THE_CURRENT_APPLICATION_SYSTEM", "当前应用系统下存在API已单独绑定{0}，请解绑后重试"),
    HOST_PORT_KONG_ERROR("HOST_PORT_KONG_ERROR", "服务器地址-端口号错误，请检查"),
    MARKET_ENV_NULL("MARKET_ENV_NULL", "市场环境未配置，请检查"),
    MK_API_STAY_ON_NOT("500012", "待上架的API不能启用插件,请先上架"),
    MARKET_SERVER_EXIST("MARKET_SERVER_EXIST", "当前服务器已被租户系统使用，API公开市场需要单独设置"),
    MARKET_GATEWAY_EXIST("MARKET_GATEWAY_EXIST", "当前网关已被租户系统使用，API公开市场需要单独设置"),
    MARKET_API_VERSION_IS_NOT_EXIST("MARKET_API_VERSION_IS_NOT_EXIST", "当前API版本在公开市场不存在"),
    MARKET_API_AUTHORITY_EXPIRED("MARKET_API_AUTHORITY_EXPIRED", "用户对API授权已失效，请检查"),
    MARKET_API_IS_NOT_EXIST("MARKET_API_VERSION_IS_NOT_EXIST", "当前API在公开市场不存在"),
    MARKET_SERVICE_IS_NOT_EXIST("MARKET_API_VERSION_IS_NOT_EXIST", "当前服务在公开市场不存在"),

    BANNER_NAME_REPEAT("BANNER_NAME_REPEAT", "保存失败，该banner名称已存在"),

    SOLUTION_NAME_REPEAT("SOLUTION_NAME_REPEAT", "保存失败，该解决方案名称已存在"),

    POLICY_NEWS_REPEAT("POLICY_NEWS_REPEAT", "保存失败，该政策新闻名称已存在"),

    TRADING_GUIDE_REPEAT("TRADING_GUIDE_REPEAT", "保存失败，该交易指南名称已存在"),


    /**
     * 已被API所属应用系统绑定，无需重复选择
     */
    THIS_POLICY_HAS_BEEN_BOUND_TO_THE_APPLICATION_SYSTEM("THIS_POLICY_HAS_BEEN_BOUND_TO_THE_APPLICATION_SYSTEM", "「{0}」已被API所属应用系统绑定，无需重复选择"),
    /**
     * 当前应用系统已绑定同类型策略「策略名称」，请解绑后重试
     */
    THE_CURRENT_APPLICATION_SYSTEM_IS_ALREADY_BOUND_TO_POLICIES_OF_THE_SAME_TYPE("THE_CURRENT_APPLICATION_SYSTEM_IS_ALREADY_BOUND_TO_POLICIES_OF_THE_SAME_TYPE", "当前应用系统已绑定同类型策略「{0}」，请解绑后重试"),
    /**
     * 请勿重复提交
     */
    PLEASE_DO_NOT_RESUBMIT("PLEASE_DO_NOT_RESUBMIT", "请勿重复提交"),
    CONFIG_ERROR("500099", "不合法的连接信息，请修改后重试@{0}"),
    /**
     * 该编排引擎不存在
     */
    THE_ORCHESTRATION_ENGINE_DOES_NOT_EXIST("THE_ORCHESTRATION_ENGINE_DOES_NOT_EXIST", "该编排引擎不存在"),
    /**
     * 当前API已存在关联灰度策略
     */
    THE_CURRENT_API_ALREADY_HAS_AN_ASSOCIATED_GRAYSCALE_POLICY("THE_CURRENT_API_ALREADY_HAS_AN_ASSOCIATED_GRAYSCALE_POLICY", "当前API已存在关联灰度策略"),
    /**
     * 存在多个使用中的虚拟服务组
     */
    THERE_ARE_MULTIPLE_VIRTUAL_SERVICE_GROUPS_IN_USE("THERE_ARE_MULTIPLE_VIRTUAL_SERVICE_GROUPS_IN_USE", "{0}存在多个使用中的虚拟服务组"),
    /**
     * 版本不在运行中
     */
    HAVE_VERSION_NOT_RUNNING("HAVE_VERSION_NOT_RUNNING", "{0}版本不在运行中"),
    /**
     * 当前版本不在运行中,无法生成Swagger文档
     */
    HAVE_CURRENT_VERSION_NOT_RUNNING("HAVE_VERSION_NOT_RUNNING", "当前{0}版本不在运行中,无法生成Swagger文件"),
    IMPORT_SUM_ZERO_ERROR("IMPORT_SUM_IS_ZERO_ERROR", "选中接口无触发方式为HTTP的运行中的默认版本，未生成下载任务，请检查！"),
    /**
     * 已启用灰度发布插件，请关闭后重试
     */
    GRAYSCALE_PUBLISHING_PLUGIN_ENABLED("GRAYSCALE_PUBLISHING_PLUGIN_ENABLED", "{0}已启用灰度发布插件，请关闭后重试"),
    /**
     * 请先完成灰度编排引擎配置
     */
    PLEASE_COMPLETE_THE_GRAYSCALE_LAYOUT_ENGINE_CONFIGURATION_FIRST("PLEASE_COMPLETE_THE_GRAYSCALE_LAYOUT_ENGINE_CONFIGURATION_FIRST", "请先完成灰度编排引擎配置"),
    /**
     * 当前租户存在已启用的灰度策略，请禁用后重试
     */
    THE_CURRENT_TENANT_HAS_AN_ENABLED_GRAYSCALE_POLICY("THE_CURRENT_TENANT_HAS_AN_ENABLED_GRAYSCALE_POLICY", "当前租户存在已启用的灰度策略，请禁用后重试"),
    /**
     * 当前租户存在自动重试策略的API，请禁用后重试
     */
    THE_CURRENT_TENANT_HAS_AN_ENABLED_AUTO_RETRY_POLICY("THE_CURRENT_TENANT_HAS_AN_ENABLED_AUTO_RETRY_POLICY", "当前租户存在自动重试策略的API或者应用系统，请禁用后重试"),
    AUTO_RETRY_PLUGIN_ERROR("500021", "{0}已启用自动化重试插件，请关闭后重试"),

    BREAKPOINT_CONTINUATION_PLUGIN_ERROR("500021", "{0}已启用断点续传插件，请关闭后重试"),
    /**
     * 数据频道
     */
    DATA_DISTRIBUTION_DISABLED_ERROR("500021", "当前租户存在已运行的数据频道或订阅者，请下线后重试"),
    DATA_DISTRIBUTION_ENV_ID_HAS_RUNNING_ERROR("500021", "该环境存在已运行的数据频道或订阅者，请下线后重试"),
    DATA_DISTRIBUTION_PLUGIN_ERROR("500021", "{0}已启用该插件，请关闭后重试"),
    CUSTOM_CONFIG_ERROR("500099", "{0}@{1}"),
    /**
     * 没有找到「{0}」的灰度编排引擎，请检查
     */
    NO_GRAYSCALE_FORMATTING_ENGINE_FOUND("NO_GRAYSCALE_FORMATTING_ENGINE_FOUND", "没有找到「{0}」的灰度编排引擎，请检查"),
    SAP_GET_IDOC_TYPE_ERROR("SAP_GET_IDOC_TYPE_ERROR", "获取IDOC类型失败，{0}"),
    GATEWAY_HEALTHY_CHECK_PATH_ERROR("GATEWAY_HEALTHY_CHECK_PATH_ERROR", "心跳检测path必须以“/”开头"),

    FORMAT_NON_STANDARD("FORMAT_NON_STANDARD", "{0}格式不规范,请检查"),
    FORMAT_ERROR("FORMAT_ERROR", "结构更新失败，请检查输入参数格式或自定义结构"),

    DUPLICATE_DIC_CODE("DUPLICATE_DIC_CODE", "字典编码重复"),
    /**
     * 配置不能为空
     */
    CONFIGURATION_CANNOT_BE_EMPTY("CONFIGURATION_CANNOT_BE_EMPTY", "配置不能为空"),
    /**
     * 模板名称与同一应用系统内其他数据重复，请修改后重试
     */
    THE_TEMPLATE_NAME_IS_DUPLICATED_WITH_OTHER_DATA_WITHIN_THE_SAME_APPLICATION_SYSTEM("THE_TEMPLATE_NAME_IS_DUPLICATED_WITH_OTHER_DATA_WITHIN_THE_SAME_APPLICATION_SYSTEM", "模板名称与同一应用系统内其他数据重复，请修改后重试"),
    /**
     * 该策略模板不存在
     */
    THE_POLICY_TEMPLATE_DOES_NOT_EXIST("THE_POLICY_TEMPLATE_DOES_NOT_EXIST", "该策略模板不存在"),
    /**
     * 已选择的策略中存在部分数据已被删除，请刷新后重试
     */
    SOME_DATA_IN_THE_SELECTED_POLICY_HAS_BEEN_DELETED("SOME_DATA_IN_THE_SELECTED_POLICY_HAS_BEEN_DELETED", "已选择的策略中存在部分数据已被删除，请刷新后重试"),
    /**
     * 变量编码不能重复
     */
    VARIABLE_ENCODING_CANNOT_BE_DUPLICATE("VARIABLE_ENCODING_CANNOT_BE_DUPLICATE", "变量编码不能重复"),
    /**
     * 变量不存在
     */
    VARIABLE_IS_NOT("VARIABLE_IS_NOT", "变量不存在"),
    /**
     * 当前变量已经引用api
     */
    THE_CURRENT_VARIABLE_IS_ALREADY_REFERENCED_BY_THE_API("THE_CURRENT_VARIABLE_IS_ALREADY_REFERENCED_BY_THE_API", "当前变量已被API引用，请修改后重试"),


    THE_CURRENT_DIC_IS_ALREADY_REFERENCED_BY_THE_API("THE_CURRENT_DIC_IS_ALREADY_REFERENCED_BY_THE_API", "当前数据字典已被API引用，请修改后重试"),
    /**
     * PLUGIN_ENABLED
     */
    PLUGIN_ENABLED("PLUGIN_ENABLED", "{0}{1}"),
    GROUP_NAME_ALREADY_EXISTS("GROUP_NAME_ALREADY_EXISTS", "该应用系统分组名称已存在"),
    EXCEL_ERROR("EXCEL_ERROR", "excel文件读取出错，请检查"),
    EXCEL_FORMAT_ERROR("EXCEL_FORMAT_ERROR", "excel文件格式错误读"),
    DUPLICATE_POLICY_NAMES_WITHIN_THE_SAME_POLICY_TYPE("DUPLICATE_POLICY_NAMES_WITHIN_THE_SAME_POLICY_TYPE","同一策略类型内策略名称重复，请检查"),

    SQL_ILLEGAL("SQL_ILLEGAL", "不合法的SQL参数，请检查"),
    HEADER_PARAM_ERROR("HEADER_PARAM_ERROR", "缺少生成数字签名的{0}参数或值，请检查调试信息"),

    /**
     * 监听订阅配置
     */
    SUBSCRIBER_EXIST_RESOURCE("SUBSCRIBER_EXIST_RESOURCE", "该监听订阅已绑定该配置资源！"),
    RESOURCE_DISABLED("RESOURCE_DISABLED", "该配置资源已被禁用！"),
    SUBSCRIBER_EXIST_RESOURCES("RESOURCE_DISABLED", "该监听配置下的配置资源，存在运行中API，无法禁用或删除！"),
    SUBSCRIBER_NOT_EXIST("SUBSCRIBER_NOT_EXIST", "该监听配置不存在，请联系管理员！"),
    DUPLICATE_KEYWORD_NAME("DUPLICATE_KEYWORD_NAME","关键字名称重复，请检查"),
    DUPLICATE_KEYWORD_PARAMETER_NAME("DUPLICATE_KEYWORD_PARAMETER_NAME","关键字参数名重复，请检查"),
    THE_KEYWORD_IS_ALREADY_IN_USE_BY_THE_POLICY("THE_KEYWORD_IS_ALREADY_IN_USE_BY_THE_POLICY","该关键字已被策略使用，请解绑后重试"),
    LINK_NAME_DUPLICATE("LINK_NAME_DUPLICATE", "链路名称与已有数据重复，请修改后重试"),
    API_DOCUMENT_EXPORT_ERROE("API_DOCUMENT_EXPORT_ERROE", "API文档导出失败"),

    AUDIT_LOG_EXPORT_ERROR("AUDIT_LOG_EXPORT_ERROR", "审计日志导出失败"),
    JAVA_ERROR("JAVA_ERROR", "Java连接器中的java代码存在问题请修改"),

    SCHEDULER_NAME_REPEAT("500017", "任务名称与已有数据重复，请检查"),
    SCHEDULER_DELETE_RUNNING("500017", "该调度任务正在运行中，无法删除"),
    PLEASE_COMPLETE_THE_SCHEDULER_CONFIGURATION_FIRST("PLEASE_COMPLETE_THE_SCHEDULER_CONFIGURATION_FIRST", "请先完成调度引擎配置"),
    THE_CURRENT_TENANT_HAS_AN_ENABLED_SCHEDULER_JOB("THE_CURRENT_TENANT_HAS_AN_ENABLED_SCHEDULER_JOB", "当前租户存在已发布的定时调度任务，请下线后重试"),
    SCHEDULER_NOT_EXIST("500002", "该调度任务不存在"),
    SCHEDULER_NOT_RUNNING("500003", "该调度任务未部署运行"),

    /**
     * ApiMock
     */
    API_MOCK_PATH_REPEAT("API_MOCK_PATH_REPEAT", "ApiMock地址重复"),
    /**
     * apifox
     */
    DUPLICATE_DIR_NAME("DUPLICATE_DIR_NAME", "名称已重复，请更换后重试！"),
    RUNTIME_VARIABLE_SAVE_ERROR("RUNTIME_VARIABLE_SAVE_ERROR", "保存成功，{0}，等环境runtime节点不可用"),
    RUNTIME_VARIABLE_DELETE_ERROR("RUNTIME_VARIABLE_SAVE_ERROR", "删除成功，{0}，等环境runtime节点不可用"),
    VARIABLE_SAVE_ERROR("RUNTIME_VARIABLE_SAVE_ERROR", "保存成功，{0}"),
    VARIABLE_DELETE_ERROR("RUNTIME_VARIABLE_SAVE_ERROR", "删除成功，{0}"),

    SAVE_ERROR("500", "保存失败"),

    /**
     * Apistore
     */
    API_STORE_API_NOT_EXSIT("API_STORE_API_NOT_EXSIT", "该API不存在"),
    API_STORE_API_IS_APPLY("API_STORE_API_IS_APPLY", "该API已经申请授权"),
    API_STORE_API_NOT_APIAUTHORITY("API_STORE_API_NOT_APIAUTHORITY", "未打开权限开关"),
    API_STORE_API_IS_APIAUTHORITY("API_STORE_API_IS_APIAUTHORITY", "该API已授权"),
    API_STORE_API_IS_APIAUTHORITY_FAIL("API_STORE_API_IS_APIAUTHORITY_FAIL", "该API授权失败"),

    APP_SCENE_CODE_OR_NAME_REPEAT("500017", "应用场景编码或名称不能重复"),

    APP_CONNECTOR_FLOW_NOT_EXITS("500", "应用连接器编排流不存在，flowCode:{0}"),



    /**
     * downloadAuditLog
     */
    AUDIT_LOG_PAYLOAD_IS_BLANK("AUDIT_LOG_PAYLOAD_IS_BLANK", "下载失败，节点日志报文为空"),
    AUDIT_LOG_PAYLOAD_IS_EMPTY("AUDIT_LOG_PAYLOAD_IS_EMPTY", "下载失败，节点报文不存在"),
    AUDIT_LOG_IS_EMPTY("AUDIT_LOG_IS_EMPTY", "下载失败，节点日志不存在"),
    REQUEST_PARAM_ERROR("REQUEST_PARAM_ERROR", "请求参数错误"),

    DUPLICATE_ENV_NAME("DUPLICATE_ENV_NAME", "环境名称已重复，请更换后重试！"),

    DUPLICATE_EXAMPLE_NAME("DUPLICATE_EXAMPLE_NAME", "用例名称已重复，请更换后重试！"),

    BASE_LOG_IS_EMPTY("BASE_LOG_IS_EMPTY", "日志已被归档或删除，无法查看日志详情"),

    REPAIR_CHOREOGRAPHY_ENGINE_ERROR("REPAIR_CHOREOGRAPHY_ENGINE_ERROR", "{0}等租户旧数据编排引擎修复失败"),
    NOT_FOUND_OPERATION_PERMISSIONS("NOT_FOUND_OPERATION_PERMISSIONS", "该用户未拥有{0}操作权限"),

    ETL_TEMPLATE_TRANSFORM_EXCEPTION("50020", "{0}节点模版转换失败！"),
    VERSION_RUNNING("50020", "该版本正在运行中！"),
    ETL_NOT_EXIST("5000008", "该任务不存在，请刷新页面"),
    SCHEDULE_EXECUTE_TIME("5000008", "当前调度策略为固定频率时显示，执行时间为必填项"),
    SCHEDULE_EXECUTE_FREQUENCY("5000008", "当前调度策略为固定频率时显示，执行频次为必填项"),
    ETL_DEPLOY_API_ERROR("5000009", "没有可用的runtime节点，请检查！{0}"),
    SERVICE_CODE_REPEAT("5000010", "同一个应用系统内服务编码不能重复，请修改后重试"),
    SERVICE_NOT_EXIST("5000010", "服务不存在"),
    ROUTE_NOT_EXIST("5000010", "路由不存在"),
    SERVICE_EXIST_ROUTE("5000010", "该服务下存在关联路由，无法删除"),
    ROUTE_CODE_REPEAT("5000010", "路由编码重复"),
    GATEWAY_MANAGE_PLUGIN_ERROR("500021", "{0}已启用网关管理插件，请关闭后重试"),

    AI_GATEWAY_MODULE_PLUGIN_ERROR("500021", "{0}已启用AI网关模块插件，请关闭后重试"),

    VERSION_HAS_RUNNING_TASK("VERSION_HAS_RUNNING_TASK", "当前版本存在执行中任务，请下线后重试"),
    AUTH_TYPE_NOT_SUPPORTED("AUTH_TYPE_NOT_SUPPORTED", "认证类型不支持"),
    SUBSCRIBER_CONSUMER_REPEAT("SUBSCRIBER_CONSUMER_REPEAT", "消费者重复"),
    DATA_CHANNEL_REPEAT("DATA_CHANNEL_REPEAT", "数据源编码或名称已重复，请更换后重试"),

    JAVA_FILE_ERROR("JAVA_FILE_ERROR", "Java文件解析异常"),
    JAVA_CODE_UNSAFE("JAVA_CODE_UNSAFE", "程序包含高危代码，无法执行！"),
    TASK_ENABLE_DELETE("TASK_ENABLE_DELETE", "任务已启用，无法执行删除操作，请禁用后重试"),
    APP_CONNECTOR_REQUEST_ERR("APP_CONNECTOR_REQUEST_ERR", "请求失败：{0}"),

    AI_MODEL_CONNECTION_ERROR("AI_MODEL_CONNECTION_ERROR", "AI模型连接失败"),



    AI_MODEL_FLUSH_ERROR("AI_MODEL_CONNECTION_ERROR", "刷新缓存失败")
    ;

    private final String code;

    private final String message;

    DeipaasExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return InternationalizationConfig.getMessage(this.getClass().getName() + "." + this, this.message);
    }
}
