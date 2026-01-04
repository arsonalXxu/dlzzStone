package com.ipaas.monitoringplstformsys.track;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合结果
 */
@Data
public class AggregationResult implements Serializable {
    /**
     * ID唯一值 （暂定为存储可以查询日志的唯一值）
     */
    private String id;

    /**
     * Api编码
     */
    @JsonProperty("apiCode")
    private String apiCode;

    /**
     * 消费者编码
     */
    @JsonProperty("consumerCode")
    private String consumerCode;

    /**
     * Api名称
     */
    @JsonProperty("apiName")
    private String apiName;

    /**
     * 应用系统编码
     */
    @JsonProperty("categoryCode")
    private String categoryCode;

    /**
     * 应用系统编码
     */
    @JsonProperty("categoryName")
    private String categoryName;

    /**
     * 消费者名称
     */
    @JsonProperty("consumerName")
    private String consumerName;

    /**
     * 总调用次数
     */
    @JsonProperty("totalCalls")
    private Long totalCalls;

    /**
     * 错误次数
     */
    @JsonProperty("errorCount")
    private Long errorCount;

    /**
     * 错误类型
     */
    @JsonProperty("errorName")
    private String errorName;

    /**
     * 预计解决时间
     */
    @JsonProperty("preResolveTime")
    private String preResolveTime;

    /**
     * 处理人
     */
    @JsonProperty("processor")
    private List<String> processor;

    /**
     * 处理结果
     */
    @JsonProperty("result")
    private String result;

    /**
     * 请求报文
     */
    @JsonProperty("requestBody")
    private String requestBody;
    /**
     * 返回报文
     */
    @JsonProperty("responseBody")
    private String responseBody;

    /**
     * 聚合数据时间
     */
    @JsonProperty("aggregateTime")
    private String aggregateTime;

    @JsonProperty("allRequestIds")
    private List<String> allRequestIds;

}
