package com.ipaas.monitoringplstformsys.module.vo;
import lombok.Data;

import java.util.Map;

@Data
public class ApiConsumerStat {
    private String apiCode;
    private String consumerCode;
    private String apiName;
    private String categoryCode;
    private String categoryName;
    private String consumerName;
    private Long totalCalls;
    private Map<String, Long> exceptionStats; // 异常名称 -> 计数
    private Map<String, Long> bizErrorStats;  // 业务错误名称 -> 计数
}