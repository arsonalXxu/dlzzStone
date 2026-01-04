package com.ipaas.monitoringplstformsys.track.util;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipaas.monitoringplstformsys.track.dto.AggregationResultComposite;

import java.util.*;

public class AggregationResultProcessor {


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final JacksonJsonpMapper JSONP_MAPPER = new JacksonJsonpMapper(OBJECT_MAPPER);

    public static AggregationResultComposite processCompositeAggregation(SearchResponse<JsonNode> response) {
        AggregationResultComposite result = new AggregationResultComposite();

        if (response.aggregations() == null) {
            return result;
        }

        // 获取顶层复合聚合
        Aggregate compositeAgg = response.aggregations().get("res");


        if (compositeAgg != null && compositeAgg.isComposite()) {
            CompositeAggregate composite = compositeAgg.composite();

            if (composite.afterKey() != null) {
                AggregationResultComposite.CompositeKey afterKey = new AggregationResultComposite.CompositeKey();
                Map<String, String> map = new HashMap<>();

                Map<String, FieldValue> stringFieldValueMap = composite.afterKey();

                stringFieldValueMap.forEach((field, value) -> {
                    map.put(field, value.isNull()? "" : value.stringValue());
                });
                result.setAfterKey(map);
            }


            // 处理每个桶
            List<AggregationResultComposite.CompositeBucket> buckets = new ArrayList<>();
            for (CompositeBucket bucket : composite.buckets().array()) {
                buckets.add(processCompositeBucket(bucket));
            }
            result.setBuckets(buckets);
        }

        return result;
    }

    private static AggregationResultComposite.CompositeBucket processCompositeBucket(CompositeBucket bucket) {
        AggregationResultComposite.CompositeBucket compositeBucket = new AggregationResultComposite.CompositeBucket();


        AggregationResultComposite.CompositeKey key = new AggregationResultComposite.CompositeKey();
        key.setApiCode(bucket.key().get("apiCode").stringValue());
        key.setConsumerCode(bucket.key().get("consumerCode").stringValue());
        key.setErrorName(bucket.key().get("errorName").isNull() ? "" : bucket.key().get("errorName").stringValue());

        compositeBucket.setKey(key);


        Map<String, Aggregate> aggregations = bucket.aggregations();

        // 3.1 总调用次数
        Optional.ofNullable(aggregations.get("total_calls"))
                .filter(Aggregate::isSum)
                .map(Aggregate::sum)
                .ifPresent(sum -> compositeBucket.setTotalCalls((long) sum.value()));

        // 3.2 错误计数
        Optional.ofNullable(aggregations.get("error_count"))
                .filter(Aggregate::isSum)
                .map(Aggregate::sum)
                .ifPresent(sum -> compositeBucket.setErrorCount((long) sum.value()));


        // 3.3 所有请求ID
        Optional.ofNullable(aggregations.get("all_request_ids"))
                .filter(Aggregate::isSterms)
                .map(Aggregate::sterms)
                .map(StringTermsAggregate::buckets)
                .map(Buckets::array)
                .ifPresent(termsBuckets -> {
                    List<String> requestIds = new ArrayList<>();
                    for (StringTermsBucket term : termsBuckets) {
                        requestIds.add(term.key().stringValue());
                    }
                    compositeBucket.setAllRequestIds(requestIds);
                });

        Optional.ofNullable(aggregations.get("doc_ids"))
                .filter(Aggregate::isTopHits)
                .map(Aggregate::topHits)
                .ifPresent(topHits -> {
                    List<String> docIds = new ArrayList<>();
                    for (Hit<JsonData> hit : topHits.hits().hits()) {
                        docIds.add(hit.id());
                    }
                    compositeBucket.setDocIds(docIds);
                });

        // 3.4 基础信息
        Optional.ofNullable(aggregations.get("base_info"))
                .filter(Aggregate::isTopHits)
                .map(Aggregate::topHits)
                .map(TopHitsAggregate::hits)
                .map(hits -> hits.hits())
                .filter(hits -> !hits.isEmpty())
                .map(hits -> hits.get(0).source())
                .ifPresent(jsonData -> {
                    AggregationResultComposite.BaseInfo baseInfo = jsonData.to(AggregationResultComposite.BaseInfo.class, JSONP_MAPPER);
                    compositeBucket.setBaseInfo(baseInfo);
                });

        return compositeBucket;
    }

}
