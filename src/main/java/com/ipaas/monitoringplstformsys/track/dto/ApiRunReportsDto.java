package com.ipaas.monitoringplstformsys.track.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class ApiRunReportsDto {

    /**
     * Api编码
     */
    @JsonProperty("apiCode")
    private String apiCode;
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
     * 系统名称
     */
    @JsonProperty("categoryName")
    private String categoryName;

    /**
     * 总调用次数
     */
    @JsonProperty("totalCalls")
    private Long totalCalls;

    /**
     * 已处理次数
     */
    @JsonProperty("resultCount")
    private Long resultCount;
    /**
     * 未处理次数
     */
    @JsonProperty("unResultCount")
    private Long unResultCount;
    /**
     * 错误次数
     */
    @JsonProperty("errorCount")
    private Long errorCount;
    /**
     * 预计解决时间
     */
    @JsonProperty("preResolveTime")
    private String preResolveTime;
    /**
     * 处理结果
     */
    @JsonProperty("result")
    private String result;
    /**
     * 处理人
     */
    @JsonProperty("processor")
    private List<String> processor;
    /**
     * 该条数据插入时间
     */
    @JsonProperty("aggregateTime")
    private String aggregateTime;

}
