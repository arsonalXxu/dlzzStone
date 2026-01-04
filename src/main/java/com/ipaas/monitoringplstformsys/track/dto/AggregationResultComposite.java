package com.ipaas.monitoringplstformsys.track.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AggregationResultComposite {
    private List<CompositeBucket> buckets;
    private Map<String,String> afterKey;

    // Getters and setters


    @Data
    public static class CompositeKey {
        private String apiCode;
        private String consumerCode;
        private String errorName;

        // Getters and setters
    }

    @Data
    public static class CompositeBucket {
        private CompositeKey key;
        private long docCount;
        private long totalCalls;
        private long errorCount;
        private List<String> allRequestIds;
        private List<String> docIds;
        private BaseInfo baseInfo;

        // Getters and setters
    }

    @Data
    public static class BaseInfo {
        private String apiName;
        private String apiCode;
        private String consumerCode;
        private String categoryCode;
        private String categoryName;
        private String consumerName;
        private String result;
        private String aggregateTime;
        private String preResolveTime;
        private String errorName;

        // Getters and setters
    }
}
