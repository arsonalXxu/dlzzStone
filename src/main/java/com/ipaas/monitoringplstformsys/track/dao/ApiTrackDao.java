//package com.ipaas.monitoringplstformsys.elastic.dao;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch._types.FieldValue;
//import co.elastic.clients.elasticsearch._types.aggregations.*;
//import co.elastic.clients.elasticsearch._types.query_dsl.*;
//import co.elastic.clients.elasticsearch.core.SearchResponse;
//import co.elastic.clients.elasticsearch.core.search.Hit;
//import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
//import co.elastic.clients.json.JsonData;
//import com.ipaas.monitoringplstformsys.elastic.AggregationResult;
//import com.ipaas.monitoringplstformsys.elastic.EsQueryDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.io.IOException;
//import java.util.*;
//
//@Repository
//public class ElasticsearchDao {
//
//    private final ElasticsearchClient client;
//
//    @Autowired
//    public ElasticsearchDao(ElasticsearchClient client) {
//        this.client = client;
//    }
//
//    public List<AggregationResult> queryLogs(EsQueryDto queryDto) throws IOException {
//        // 1. 构建查询条件
//        Query boolQuery = buildBoolQuery(queryDto);
//
//        // 2. 构建聚合
//        Aggregation compositeAgg = buildCompositeAggregation();
//
//        // 3. 执行查询
//        SearchResponse<Object> response = client.search(s -> s
//                        .index("ipaas_apiflow_base_log_701100155771814912")
//                        .size(0)
//                        .query(boolQuery)
//                        .aggregations("by_api_consumer", compositeAgg),
//                Object.class
//        );
//
//        // 4. 解析聚合结果
//        return parseAggregationResponse(response);
//    }
//
//    private Query buildBoolQuery(EsQueryDto queryDto) {
//        // 创建范围查询 - 动态时间范围
//        Query rangeQuery = new Query.Builder()
//                .range(r -> r
//                        .field("requestTime")
//                        .gte(JsonData.of(queryDto.getStartTime()))
//                        .lt(JsonData.of(queryDto.getEndTime()))
//                )
//                .build();
//
//        // 创建术语查询 - 动态API编码列表
//        Query termsQuery = new Query.Builder()
//                .terms(t -> t
//                        .field("apiCode.keyword")
//                        .terms(ts -> ts.value(queryDto.getApiCodes().stream()
//                                .map(FieldValue::of)
//                                .toList()))
//                )
//                .build();
//
//        // 构建布尔查询
//        return new Query.Builder()
//                .bool(b -> b
//                        .filter(rangeQuery, termsQuery)
//                )
//                .build();
//    }
//
//    private Aggregation buildCompositeAggregation() {
//        // 1. 构建复合聚合源
//        List<CompositeAggregationSource> sources = Arrays.asList(
//                new CompositeAggregationSource.Builder()
//                        .terms(t -> t.field("apiCode.keyword"))
//                        .build(),
//                new CompositeAggregationSource.Builder()
//                        .terms(t -> t.field("consumerCode.keyword"))
//                        .build()
//        );
//
//        // 2. 构建基础信息聚合
//        Aggregation basicInfoAgg = new Aggregation.Builder()
//                .topHits(th -> th
//                        .size(1)
//                        .source(sf -> sf.filter(f -> f.includes(
//                                "apiName", "categoryCode", "categoryName", "consumerCode", "consumerName")))
//                )
//                .build();
//
//        // 3. 构建总调用量聚合
//        Aggregation totalCallsAgg = new Aggregation.Builder()
//                .valueCount(vc -> vc.field("requestId.keyword"))
//                .build();
//
//        // 4. 构建异常分割聚合
//        Aggregation exceptionsSplitAgg = buildExceptionsSplitAggregation();
//
//        // 5. 构建业务错误分割聚合
//        Aggregation bizErrorSplitAgg = buildBizErrorSplitAggregation();
//
//        // 6. 构建完整的复合聚合
//        return new Aggregation.Builder()
//                .composite(c -> c
//                        .sources(sources)
//                        .size(1000)
//                        .aggregations("basic_info", basicInfoAgg)
//                        .aggregations("total_calls", totalCallsAgg)
//                        .aggregations("exceptions_split", exceptionsSplitAgg)
//                        .aggregations("bizError_split", bizErrorSplitAgg)
//                )
//                .build();
//    }
//
//    private Aggregation buildExceptionsSplitAggregation() {
//        // 创建异常查询条件
//        Map<String, Query> filters = new HashMap<>();
//
//        // 有异常查询 - 固定值
//        Query hasExceptionQuery = new Query.Builder()
//                .bool(b -> b
//                        .mustNot(
//                                new Query.Builder().prefix(p -> p.field("responseCode").value("2")).build(),
//                                new Query.Builder().prefix(p -> p.field("responseCode").value("4")).build()
//                        )
//                )
//                .build();
//
//        // 无异常查询 - 固定值
//        Query noExceptionQuery = new Query.Builder()
//                .bool(b -> b
//                        .should(
//                                new Query.Builder().prefix(p -> p.field("responseCode").value("2")).build(),
//                                new Query.Builder().prefix(p -> p.field("responseCode").value("4")).build()
//                        )
//                        .minimumShouldMatch("1")
//                )
//                .build();
//
//        filters.put("has_exception", hasExceptionQuery);
//        filters.put("no_exception", noExceptionQuery);
//
//        // 构建异常名称聚合 - 固定值
//        Aggregation exceptionNamesAgg = new Aggregation.Builder()
//                .terms(t -> t
//                        .field("exceptionKnowledge.exceptionName.keyword")
//                        .size(10)
//                        .missing("未识别"))
//                .build();
//
//        // 构建异常分割聚合
//        return new Aggregation.Builder()
//                .filters(fs -> fs
//                        .filters(filters)
//                        .aggregations("exception_names", exceptionNamesAgg)
//                )
//                .build();
//    }
//
//    private Aggregation buildBizErrorSplitAggregation() {
//        // 创建业务错误查询条件
//        Map<String, Query> filters = new HashMap<>();
//
//        // 有业务错误查询 - 固定值
//        Query hasBizErrorQuery = new Query.Builder()
//                .nested(n -> n
//                        .path("bizState")
//                        .query(q -> q.bool(b -> b
//                                        .must(new Query.Builder().exists(e -> e.field("bizState.stateInfoName.keyword")).build())
//                                        .mustNot(new Query.Builder().term(t -> t.field("bizState.stateInfoName.keyword").value("业务成功")).build())
//                                )
//                        )
//                        .build();
//
//        // 无业务错误查询 - 固定值
//        Query noBizErrorQuery = new Query.Builder()
//                .bool(b -> b
//                        .should(
//                                new Query.Builder().nested(n -> n
//                                                        .path("bizState")
//                                                        .query(q -> q.term(t -> t.field("bizState.stateInfoName.keyword").value("业务成功"))).build(),
//                                                new Query.Builder().bool(bb -> bb
//                                                                .mustNot(new Query.Builder().nested(n -> n
//                                                                        .path("bizState")
//                                                                        .query(q -> q.exists(e -> e.field("bizState.stateInfoName.keyword"))).build())
//                                                                ).build()
//                                                        )
//                                                        .minimumShouldMatch("1")
//                                        )
//                                        .build();
//
//        filters.put("has_bizError", hasBizErrorQuery);
//        filters.put("no_bizError", noBizErrorQuery);
//
//        // 构建业务错误详情聚合 - 固定值
//        Aggregation stateNamesAgg = new Aggregation.Builder()
//                .terms(t -> t
//                        .field("bizState.stateInfoName.keyword")
//                        .size(10)
//                        .missing("无"))
//                .build();
//
//        Aggregation bizErrorNamesAgg = new Aggregation.Builder()
//                .nested(n -> n.path("bizState"))
//                .aggregations("state_names", stateNamesAgg)
//                .build();
//
//        // 构建业务错误分割聚合
//        return new Aggregation.Builder()
//                .filters(fs -> fs
//                        .filters(filters)
//                        .aggregations("bizError_names", bizErrorNamesAgg)
//                )
//                .build();
//    }
//
//    private List<AggregationResult> parseAggregationResponse(SearchResponse<Object> response) {
//        List<AggregationResult> results = new ArrayList<>();
//
//        // 检查聚合结果是否存在
//        if (response.aggregations() == null ||
//                response.aggregations().get("by_api_consumer") == null) {
//            return results;
//        }
//
//        // 获取复合聚合结果
//        CompositeAggregate compositeAgg = response.aggregations()
//                .get("by_api_consumer").composite();
//
//        // 检查桶是否存在
//        if (compositeAgg.buckets() == null || compositeAgg.buckets().array() == null) {
//            return results;
//        }
//
//        // 遍历每个桶
//        for (CompositeBucket bucket : compositeAgg.buckets().array()) {
//            AggregationResult result = new AggregationResult();
//
//            // 解析主键
//            result.setApiCode(bucket.key().get("apiCode").toString());
//            result.setConsumerCode(bucket.key().get("consumerCode").toString());
//
//            // 解析基础信息
//            parseBasicInfo(bucket, result);
//
//            // 解析总调用量
//            parseTotalCalls(bucket, result);
//
//            // 解析异常信息
//            parseExceptions(bucket, result);
//
//            // 解析业务错误
//            parseBizErrors(bucket, result);
//
//            results.add(result);
//        }
//
//        return results;
//    }
//
//    private void parseBasicInfo(CompositeBucket bucket, AggregationResult result) {
//        if (bucket.aggregations().get("basic_info") == null) return;
//
//        TopHitsAggregate basicInfo = bucket.aggregations().get("basic_info").topHits();
//        HitsMetadata<Object> hits = basicInfo.hits();
//
//        if (hits.total() != null && hits.total().value() > 0 && !hits.hits().isEmpty()) {
//            Hit<Object> hit = hits.hits().get(0);
//            if (hit.source() != null) {
//                Map<String, Object> source = (Map<String, Object>) hit.source();
//                result.setApiName(getString(source, "apiName"));
//                result.setCategoryCode(getString(source, "categoryCode"));
//                result.setCategoryName(getString(source, "categoryName"));
//                result.setConsumerName(getString(source, "consumerName"));
//            }
//        }
//    }
//
//    private void parseTotalCalls(CompositeBucket bucket, AggregationResult result) {
//        if (bucket.aggregations().get("total_calls") == null) return;
//
//        ValueCountAggregate totalCalls = bucket.aggregations().get("total_calls").valueCount();
//        result.setTotalCalls((long) totalCalls.value());
//    }
//
//    private void parseExceptions(CompositeBucket bucket, AggregationResult result) {
//        if (bucket.aggregations().get("exceptions_split") == null) return;
//
//        FiltersAggregate exceptions = bucket.aggregations().get("exceptions_split").filters();
//        Map<String, FiltersBucket> bucketsMap = exceptions.buckets().keyed();
//
//        if (bucketsMap != null && bucketsMap.containsKey("has_exception")) {
//            FiltersBucket hasException = bucketsMap.get("has_exception");
//            result.setExceptionCount(hasException.docCount());
//
//            if (hasException.aggregations().get("exception_names") != null) {
//                StringTermsAggregate exceptionNames = hasException.aggregations().get("exception_names").sterms();
//                Map<String, Long> exceptionDetails = new HashMap<>();
//
//                if (exceptionNames.buckets() != null && exceptionNames.buckets().array() != null) {
//                    for (StringTermsBucket bucketItem : exceptionNames.buckets().array()) {
//                        exceptionDetails.put(bucketItem.key().stringValue(), bucketItem.docCount());
//                    }
//                }
//
//                result.setExceptionDetails(exceptionDetails);
//            }
//        }
//    }
//
//    private void parseBizErrors(CompositeBucket bucket, AggregationResult result) {
//        if (bucket.aggregations().get("bizError_split") == null) return;
//
//        FiltersAggregate bizErrors = bucket.aggregations().get("bizError_split").filters();
//        Map<String, FiltersBucket> bucketsMap = bizErrors.buckets().keyed();
//
//        if (bucketsMap != null && bucketsMap.containsKey("has_bizError")) {
//            FiltersBucket hasBizError = bucketsMap.get("has_bizError");
//            result.setBizErrorCount(hasBizError.docCount());
//
//            if (hasBizError.aggregations().get("bizError_names") != null) {
//                NestedAggregate bizErrorNames = hasBizError.aggregations().get("bizError_names").nested();
//
//                if (bizErrorNames.aggregations().get("state_names") != null) {
//                    StringTermsAggregate stateNames = bizErrorNames.aggregations().get("state_names").sterms();
//                    Map<String, Long> bizErrorDetails = new HashMap<>();
//
//                    if (stateNames.buckets() != null && stateNames.buckets().array() != null) {
//                        for (StringTermsBucket bucketItem : stateNames.buckets().array()) {
//                            bizErrorDetails.put(bucketItem.key().stringValue(), bucketItem.docCount());
//                        }
//                    }
//
//                    result.setBizErrorDetails(bizErrorDetails);
//                }
//            }
//        }
//    }
//
//    private String getString(Map<String, Object> source, String key) {
//        if (source.containsKey(key)) {
//            Object value = source.get(key);
//            return value != null ? value.toString() : null;
//        }
//        return null;
//    }
//}