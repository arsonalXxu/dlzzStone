package com.ipaas.monitoringplstformsys.track.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
public class HitResultVo implements Serializable {

    public final static String REQUEST_ID = "requestId";
    public final static String VERSION_CONFIG_ID = "versionConfigId";
    public final static String LOG_TYPE = "logType";
    public final static String API_NAME = "apiName";
    public final static String RETRY_DATA = "retryData";
    public final static String IS_PROD = "isProd";
    public final static String REQUEST_METHOD = "requestMethod";
    public final static String SERVER_GROUP = "serverGroup";
    public final static String TENANT_CODE = "tenantCode";
    public final static String CATEGORY_NAME = "categoryName";
    public final static String RESPONSE_CODE = "responseCode";
    public final static String API_VERSION = "apiVersion";
    public final static String ENV_CODE = "envCode";
    public final static String TENANT_NAME = "tenantName";
    public final static String REQUEST_URL = "requestUrl";
    public final static String EXCEPTION_KNOWLEDGE = "exceptionKnowledge";
    public final static String CONSUMER_CODE = "consumerCode";
    public final static String AUTH_TYPE_NAME = "authTypeName";
    public final static String API_ID = "apiId";
    public final static String TIMESTAMP = "timestamp";
    public final static String TRACE_ID = "traceId";
    public final static String AUTH_TYPE_CODE = "authTypeCode";
    public final static String API_CODE = "apiCode";
    public final static String RESPONSE_TIME = "responseTime";
    public final static String CONSUMER_ID = "consumerId";
    public final static String BIZ_STATE = "bizState";
    public final static String ENV_ID = "envId";
    public final static String CATEGORY_CODE = "categoryCode";
    public final static String REQUEST_TIME = "requestTime";
    public final static String ENV_NAME = "envName";
    public final static String ATTRIBUTE7 = "attribute7";
    public final static String CLIENT_IP = "clientIp";
    public final static String ATTRIBUTE6 = "attribute6";
    public final static String TENANT_ID = "tenantId";
    public final static String RESPONSE_INTERVAL = "responseInterval";
    public final static String RESPONSE_BODY_SIZE = "responseBodySize";
    public final static String BIZ_KEYWORD = "bizKeyword";
    public final static String CATEGORY_ID = "categoryId";
    public final static String API_TYPE = "apiType";
    public final static String REQUEST_BODY_SIZE = "requestBodySize";
    public final static String CONSUMER_NAME = "consumerName";

    private String requestId;

    private String versionConfigId;

    private String logType;

    private String apiName;

    private boolean retryData;

    private boolean isProd;

    private String requestMethod;

    private String serverGroup;

    private String tenantCode;

    private String categoryName;

    private String responseCode;

    private String apiVersion;

    private String envCode;

    private String tenantName;

    private String requestUrl;

    private JSONObject exceptionKnowledge;

    private String consumerCode;

    private String authTypeName;

    private String apiId;

    private String timestamp;

    private String traceId;

    private String authTypeCode;

    private String apiCode;

    private String responseTime;

    private String consumerId;

    private List bizState;

    private String envId;

    private String categoryCode;

    private String requestTime;

    private String envName;

    private String attribute7;

    private String clientIp;

    private String attribute6;

    private String tenantId;

    private long responseInterval;

    private long responseBodySize;

    private List bizKeyword;

    private String categoryId;

    private String apiType;

    private long requestBodySize;

    private String consumerName;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class BizState {
        private String id;

        private String stateInfoColor;

        private String stateInfoName;

        private String stateInfoStats;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class BizKeyword {
        private String type;

        private String value;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class ExceptionKnowledge {
        private String id;

        private String exceptionCode;

        private String exceptionName;
    }


}
