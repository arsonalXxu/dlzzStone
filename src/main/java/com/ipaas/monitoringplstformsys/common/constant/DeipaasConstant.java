package com.ipaas.monitoringplstformsys.common.constant;

import java.util.Arrays;
import java.util.List;

public interface DeipaasConstant {

    //通用
    String PERCENT = "%";
    String YES = "Y";
    String COMMA = ",";

    String NO = "N";
    String SPOT = ".";
    String STAR = "*";
    String GET = "GET";
    String POST = "POST";
    String PATCH = "PATCH";
    String DELETE = "DELETE";
    String PUT = "PUT";
    String ALL = "all";

    int UNLIMITED_COUNT = -1;
    //布尔-true
    String BOOLEAN_TRUE = "true";

    //布尔-false
    String BOOLEAN_FALSE = "false";
    /**
     * 运行情况-未运行
     */
    String VERSION_STOP_STATUS = "stop";

    String VERSION_ENV_RUNNING_STATUS = "running";

    String STATUS_DISABLED = "DISABLED"; // 失效状态

    String STATUS_ENABLE = "ENABLED"; // 生效状态

    String RETRY = "retry"; // 重试

    String TENANT_ID = "tenantid";

    String USER_OPERATION_ID = "useroperationid";

    static final String FOLDER_PREFIX = "attach-";

    static final String HEAD_PORTRAIT_FOLDER = "headPortrait";

    String USER_TYPE_SYS_ADMIN = "sysAdmin"; // 系统管理员

    String ENABLE_STATUS = "enableStatus";

    String ARCHIVE_VEIDOO = "archiveVeidoo"; // 归档维度

    String DISABLED_CN = "禁用";
    String ENABLE_CN = "启用";
    String DELETE_CN = "删除";
    String FAIL = "失败";
    String SUCCESS = "成功";
    /**
     * 版本状态
     */
    String VERSION_RUNNING_STATUS = "apiVersionRunningStatus";
    String VERSION_PUBLIC_STATUS = "apiVersionPublicStatus";
    String VERSION_SAVE_STATUS = "apiVersionSaveStatus";

    /**
     * 编排类型
     */
    String CONFIG_TYPE_WEBSERVICE = "webService";

    /**
     * 快速开发-WebService透传
     */
    String FAST_PROXY_WEBSERVICE = "FAST_PROXY_WEBSERVICE";

    String PASS_WS = "PASS_WS";

    /**
     * 运行实例结果状态
     */
    String INSTANCE_RESULT_STATUS = "apiVersionResultStatus";

    /**
     * secretType
     */
    String SECRET_TYPE_KEY_AUTH = "KeyAuth";
    String SECRET_TYPE_BASIC_AUTH = "BasicAuth";
    String SECRET_TYPE_HMAC = "Hmac";

    /**
     * 运行状态
     */
    String RUN_STATUS = "apiVersionRunningStatus";

    /**
     * 运行信息
     */
    String REQUEST_BODY = "requestBody";
    String REQUEST_BODY_LIST = "requestBodyList";
    String RESPONSE_BODY = "responseBody";
    String RESPONSE_BODY_LIST = "responseBodyList";
    String HEADER_TABLE = "headerTable";
    String BODY_TYPE = "bodyType";
    String REQ_BODY_TYPE = "reqBodyType";
    String RESP_BODY_TYPE = "respBodyType";

    /**
     * 权限分类
     */
    String TYPE_DATA = "dataPermission"; //数据权限
    String TYPE_OPERATION = "operationPermission"; //操作权限
    String OPERATION_ID = "343049531715256320";
    String TYPE_FUNCTION = "functionPermission";  //功能权限
    String PERMISSION_TYPE = "permissionType"; //权限类型

    String PARAM_PATH = "Param_path";
    String QUERY = "Query";
    String HEADER = "Header";
    String BODY = "Body";

    //虚拟服务组/分类 类型
    String GROUP_TYPE_AGENT = "agent";
    String GROUP_TYPE_ARRANGEMENT = "arrangement";

    //网关
    String DEFAULT_PROTOCOL = "HTTP";//默认协议
    String DEFAULT_PROTOCOL_HTTPS = "HTTPS";//默认协议
    String DEFAULT_PROTOCOLS = "HTTP,HTTPS";
    String KONG_SELF_UPSTREAM = "KONG_SELF_UPSTREAM"; //kong自身服务组
    String DEFAULT_SERVICE_PATH = "/"; //kong自身服务组
    String CATEGORY_PATH = "/category";//分类默认path
    int CREATED_CODE = 201;//创建成功code
    int UPDATE_CODE = 200;//修改成功code
    String DENY = "deny";//黑名单
    String ALLOW = "allow";//白名单

    /**
     * 插件对象类型,与kong统一
     */
    String TENANT_TYPE = "tenants";
    String CONSUMER_TYPE = "consumers";
    String CATEGORY_TYPE = "categories";
    String SERVICE_TYPE = "services";
    String ROUTE_TYPE = "routes";
    String OAUTH2_TYPE = "oauth2";

    String PROXY_AUTH="oauth2_introspection";

    /**
     * 插件code，跟kong中同步，且唯一
     */
    String BASIC_AUTH_CODE = "deipaas-basic-auth";
    /**
     * API熔断
     **/
    String CIRCUIT_BREAKER_CODE = "deipaas-circuit-breaker";
    /**
     * 数字签名
     **/
    String HMAC_AUTH_CODE = "deipaas-hmac-auth";
    String JWT_CODE = "deipaas-jwt";
    String OAUTH2_INTROSPECTION_CODE = "deipaas-oauth2-introspection";

    String DEIPAAS_OAUTH2_CODE = "deipaas-oauth2";

    String KEY_AUTH_CODE = "deipaas-key-auth";

    String WEBHOOK_CODE = "deipaas-market-webhook";
    /**
     * 请求频次控制
     **/
    String RATE_LIMIT_CODE = "deipaas-rate-limiting";
    /**
     * 请求体大小限制
     **/
    String REQUEST_SIZE_CODE = "deipaas-request-size-limiting";
    /**
     * IP控制
     **/
    String IP_RESTRICTION_CODE = "deipaas-ip-restriction";
    /**
     * 跨域访问控制
     **/
    String CORS_CODE = "deipaas-cors";
    /**
     * API降级
     **/
    String REQUEST_TERMINATION_CODE = "deipaas-request-termination";
    /**
     * 请求转换
     **/
    String REQUEST_TRANSFORMER_CODE = "deipaas-request-transformer";
    /**
     * 响应转换
     **/
    String RESPONSE_TRANSFORMER_CODE = "deipaas-response-transformer";
    /**
     * 代理缓存
     **/
    String PROXY_CACHE_CODE = "deipaas-proxy-cache";
    /**
     * 灰度发布
     **/
    String GRAY_RELEASE_CODE = "deipaas-gray-release";
    /**
     * 数据加解密
     **/
    String DATA_ENCRYPTION_CODE = "deipaas-data-encryption";
    /**
     * 数据脱敏
     **/
    String DATA_DESENSITIZE_CODE = "deipaas-data-desensitize";
    /**
     * 防重复提交
     */
    String ANTI_DUPLICATION = "deipaas-anti-duplication";
    /**
     * 请求时段限制
     **/
    String REQUEST_TIME_LIMIT_CODE = "deipaas-request-time-limit";
    /**
     * Kafka日志记录插件
     **/
    String KAFKA_LOGGER_CODE = "deipaas-kafka-logger";
    /**
     * 日志发送
     **/
    String HTTP_LOGGER_CODE = "deipaas-http-logger";
    /**
     * 日志TCP发送
     **/
    String TCP_LOGGER_CODE = "deipaas-tcp-logger";
    /**
     * Acl授权（黑白名单）
     **/
    String ACL_CODE = "deipaas-acl";

    String MOCK = "deipaas-mock";
    /**
     * 通过下游验证
     **/
    String API_AUTH_CODE = "deipaas-api-auth";
    String CORRELATION_ID_CODE = "deipaas-correlation-id";
    String WEBHOOK_INVOKE_CODE = "deipaas-webhook-invoke";
    String WEBHOOK_REPLACE_CODE = "deipaas-webhook-replace";
    /**
     * 代理透传
     **/
    String DYNAMIC_UPSTREAM_CODE = "deipaas-dynamic-upstream";
    String KONG_HTTP_LOG_WITH_BODY_V2_CODE = "deipaas-kong-http-log-with-body-v2";
    /**
     * AI Agent
     */
    String LLM_PROXY_CODE = "deipaas-llm-proxy";
    /**
     * 提示词模版插件
     */
    String CALL_WORD_TEMPLATE_CODE = "ai-prompt-template";

    /**
     * 微服务透传（Service插件）
     **/
    String MICROSERVICE_PROXY_SERVICE_CODE = "deipaas-microservice-proxy-service";
    /**
     * 微服务透传（route插件）
     **/
    String MICROSERVICE_PROXY_SIGN_CODE = "deipaas-microservice-proxy-sign";
    /**
     * 防止重复提交
     **/
    String ANTI_DUPLICATION_CODE = "deipaas-anti-duplication";
    /**
     * 错误码映射
     **/
    String ERROR_CODE_MAPPING_CODE = "deipaas-error_code_mapping";
    /**
     * AK/SK鉴权
     **/
    String AK_SK_CODE = "deipaas-ak-sk-auth";

    /**
     * 默认版本插件
     */
    String DEFAULT_VERSION = "deipaas-default-version";

    /**
     * 请求头参数相关
     */
    String DEIPAAS_TOKEN = "deipaastoken";
    String DEIPAAS_KEYAUTH = "deipaaskeyauth";
    String DEIPAAS_CONSUMER = "deipaasconsumer";
    String DEIPAAS_CATEGORY = "deipaascategory";
    String DEIPAAS_REQUESTID = "deipaasrequestid";

    /**
     * 公开状态-未公开
     */
    String API_VERSION_PUBLIC_PRIVATE = "private";
    String API_VERSION_PUBLIC_PROTECTED = "protected";
    String API_VERSION_PUBLIC_PUBLIC = "public";

    /**
     * api状态
     */
    String API_STATUS_RUNNING = "running";
    String API_STATUS_RUNNING_MEANING = "使用中";
    String API_STATUS_NOAUTH = "noauth";
    String API_STATUS_NOAUTH_MEANING = "无权限";

    /**
     * api申请状态
     */
    String API_APPLY_VERIFYING = "verifying";
    String API_APPLY_REFUSED = "refused";
    String API_APPLY_APPROVED = "approved";

    /**
     * API门户授权申请相关
     */
    String BLACKLIST = "BLACKLIST";
    String OPERATE_SUCCESS = "SUCCESS";


    /**
     * 用户注册/租户申请 状态
     */
    String APPLY_STATUS_PENDING = "PENDING";
    String APPLY_STATUS_COMPLETED = "COMPLETED";
    String APPLY_STATUS_REFUSED = "REFUSED";

    /**
     * 流程节点审批状态
     */
    String APPLY_STATUS_AGREED = "AGREED";
    String APPLY_STATUS_COMMITTED = "COMMITTED";
    /**
     * 连接器类型
     */
    String COMPONENT_TYPE_COMPONENT = "component";
    String COMPONENT_TYPE_CATEGORY = "category";
    String COMPONENT_TYPE_SUBCOMPONENT = "subcomponent";
    String COMPONENT_TYPE_ARRANGEMENT = "arrangement";
    /**
     * 平台登录
     */
    String PLATFORM_LOGIN = "platform";

    /**
     * 验证码
     */
    String VERIFICATION_CODE_NOT_USED = "NOT_USED";
    String VERIFICATION_CODE_USED = "USED";
    String VERIFICATION_CODE_TYPE_PHONE_LOGIN = "LOGIN_BY_PHONE_CODE";
    String VERIFICATION_CODE_TYPE_EMAIL_LOGIN = "LOGIN_BY_EMAIL_CODE";
    String VERIFICATION_CODE_TYPE_PHONE_REGISTER = "REGISTER_PHONE_CODE";
    String VERIFICATION_CODE_TYPE_EMAIL_REGISTER = "REGISTER_EMAIL_CODE";
    String VERIFICATION_CODE_TYPE_UPDATEPWD_BYPHONE = "UPDATE_PASSWORD_BY_PHONE_CODE";
    String VERIFICATION_CODE_TYPE_UPDATEPWD_BYEMAIL = "UPDATE_PASSWORD_BY_EMAIL_CODE";
    String VERIFICATION_CODE_TYPE_UPDATEPHONE = "UPDATE_PHONE_BY_PHONE_CODE";
    String VERIFICATION_CODE_TYPE_UPDATEEMAIL = "UPDATE_EMAIL_BY_EMAIL_CODE";

    String WEB_SERVICE = "webService";
    String SELF_RESOURCE = "selfResource";
    String WEB_SERVICE_WSDL = "webService_wsdl";
    String SCHEDULER = "scheduler";
    String API_TYPE_PROXY = "proxy";
    String ES_HTTPS = "https";
    /**
     * 环境列表类型
     */
    String QUERY_ENV_FOR_TABLE = "tableEnvList";
    String QUERY_ENV_ALL_FORMAL_ENV = "queryAllFormalEnv";
    String QUERY_ENV_API_RUNNING_FORMAL_ENV = "apiRunningFormal";

    /**
     * 认证方式
     */
    String NO_AUTH = "No Auth";
    String BASIC_AUTH = "Basic Auth";
    String OAUTH2 = "OAuth 2.0";
    String OAUTH2_CODE = "Authorization-code";
    String OAUTH2_IMPLICIT = "Implicit";
    String OAUTH2_PASSWORD = "Password";
    String OAUTH2_CLIENT_CREDENTIALS = "Client Credentials";

    /**
     * http协议：https/http
     */
    String HTTPS = "https";
    String HTTP = "http";
    String HTTP_UPPER = "HTTP";
    String HTTPS_UPPER = "HTTPS";
    String REQUEST_PROTOCOL_HTTTP = "HTTP(S)";
    String REQUEST_PROTOCOL_WEBSERVICE = "SOAP";
    String HTTPS_PREFIX = "https://";
    String HTTP_PREFIX = "http://";

    String HTTP_METHOD = "method";

    String URI = "uri";

    String DEFINE_API = "defineConfig";
    String API_FLOW = "apiFlow";
    String HTTP_BODY_JSON_TYPE = "JSON";
    String HTTP_BODY_XML_TYPE = "XML";
    String API_PROXY = "proxy";
    String NEW_API_PROXY = "newProxy";

    String AI_PROXY="aiProxy";
    String RAPID_DEVELOPMENT = "RAPID_DEVELOPMENT";
    String HTTP_BODY_TEXT_TYPE = "TEXT";
    String HTTP_BODY_TEXT_TYPE_LOWER = "text";

    String API_TEMP_STATUS = "temp";

    String API_SAVE_STATUS = "saved";


    String BODY_XML_TYPE = "xml";
    String HTTP_BODY_X_WWW_FORM_URLENCODED_TYPE = "x-www-form-urlencoded";
    String DEBUGGING_BODY_JSON_TYPE = "json";
    String BODY_TYPE_TEXT_PLAIN = "text/plain";

    String MULE_EXPRESSION_START_MARK = "#[";
    String MULE_EXPRESSION_END_MARK = "]";

    /**
     * 实例字段
     */
    String INSTANCE_REQUEST_DATE = "";

    /**
     * 实例别名
     */
    String INDEX_API_INSTANCES = "api_instances";
    String INDEX_API_INSTANCE_AUDIT_LOGS = "api_instance_audit_logs";
    String INDEX_API_RETRY_INSTANCES = "api_retry_instances";
    String API_INSTANCES_LOGS_ARCHIVED = "api_instances_logs_archived";
    String API_RETRY_INSTANCES_LOGS_ARCHIVED = "api_retry_instances_logs_archived";
    String API_INSTANCES_NODES_LOGS_ARCHIVED = "api_instances_nodes_logs_archived";
    String DEPLOY_LOG_ALIAS = "deipaas_apiflow_engine_deployment_log";

    /**
     * ipaas在header里面添加的工具字段
     * 以及header里面需要处理的默认字段
     */
    String IPAAS_HEADER_REQUEST_COUNT = "deipaasrequestcount";
    String IPAAS_HEADER_INSTANCE_ID = "deipaasretryinstanceid";
    String IPAAS_HEADER_RETRY_ID = "deipaasretryid";
    String IPAAS_HEADER_REQUEST_ID = "deipaasrequestid";
    String IPAAS_HEADER_CONTENT_TYPE = "content-type";

    /**
     * 重试可编辑时需要隐藏的headerList
     */
    List<String> IPAAS_HEADERS = Arrays.asList(IPAAS_HEADER_INSTANCE_ID, IPAAS_HEADER_RETRY_ID, IPAAS_HEADER_REQUEST_COUNT, IPAAS_HEADER_REQUEST_ID,
            IPAAS_HEADER_CONTENT_TYPE, "content-length", "host", "user-agent", "accept", "connection", "x-correlation-id", "x-credential-identifier",
            "x-consumer-id", "x-consumer-username", "x-credential-username", "x-forwarded-for", "x-forwarded-proto", "x-forwarded-host", "x-forwarded-port",
            "x-forwarded-path", "x-real-ip", "cache-control", "pragma");
    String CLASS_NAME_HTTP_RESPONSE = "HttpResponse";
    String CLASS_NAME_HTTP_REQUEST = "HttpRequest";
    String CLASS_NAME_HTTP_WEBSERVICE = "soapListener";

    /**
     * 编排json组件类型
     */
    String CONFIG_SHAPE_TYPE_NODE = "ipaas-flow-node";

    /**
     * 编排节点类型
     */
    String NODE_TYPE_START_NODE = "startNode";
    String NODE_TYPE_END_NODE = "endNode";

    /**
     * web service 请求/响应节点class name
     */
    String WEBSERVICE_REQ_NODE = "SoapFlowRequest";
    String WEBSERVICE_RES_NODE = "SoapFlowResponse";
    String HTTP_REQ_NODE = "HttpRequest";
    String HTTP_RES_NODE = "HttpResponse";
    String GRAY_RELEASE = "gray-release";
    String DATA_ENCRYPTION = "data-encryption";
    String OFF_STATUS = "off";
    String DEIPAAS_TCP_LOGGER = "deipaas-tcp-logger";
    String HTTP_LOG = "http-log";
    /**
     * 节点配置文件目录名称
     */
    String NODE_CONFIG_FOLDER = "config";
    /**
     * 审批流程类型
     */
    String PROCESS_TYPE_API = "apiAuthorization";
    /**
     * 对外发布审批
     */
    String PROCESS_TYPE_MARKET = "marketAuthorization";
    /**
     * api公开申请
     */
    String PUBLIC_APPLY = "publicApply";
    String PROCESS_TYPE_PORTAL = "portalAuthorization";
    /**
     * 租户门户上架申请
     */
    String TENANT_PORTAL_APPLY = "tenantPortalApply";
    /**
     * 命令执行结果
     */
    String COMMAND_SUCCESS = "SUCCESS";
    String COMMAND_FAIL = "FAIL";
    String COMMAND_FAILURE = "FAILURE";
    String COMMAND_PARTIAL_SUCCESS = "PARTIAL_SUCCESS";
    String DEPLOY_CODE = "200";
    /**
     * api操作
     */
    String PUBLISH = "PUBLISH";
    String OFFLINE = "OFFLINE";
    String DEFAULT_REQUEST_BODY = "{\"bodyType\":\"json\",\"params\":[],\"code\":\"request\",\"requestBodyList\":[],\"requestBody\":\"\",\"methods\":\"POST\",\"name\":\"HTTP请求\"}";
    String DEFAULT_RESPONSE_BODY = "{\"bodyType\":\"json\",\"code\":\"response\",\"responseBodyList\":[],\"responseBody\":\"{}\",\"name\":\"HTTP响应\"}";
    /**
     * SAP操作，RFC函数列表 用作redis的hash key
     */
    String RFC_FUNCTIONS = "rfcFunctions";
    String SOAP_WSDL_FOLDER = "soapWsdl";
    String DEIPAAS_SOAP_WSDL_FOLDER = "deipaasSoapWsdl";
    String SOAP_FLOW = "soapFlow";
    String SOAP_CONFIG_FOLDER = "api";
    /**
     * API编排模板
     */
    String APPLY_HIERARCHY_SELF = "1";
    String APPLY_HIERARCHY_TENANTS = "2";
    String APPLY_HIERARCHY_PLATFORM = "3";
    /**
     * 平台插件code
     */
    String MS_CODE = "micro-service";
    String AP_CODE = "apaas-plugin";
    String MDM_CODE = "mdm-plugin";
    String API_MARKET_PLUGIN = "api-market-plugin";
    String INTERFACE_OPEN_PLATFORM = "interface-open-platform";
    String I18N_PLUGIN = "i18n-plugin";
    String AI_GATEWAY_MODULE = "ai-gateway-module";

    String VARIABLE="variable";
    /**
     * 开发者中心插件
     */
    String DEVELOPER_CENTER = "developer-center";
    String PLATFORM_PORTAL="platform-portal";
    String CALL_LINK_TRACING = "call-link-tracing";

    String MONITORING_SCREEN="monitoring-screen";
    /**
     * 菜单
     */
    String ROOT = "root";
    String BASE = "base";
    String MENU = "menu";
    String SYSTEM = "system";
    String SELF = "self";
    String GROUP = "group";
    String API = "api";
    /**
     * sql
     */
    String ASC = "asc";
    String DESC = "desc";
    /**
     * 授权认证请求头参数
     */
    String DEIPAASKEYAUTH="deipaaskeyauth";
    String AUTHORIZATION = "Authorization";
    String DEIPAASMARKET = "deipaasmarket";
    /**
     * 单点登录
     */
    String GRANT_TYPE = "grant_type";
    String AUTHORIZATION_CODE = "authorization_code";
    String CODE = "code";
    String AUTH_CODE = "authCode";
    String REDIRECT_URI = "redirect_uri";
    String REDIRECT_URL = "redirect_url";
    String USERNAME_SELECTOR = "usernameSelector";
    String TICKET = "ticket";
    String LOGOUT_URL = "logoutUrl";
    String LOGOUT_METHOD = "logoutMethod";
    String RUNTIME_PREFIX = "/apiflow/runtime/admin/SpringContexts";
    String RUNTIME_ADMIN_PREFIX = "/apiflow/runtime/admin";
    /**
     * 请求头参数
     */
    String CONTENT_DISPOSITION = "Content-Disposition";
    /**
     * 查看日志path
     */
    String CONSOLE_DEIPAAS_LOG = "/deipaasapi/consoleLog/deipaasLog";
    String CONSOLE_HANDLER_LOG = "/consoleLog/handlerLog";
    String CONSOLE_RUNTIME_LOG = "/consoleLog/runtime";
    /**
     * 下载日志path
     */
    String CONSOLE_DEIPAAS_LOG_DOWNLOAD = "/deipaasapi/consoleLog/download";
    String CONSOLE_HANDLER_LOG_DOWNLOAD = "/consoleLog/handlerLog/download";
    String CONSOLE_RUNTIME_LOG_DOWNLOAD = "/consoleLog/download";
    /**
     * HEADER里的日志相关参数
     */
    String TRACE_ID = "traceid";
    String I_REQUEST_ID = "i-request-id";
    String ORIGIN_REQUEST_ID = "originRequestId";
    /**
     * webhook任务运行状态
     */
    String WEB_HOOK_RUNNING = "RUNNING";
    String WEB_HOOK_STOP = "STOP";
    /**
     * 下游地址数据库IP空格
     */
    String BLANK = "    ";
    String SLASH = "/";

    String ENVIRONMENT_PUBLIC_SUFFIX = "源接口地址";

    /**
     * 模拟调用消费者Code
     */
    String APIMOCK_OVERALLSITUATION = "apiMock-overallSituation";

    public static void main(String[] args) {
        System.out.println("");
        System.out.println(EarlyWarning.NO_NCONVERGENCE);
    }

    interface nodeIoRecordType {
        String ALL = "ALL";
        String INPUT = "INPUT";
        String OUTPUT = "OUTPUT";
    }
    interface wsdlMap {
        String HTTP = "http";
        String SOAP = "soap";
    }
    interface wsdlFileType {
        String WSDL = "wsdl";
        String XML = "xml";
        String TXT = "txt";
    }

    /**
     * 脱敏状态
     */
    interface Desensitization {
        /**
         * 关闭状态
         */
        String DISABLED = "DISABLED";
        /**
         * 开启状态
         */
        String ENABLE = "ENABLED";
    }
    interface Judgment {
        /**
         * FALSE
         */
        String TRUE = "TRUE";
        /**
         * FALSE
         */
        String FALSE = "FALSE";
    }
    /**
     * aPaaS透传所用常量
     */
    interface aPaaSProxy {
        //aPaaS透传代码
        String APAAS_PROXY = "aPaaSProxy";
        //aPaaS的租户token
        String TENANT_TOKEN = "tenantAccessToken";
        //iPaaS的租户缓存token
        String APAAS_TOKEN = "deipaas-app:apaas-proxy:";
        //换取租户token接口
        String TENANT_TOKEN_INTERFACE = "/xdap-admin/user/get/tenantAccessToken";
        //产品凭证
        String PRODUCT_KEY = "productKey";
        //租户key
        String TENANT_KEY = "tenantKey";
        //租户密钥
        String TENANT_SECRET = "tenantSecret";
        //租户请求头
        String TENANT_HEADER = "xdaptoken";
        String TIMESTAMP = "?timestamp=";
        String DATA = "data";
        String URI = "uri";
        String METHODS = "methods";
        String METHOD = "method";
        String TEMP_SAVE_VERSION = "1.0.0";
        String RUN_SERVER_GROUP = "application";
        String PROTOCOL_TYPE = "protocolType";
        String REQUEST_PARAMS = "requestParams";
        String RESP_BODY_TYPE = "respBodyType";
        String PROXY_PATH = "proxyPath";
        String API_TYPE_APP = "APP";
        String DIAGONAL = "//";
    }

    /**
     * aPaaS透传所用常量
     */
    interface mDmProxy {
        //MDM透传代码
        String MDM_PROXY = "mDmProxy";
        //MDM的租户token
        String TENANT_TOKEN = "token";
        //iPaaS的租户缓存token
        String MDM_TOKEN = "deipaas-app:mdm-proxy:";
        //换取租户token接口
        String TENANT_TOKEN_INTERFACE = "/openipaas/returnTenantToken";
        //产品凭证
        String PRODUCT_KEY = "productKey";
        //租户key
        String APP_ID = "appId";
        //租户密钥
        String APP_KEY = "appKey";
        //租户请求头
        String TENANT_HEADER = "demdmtoken";
        String MDM_TENANT_ID_HEADER = "demdmtenantid";
        String DATA = "data";
        String URI = "uri";
        String TIMESTAMP = "?timestamp=";
        String METHODS = "methods";
        String METHOD = "method";
        String API_URI = "/api/getApiListByFormId";
        String MDM_URL = "/demdm-api";
        String REQUEST_BODY = "requestBody";
        String MS_PROXY = "msProxy";
        String AI_PROXY = "aiProxy";
    }

    interface AuthorizationType {
        /**
         * Basic Auth
         */
        String BASIC_AUTH = "BASIC_AUTH";
        /**
         * Key Auth
         */
        String KEY_AUTH = "KEY_AUTH";
        /**
         * 数字签名
         */
        String HMAC_AUTH = "HMAC_AUTH";
        /**
         * JWT
         */
        String JWT = "JWT";
        /**
         * OAuth2认证
         */
        String OAUTH2_INTROSPECTION = "OAUTH2_INTROSPECTION";
        /**
         * OAUTH2认证
         */
        String OAUTH2_V1 = "OAUTH2_V1";
    }

    /**
     * 请求头参数
     */
    interface HeaderParameter {
        /**
         * 请求头版本号
         */
        String DEIPAAS_VERSION = "deipaasversion";
    }

    /**
     * 导出api-pdf文档类型
     */
    interface ExportApiDocumentType {
        /**
         * 消费中心
         */
        String CONSUMER_CENTER = "consumerCenter";

        /**
         * 运行列表
         */
        String VERSION_ENV = "versionEnv";
    }

    /**
     * 单点登录方式
     */
    interface LoginMethod {
        String IPAAS = "iPaaS";
        String EXT_LOGIN = "ExtLoginEndpoint";
        String OAUTH2 = "OAuth2";
        String OIDC = "OIDC";
        String CAS = "CAS";
        String MARKET = "market";

        String APISTORE = "apiStore";
        String DING_TALK = "Ding-talk";
        String WECHAT = "wechat";
    }

    interface LoginJsonConfig{
        String CLIENT_ID = "clientId";
        String CLIENT_SECRET = "clientSecret";
        String REDIRECT_URI = "redirectUri";
        String SCOPE = "scope";
        String PROMPT = "prompt";
        String ORG_TYPE = "orgType";
        String CORP_ID = "corpId";
        String RESPONSE_TYPE = "responseType";
        String APP_ID = "appid";
        String AGENT_ID = "agentid";
        String STATE = "state";
        String SECRET = "secret";
    }
    interface LoginSsoConfig {
        String RESPONSE_TYPE = "response_type";
        String CLIENT_ID = "client_Id";
        String CLIENT_SECRET = "client_secret";
        String ORG_TYPE = "org_type";
        String CORP_ID = "corpId";
        String REDIRECT_URI = "redirect_uri";
        String STATE = "state";
        String SCOPE = "scope";
        String PROMPT = "prompt";
        String APP_ID = "appid";
        String AGENT_ID = "agentid";
        String SECRET = "secret";
        String WECHAT_REDIRECT = "#wechat_redirect";
    }
    interface EnableStatus {
        /**
         * 启用
         */
        String ENABLED = "ENABLED";
        /**
         * 禁用
         */
        String DISABLED = "DISABLED";
    }


    interface PortalStatus {
        /**
         * 启用
         */
        String ENABLED = "ENABLED";
        /**
         * 禁用
         */
        String DISABLED = "DISABLED";
        /**
         * 申请中
         */
        String APPLY = "APPLY";
        /**
         * 处理中
         */
        String PENDING = "PENDING";
    }

    interface Deleted {
        /**
         * Y
         */
        String Y = "Y";
        /**
         * N
         */
        String N = "N";
    }

    interface PowerJob {
        String MK_AUTHORITY_EXPIRE_JOB_NAME = "MK_AUTHORITY_EXPIRE_JOB";
        String CRON_EXEC_PER_DAY = "0 0 0 * * ?";
    }

    interface EarlyWarning {
        /**
         * 错误预警
         */
        String ERROR_TYPE = "ERROR";
        //email模板字符
        String EMAIL_PREFIX = "<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\"/>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\" />\n" +
                "    <meta http-equiv=\"Cache-Control\" name=\"no-store\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />\n" +
                "    <meta name=\"description\" content=\"不超过150个字符\" />\n" +
                "    <meta name=\"keywords\" content=\"\" />\n" +
                "    <meta name=\"author\" content=\"support@definesys.cn\" />\n" +
                "    <title>邮箱模板</title>\n" +
                "    <style>\n" +
                "        html {\n" +
                "            font-family: SourceHanSansSC-regular;\n" +
                "            height: 100%;\n" +
                "        }\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            height: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .wapper {\n" +
                "            height: 100%;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            background-color: #F7F8FA;\n" +
                "  box-sizing: border-box;\n" +
                "  border-radius: 2px;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            max-width: 560px;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "\n" +
                "        .email-container {\n" +
                "            max-width: 560px;\n" +
                "            margin: 0 auto;\n" +
                "            width: 100%;\n" +
                "            border-top: 4px solid #027AFF;\n" +
                "            border-bottom: 2px solid #027AFF;\n" +
                "        }\n" +
                "\n" +
                "        .header{\n" +
                "            display: flex;\n" +
                "            flex-direction : row-reverse;\n" +
                "            padding-bottom: 20px;\n" +
                "            width: 100%;\n" +
                "            background-color: #ffffff;\n" +
                "        }\n" +
                "        .header img {\n" +
                "            width: 104px;\n" +
                "            height: 36px;\n" +
                "            margin-top: 24px;\n" +
                "            margin-right: 36px;\n" +
                "        }\n" +
                "        .main {\n" +
                "            /* margin-left: 32px; */\n" +
                "            padding-left: 32px;\n" +
                "            padding-right: 32px;\n" +
                "            /* margin-bottom: 58px; */\n" +
                "            padding-bottom: 58px;\n" +
                "            background-color: #ffffff;\n" +
                "\n" +
                "        }\n" +
                "        .main-header {\n" +
                "            font-weight: bold;\n" +
                "            color: #303133;\n" +
                "            font-size: 16px;\n" +
                "            font-family: SourceHanSansSC-bold;\n" +
                "        }\n" +
                "        .main-code {\n" +
                "            color: #027AFF;\n" +
                "            font-size: 36px;\n" +
                "            font-weight: bold;\n" +
                "            margin: 20px 0;\n" +
                "        }\n" +
                "\n" +
                "        .main-body {\n" +
                "            font-size: 12px;\n" +
                "            color: #303133;\n" +
                "            line-height: 24px;\n" +
                "            min-height: 36px;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "\n" +
                "        .web-a {\n" +
                "            text-decoration: none;\n" +
                "            font-size: 12px;\n" +
                "            color: #027AFF;\n" +
                "        }\n" +
                "\n" +
                "        .circleAndline {\n" +
                "            display: flex;\n" +
                "            background-color: #ffffff;\n" +
                "\n" +
                "        }\n" +
                "        .circle1,\n" +
                "        .circle2{\n" +
                "            width: 13px;\n" +
                "            height: 24px;\n" +
                "            background-color: #F7F8FA;\n" +
                "        }\n" +
                "        .circle1 {\n" +
                "            border-radius: 0 12px 12px 0;\n" +
                "        }\n" +
                "        .circle2 {\n" +
                "            border-radius: 12px 0 0 12px;\n" +
                "        }\n" +
                "        .line {\n" +
                "            border-top: 1px dashed #EBEEF5;\n" +
                "            height: 1px;\n" +
                "            width: 100%;\n" +
                "            margin: auto 0;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "            padding: 12px 32px 32px;\n" +
                "            border-bottom: 1px solid #F5F7FA;\n" +
                "            background-color: #ffffff;\n" +
                "        }\n" +
                "\n" +
                "        .footer-main {\n" +
                "            display: flex;\n" +
                "            margin-top: 8px;\n" +
                "        }\n" +
                "\n" +
                "        .footer-info {\n" +
                "            flex: 1;\n" +
                "            height: 86px;\n" +
                "        }\n" +
                "\n" +
                "        .footer-notice {\n" +
                "            font-size: 12px;\n" +
                "            color: #B2B2B2;\n" +
                "        }\n" +
                "        .footer-web {\n" +
                "            font-size: 12px;\n" +
                "            color: #027AFF;\n" +
                "        }\n" +
                "        .bottomInfo {\n" +
                "            font-size: 12px;\n" +
                "            color: #b2b2b2;\n" +
                "            text-align: center;\n" +
                "            margin-top: 24px;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body style=\"margin: 0\">\n" +
                "<div class=\"wapper\" style=\"padding: 64px 0;\">\n" +
                "    <div class=\"container\" >\n" +
                "        <div class=\"email-container\" >\n" +
                "            <div class=\"header\" >\n" +
                "                <!-- 这个图片与相应的租户头像保持一致 -->\n" +
                "                <img src=\"${tenantLogo}\"\n" +
                "                     alt=\"logo\"/>\n" +
                "            </div>\n" +
                "            <div class=\"main\">\n" +
                "                <span class=\"main-header\" >${title}</span>\n" +
                "                <br />\n" +
                "                <br />\n" +
                "                    <span class=\"main-body\" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        String EMAIL_SUFFIX = "</span>\n" +
                "                    <br />\n" +
                "\n" +
                "            </div>\n" +
                "            <div class=\"circleAndline\">\n" +
                "                <div class=\"circle1\"></div>\n" +
                "                <div class=\"line\"></div>\n" +
                "                <div class=\"circle2\"></div>\n" +
                "            </div>\n" +
                "            <div class=\"footer\">\n" +
                "          <span class=\"footer-notice\" >\n" +
                "            ${platform}\n" +
                "          </span>\n" +
                "                <div class=\"footer-main\" >\n" +
                "                    <a  class=\"footer-web\" href=\"${frontUrl}\" target=\"_blank\">${frontUrl}\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"bottomInfo\">此为系统邮件，请勿回复</div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        /**
         * 不收敛
         */
        String NO_NCONVERGENCE = EMAIL_PREFIX + "您关注的API-「${apiName}」在「${envName}」出现错误异常，请登录<a style=\"text-decoration: none;font-size: 12px;color: #027AFF;\" href=\"${appUrl}\" target=\"_blank\" >iPaaS系统</a>查看。" + EMAIL_SUFFIX;
        /**
         * 错误预警模板
         */
        String ERROR_TYPE_TEMPLATE = NO_NCONVERGENCE;
        /**
         * 统计预警
         */
        String STATISTICAL_TYPE = "STATISTICAL";
        /**
         * 统计预警模板
         */
        String STATISTICAL_TYPE_TEMPLATE = EMAIL_PREFIX + "您关注的API-「${apiName}」在「${envName}」出现统计预警异常，请登录<a style=\"text-decoration: none;font-size: 12px;color: #027AFF;\" href=\"${appUrl} \" target=\"_blank\" >iPaaS系统</a>查看。" + EMAIL_SUFFIX;
        /**
         * API运行引擎预警
         */
        String RUNTIME_TYPE = "RUNTIME";
        /**
         * API运行引擎预警模板
         */
        String RUNTIME_TYPE_TEMPLATE = EMAIL_PREFIX + "${userName}，您好！您关注的API运行引擎触发预警，请登录<a style=\"text-decoration: none;font-size: 12px;color: #027AFF;\" href=\"${virtualServiceGroupUrl}  \" target=\"_blank\">iPaaS系统</a>查看。<div style=\"text-indent: 1em;\">${emailContent}</div>" + EMAIL_SUFFIX;
        /**
         * 第三方系统预警
         */
        String APPLICATION_SYSTEM_TYPE = "APPLICATION_SYSTEM";
        /**
         * 第三方系统预警模板
         */
        String APPLICATION_SYSTEM_TYPE_TEMPLATE = EMAIL_PREFIX + "${userName}，您好！您关注的第三方系统触发预警，包括${applicationContent}等，请登录<a style=\"text-decoration: none;font-size: 12px;color: #027AFF;\" href=\"${warningRecordManageUrl}  \" target=\"_blank\">iPaaS系统</a>查看。" + EMAIL_SUFFIX;
    }

    /**
     * Camel
     */
    interface Camel {
        String CAMEL_EXPRESSION_OR = " || ";
        String CAMEL_EXPRESSION_AND = " && ";

        String APPLICATION_DEFINITION = "<beans xmlns=\"http://www.springframework.org/schema/beans\"\n" +
                "       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "        xmlns:jdbc=\"http://www.springframework.org/schema/jdbc\"\n" +
                "        xmlns:camel=\"http://camel.apache.org/schema/spring\"\n" +
                "        xmlns:cxf=\"http://camel.apache.org/schema/cxf\"\n" +
                "       xsi:schemaLocation=\"\n" +
                "         http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd\n" +
                "         http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc.xsd\n" +
                "         http://camel.apache.org/schema/cxf http://camel.apache.org/schema/cxf/camel-cxf.xsd\n" +
                "         http://camel.apache.org/schema/spring http://camel.apache.org/schema/spring/camel-spring.xsd\">\n" +
                "      <bean id=\"stringRedisSerializer\" class=\"org.springframework.data.redis.serializer.StringRedisSerializer\" />\n" +
                "      <bean id=\"kafkaManualCommitProcess\" class=\"com.definesys.deipaas.apiflow.runtime.framework.camel.process.KafkaManualCommitProcess\" />\n" +
                "      <bean id=\"stringKafkaHeaderDeserializer\" class=\"com.definesys.deipaas.apiflow.runtime.framework.camel.core.StringKafkaHeaderDeserializer\"/>\n" +
//                "      <bean id=\"hazelcastLifecycle\" class=\"com.hazelcast.core.LifecycleService\"\n" +
//                "        factory-bean=\"hazelcastInstance\" factory-method=\"getLifecycleService\" destroy-method=\"shutdown\" />\n" +
//                "      <bean id=\"hazelcastConfig\" class=\"com.hazelcast.config.Config\" />\n" +
//                "      <bean id=\"hazelcastInstance\" class=\"com.hazelcast.core.Hazelcast\" factory-method=\"newHazelcastInstance\">\n" +
//                "        <constructor-arg type=\"com.hazelcast.config.Config\" ref=\"hazelcastConfig\"/>\n" +
//                "      </bean>\n" +
                "</beans>";
    }

    interface BulkAgent {
        String apiCode = "API编码";
        String apiName = "API名称";
        String path = "Path";
        String method = "请求方式";
        String apiDesc = "API描述";
        String apiClassification = "API分类";
        String tag = "标签";
        String ENVIRONMENT_SUFFIX = "代理地址-";

    }

    interface sqlTemplate {

        //存储过程生成SQL
        String GENERATE_PROCEDURES_SQL = "{ CALL %s(%s) }";
        //数据库查询生成SQL(无条件)
        String GENERATE_SELECT_SQL = "SELECT %s FROM %s ";
        //数据库查询生成SQL(有条件)
        String GENERATE_SELECT_SQL_WHERE = "SELECT %s FROM %s WHERE %s ";
        //数据库插入生成SQL
        String GENERATE_INSERT_SQL = "INSERT INTO %s (%s) VALUES (%s) ";
        //数据库更新生成SQL(必须有条件)
        String GENERATE_UPDATE_SQL = "UPDATE %s SET %s WHERE %s ";
        //数据库删除生成SQL(必须有条件)
        String GENERATE_DELETE_SQL = "DELETE FROM %s WHERE %s ";

        String ORACLE_COMMENT = "SELECT * FROM all_col_comments WHERE OWNER = '%s'  AND TABLE_NAME = '%s'  AND COLUMN_NAME = '%s' ";

        String SQLSERVER_COMMENT = "SELECT A.name AS TABLE_NAME, B.name AS COLUMN_NAME, C.value AS COMMENTS FROM sys.tables A INNER JOIN sys.columns B ON B.object_id = A.object_id LEFT JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id WHERE A.name = '%s' AND  B.name = '%s' ";
    }

    interface datasonnet {
        String DATASONNET = "datasonnet";
        String RESULT_TYPE = "resultType";
        String BODY_MEDIA_TYPE = "bodyMediaType";
        String OUTPUT_MEDIA_TYPE = "outputMediaType";
        String STRING_TYPE = "java.lang.String";
        String INTEGER_TYPE = "java.lang.Integer";
        String BOOLEAN_TYPE = "java.lang.Boolean";
        String APPLICATION_JSON = "application/json";
        String CONSTANT = "constant";
        String CUSTOM_OUTPUT_MEDIA_TYPE = "custom";
    }


    interface CustomHeader {
        String AUTHORIZATION = "Authorization";
        String DEIPAASKEYAUTH = "deipaaskeyauth";
        String AUTHORIZATION_LOW = "authorization";
        String I_CONSUMER_CODE = "i-consumer-code";

        String I_CATEGORY_CODE = "i-category-code";
    }

    interface MultiEnv {
        String ENV_CONFIG = "envConfig";
        String CONFIG_LIST = "configList";
        String CONFIG_JSON = "configJson";
        String ENV_LIST = "envList";
        String FORM_DATA = "formData";
    }

    interface PublicApprovalStatus {
        String UN_PUBLISHED = "unPublished";
        String PUBLISHED = "published";
        String IN_REVIEW = "inReview";
    }

    interface ApplyStatus {
        /**
         * 未授权
         */
        String UNAPPLY = "UNAPPLY";
        /**
         * 已授权
         */
        String APPLY = "APPLY";
        /**
         * 处理中
         */
        String PENDING = "PENDING";
        /**
         * 不需要授权
         */
        String UNPOLICY = "UNPOLICY";
    }

    interface AssignPointsRule {
        /**
         * 加
         */
        String PLUS = "PLUS";
        /**
         * 减
         */
        String REDUCE = "REDUCE";
    }

    interface MkUserType {
        /**
         * 普通用户
         */
        String PERSONAL_MANAGE = "personal_manage";
        /**
         * 市场管理员
         */
        String MARKET_MANAGE = "market_manage";
        /**
         * 租户管理员
         */
        String TENANT_MANAGE = "tenant_manage";
    }

    interface ShelfWay{
        /**
         * 发布至新服务
         */
        String NEW_SERVICE="newService";

        /**
         * 发布至已有服务
         */
        String EXISTING_SERVICE="existingService";
    }

    interface DefaultAuthority {
        String REAL_CALL = "realCall";
        String MOCK_CALL = "mockCall";
    }

    interface EDIT_TYPE {
        String REMOVE = "remove";
        String ADD = "add";
    }

    /**
     * 认证url后缀
     */
    interface AuthUrlSuffix {

        /**
         * AUTH2
         */
        String AUTH2_SUFFIX = "/oauth2/token";

        /**
         * JWT
         */
        String JWT_SUFFIX = "/jwt/token";
    }

    /**
     * 认证请求参数
     */
    interface AuthParam {

        /**
         * client_id
         */
        String CLIENT_ID = "client_id";

        /**
         * client_secret
         */
        String CLIENT_SECRET = "client_secret";

        /**
         * grant_type
         */
        String GRANT_TYPE = "grant_type";

        /**
         * token_type
         */
        String TOKEN_TYPE = "token_type";

        /**
         * access_token
         */
        String ACCESS_TOKEN = "access_token";

        /**
         * expires_in
         */
        String EXPIRES_IN = "expires_in";

        /**
         * 生效时间
         */
        String NBF = "nbf";

        /**
         * 过期时间
         */
        String EXP = "exp";
    }

    /**
     * 循环类型
     */
    interface LoopType {

        /**
         * 数组
         */
        String ARRAY = "array";

        /**
         * 条件
         */
        String CONDITION = "condition";

        /**
         * 字符串
         */
        String CHARACTER = "string";

        /**
         * 固定次数
         */
        String FIXED = "fixedTimes";
    }

    /**
     * api是否支持重试
     */
    interface RetryStatus {
        /**
         * 允许重试
         */
        String ENABLED = "ENABLED";
        /**
         * 不允许重试
         */
        String DISABLED = "DISABLED";

        /**
         * 失败时允许重试
         */
        String ERROR_ENABLED = "ERROR_ENABLED";
    }
}
