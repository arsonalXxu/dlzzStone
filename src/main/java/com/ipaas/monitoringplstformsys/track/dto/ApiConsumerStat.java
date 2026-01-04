package com.ipaas.monitoringplstformsys.track.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ApiConsumerStat {
    // 复合聚合键
    @Data
    public static class CompositeKey {
        private String apiCode;
        private String consumerCode;
        // getters/setters
    }

    // 基础信息
    @Data
    public static class BasicInfo implements Serializable {
        private String apiName;
        private String categoryCode;
        private String categoryName;
        private String consumerCode;
        private String consumerName;
        // getters/setters
    }

    // 异常名称分布
    @Data
    public static class ExceptionNameBucket {
        private String key;
        private long docCount;
        List<String> requestId;
        // getters/setters
    }

    // 业务错误名称分布
    @Data
    public static class BizErrorNameBucket {
        private String key;
        private long docCount;
        List<String> requestId;
        // getters/setters
    }

    @Data
    public static class BizErrorRequestIdBucket {
        private String key;
        private long docCount;
        // getters/setters
    }

    // 异常分析
    @Data
    public static class ExceptionAnalysis {
        private long docCount;
        private List<ExceptionNameBucket> exceptionNames;
        // getters/setters
    }

    // 业务错误分析
    @Data
    public static class BizErrorAnalysis {
        private long docCount;
        private List<BizErrorNameBucket> bizErrorNames;
        // getters/setters
    }

    // 主聚合桶结构
    @Data
    public static class CompositeBucket {
        private CompositeKey key;
        private long docCount;
        private long totalCalls;
        private BasicInfo basicInfo;
        private List<String> allRequestIds;
        private Map<String, ExceptionAnalysis> exceptionsSplit;
        private Map<String, BizErrorAnalysis> bizErrorSplit;
        // getters/setters
    }

    // 完整响应结构
    private CompositeKey afterKey;
    private List<CompositeBucket> buckets;
    // getters/setters
}
