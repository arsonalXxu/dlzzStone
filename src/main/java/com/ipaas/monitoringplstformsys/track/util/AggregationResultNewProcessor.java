package com.ipaas.monitoringplstformsys.track.util;

import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipaas.monitoringplstformsys.track.dto.ApiConsumerStat;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregationResultNewProcessor {

    public static ApiConsumerStat processAggregationResult(SearchResponse<JsonNode> response) {
        ApiConsumerStat result = new ApiConsumerStat();

        if (CollectionUtils.isEmpty(response.hits().hits())) {
            return result;
        }

        ObjectMapper mapper = new ObjectMapper();

        // 获取顶层聚合
        Aggregate byApiConsumerAgg = response.aggregations().get("by_api_consumer");

        // 处理复合聚合
        if (byApiConsumerAgg != null && byApiConsumerAgg.isComposite()) {
            CompositeAggregate compositeAgg = byApiConsumerAgg.composite();

            // 处理 after_key
            if (compositeAgg.afterKey() != null) {
                ApiConsumerStat.CompositeKey afterKey = new ApiConsumerStat.CompositeKey();
                afterKey.setApiCode(compositeAgg.afterKey().get("apiCode").stringValue());
                afterKey.setConsumerCode(compositeAgg.afterKey().get("consumerCode").stringValue());
                result.setAfterKey(afterKey);
            }

            // 处理每个桶
            List<ApiConsumerStat.CompositeBucket> buckets = new ArrayList<>();
            for (CompositeBucket bucket : compositeAgg.buckets().array()) {
                ApiConsumerStat.CompositeBucket compositeBucket = processCompositeBucket(bucket, mapper);
                buckets.add(compositeBucket);
            }
            result.setBuckets(buckets);
        }

        return result;
    }

    private static ApiConsumerStat.CompositeBucket processCompositeBucket(CompositeBucket bucket, ObjectMapper mapper) {
        ApiConsumerStat.CompositeBucket compositeBucket = new ApiConsumerStat.CompositeBucket();

        // 1. 处理复合键
        ApiConsumerStat.CompositeKey key = new ApiConsumerStat.CompositeKey();
        key.setApiCode(bucket.key().get("apiCode").stringValue());
        key.setConsumerCode(bucket.key().get("consumerCode").stringValue());
        compositeBucket.setKey(key);

        // 2. 基础计数
        compositeBucket.setDocCount(bucket.docCount());

        // 3. 总调用次数
        Aggregate totalCallsAgg = bucket.aggregations().get("total_calls");
        if (totalCallsAgg != null && totalCallsAgg.isValueCount()) {
            ValueCountAggregate valueCount = totalCallsAgg.valueCount();
            compositeBucket.setTotalCalls((long) valueCount.value());
        } else {
            compositeBucket.setTotalCalls(0L);
        }

        // 4. 处理基础信息
        Aggregate basicInfoAgg = bucket.aggregations().get("basic_info");
        if (basicInfoAgg != null && basicInfoAgg.isTopHits()) {
            TopHitsAggregate topHits = basicInfoAgg.topHits();
            if (!topHits.hits().hits().isEmpty()) {
                Hit<JsonData> hit = topHits.hits().hits().get(0);
                JsonData jsonData = hit.source();
                ObjectMapper objectMapper = new ObjectMapper();
                JacksonJsonpMapper jsonpMapper = new JacksonJsonpMapper(objectMapper);
                ApiConsumerStat.BasicInfo basicInfo = jsonData.to(ApiConsumerStat.BasicInfo.class, jsonpMapper);
                compositeBucket.setBasicInfo(basicInfo);
            }
        }

//        Aggregate allRequestIdsAgg = bucket.aggregations().get("all_request_ids");
//        if (allRequestIdsAgg != null && allRequestIdsAgg.isFilters()) {
//            FiltersAggregate filtersAgg = allRequestIdsAgg.filters();
//
//            // 1. 获取 "has_exception" 桶
//            Map<String, FiltersBucket> buckets = filtersAgg.buckets().keyed();
//            FiltersBucket hasExceptionBucket = buckets.get("has_exception");
//
//            if (hasExceptionBucket != null && hasExceptionBucket.docCount() > 0) {
//                // 2. 从桶中获取 "requestId" 子聚合
//                Aggregate requestIdAgg = hasExceptionBucket.aggregations().get("requestId");
//
//                if (requestIdAgg != null && requestIdAgg.isTopHits()) {
//                    TopHitsAggregate topHits = requestIdAgg.topHits();
//                    List<String> allRequestIdStrList = new ArrayList<>();
//
//                    // 3. 处理每个命中的文档
//                    for (Hit<JsonData> hit : topHits.hits().hits()) {
//                        JsonData jsonData = hit.source();
//                        if (jsonData != null) {
//                            // 更高效的处理方式
//                            Map<String, Object> sourceMap = jsonData.to(Map.class);
//                            String requestId = (String) sourceMap.get("requestId");
//
//                            if (requestId != null) {
//                                allRequestIdStrList.add(requestId);
//                            }
//                        }
//                    }
//
//                    compositeBucket.setAllRequestIds(allRequestIdStrList);
//                }
//            }
//        }


        // 5. 处理异常分析
        Map<String, ApiConsumerStat.ExceptionAnalysis> exceptionsSplit = new HashMap<>();
        processExceptionsSplit(bucket, exceptionsSplit);
        compositeBucket.setExceptionsSplit(exceptionsSplit);

        // 6. 处理业务错误分析
        Map<String, ApiConsumerStat.BizErrorAnalysis> bizErrorSplit = new HashMap<>();
        processBizErrorSplit(bucket, bizErrorSplit);
        compositeBucket.setBizErrorSplit(bizErrorSplit);

        return compositeBucket;
    }

    private static void processExceptionsSplit(
            CompositeBucket bucket,
            Map<String, ApiConsumerStat.ExceptionAnalysis> exceptionsSplit) {

        // 获取异常分析聚合
        Aggregate exceptionsAgg = bucket.aggregations().get("exceptions_split");
        if (exceptionsAgg != null && exceptionsAgg.isFilters()) {
            Map<String, FiltersBucket> filterBuckets = exceptionsAgg.filters().buckets().keyed();

            // 处理 has_exception
            if (filterBuckets.containsKey("has_exception")) {
                FiltersBucket hasExceptionBucket = filterBuckets.get("has_exception");
                ApiConsumerStat.ExceptionAnalysis hasException = new ApiConsumerStat.ExceptionAnalysis();
                hasException.setDocCount(hasExceptionBucket.docCount());
                hasException.setExceptionNames(processExceptionNames(hasExceptionBucket));
//                hasException.setExceptionRequestIds(processExceptionRequestId(hasExceptionBucket));
                exceptionsSplit.put("has_exception", hasException);
            }
        }
    }

    private static List<ApiConsumerStat.ExceptionNameBucket> processExceptionNames(
            FiltersBucket bucket) {

        List<ApiConsumerStat.ExceptionNameBucket> result = new ArrayList<>();
        Aggregate exceptionNamesAgg = bucket.aggregations().get("exception_names");

        if (exceptionNamesAgg != null && exceptionNamesAgg.isSterms()) {
            Buckets<StringTermsBucket> termsBuckets = exceptionNamesAgg.sterms().buckets();
            if (termsBuckets != null && termsBuckets.array() != null) {
                for (StringTermsBucket term : termsBuckets.array()) {
                    ApiConsumerStat.ExceptionNameBucket nameBucket = new ApiConsumerStat.ExceptionNameBucket();
                    nameBucket.setKey(term.key().stringValue());
                    nameBucket.setDocCount(term.docCount());
                    List<String> requestIdList = new ArrayList<>();
                    Buckets<StringTermsBucket> errorRequestId = term.aggregations().get("errorRequestId").sterms().buckets();
                    errorRequestId.array().forEach(t -> {
                        requestIdList.add(t.key().stringValue());
                    });
                    nameBucket.setRequestId(requestIdList);
                    result.add(nameBucket);
                }
            }
        }

        return result;
    }

    private static void processBizErrorSplit(
            CompositeBucket bucket,
            Map<String, ApiConsumerStat.BizErrorAnalysis> bizErrorSplit) {

        // 获取业务错误分析聚合
        Aggregate bizErrorAgg = bucket.aggregations().get("bizError_split");
        if (bizErrorAgg != null && bizErrorAgg.isFilters()) {
            Map<String, FiltersBucket> filterBuckets = bizErrorAgg.filters().buckets().keyed();

            // 处理 has_bizError
            if (filterBuckets.containsKey("has_bizError")) {
                FiltersBucket hasBizErrorBucket = filterBuckets.get("has_bizError");
                ApiConsumerStat.BizErrorAnalysis hasBizError = new ApiConsumerStat.BizErrorAnalysis();
                hasBizError.setDocCount(hasBizErrorBucket.docCount());
                hasBizError.setBizErrorNames(processBizErrorNames(hasBizErrorBucket));
                bizErrorSplit.put("has_bizError", hasBizError);
            }
        }
    }

    private static List<ApiConsumerStat.BizErrorNameBucket> processBizErrorNames(
            FiltersBucket bucket) {

        List<ApiConsumerStat.BizErrorNameBucket> result = new ArrayList<>();
        Aggregate bizErrorNamesAgg = bucket.aggregations().get("bizError_names");
        Aggregate stateNamesAgg = bizErrorNamesAgg.nested().aggregations().get("state_names");

        if (stateNamesAgg != null && stateNamesAgg.isSterms()) {

            // 获取嵌套聚合
            Buckets<StringTermsBucket> termsBuckets = stateNamesAgg.sterms().buckets();
            if (termsBuckets != null && termsBuckets.isArray()) {
                for (StringTermsBucket term : termsBuckets.array()) {
                    ApiConsumerStat.BizErrorNameBucket nameBucket = new ApiConsumerStat.BizErrorNameBucket();
                    nameBucket.setKey(term.key().stringValue());
                    nameBucket.setDocCount(term.docCount());
                    Aggregate backToRoot = term.aggregations().get("back_to_root");
                    if (backToRoot != null && backToRoot.isReverseNested()) {
                        Buckets<StringTermsBucket> buckets = backToRoot.reverseNested().aggregations().get("errorRequestId").sterms().buckets();
                        List<String> requestIdList = new ArrayList<>();
                        buckets.array().forEach(t -> {
                            requestIdList.add(t.key().stringValue());
                        });
                        nameBucket.setRequestId(requestIdList);
                        result.add(nameBucket);
                    }
                }
            }
        }

        return result;
    }


    private static List<ApiConsumerStat.BizErrorRequestIdBucket> processBizErrorRequestId(
            FiltersBucket bucket) {

        List<ApiConsumerStat.BizErrorRequestIdBucket> result = new ArrayList<>();
        Aggregate errorRequestIdAgg = bucket.aggregations().get("error_request_id");

        if (errorRequestIdAgg != null && errorRequestIdAgg.isSterms()) {
            Buckets<StringTermsBucket> termsBuckets = errorRequestIdAgg.sterms().buckets();
            if (termsBuckets != null && termsBuckets.array() != null) {
                for (StringTermsBucket term : termsBuckets.array()) {
                    ApiConsumerStat.BizErrorRequestIdBucket requestIdBucket = new ApiConsumerStat.BizErrorRequestIdBucket();
                    requestIdBucket.setKey(term.key().stringValue());
                    requestIdBucket.setDocCount(term.docCount());
                    result.add(requestIdBucket);
                }
            }
        }

        return result;
    }
}