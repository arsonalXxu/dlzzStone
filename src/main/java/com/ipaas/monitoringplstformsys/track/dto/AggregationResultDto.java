package com.ipaas.monitoringplstformsys.track.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class AggregationResultDto {

    /**
     * ID唯一值 （暂定为存储可以查询日志的唯一值）
     */
    private List<String> docIds;

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
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

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

    @JsonProperty("preResolveTime")
    private String preResolveTime;


    private Map<String,String> afterKey;

}
