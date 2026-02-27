package com.ipaas.monitoringplstformsys.track.service;

import cn.hutool.core.map.MapUtil;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.ipaas.monitoringplstformsys.common.constant.DeipaasExceptionEnum;
import com.ipaas.monitoringplstformsys.common.exception.base.XdapWarningException;
import com.ipaas.monitoringplstformsys.elasticsearch.dto.EsSearchBaseBo;
import com.ipaas.monitoringplstformsys.elasticsearch.dto.EsUpdateBaseBo;
import com.ipaas.monitoringplstformsys.elasticsearch.service.EsCommonService;
import com.ipaas.monitoringplstformsys.mapper.ApiApisMapper;
import com.ipaas.monitoringplstformsys.mapper.ApiDictionaryMapper;
import com.ipaas.monitoringplstformsys.mapper.ApiRunTrackInfoMapper;
import com.ipaas.monitoringplstformsys.mapper.IApiRunTrackInfoService;
import com.ipaas.monitoringplstformsys.module.ApiRunTrackInfo;
import com.ipaas.monitoringplstformsys.module.vo.SearchByCategoryDto;
import com.ipaas.monitoringplstformsys.service.ApiService;
import com.ipaas.monitoringplstformsys.track.AggResultSearchReq;
import com.ipaas.monitoringplstformsys.track.AggregationResult;
import com.ipaas.monitoringplstformsys.track.ApiErrorAnalysisReq;
import com.ipaas.monitoringplstformsys.track.ApiInfoReq;
import com.ipaas.monitoringplstformsys.track.dto.*;
import com.ipaas.monitoringplstformsys.track.util.AggregationResultNewProcessor;
import com.ipaas.monitoringplstformsys.track.util.AggregationResultProcessor;
import com.ipaas.monitoringplstformsys.track.vo.ApiInfoExportVO;
import com.ipaas.monitoringplstformsys.track.vo.HitResultVo;
import com.ipaas.monitoringplstformsys.track.vo.UpdateDocByIdVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApiTrackService {

    public static final String API_CODE_KEYWORD = "apiCode.keyword";
    public static final String REQUEST_TIME = "requestTime";
    public static final String AGGREGATE_TIME = "aggregateTime";
    public static final String PRE_RESOLVE_TIME = "preResolveTime";

    public static final String REQUEST_ID = "requestId";
    public static final String ERROR_COUNT = "errorCount";
    public static final String TOTAL_COUNT = "totalCount";

    @Resource
    private EsCommonService esCommonService;

    @Resource
    private ApiService apiService;

    @Resource
    private ApiApisMapper apiApisMapper;

    @Resource
    private ApiRunTrackInfoMapper apiRunTrackInfoMapper;

    @Resource
    private ApiDictionaryMapper apiDictionaryMapper;

    @Resource
    private IApiRunTrackInfoService apiRunTrackInfoService;

    @Value("${sync.tenantId}")
    private String tenantId;

    @Value("${sync.originalIndexName}")
    private String originalIndexName;
    @Value("${sync.gateIndexName}")
    private String gateIndexName;

    @Value("${sync.page}")
    private int page;

    @Value("${sync.pageSize}")
    private int pageSize;

    @Value("${sync.targetIndexName}")
    private String targetIndexName;

    @Value("${sync.days}")
    private int days;

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime startOfDay = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
        for (int i = 0; i < 48; i++) {
            LocalDateTime intervalStart = startOfDay.plusMinutes(i * 30);
            LocalDateTime intervalEnd = intervalStart.plusMinutes(30);
            System.out.println(
                    intervalStart.format(formatter) + " 至 " +
                            intervalEnd.format(formatter)
            );
        }
    }

    public void queryApiUsageStats() {
        try {
            // 定义时间格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            DateTimeFormatter formatterWithoutMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


            // 1. 计算当前任务要处理的区间：以上一次执行时间为结束时间，往前推30分钟为开始时间
            LocalDateTime now = LocalDateTime.now();
            // 对齐到最近的30分钟结束点（如当前是00:30:05，则对齐到00:30:00）
            LocalDateTime endTime = now.withSecond(0).withNano(0);
            if (endTime.getMinute() % 30 != 0) {
                // 理论上不会进入此分支，因cron已确保在30分或0分执行
                endTime = endTime.minusMinutes(endTime.getMinute() % 30);
            }
            // 开始时间 = 结束时间 - 30分钟
            LocalDateTime startTime = endTime.minusMinutes(30);


            // 2. 格式化时间（可选：带毫秒可改为"yyyy-MM-dd HH:mm:ss.SSS"）
            String startTimeStr = startTime.format(formatter);
            String endTimeStr = endTime.format(formatter);

            String endTimeStrWithoutMillis = endTime.format(formatterWithoutMillis);

            // 初始化bool查询构建器
            BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
            List<String> apiCodes = apiService.queryAllApiCodes(tenantId);

            if (!CollectionUtils.isEmpty(apiCodes)) {
                boolQueryBuilder.filter(f -> f.terms(t -> t
                        .field(API_CODE_KEYWORD)
                        .terms(terms -> terms.value(apiCodes.stream().map(FieldValue::of).collect(Collectors.toList())))
                ));
            }

            boolQueryBuilder.filter(f -> f.range(r -> r.date(n -> n.field(REQUEST_TIME).gte(startTimeStr))));


            boolQueryBuilder.filter(f -> f.range(r -> r.date(n -> n.field(REQUEST_TIME).lt(endTimeStr))));


            Query.Builder queryBuilder = new Query.Builder();
            queryBuilder.bool(boolQueryBuilder.build());


            EsSearchBaseBo baseBo = EsSearchBaseBo.builder()
                    .indexName(originalIndexName)
                    .queryBuilder(queryBuilder.build())
                    .aggregationName("by_api_consumer")
                    .aggregation(buildCompositeAggregation())
                    .page(page)
                    .pageSize(pageSize)
                    .size(1)
                    .build();

            SearchResponse<JsonNode> jsonNodeSearchResponse = esCommonService.pageSearch(baseBo, JsonNode.class);
            ApiConsumerStat apiConsumerStatNew = AggregationResultNewProcessor.processAggregationResult(jsonNodeSearchResponse);

            apiConsumerStatNew.getBuckets().forEach(t -> {

                ApiConsumerStat.BasicInfo basicInfo = t.getBasicInfo();
                com.ipaas.monitoringplstformsys.track.AggregationResult aggregationResult = new com.ipaas.monitoringplstformsys.track.AggregationResult();
                BeanUtils.copyProperties(basicInfo, aggregationResult);

                aggregationResult.setAggregateTime(endTimeStrWithoutMillis);

                ApiConsumerStat.CompositeKey key = t.getKey();
                String apiCode = key.getApiCode();
                String consumerCode = key.getConsumerCode();

                aggregationResult.setApiCode(apiCode);
                aggregationResult.setConsumerCode(consumerCode);

//                List<String> allRequestIds = t.getAllRequestIds();
//                aggregationResult.setAllRequestIds(allRequestIds);

                if (!StringUtils.isEmpty(aggregationResult.getApiCode()) && !StringUtils.isEmpty(aggregationResult.getConsumerCode())) {

                    EsUpdateBaseBo esUpdateBaseBo = new EsUpdateBaseBo();
                    esUpdateBaseBo.setIndexName(targetIndexName);

                    long docCount = t.getDocCount();
                    aggregationResult.setTotalCalls(docCount);

                    Map<String, ApiConsumerStat.ExceptionAnalysis> exceptionsSplit = t.getExceptionsSplit();
                    ApiConsumerStat.ExceptionAnalysis hasException = exceptionsSplit.get("has_exception");

                    Map<String, ApiConsumerStat.BizErrorAnalysis> bizErrorSplit = t.getBizErrorSplit();
                    ApiConsumerStat.BizErrorAnalysis hasBizError = bizErrorSplit.get("has_bizError");

                    if (judgeNum(hasException.getDocCount())) {
                        if (!CollectionUtils.isEmpty(hasException.getExceptionNames())) {
                            hasException.getExceptionNames().forEach(e -> {
                                aggregationResult.setErrorCount(e.getDocCount());
                                aggregationResult.setErrorName(e.getKey());
                                aggregationResult.setAllRequestIds(e.getRequestId());
                                saveDocument(esUpdateBaseBo, aggregationResult);
                            });
                        }
                    } else if (judgeNum(hasBizError.getDocCount())) {
                        if (!CollectionUtils.isEmpty(hasBizError.getBizErrorNames())) {
                            hasBizError.getBizErrorNames().forEach(e -> {
                                aggregationResult.setErrorCount(e.getDocCount());
                                aggregationResult.setErrorName(e.getKey());
                                aggregationResult.setAllRequestIds(e.getRequestId());
                                saveDocument(esUpdateBaseBo, aggregationResult);
                            });
                        }
                    } else {
                        aggregationResult.setErrorCount(0L);
                        saveDocument(esUpdateBaseBo, aggregationResult);

                    }
//                    ApiConsumerStat.ExceptionAnalysis noException = exceptionsSplit.get("no_exception");
//                    if (judgeNum(noException.getDocCount())) {
//                        long docCount1 = noException.getDocCount();
//                        aggregationResult.setErrorCount(docCount1);
//                        aggregationResult.setErrorName(!CollectionUtils.isEmpty(noException.getExceptionNames()) ? noException.getExceptionNames().get(0).getKey() : "");
//                        saveDocument(esUpdateBaseBo, aggregationResult);
//                    }


//                    ApiConsumerStat.BizErrorAnalysis noBizError = bizErrorSplit.get("no_bizError");
//                    if (judgeNum(noBizError.getDocCount())) {
//                        long docCount1 = noBizError.getDocCount();
//                        aggregationResult.setErrorCount(docCount1);
//                        aggregationResult.setErrorName(!CollectionUtils.isEmpty(noBizError.getBizErrorNames()) ? noBizError.getBizErrorNames().get(0).getKey() : "");
//                        saveDocument(esUpdateBaseBo, aggregationResult);
//                    }
                }
            });

        } catch (Exception e) {
            log.error("queryApiUsageStats error:", e);
            throw new XdapWarningException(DeipaasExceptionEnum.SEARCH_FAIL, e);
        }
    }

    private boolean judgeNum(long l) {
        return l != 0;
    }


    public void saveApiUsageStats(HitResultVo resultVo) {
        String index = "poc_apiflow_base_log_702591298544075776_1745318931067-000001";
        try {
            esCommonService.save(index, resultVo);
        } catch (IOException e) {
            throw new XdapWarningException(DeipaasExceptionEnum.SAVE_ERROR, e);

        }
    }


    /**
     * 构建复合聚合 - 对应JSON中的aggs.by_api_consumer部分
     */
    private Aggregation buildCompositeAggregation() {
        // 构建复合聚合的两个维度: apiCode和consumerCode
        Map<String, CompositeAggregationSource> apiCodeSource = new HashMap<>();
        apiCodeSource.put("apiCode", CompositeAggregationSource.of(s -> s
                .terms(t -> t.field("apiCode.keyword"))
        ));

        // 1.2 创建 consumerCode 聚合源
        Map<String, CompositeAggregationSource> consumerCodeSource = new HashMap<>();
        consumerCodeSource.put("consumerCode", CompositeAggregationSource.of(s -> s
                .terms(t -> t.field("consumerCode.keyword"))
        ));

        // 构建复合聚合
        CompositeAggregation compositeAgg = CompositeAggregation.of(c -> c
                .size(10000)
                .sources(apiCodeSource, consumerCodeSource)
        );

        // 添加子聚合
        return Aggregation.of(a -> a
                        .composite(compositeAgg)
                        .aggregations("basic_info", buildTopHitsAggregation())
//                .aggregations("all_request_ids",buildAllRequestIds())
                        .aggregations("total_calls", buildValueCountAggregation())
                        .aggregations("exceptions_split", buildExceptionsSplitAggregation())
                        .aggregations("bizError_split", buildBizErrorSplitAggregation())
        );
    }


    /**
     * 构建基础信息聚合 - 对应basic_info
     */
    private Aggregation buildTopHitsAggregation() {
        return AggregationBuilders.topHits(b -> b
                .source(s -> s.filter(f -> f.includes(Arrays.asList(
                        "apiName", "categoryCode", "categoryName",
                        "consumerCode", "consumerName"
                ))))
                .size(1)
        );
    }

    private Aggregation buildAllRequestIds() {
        Query hasExceptionQuery = BoolQuery.of(b -> b
                .mustNot(mn -> mn.prefix(p -> p.field("responseCode").value("2")))
        )._toQuery();

        Map<String, Query> filtersMap = new HashMap<>();
        filtersMap.put("has_exception", hasExceptionQuery);


        FiltersAggregation filtersAgg = FiltersAggregation.of(f ->
                f.filters(faf -> faf.keyed(filtersMap))
        );
        Aggregation aggregation = AggregationBuilders.topHits(b -> b
                .source(s -> s.filter(f -> f.includes(Arrays.asList(
                        "requestId"
                ))))
                .size(100)
        );

        return Aggregation.of(a -> a
                .filters(filtersAgg)
                .aggregations("requestId", aggregation)
        );
    }

    /**
     * 构建总调用次数聚合 - 对应total_calls
     */
    private Aggregation buildValueCountAggregation() {
        return Aggregation.of(a -> a
                .valueCount(v -> v
                        .field("requestId")
                )
        );
    }

    /**
     * 构建异常情况分析聚合 - 对应exceptions_split
     */
    private Aggregation buildExceptionsSplitAggregation() {
        // 有异常的过滤条件
        Query hasExceptionQuery = BoolQuery.of(b -> b
                        .mustNot(mn -> mn.prefix(p -> p.field("responseCode").value("2")))
                //                .mustNot(mn -> mn.prefix(p -> p.field("responseCode").value("4")))
        )._toQuery();


        // 异常名称聚合
        Aggregation exceptionNamesAgg = Aggregation.of(a -> a
                .terms(t -> t
                        .field("exceptionKnowledge.exceptionName.keyword")
                        .size(10000)
                        .missing("未识别")
                )
                .aggregations("errorRequestId", subAgg -> subAgg  // 添加子聚合
                        .terms(termsAgg -> termsAgg
                                .field("requestId")  // 聚合 requestId 字段
                                .size(10000)  // 设置适当的大小
                        )
                )
        );

        // 2. 构建过滤器Map
        Map<String, Query> filtersMap = new HashMap<>();
        filtersMap.put("has_exception", hasExceptionQuery);
//            filtersMap.put("no_exception", noExceptionQuery);


        FiltersAggregation filtersAgg = FiltersAggregation.of(f ->
                f.filters(faf -> faf.keyed(filtersMap))
        );

        // 5. 构建最终聚合
        return Aggregation.of(a -> a
                .filters(filtersAgg)
                .aggregations("exception_names", exceptionNamesAgg)
        );
    }

    /**
     * 构建业务错误分析聚合 - 对应bizError_split
     */
    private Aggregation buildBizErrorSplitAggregation() {
        // 有业务错误的过滤条件
        Query hasBizErrorQuery = NestedQuery.of(n -> n
                .path("bizState")
                .query(q -> q
                        .bool(b -> b
                                .must(m -> m.exists(e -> e.field("bizState.stateInfoName.keyword")))  // 修正1：移除父路径
                                .mustNot(mn -> mn.term(t -> t
                                        .field("bizState.stateInfoName.keyword")  // 修正2：添加.keyword后缀
                                        .value("业务成功")
                                ))
                        )
                )
        )._toQuery();

        // 业务错误名称聚合
        Aggregation bizErrorNamesAgg = Aggregation.of(a -> a
                .nested(n -> n.path("bizState"))
                .aggregations("state_names", Aggregation.of(aa -> aa
                        .terms(t -> t.field("bizState.stateInfoName.keyword")
                                .size(10000)
                                .missing("未识别")
                        )
                        .aggregations("back_to_root", Aggregation.of(rn -> rn
                                .reverseNested(r -> r) // 从bizState嵌套对象切换回主文档
                                // 在主文档上下文中聚合requestId
                                .aggregations("errorRequestId", Aggregation.of(subAgg -> subAgg
                                        .terms(termsAgg -> termsAgg
                                                .field("requestId") // 注意使用keyword子字段
                                                .size(10000)
                                        )
                                ))
                        ))
                ))
        );

//
//        Aggregation bizErrorNamesAgg = Aggregation.of(a -> a
//                .terms(t -> t.field("bizState.stateInfoName.keyword")
//                        .size(10000)
//                )
//                .aggregations("errorRequestId", Aggregation.of(subAgg -> subAgg
//                        .terms(termsAgg -> termsAgg
//                                .field("requestId") // 注意使用keyword子字段
//                                .size(10000)
//                        )
//                ))
//
//        );


        Map<String, Query> filtersMap = new HashMap<>();
        filtersMap.put("has_bizError", hasBizErrorQuery);


        FiltersAggregation filtersQuery = FiltersAggregation.of(f ->
                f.filters(faf -> faf.keyed(filtersMap))
        );


        Aggregation errorRequestId = Aggregation.of(a -> a
                .terms(t -> t
                        .field("requestId")
                        .size(10000)  // 确保收集所有ID
                )
        );

        // 5. 构建最终聚合
        return Aggregation.of(a -> a
                        .filters(filtersQuery)
                        .aggregations("bizError_names", bizErrorNamesAgg)
//                .aggregations("error_request_id",errorRequestId)
        );

    }


    public void saveDocument(EsUpdateBaseBo updateBo, com.ipaas.monitoringplstformsys.track.AggregationResult aggregationResult) {

        try {
//            BoolQuery boolQuery = BoolQuery.of(b -> b
//                    // 必须匹配apiCode
//                    .must(m1 -> m1
//                            .term(t -> t
//                                    .field("apiCode")  // 字段名与映射一致（注意大小写）
//                                    .value(aggregationResult.getApiCode())    // 要匹配的值
//                            )
//                    )
//                    // 必须匹配consumerCode
//                    .must(m2 -> m2
//                            .term(t -> t
//                                    .field("consumerCode")  // 字段名与映射一致
//                                    .value(aggregationResult.getConsumerCode())    // 要匹配的值
//                            )
//                    )
//            );
//
//
//
//            Query query = Query.of(q -> q.bool(boolQuery));
//
//            SearchRequest searchRequest = SearchRequest.of(sr -> sr
//                    .index(updateBo.getIndexName()) // 使用更新BO中的索引名
//                    .query(query)
//                    .size(1) // 只需判断是否存在，无需返回所有结果
//            );
//
//            SearchResponse<AggregationResult> searchResponse = esCommonService.search(searchRequest, AggregationResult.class);
//
//
//            if (!CollectionUtils.isEmpty(searchResponse.hits().hits())) {
//
//
//                JsonData jsonData = JsonData.of(aggregationResult);
//                Script script = Script.of(s -> s
//                        .lang("painless")         // 脚本语言
//                        .source("ctx._source = params.newDoc")  // 脚本内容
//                        .params("newDoc", jsonData)           // 传递参数
//                );
//
//                updateBo.setScript(script);
//                updateBo.setQueryBuilder(query);
//
//
//                this.esCommonService.updateByQuery(updateBo);
//            } else {
            esCommonService.save(targetIndexName, aggregationResult);
//            }
        } catch (IOException e) {

        }
    }

    public Map<String, Object> queryAggregationResult(AggResultSearchReq reqVo) {
        try {
            // 1. 构建布尔查询
            BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

            boolQueryBuilder.must(m -> m.matchAll(ma -> ma));
            // API编码模糊查询
            if (org.springframework.util.StringUtils.hasText(reqVo.getApiCode())) {
                boolQueryBuilder.must(m -> m.wildcard(w -> w
                        .field("apiCode")
                        .value("*" + reqVo.getApiCode() + "*")
                ));
            }

            // API名称模糊查询
            if (org.springframework.util.StringUtils.hasText(reqVo.getApiName())) {
                boolQueryBuilder.must(m -> m.wildcard(w -> w
                        .field("apiName.keyword")
                        .value("*" + reqVo.getApiName() + "*")
                ));
            }

            // 应用系统查询 (模糊匹配)
            if (org.springframework.util.StringUtils.hasText(reqVo.getCategory())) {
                // 使用多字段组合查询
                boolQueryBuilder.must(m -> m.wildcard(w -> w
                        .field("categoryName.keyword")
                        .value("*" + reqVo.getCategory() + "*")
                ));
            }

            // 消费者查询 (模糊匹配)
            if (org.springframework.util.StringUtils.hasText(reqVo.getConsumer())) {
                // 使用多字段组合查询
                boolQueryBuilder.must(m -> m.wildcard(w -> w
                        .field("consumerName.keyword")
                        .value("*" + reqVo.getConsumer() + "*")
                ));
            }

            // 处理状态多选查询
            if (reqVo.getResultStatusList() != null && !reqVo.getResultStatusList().isEmpty()) {
                // 使用terms查询实现多选
                boolQueryBuilder.must(m -> m.terms(t -> t
                        .field("result.keyword")
                        .terms(terms -> terms.value(reqVo.getResultStatusList().stream()
                                .map(FieldValue::of)
                                .collect(Collectors.toList()))
                        )
                ));
            }

            if (!StringUtils.isEmpty(reqVo.getStartTime())) {
                boolQueryBuilder.filter(f -> f.range(r -> r.date(n -> n.field(AGGREGATE_TIME).gte(reqVo.getStartTime()))));

            }
            if (!StringUtils.isEmpty(reqVo.getEndTime())) {
                boolQueryBuilder.filter(f -> f.range(r -> r.date(n -> n.field(AGGREGATE_TIME).lt(reqVo.getEndTime()))));
            }

            if (!StringUtils.isEmpty(reqVo.getPreResolveStartTime())) {
                boolQueryBuilder.filter(f -> f.range(r -> r.date(n -> n.field(PRE_RESOLVE_TIME).gte(reqVo.getPreResolveStartTime()))));

            }
            if (!StringUtils.isEmpty(reqVo.getPreResolveEndTime())) {
                boolQueryBuilder.filter(f -> f.range(r -> r.date(n -> n.field(PRE_RESOLVE_TIME).lt(reqVo.getPreResolveEndTime()))));
            }

            Query query = new Query.Builder().bool(boolQueryBuilder.build()).build();


            // 2. 构建查询参数
            EsSearchBaseBo baseBo = EsSearchBaseBo.builder()
                    .indexName(targetIndexName)
                    .queryBuilder(query)
                    .aggregation(buildAggregationResultAgg(reqVo.getPageSize(), reqVo.getAfterKey()))
                    .aggregationName("res")
                    .page(reqVo.getPage())
                    .pageSize(reqVo.getPageSize())
                    .size(1)
                    .build();

            // 3. 执行查询
            SearchResponse<JsonNode> response = esCommonService.pageSearch(baseBo, JsonNode.class);

            baseBo.setAggregation(buildAggregationResultAgg(10000, new HashMap<>()));
            SearchResponse<JsonNode> total = esCommonService.pageSearch(baseBo, JsonNode.class);


            AggregationResultComposite aggregationResult = AggregationResultProcessor.processCompositeAggregation(response);

            List<AggregationResultDto> aggregationResultDtoList = new ArrayList<>();
            aggregationResult.getBuckets().forEach(t -> {
                AggregationResultDto aggregationResultDto = new AggregationResultDto();
                aggregationResultDto.setAfterKey(aggregationResult.getAfterKey());

                AggregationResultComposite.BaseInfo baseInfo = t.getBaseInfo();
                BeanUtils.copyProperties(baseInfo, aggregationResultDto);

                List<String> docIds = t.getDocIds();
                List<String> allRequestIds = t.getAllRequestIds();
                long totalCalls = t.getTotalCalls();
                long errorCount = t.getErrorCount();
                aggregationResultDto.setDocIds(docIds);
                aggregationResultDto.setAllRequestIds(allRequestIds);
                aggregationResultDto.setTotalCalls(totalCalls);
                aggregationResultDto.setErrorCount(errorCount);
                if ("业务失败".equals(baseInfo.getErrorName())) {
                    String roleType = "business";
                    aggregationResultDto.setProcessor(apiApisMapper.queryProcessorByRoleType(aggregationResultDto.getApiCode(), roleType));

                } else {
                    String roleType = "technical";
                    aggregationResultDto.setProcessor(apiApisMapper.queryProcessorByRoleType(aggregationResultDto.getApiCode(), roleType));

                }
                aggregationResultDtoList.add(aggregationResultDto);

            });

            if (reqVo.getSortField() != null) {
                Comparator<AggregationResultDto> comparator = null;
                if (ERROR_COUNT.equals(reqVo.getSortField())) {
                    comparator = Comparator.comparingLong(AggregationResultDto::getErrorCount);
                }
                if (TOTAL_COUNT.equals(reqVo.getSortField())) {
                    comparator = Comparator.comparingLong(AggregationResultDto::getTotalCalls);
                }
                // 判断排序方向
                if (reqVo.getSortOrder().equals("desc")) {
                    comparator = comparator.reversed();
                }
                // 应用排序
                aggregationResultDtoList.sort(comparator);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("total", total.aggregations().get("res").composite().buckets().array().size());
            result.put("data", aggregationResultDtoList);
            return result;

        } catch (Exception e) {
            log.error("queryAggregationResult：", e);
            throw new XdapWarningException(DeipaasExceptionEnum.SEARCH_FAIL, e);
        }
    }

    public Aggregation buildAggregationResultAgg(int size, Map<String, String> afterKey) {

        Aggregation baseInfo = AggregationBuilders.topHits(b -> b
                .source(s -> s.filter(f -> f.includes(Arrays.asList(
                        "apiCode",
                        "apiName", "categoryCode", "categoryName",
                        "consumerCode", "consumerName", "result", "aggregateTime", "preResolveTime", "errorName"
                ))))
                .size(1)
        );

        Map<String, CompositeAggregationSource> apiCodeSource = new HashMap<>();
        apiCodeSource.put("apiCode", CompositeAggregationSource.of(s -> s
                .terms(t -> t.field("apiCode"))
        ));

        Map<String, CompositeAggregationSource> consumerCodeSource = new HashMap<>();
        consumerCodeSource.put("consumerCode", CompositeAggregationSource.of(s -> s
                .terms(t -> t.field("consumerCode"))
        ));

        Map<String, CompositeAggregationSource> errorNameSource = new HashMap<>();
        errorNameSource.put("errorName", CompositeAggregationSource.of(s -> s
                .terms(t -> t.field("errorName.keyword").missingBucket(Boolean.TRUE).order(SortOrder.Asc))
        ));

        CompositeAggregation compositeAgg;

// 转换为 FieldValue Map
        Map<String, FieldValue> afterKeyMap = new HashMap<>();
        if (!MapUtil.isEmpty(afterKey)) {
            afterKey.forEach((field, value) -> {
                afterKeyMap.put(field, FieldValue.of(value));
            });
            // 3. 【修改】将  加入 sources 列表
            compositeAgg = CompositeAggregation.of(c -> c
                    .size(size)
                    .after(afterKeyMap)
                    .sources(apiCodeSource, consumerCodeSource, errorNameSource)
            );
        } else {
            // 3. 【修改】将  加入 sources 列表
            compositeAgg = CompositeAggregation.of(c -> c
                    .size(size)
                    .sources(apiCodeSource, consumerCodeSource, errorNameSource)
            );
        }


        Aggregation allRequestIds = Aggregation.of(a -> a
                .terms(t -> t
                        .field("allRequestIds")
                        .size(10000)  // 确保收集所有ID
                )
        );

        Aggregation docIds = Aggregation.of(a -> a
                .topHits(t -> t.size(100)
                        .source(s -> s.fetch(false))
                        .docvalueFields(new ArrayList<>()))
        );

        Aggregation totalCalls = Aggregation.of(a -> a
                .sum(s -> s.field("totalCalls"))
        );

        Aggregation errorCount = Aggregation.of(a -> a
                .sum(s -> s.field("errorCount"))
        );

        return Aggregation.of(a -> a
                .composite(compositeAgg)
                .aggregations("base_info", baseInfo)
                .aggregations("doc_ids", docIds)
                .aggregations("total_calls", totalCalls)
                .aggregations("error_count", errorCount)
                .aggregations("all_request_ids", allRequestIds)

        );
    }

    public void saveDocumentById(List<UpdateDocByIdVo> updateDocByIdVos) {
        updateDocByIdVos.forEach(updateDocByIdVo -> {
            updateDocByIdVo.getDocIds().forEach(t -> {
                try {
                    GetResponse<JsonData> jsonDataGetResponse = esCommonService.get(targetIndexName, t);
                    JsonData jsonData = jsonDataGetResponse.source();
                    AggregationResult aggregationResult = jsonData.to(AggregationResult.class);
                    if (StringUtils.isNoneEmpty(updateDocByIdVo.getResult())) {
                        aggregationResult.setResult(updateDocByIdVo.getResult());
                    }
                    if (StringUtils.isNoneEmpty(updateDocByIdVo.getPreResolveTime())) {
                        aggregationResult.setPreResolveTime(updateDocByIdVo.getPreResolveTime());
                    }
                    esCommonService.save(targetIndexName, t, aggregationResult);
                } catch (IOException e) {
                    log.error("saveDocumentById：", e);
                    throw new RuntimeException(e);
                }
            });
        });
    }

    /**
     * 保存/更新预处理时间和结果 (操作 MySQL)
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateApiRunTrackInfo(List<UpdateDocByIdVo> reqVos) {
        if (CollectionUtils.isEmpty(reqVos)) return;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 1. 提取本次请求涉及的所有 requestId (去重)
        List<String> allRequestIds = reqVos.stream()
                .map(UpdateDocByIdVo::getRequestId) // 假设前端传的是 requestId
                .filter(StringUtils::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(allRequestIds)) return;

        // 2. 【批量查询】一次性查出数据库中已存在的记录
        List<ApiRunTrackInfo> existList = apiRunTrackInfoService.list(
                new QueryWrapper<ApiRunTrackInfo>().in("request_id", allRequestIds)
        );
        // 转为 Map 方便匹配: Key=requestId, Value=Entity
        Map<String, ApiRunTrackInfo> existMap = existList.stream()
                .collect(Collectors.toMap(ApiRunTrackInfo::getRequestId, v -> v));

        // 3. 准备两个集合：新增列表 和 更新列表
        List<ApiRunTrackInfo> toInsertList = new ArrayList<>();
        List<ApiRunTrackInfo> toUpdateList = new ArrayList<>();

        for (UpdateDocByIdVo req : reqVos) {
            String requestId = req.getRequestId();
            if (StringUtils.isEmpty(requestId)) continue;

            // 解析时间
            Date preResolveDate = null;
            if (StringUtils.isNotEmpty(req.getPreResolveTime())) {
                try {
                    preResolveDate = simpleDateFormat.parse(req.getPreResolveTime());
                } catch (ParseException e) {
                    log.error("时间解析失败", e);
                }
            }

            ApiRunTrackInfo existInfo = existMap.get(requestId);

            if (existInfo == null) {
                // --- 不存在，加入新增列表 ---
                // 注意：防止 reqVos 里有重复的 ID 导致重复 insert，这里可以再做一个去重判断，或者相信前端
                ApiRunTrackInfo newInfo = new ApiRunTrackInfo();
                newInfo.setRequestId(requestId);
                newInfo.setResult(req.getResult());
                newInfo.setPreResolveTime(preResolveDate);
                toInsertList.add(newInfo);
            } else {
                // --- 已存在，加入更新列表 ---
                boolean needUpdate = false;
                if (req.getResult() != null) {
                    existInfo.setResult(req.getResult());
                    needUpdate = true;
                }
                if (req.getPreResolveTime() != null) { // 只要传了字段，就更新时间
                    existInfo.setPreResolveTime(preResolveDate);
                    needUpdate = true;
                }

                if (needUpdate) {
                    toUpdateList.add(existInfo);
                }
            }
        }

        // 4. 【批量执行】
        if (!CollectionUtils.isEmpty(toInsertList)) {
            // 批量新增 (1条 SQL 插入多行)
            apiRunTrackInfoService.saveBatch(toInsertList);
        }
        if (!CollectionUtils.isEmpty(toUpdateList)) {
            // 批量更新 (MyBatis Plus 会根据 ID 批量 update)
            apiRunTrackInfoService.updateBatchById(toUpdateList);
        }
    }

    // 建议将 ObjectMapper 定义为类的静态成员或注入进来，避免循环内创建
    private static final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

    public Map<String, Object> queryApiInfo(ApiInfoReq reqVo) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            BoolQuery.Builder boolQueryBuilderLogs = new BoolQuery.Builder();

            boolQueryBuilderLogs.filter(f -> f.range(r -> r.date(n -> n.field(REQUEST_TIME).gte(reqVo.getStartTime()))));
            boolQueryBuilderLogs.filter(f -> f.range(r -> r.date(n -> n.field(REQUEST_TIME).lt(reqVo.getEndTime()))));
            //requestId精确查询

            // ================= 【新增：条件透传查询】 =================

            // 3. API Code 精确匹配
            if (StringUtils.isNotBlank(reqVo.getApiCode())) {
                boolQueryBuilderLogs.filter(f -> f.term(t -> t
                        // 【修改点】加上 apiBaseInfo. 前缀
                        .field("apiBaseInfo.apiCode.keyword")
                        .value(reqVo.getApiCode())
                ));
            }

            // 4. Consumer Code 精确匹配

            // 5. Category Code 精确匹配
            if (StringUtils.isNotBlank(reqVo.getCategoryCode())) {
                boolQueryBuilderLogs.filter(f -> f.term(t -> t
                        // 【修改点】也加上前缀
                        .field("apiBaseInfo.categoryCode.keyword")
                        .value(reqVo.getCategoryCode())
                ));
            }

            // 6. 结果状态筛选 (Result / ErrorName)
            // 这是一个难点，因为原始日志里可能没有 resultStatusList 这种聚合后的字段
            // 你需要根据 responseCode 或 bizState 来还原筛选逻辑
            if (!CollectionUtils.isEmpty(reqVo.getResultStatusList())) {
                BoolQuery.Builder statusBool = new BoolQuery.Builder();

                for (String status : reqVo.getResultStatusList()) {
                    if ("成功".equals(status)) {
                        // 【成功】定义：
                        // 1. HTTP 状态码是 2xx
                        // 2. 并且 (bizState 不存在 OR bizState 等于 "业务成功")
                        statusBool.should(s -> s.bool(b -> b
                                .must(m -> m.prefix(p -> p.field("responseCode").value("2")))
                                .must(m -> m.bool(sub -> sub
                                        // 情况A: 没有业务状态字段 (认为成功)
                                        .should(sh -> sh.bool(bb -> bb.mustNot(mn -> mn.exists(e -> e.field("bizState.stateInfoName.keyword")))))
                                        // 情况B: 有业务状态，且显式为 "业务成功"
                                        .should(sh -> sh.term(t -> t.field("bizState.stateInfoName.keyword").value("业务成功")))
                                        .minimumShouldMatch("1")
                                ))
                        ));
                    } else if ("业务失败".equals(status)) {
                        // 【业务失败】定义：
                        // 1. HTTP 状态码是 2xx
                        // 2. 并且 (bizState 存在 且 不等于 "业务成功")
                        statusBool.should(s -> s.bool(b -> b
                                .must(m -> m.prefix(p -> p.field("responseCode").value("2")))
                                .must(m -> m.bool(sub -> sub
                                        // 情况A：标准的 bizState 错误 (如果有这个字段)
                                        .should(sh -> sh.bool(bb -> bb
                                                .must(mm -> mm.exists(e -> e.field("bizState.stateInfoName.keyword")))
                                                .mustNot(mn -> mn.term(t -> t.field("bizState.stateInfoName.keyword").value("业务成功")))
                                        ))
                                        // 情况B：Response Body 里包含明确的错误标识 (针对你这条数据)
                                        // 匹配 "CODE":"E"
                                        .should(sh -> sh.matchPhrase(mp -> mp.field("responseBody").query("CODE E")))
                                        .minimumShouldMatch("1")
                                ))
                        ));
                    } else {
                        // 【技术失败】定义：HTTP 状态码 不是 2xx
                        statusBool.should(s -> s.bool(b -> b
                                .mustNot(mn -> mn.prefix(p -> p.field("responseCode").value("2")))
                        ));
                    }
                }
                statusBool.minimumShouldMatch("1");
                boolQueryBuilderLogs.filter(f -> f.bool(statusBool.build()));
            } else if (StringUtils.isNotBlank(reqVo.getErrorName())) {
                String errorName = reqVo.getErrorName().trim();

                if ("业务失败".equals(errorName)) {
                    // 1. 【业务失败】(逻辑必须与 resultStatusList 中完全一致)
                    boolQueryBuilderLogs.filter(f -> f.bool(b -> b
                            .must(m -> m.prefix(p -> p.field("responseCode").value("2")))
                            .must(m -> m.bool(sub -> sub
                                    // 情况A: 标准的 bizState 错误
                                    .should(sh -> sh.bool(bb -> bb
                                            .must(mm -> mm.exists(e -> e.field("bizState.stateInfoName.keyword")))
                                            .mustNot(mn -> mn.term(t -> t.field("bizState.stateInfoName.keyword").value("业务成功")))
                                    ))
                                    // 情况B: Response Body 包含错误码 (补上这个！)
                                    .should(sh -> sh.matchPhrase(mp -> mp.field("responseBody").query("CODE E")))
                                    .minimumShouldMatch("1")
                            ))
                    ));
                } else if ("未识别".equals(errorName)) {
                    // 2. 【未识别】
                    // 逻辑：技术失败(非2xx) 且 没有异常名称字段
                    boolQueryBuilderLogs.filter(f -> f.bool(b -> b
                            .mustNot(mn -> mn.prefix(p -> p.field("responseCode").value("2")))
                            .mustNot(mn -> mn.exists(e -> e.field("exceptionKnowledge.exceptionName.keyword")))
                    ));
                } else {
                    // 3. 【具体错误名称】
                    // 逻辑：精确匹配异常名称
                    boolQueryBuilderLogs.filter(f -> f.term(t -> t
                            .field("exceptionKnowledge.exceptionName.keyword")
                            .value(errorName)
                    ));
                }
            }

            // 3. 工厂查询 (Factory -> WERKS)
            // 原理：使用短语匹配。ES 会自动分析查询语句，忽略标点，
            // 寻找 "WERKS" 后面紧跟 "A050" 的文档。
            if (StringUtils.isNotEmpty(reqVo.getFactory())) {
                String val = reqVo.getFactory().trim().toLowerCase(); // wildcard要转小写
                boolQueryBuilderLogs.must(m -> m.bool(b -> b
                        .must(sub -> sub.matchPhrase(mp -> mp.field("requestBody").query("WERKS"))) // 必须包含 Key
                        .must(sub -> sub.wildcard(w -> w.field("requestBody").value("*" + val + "*"))) // 值模糊匹配
                ));
            }

            // 2. OrderNumber (订单号 -> EBELN)
            if (StringUtils.isNotEmpty(reqVo.getOrderNumber())) {
                String[] codes = reqVo.getOrderNumber().split("[,，]"); // 支持中英文逗号

                boolQueryBuilderLogs.must(m -> m.bool(b -> {
                    for (String rawCode : codes) {
                        if (StringUtils.isBlank(rawCode)) continue;
                        String val = rawCode.trim().toLowerCase(); // wildcard 需转小写

                        // 逻辑：必须包含 Key "EBELN" 且 Value 包含用户输入的数值
                        // 使用 should 是为了实现：(匹配单号A) OR (匹配单号B)
                        b.should(s -> s.bool(sub -> sub
                                .must(mm -> mm.matchPhrase(mp -> mp.field("requestBody").query("EBELN")))
                                .must(mm -> mm.wildcard(w -> w.field("requestBody").value("*" + val + "*")))
                        ));
                    }
                    // 至少匹配列表中的一个单号
                    return b.minimumShouldMatch("1");
                }));
            }

            // 3. OrderItem (EBELP)
            if (StringUtils.isNotEmpty(reqVo.getOrderItem())) {
                String val = reqVo.getOrderItem().trim().toLowerCase();
                boolQueryBuilderLogs.must(m -> m.bool(b -> b
                        .must(sub -> sub.matchPhrase(mp -> mp.field("requestBody").query("EBELP")))
                        .must(sub -> sub.wildcard(w -> w.field("requestBody").value("*" + val + "*")))
                ));
            }

            // 4. ProductNumber (AUFNR - 生产订单号)
            if (StringUtils.isNotEmpty(reqVo.getProductNumber())) {
                String[] codes = reqVo.getProductNumber().split("[,，]");

                boolQueryBuilderLogs.must(m -> m.bool(b -> {
                    for (String rawCode : codes) {
                        if (StringUtils.isBlank(rawCode)) continue;
                        String val = rawCode.trim().toLowerCase();

                        // 逻辑：是AUFNR 且 包含值
                        b.should(s -> s.bool(sub -> sub
                                .must(mm -> mm.matchPhrase(mp -> mp.field("requestBody").query("AUFNR")))
                                .must(mm -> mm.wildcard(w -> w.field("requestBody").value("*" + val + "*")))
                        ));
                    }
                    return b.minimumShouldMatch("1");
                }));
            }

            // 5. ProcessNumber (AUFPL)
            if (StringUtils.isNotEmpty(reqVo.getProcessNumber())) {
                String val = reqVo.getProcessNumber().trim().toLowerCase();
                boolQueryBuilderLogs.must(m -> m.bool(b -> b
                        .must(sub -> sub.matchPhrase(mp -> mp.field("requestBody").query("AUFPL")))
                        .must(sub -> sub.wildcard(w -> w.field("requestBody").value("*" + val + "*")))
                ));
            }

            // 6. WbsNumber (MAT_PSPNR)
            if (StringUtils.isNotEmpty(reqVo.getWbsNumber())) {
                String val = reqVo.getWbsNumber().trim().toLowerCase();
                boolQueryBuilderLogs.must(m -> m.bool(b -> b
                        .must(sub -> sub.matchPhrase(mp -> mp.field("requestBody").query("MAT_PSPNR")))
                        .must(sub -> sub.wildcard(w -> w.field("requestBody").value("*" + val + "*")))
                ));
            }

            // 7. MovementType (BWART)
            if (StringUtils.isNotEmpty(reqVo.getMovementType())) {
                String val = reqVo.getMovementType().trim().toLowerCase();
                boolQueryBuilderLogs.must(m -> m.bool(b -> b
                        .must(sub -> sub.matchPhrase(mp -> mp.field("requestBody").query("BWART")))
                        .must(sub -> sub.wildcard(w -> w.field("requestBody").value("*" + val + "*")))
                ));
            }

            // 8. RequestBody (请求报文模糊搜索)
            // 用户输入什么搜什么，不限字段。建议用 wildcard 前后匹配
            if (StringUtils.isNotEmpty(reqVo.getRequestBody())) {
                String bodyVal = reqVo.getRequestBody().trim();

                // 【修改】改用 matchPhrase，支持中文短语（如"物料"）
                // 同时也支持英文全词匹配
                boolQueryBuilderLogs.must(m -> m.matchPhrase(mp -> mp
                        .field("requestBody")
                        .query(bodyVal)
                ));
            }

            // ================= 【响应报文搜索】 =================
            // 9. Msg (响应消息 -> MSG)
            if (StringUtils.isNotEmpty(reqVo.getMsg())) {
                String msgVal = reqVo.getMsg().trim();

                // 【核心修改】
                // 不要拼在一起搜，而是拆成两个独立的条件
                // 逻辑：responseBody 必须包含 "MSG" 且 必须包含 "请检查"
                boolQueryBuilderLogs.must(m -> m.bool(b -> b
                        // 条件1：上下文限制，确保报文里有 MSG 这个字段名
                        .must(sub -> sub.matchPhrase(mp -> mp.field("responseBody").query("MSG")))

                        // 条件2：内容匹配，搜索用户输入的中文/英文短语
                        .must(sub -> sub.matchPhrase(mp -> mp.field("responseBody").query(msgVal)))
                ));
            }

            // 10. ResponseBody (响应报文全局搜索)
            if (StringUtils.isNotEmpty(reqVo.getResponseBody())) {
                String bodyVal = reqVo.getResponseBody().trim();
                // 【建议】使用 matchPhrase
                // 如果用户搜 "请检查"，ES 会去找 "请"+"检"+"查" 连在一起的数据，能搜到。
                // 如果用户搜 "UUID"，也能搜到。
                boolQueryBuilderLogs.must(m -> m.matchPhrase(mp -> mp
                        .field("responseBody")
                        .query(bodyVal)
                ));
            }

            Query query = new Query.Builder().bool(boolQueryBuilderLogs.build()).build();
            List<Map<String, Object>> resultList = new ArrayList<>();

            // 局部缓存 (避免循环查库)
            Map<String, List<String>> processorCache = new HashMap<>();
            long totalHits = 0;  // 总条数

            // ================= 【核心分流逻辑】 =================

            // 判断是【普通分页】还是【大批量导出/查询】
            // 阈值设为 500 (一般前端分页不会超过100)
            boolean isNormalPaging = reqVo.getPageSize() != null && reqVo.getPageSize() <= 500;

            if (isNormalPaging) {
                // === 场景 A：普通分页 (只查一次，速度快) ===
                SearchRequest searchRequest = new SearchRequest.Builder()
                        .index(gateIndexName)
                        .query(query)
                        .from((reqVo.getPage() - 1) * reqVo.getPageSize()) // 使用 from 跳过
                        .size(reqVo.getPageSize())
                        .sort(s -> s.field(f -> f.field("requestTime").order(SortOrder.Desc))) // 排序
                        .trackTotalHits(t -> t.enabled(true)) // 开启精确总数
                        .build();

                SearchResponse<JsonNode> response = esCommonService.search(searchRequest, JsonNode.class);
                totalHits = response.hits().total().value();

                // 调用公共方法处理数据
                resultList.addAll(processHits(response.hits().hits(), reqVo, processorCache));

            } else {
                // === 场景 B：深分页/导出 (循环查，突破10000条) ===
                List<FieldValue> searchAfterValues = null;
                int batchSize = 2000; // 内部批次大小

                while (true) {
                    SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                            .index(gateIndexName)
                            .query(query)
                            .size(batchSize) // 每次取 2000
                            .sort(s -> s.field(f -> f.field("requestTime").order(SortOrder.Desc)))
                            .sort(s -> s.field(f -> f.field("_id").order(SortOrder.Desc))) // 必须加 _id 保证顺序
                            .trackTotalHits(t -> t.enabled(true));

                    if (searchAfterValues != null) {
                        requestBuilder.searchAfter(searchAfterValues);
                    }

                    SearchResponse<JsonNode> response = esCommonService.search(requestBuilder.build(), JsonNode.class);
                    List<Hit<JsonNode>> hits = response.hits().hits();

                    if (searchAfterValues == null) {
                        totalHits = response.hits().total().value();
                    }

                    if (CollectionUtils.isEmpty(hits)) break;

                    // 调用公共方法处理数据
                    resultList.addAll(processHits(hits, reqVo, processorCache));

                    // 更新游标
                    Hit<JsonNode> lastHit = hits.get(hits.size() - 1);
                    searchAfterValues = lastHit.sort();

                    if (hits.size() < batchSize) break;

                    // 安全阀
                    if (resultList.size() >= 50000) {
                        log.warn("查询截断 50000 条");
                        break;
                    }
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("total", totalHits);
            result.put("data", resultList);
            return result;

        } catch (ElasticsearchException esEx) {
            // 【关键】打印 ES 返回的具体错误原因
            log.error("ES查询详细报错: {}", esEx.response().error().reason());
            if (esEx.response().error().rootCause() != null) {
                esEx.response().error().rootCause().forEach(cause -> {
                    log.error("Root Cause: Type=[{}], Reason=[{}]", cause.type(), cause.reason());
                });
            }
            throw new XdapWarningException(DeipaasExceptionEnum.SEARCH_FAIL, esEx);
        } catch (Exception e) {
            log.error("queryApiInfo unknown error", e);
            throw new XdapWarningException(DeipaasExceptionEnum.SEARCH_FAIL, e);
        }
    }

    private List<Map<String, Object>> processHits(List<Hit<JsonNode>> hits, ApiInfoReq reqVo, Map<String, List<String>> processorCache) {
        List<Map<String, Object>> batchResult = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 1. 提取当前批次的 requestId
        List<String> requestIds = hits.stream()
                .map(h -> h.source().path("requestId").asText())
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());

        // 2. 批量查询 MySQL
        Map<String, ApiRunTrackInfo> trackInfoMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(requestIds)) {
            List<ApiRunTrackInfo> trackInfos = apiRunTrackInfoMapper.selectList(
                    new QueryWrapper<ApiRunTrackInfo>().in("request_id", requestIds)
            );
            trackInfoMap = trackInfos.stream()
                    .collect(Collectors.toMap(ApiRunTrackInfo::getRequestId, v -> v, (k1, k2) -> k1));
        }

        // 3. 循环解析单条数据
        for (Hit<JsonNode> hit : hits) {
            JsonNode source = hit.source();
            Map<String, Object> item = new HashMap<>();

            String requestId = source.path("requestId").asText();
            String requestBodyStr = source.path("requestBody").asText();

            // --- 基础字段 ---
            item.put("responseBody", source.path("responseBody").asText());
            item.put("requestBody", requestBodyStr);
            item.put("requestId", requestId);
            item.put("requestTime", source.path("requestTime").asText());
            item.put("responseCode", source.path("responseCode").asText());

            // --- MySQL 数据 ---
            ApiRunTrackInfo trackInfo = trackInfoMap.get(requestId);
            if (trackInfo != null) {
                String timeStr = "";
                if (trackInfo.getPreResolveTime() != null) {
                    timeStr = sdf.format(trackInfo.getPreResolveTime());
                }
                item.put("preResolveTime", timeStr);
                item.put("result", trackInfo.getResult());
            } else {
                item.put("preResolveTime", "");
                item.put("result", "");
            }

            // --- API Info & 报文解析 ---
            JsonNode apiBaseInfo = source.path("apiBaseInfo");
            if (!apiBaseInfo.isMissingNode()) {
                Map<String, Object> apiInfoMap = new HashMap<>();
                String apiCode = apiBaseInfo.path("apiCode").asText();

                apiInfoMap.put("apiId", apiBaseInfo.path("apiId").asText());
                apiInfoMap.put("apiCode", apiCode);
                apiInfoMap.put("apiName", apiBaseInfo.path("apiName").asText());
                apiInfoMap.put("categoryName", apiBaseInfo.path("categoryName").asText());

                // 解析 Factory 和 OrderNumber
                String factory = "";
                String orderNumber = "";
                String orderItem = "";     // EBELP
                String productNumber = ""; // AUFNR
                String processNumber = ""; // AUFPL
                String wbsNumber = "";     // MAT_PSPNR
                String movementType = "";  // BWART
                String msg = "";           // MSG (来自响应报文)

                // 1. 解析请求报文 (RequestBody)
                if (StringUtils.isNotEmpty(requestBodyStr) && StringUtils.isNotEmpty(apiCode)) {
                    try {
                        JsonNode bodyNode = objectMapper.readTree(requestBodyStr);
                        JsonNode itemsNode = null;

                        // 根据不同的 API Code 定位到 item 数组节点
                        if ("SAP_008".equals(apiCode)) {
                            itemsNode = bodyNode.path("IV_DATA").path("ITEM");
                        } else if ("SAP_012".equals(apiCode) || "SAP_013".equals(apiCode)) {
                            itemsNode = bodyNode.path("IT_DATA").path("item");
                        } else if ("SAP_009".equals(apiCode)) {
                            itemsNode = bodyNode.path("IT_ORDER_NUMBER").path("item");
                        }

                        // 如果找到了数组节点，且不为空，提取第一行数据
                        if (itemsNode != null && itemsNode.isArray() && itemsNode.size() > 0) {
                            JsonNode firstItem = itemsNode.get(0);

                            factory = firstItem.path("WERKS").asText("");       // 工厂
                            orderNumber = firstItem.path("EBELN").asText("");   // 订单号 (采购单)
                            orderItem = firstItem.path("EBELP").asText("");     // 订单行号
                            productNumber = firstItem.path("AUFNR").asText(""); // 生产订单号/产品号
                            processNumber = firstItem.path("AUFPL").asText(""); // 流程/工序号
                            wbsNumber = firstItem.path("MAT_PSPNR").asText(""); // WBS元素
                            movementType = firstItem.path("BWART").asText("");  // 移动类型
                        }
                    } catch (Exception e) {
                        log.warn("解析请求报文失败, requestId: {}", requestId);
                    }
                }

                // 2. 解析响应报文 (ResponseBody) -> 提取 MSG
                String responseBodyStr = source.path("responseBody").asText();
                if (StringUtils.isNotEmpty(responseBodyStr)) {
                    try {
                        JsonNode resNode = objectMapper.readTree(responseBodyStr);
                        // 尝试路径 A: 根目录直接有 MSG
                        if (resNode.has("MSG")) {
                            msg = resNode.path("MSG").asText("");
                        }
                        // 尝试路径 B: item 数组里的 MSG (如 {"item":[{"MSG":"..."}]})
                        else {
                            JsonNode resItems = resNode.path("item");
                            if (resItems.isArray() && resItems.size() > 0) {
                                msg = resItems.get(0).path("MSG").asText("");
                            }
                        }
                    } catch (Exception e) {
                        // 响应报文可能不是 JSON，忽略解析错误
                    }
                }
                apiInfoMap.put("factory", factory);
                apiInfoMap.put("orderNumber", orderNumber);
                apiInfoMap.put("orderItem", orderItem);
                apiInfoMap.put("productNumber", productNumber);
                apiInfoMap.put("processNumber", processNumber);
                apiInfoMap.put("wbsNumber", wbsNumber);
                apiInfoMap.put("movementType", movementType);
                apiInfoMap.put("msg", msg);

                // 查询 Processor
                List<String> processorNames = new ArrayList<>();
                if (StringUtils.isNotEmpty(apiCode) && StringUtils.isNotEmpty(factory)) {
                    String roleType = "technical";
                    if ("业务失败".equals(reqVo.getErrorName())) {
                        roleType = "business";
                    } else {
                        JsonNode bizState = source.path("bizState");
                        if (!bizState.isMissingNode()) {
                            JsonNode stateNode = bizState.isArray() && bizState.size() > 0 ? bizState.get(0) : bizState;
                            String stateName = stateNode.path("stateInfoName").asText();
                            if (StringUtils.isNotEmpty(stateName) && !"业务成功".equals(stateName)) {
                                roleType = "business";
                            }
                        }
                    }

                    String cacheKey = apiCode + "_" + factory + "_" + roleType;
                    if (processorCache.containsKey(cacheKey)) {
                        processorNames = processorCache.get(cacheKey);
                    } else {
                        processorNames = apiApisMapper.queryUserNameByFactoryAndRole(apiCode, factory, roleType);
                        if (processorNames == null) processorNames = new ArrayList<>();
                        processorCache.put(cacheKey, processorNames);
                    }
                }
                item.put("processor", processorNames);
                item.put("apiInfo", apiInfoMap);
            } else {
                item.put("apiInfo", Collections.emptyMap());
            }
            batchResult.add(item);
        }
        return batchResult;
    }

    public void exportApiInfo(ApiInfoReq reqVo, HttpServletResponse response) {
        ExcelWriter writer = null;
        try {
            // 1. 获取并转换数据
            reqVo.setPageSize(1000); // 深度导出
            Map<String, Object> queryResult = this.queryApiInfo(reqVo);
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) queryResult.get("data");
            if (dataList == null) dataList = new ArrayList<>();

            List<ApiInfoExportVO> exportData = dataList.stream().map(item -> {
                ApiInfoExportVO vo = new ApiInfoExportVO();
                // --- 基础字段 ---
                vo.setRequestId(getString(item.get("requestId")));
                vo.setRequestTime(getString(item.get("requestTime")));
                vo.setResponseCode(getString(item.get("responseCode")));

                // --- MySQL 补充字段 (修正：从外层 item 获取) ---
                vo.setPreResolveTime(getString(item.get("preResolveTime")));
                vo.setResult(getString(item.get("result")));

                // --- 报文截断 (防止 Excel 崩溃) ---
                vo.setRequestBody(truncateString(getString(item.get("requestBody")), 32000));
                vo.setResponseBody(truncateString(getString(item.get("responseBody")), 32000));

                // --- 负责人 (修正：从外层 item 获取，且 List 转 String) ---
                Object processorObj = item.get("processor");
                if (processorObj instanceof List) {
                    vo.setProcessor(String.join(",", (List<String>) processorObj));
                } else {
                    vo.setProcessor(getString(processorObj));
                }

                // --- API Info 及 解析字段 ---
                Map<String, Object> apiInfo = (Map<String, Object>) item.get("apiInfo");
                if (apiInfo != null) {
                    vo.setApiName(getString(apiInfo.get("apiName")));
                    vo.setApiCode(getString(apiInfo.get("apiCode")));
                    vo.setCategoryName(getString(apiInfo.get("categoryName")));

                    // 解析出来的扩展字段
                    vo.setFactory(getString(apiInfo.get("factory")));
                    vo.setOrderNumber(getString(apiInfo.get("orderNumber")));
                    vo.setOrderItem(getString(apiInfo.get("orderItem")));
                    vo.setProductNumber(getString(apiInfo.get("productNumber")));
                    vo.setProcessNumber(getString(apiInfo.get("processNumber")));
                    vo.setWbsNumber(getString(apiInfo.get("wbsNumber")));
                    vo.setMovementType(getString(apiInfo.get("movementType")));
                    vo.setMsg(getString(apiInfo.get("msg")));
                }
                return vo;
            }).collect(Collectors.toList());

            // 2. 设置响应头
            response.setContentType("text/csv");
            response.setCharacterEncoding("UTF-8"); // CSV 建议统一用 UTF-8
            String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String fileName = "ApiInfo" + dateStr + ".csv";
            response.setHeader("content-disposition", "attachment;filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

            // 3. 构建 Writer (EasyExcel 3.1.1)
            // 注意：excelType(ExcelTypeEnum.CSV) 必须指定
            writer = EasyExcel.write(response.getOutputStream(), ApiInfoExportVO.class)
                    .excelType(ExcelTypeEnum.CSV)
                    .charset(StandardCharsets.UTF_8)
                    .build();

            // 4. 写数据
            WriteSheet sheet = EasyExcel.writerSheet("sheet1").build();
            writer.write(exportData, sheet);

        } catch (Exception e) {
            log.error("[exportApiInfo]导出失败", e);
            if (!response.isCommitted()) {
                try {
                    response.reset();
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().println("{\"code\":500,\"message\":\"导出失败：" + e.getMessage() + "\"}");
                } catch (IOException ex) {
                    log.error("响应重置失败", ex);
                }
            }
        } finally {
            // 5. 必须 finish，否则 CSV 数据可能不会刷出缓冲区
            if (writer != null) {
                writer.finish();
            }
        }
    }

    private String getString(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }

    /**
     * 字符串截断
     */
    private String truncateString(String str, int len) {
        if (str == null) return "";
        return str.length() > len ? str.substring(0, len) + "..." : str;
    }

    public Map<String, Object> aggregationResultByCategory(AggResultSearchReq reqVo) {
        try {
            BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

            boolQueryBuilder.must(m -> m.matchAll(ma -> ma));
            if (!StringUtils.isEmpty(reqVo.getStartTime())) {
                boolQueryBuilder.filter(f -> f.range(r -> r.date(n -> n.field(AGGREGATE_TIME).gte(reqVo.getStartTime()))));
            }
            if (!StringUtils.isEmpty(reqVo.getEndTime())) {
                boolQueryBuilder.filter(f -> f.range(r -> r.date(n -> n.field(AGGREGATE_TIME).lt(reqVo.getEndTime()))));
            }
            Query query = new Query.Builder().bool(boolQueryBuilder.build()).build();

            // 构建查询参数
            EsSearchBaseBo baseBo = EsSearchBaseBo.builder()
                    .indexName(targetIndexName)
                    .queryBuilder(query)
                    .aggregation(buildAggregationResultAgg(10000, reqVo.getAfterKey()))
                    .aggregationName("res")
                    .page(reqVo.getPage())  // 使用请求参数中的分页
                    .pageSize(reqVo.getPageSize())  // 使用请求参数中的分页大小
                    .size(1)
                    .build();

            // 执行查询
            SearchResponse<JsonNode> response = esCommonService.pageSearch(baseBo, JsonNode.class);
            baseBo.setAggregation(buildAggregationResultAgg(10000, new HashMap<>()));

            AggregationResultComposite aggregationResult = AggregationResultProcessor.processCompositeAggregation(response);

            // 提取请求中的目标系统编码
            String targetCategoryCode = reqVo.getApiRunReports();

            // 判断请求类型
            if ("ALL".equals(targetCategoryCode)) {
                // 处理 ALL 情况：返回所有 API 的详细信息，不进行系统分层
                List<ApiRunReportsDto> allApiDetails = new ArrayList<>();
                Map<String, ApiRunReportsDto> apiMap = new HashMap<>(); // 用于按 apiCode 去重

                aggregationResult.getBuckets().forEach(bucket -> {
                    String apiCode = bucket.getBaseInfo().getApiCode();

                    // 获取或创建 API 详情对象
                    ApiRunReportsDto apiDto = apiMap.get(apiCode);
                    if (apiDto == null) {
                        apiDto = new ApiRunReportsDto();
                        apiDto.setApiCode(apiCode);
                        apiDto.setApiName(bucket.getBaseInfo().getApiName());
                        apiDto.setCategoryCode(bucket.getBaseInfo().getCategoryCode());
                        apiDto.setTotalCalls(0L);
                        apiDto.setErrorCount(0L);
                        apiDto.setResultCount(0L);
                        apiDto.setPreResolveTime(bucket.getBaseInfo().getPreResolveTime());
                        apiDto.setResult(bucket.getBaseInfo().getResult());
                        apiMap.put(apiCode, apiDto);
                        allApiDetails.add(apiDto);
                    }

                    // 累加统计值
                    apiDto.setTotalCalls(apiDto.getTotalCalls() + bucket.getTotalCalls());
                    apiDto.setErrorCount(apiDto.getErrorCount() + bucket.getErrorCount());

                    // 如果处理状态为"已处理"，则累加已处理数目
                    if ("已处理".equals(bucket.getBaseInfo().getResult())) {
                        apiDto.setResultCount(apiDto.getResultCount() + bucket.getErrorCount());
                    }
                });

                // 直接返回所有 API 详情
                Map<String, Object> result = new HashMap<>();
                result.put("total", allApiDetails.size());
                result.put("data", allApiDetails);
                return result;
            } else if (!StringUtils.isEmpty(targetCategoryCode)) {
                // 处理指定系统的情况：返回指定系统的 API 详细信息
                Map<String, SearchByCategoryDto> categorySummaryMap = new HashMap<>();
                Map<String, Map<String, ApiRunReportsDto>> apiDetailMap = new HashMap<>();

                // 收集所有 API 详情
                List<ApiRunReportsDto> allApiDetails = new ArrayList<>();

                aggregationResult.getBuckets().forEach(bucket -> {
                    String categoryCode = bucket.getBaseInfo().getCategoryCode();
                    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

                    // 只处理目标系统的数据
                    if (!targetCategoryCode.equals(categoryCode)) {
                        return;
                    }

                    String apiCode = bucket.getBaseInfo().getApiCode();

                    // 更新系统级别统计
                    SearchByCategoryDto summary = categorySummaryMap.get(categoryCode);
                    if (summary == null) {
                        summary = new SearchByCategoryDto();
                        summary.setCategoryCode(categoryCode);
                        summary.setCategoryName(bucket.getBaseInfo().getCategoryName());
                        summary.setTotalCalls(0L);
                        summary.setErrorCounts(0L);
                        categorySummaryMap.put(categoryCode, summary);
                    }
                    summary.setTotalCalls(summary.getTotalCalls() + bucket.getTotalCalls());
                    summary.setErrorCounts(summary.getErrorCounts() + bucket.getErrorCount());

                    // 收集 API 详细信息
                    apiDetailMap.putIfAbsent(categoryCode, new HashMap<>());
                    Map<String, ApiRunReportsDto> apiMap = apiDetailMap.get(categoryCode);

                    ApiRunReportsDto apiDto = apiMap.get(apiCode);
                    if (apiDto == null) {
                        apiDto = new ApiRunReportsDto();
                        apiDto.setApiCode(apiCode);
                        apiDto.setApiName(bucket.getBaseInfo().getApiName());
                        apiDto.setCategoryCode(categoryCode);
                        apiDto.setCategoryName(bucket.getBaseInfo().getCategoryName());
                        apiDto.setTotalCalls(0L);
                        apiDto.setErrorCount(0L);
                        apiDto.setResultCount(0L);
                        apiDto.setUnResultCount(0L);
                        try {
                            Date date = originalFormat.parse(bucket.getBaseInfo().getAggregateTime());
                            apiDto.setAggregateTime(newFormat.format(date));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        apiDto.setProcessor(apiApisMapper.queryProcessor(apiCode));
                        apiDto.setPreResolveTime(bucket.getBaseInfo().getPreResolveTime());
                        apiDto.setResult(bucket.getBaseInfo().getResult());
                        apiMap.put(apiCode, apiDto);
                        allApiDetails.add(apiDto); // 添加到所有 API 详情列表
                    }
                    apiDto.setTotalCalls(apiDto.getTotalCalls() + bucket.getTotalCalls());
                    apiDto.setErrorCount(apiDto.getErrorCount() + bucket.getErrorCount());

                    // 如果处理状态为"已处理"，则累加已处理数目
                    if ("已处理".equals(bucket.getBaseInfo().getResult())) {
                        apiDto.setResultCount(apiDto.getResultCount() + bucket.getErrorCount());
                    }
                    apiDto.setUnResultCount(apiDto.getErrorCount() - apiDto.getResultCount());
                });

                //排序
                if (reqVo.getSortField() != null) {
                    Comparator<ApiRunReportsDto> comparator = null;
                    if (ERROR_COUNT.equals(reqVo.getSortField())) {
                        comparator = Comparator.comparingLong(ApiRunReportsDto::getErrorCount);
                    } else if (TOTAL_COUNT.equals(reqVo.getSortField())) {
                        comparator = Comparator.comparingLong(ApiRunReportsDto::getTotalCalls);
                    }
                    if (comparator != null) {
                        if ("desc".equals(reqVo.getSortOrder())) {
                            comparator = comparator.reversed();
                        }
                        allApiDetails.sort(comparator);
                    }
                }
                // 对 API 详情进行分页
                int page = reqVo.getPage();
                int pageSize = reqVo.getPageSize();
                int startIndex = (page - 1) * pageSize;
                int endIndex = Math.min(startIndex + pageSize, allApiDetails.size());

                List<ApiRunReportsDto> pagedApiDetails = allApiDetails.subList(startIndex, endIndex);

                // 将分页后的 API 详细信息设置到对应的系统中
                categorySummaryMap.forEach((categoryCode, summary) -> {
                    if (apiDetailMap.containsKey(categoryCode)) {
                        summary.setBasicInfos(pagedApiDetails);
                    }
                });

                List<SearchByCategoryDto> summaryList = new ArrayList<>(categorySummaryMap.values());

                Map<String, Object> result = new HashMap<>();
                result.put("total", summaryList.size());
                result.put("data", summaryList);
                result.put("page", page);
                result.put("pageSize", pageSize);
                result.put("totalApiCount", allApiDetails.size()); // 添加 API 总数
                return result;
            } else {
                // 处理空值情况：只进行系统级别统计，不包含 API 详细信息
                Map<String, SearchByCategoryDto> categorySummaryMap = new HashMap<>();

                aggregationResult.getBuckets().forEach(bucket -> {
                    String categoryCode = bucket.getBaseInfo().getCategoryCode();

                    // 更新系统级别统计
                    SearchByCategoryDto summary = categorySummaryMap.get(categoryCode);
                    if (summary == null) {
                        summary = new SearchByCategoryDto();
                        summary.setCategoryCode(categoryCode);
                        summary.setCategoryName(bucket.getBaseInfo().getCategoryName());
                        summary.setTotalCalls(0L);
                        summary.setErrorCounts(0L);
                        summary.setBasicInfos(new ArrayList<>());
                        categorySummaryMap.put(categoryCode, summary);
                    }
                    summary.setTotalCalls(summary.getTotalCalls() + bucket.getTotalCalls());
                    summary.setErrorCounts(summary.getErrorCounts() + bucket.getErrorCount());
                });

                List<SearchByCategoryDto> summaryList = new ArrayList<>(categorySummaryMap.values());

                Map<String, Object> result = new HashMap<>();
                result.put("total", summaryList.size());
                result.put("data", summaryList);
                return result;
            }
        } catch (Exception e) {
            throw new XdapWarningException(DeipaasExceptionEnum.SEARCH_FAIL, e);
        }
    }

    /**
     * 统计接口报错分布
     */
    public List<ApiErrorStatDto> analyzeApiErrorStats(ApiErrorAnalysisReq reqVo) {
        try {
            // 1. 构建基础查询条件 (保持不变：只查失败的数据)
            BoolQuery.Builder bool = new BoolQuery.Builder();
            // 处理开始时间
            if (StringUtils.isNotEmpty(reqVo.getStartTime())) {
                String startTime = reqVo.getStartTime().trim();
                if (startTime.length() == 19) {
                    startTime = startTime + ".000";
                }
                String finalStartTime = startTime;
                bool.filter(f -> f.range(r -> r.date(n -> n.field(REQUEST_TIME).gte(finalStartTime))));
            }

            // 2. 处理结束时间
            if (StringUtils.isNotEmpty(reqVo.getEndTime())) {
                String endTime = reqVo.getEndTime().trim();
                if (endTime.length() == 19) {
                    endTime = endTime + ".000";
                }
                String finalEndTime = endTime;
                bool.filter(f -> f.range(r -> r.date(n -> n.field(REQUEST_TIME).lt(finalEndTime))));
            }
            if (StringUtils.isNotEmpty(reqVo.getEnvId())) {
                bool.filter(f -> f.term(t -> t.field("envId").value(reqVo.getEnvId())));
            }

            if (StringUtils.isNotBlank(reqVo.getApiCode())) {
                bool.filter(f -> f.term(t -> t
                        .field("apiBaseInfo.apiCode.keyword") // 字段路径
                        .value(reqVo.getApiCode())            // 传入单个字符串
                ));
            }
            if (!CollectionUtils.isEmpty(reqVo.getApiName())) {
                bool.filter(f -> f.terms(t -> t
                        .field("apiBaseInfo.apiName.keyword")
                        .terms(terms -> terms.value(reqVo.getApiName().stream().map(FieldValue::of).collect(Collectors.toList())))
                ));
            }
            if (StringUtils.isNotBlank(reqVo.getCategory())) {
                // 【修改】必须使用 wildcard 而不是 term
                bool.must(m -> m.wildcard(w -> w
                        .field("apiBaseInfo.categoryName.keyword")
                        .value("*" + reqVo.getCategory().trim() + "*") // 前后加 * 实现包含匹配
                ));
            }

            if (StringUtils.isNotEmpty(reqVo.getFactory())) {
                // 使用 matchPhrase 匹配 "WERKS A050"
                // 只要 requestBody 里有 "WERKS":"A050" 就能搜到
                bool.must(m -> m.matchPhrase(mp -> mp
                        .field("requestBody")
                        .query("WERKS " + reqVo.getFactory().trim())
                ));
            }

            if (StringUtils.isNotEmpty(reqVo.getMsg())) {
                String keyword = reqVo.getMsg().trim();
                bool.must(m -> m.bool(b -> b
                        // 搜响应体 (中文/英文短语)
                        .should(s -> s.bool(sub -> sub
                                .must(mm -> mm.matchPhrase(mp -> mp.field("responseBody").query("MSG")))
                                .must(mm -> mm.matchPhrase(mp -> mp.field("responseBody").query(keyword)))
                        ))
                        // 搜异常栈信息 (如果需要的话)
                        .should(s -> s.matchPhrase(mp -> mp.field("exceptionKnowledge.exceptionName").query(keyword)))
                        .minimumShouldMatch("1")
                ));
            }

            // 筛选失败数据
            bool.must(m -> m.bool(b -> b
                    // 1. 技术失败 (HTTP 非 2xx)
                    .should(s -> s.bool(bb -> bb.mustNot(mn -> mn.prefix(p -> p.field("responseCode").value("2")))))

                    // 2. 标准业务失败 (HTTP 2xx 且 有bizState 且 状态不为成功)
                    .should(s -> s.bool(bb -> bb
                            .must(mn -> mn.prefix(p -> p.field("responseCode").value("2")))
                            .must(mn -> mn.exists(e -> e.field("bizState.stateInfoName.keyword")))
                            .mustNot(mn -> mn.term(t -> t.field("bizState.stateInfoName.keyword").value("业务成功")))
                    ))

                    // 3. 【新增】特殊业务失败 (HTTP 2xx 且 响应体包含错误码)
                    // 针对那些没有 bizState，但 responseBody 里写了 "CODE":"E" 的情况
                    .should(s -> s.bool(bb -> bb
                            .must(mn -> mn.prefix(p -> p.field("responseCode").value("2")))
                            // 必须包含 "CODE" 和 "E" (根据你的实际报文调整，比如 "CODE E" 或 "status error")
                            .must(mn -> mn.matchPhrase(mp -> mp.field("responseBody").query("CODE E")))
                    ))

                    .minimumShouldMatch("1")
            ));

            // 增加工厂
            // 脚本 A: 提取工厂 (从 requestBody)
            String factoryScript =
                    "if (params['_source']['requestBody'] == null) return '无工厂'; " +
                            "String body = params['_source']['requestBody'].toString(); " +
                            // 正则匹配 "WERKS":"A050" (兼容冒号两边的空格)
                            "def m = /\"WERKS\"\\s*:\\s*\"([^\"]+)\"/.matcher(body); " +
                            "if (m.find()) { return m.group(1); } " +
                            "return '无工厂';";

            // 1. 获取动态规则 (调用上面的新方法)
            List<Map<String, String>> ruleParams = getErrorAggregationRules();
            // 2. 脚本逻辑 (保持不变，它只负责接收 rules 参数并执行)
            String errorMsgScript =
                    "if (params['_source']['responseBody'] == null) return null; " +
                            "String body = params['_source']['responseBody'].toString(); " +
                            // 提取 MSG
                            "def m = /\"MSG\":\"([^\"]+)\"/.matcher(body); " +
                            "if (m.find()) { " +
                            "   String rawMsg = m.group(1); " +
                            // 动态匹配
                            "   def rules = params['rules']; " +
                            "   if (rules != null) { " +
                            "       for (def rule : rules) { " +
                            "           if (rawMsg.contains(rule['keyword'])) { " +
                            "               return rule['category']; " +
                            "           } " +
                            "       } " +
                            "   } " +
                            "   return rawMsg; " +
                            "} " +
                            "return '未提取到错误信息';"; // 不要返回 null，返回固定字符串

            // 3. 构建聚合 (传入 rules)
            SearchRequest searchRequest = new SearchRequest.Builder()
                    .index(gateIndexName)
                    .query(bool.build()._toQuery())
                    .size(0)
                    // 第一层：按工厂聚合 (Script)
                    .aggregations("by_factory", agg1 -> agg1
                            .terms(t -> t
                                    .script(sc -> sc.source(factoryScript))
                                    .size(50) // 假设工厂数不超过50
                            )
                            // 第二层：按 API 编码聚合
                            .aggregations("by_api_code", agg2 -> agg2
                                    .terms(t -> t
                                            .field("apiBaseInfo.apiCode.keyword")
                                            .size(100)
                                    )
                                    // 子聚合：取 API 名称
                                    .aggregations("get_api_name", sub -> sub.topHits(th -> th.size(1).source(s -> s.filter(f -> f.includes("apiBaseInfo.apiName")))))
                                    // 第三层：按错误信息聚合 (Script + Params)
                                    .aggregations("by_msg", agg3 -> agg3
                                            .terms(t -> t
                                                    .script(sc -> sc
                                                            .source(errorMsgScript)
                                                            .params("rules", JsonData.of(ruleParams))
                                                    )
                                                    .size(50)
                                            )
                                    )
                            )
                    )
                    .build();

            // 4. 执行查询
            SearchResponse<JsonNode> response = esCommonService.search(searchRequest, JsonNode.class);
            List<ApiErrorStatDto> resultList = new ArrayList<>();
            Aggregate factoryAgg = response.aggregations().get("by_factory");

            if (factoryAgg != null) {
                // 1. 遍历工厂桶
                for (StringTermsBucket factoryBucket : factoryAgg.sterms().buckets().array()) {
                    String factory = factoryBucket.key().stringValue();

                    Aggregate apiAgg = factoryBucket.aggregations().get("by_api_code");
                    if (apiAgg != null) {
                        // 2. 遍历 API 桶
                        for (StringTermsBucket apiBucket : apiAgg.sterms().buckets().array()) {
                            String apiCode = apiBucket.key().stringValue();
                            long subTotalErrorCount = apiBucket.docCount(); // 该工厂下该 API 的报错总数

                            // 获取 API Name
                            String apiName = apiCode;
                            Aggregate getNameAgg = apiBucket.aggregations().get("get_api_name");
                            if (getNameAgg != null && getNameAgg.isTopHits()) {
                                List<Hit<JsonData>> hits = getNameAgg.topHits().hits().hits();
                                if (!hits.isEmpty()) {
                                    JsonData jsonData = hits.get(0).source();
                                    if (jsonData != null) {
                                        JsonNode sourceNode = jsonData.to(JsonNode.class);
                                        apiName = sourceNode.path("apiBaseInfo").path("apiName").asText(apiCode);
                                    }
                                }
                            }

                            Aggregate msgAgg = apiBucket.aggregations().get("by_msg");
                            if (msgAgg != null) {
                                long sumOfDetailedErrors = 0L;
                                // 3. 遍历错误信息桶
                                for (StringTermsBucket msgBucket : msgAgg.sterms().buckets().array()) {
                                    String errorMsg = msgBucket.key().stringValue();
                                    long errorCount = msgBucket.docCount();

                                    sumOfDetailedErrors += errorCount;

                                    // 组装对象
                                    ApiErrorStatDto dto = new ApiErrorStatDto();
                                    dto.setFactory(factory); // 【新增】设置工厂
                                    dto.setApiCode(apiCode);
                                    dto.setApiName(apiName);
                                    dto.setMsg(errorMsg);
                                    dto.setErrorCount(errorCount);
                                    dto.setSubTotalErrorCount(subTotalErrorCount);

                                    if (subTotalErrorCount > 0) {
                                        double ratio = (double) errorCount / subTotalErrorCount * 100;
                                        dto.setErrorRatio(String.format("%.2f%%", ratio));
                                    } else {
                                        dto.setErrorRatio("0.00%");
                                    }
                                    resultList.add(dto);
                                }

                                long remainder = subTotalErrorCount - sumOfDetailedErrors;
                                if (remainder > 0) {
                                    ApiErrorStatDto otherDto = new ApiErrorStatDto();
                                    // 如果你在最外层还有 factory 循环，这里记得 setFactory
                                    if (StringUtils.isNotEmpty(reqVo.getFactory())) {
                                        otherDto.setFactory(reqVo.getFactory());
                                    } else {
                                        otherDto.setFactory("其他/混合");
                                    }

                                    otherDto.setApiCode(apiCode);
                                    otherDto.setApiName(apiName);
                                    otherDto.setMsg("其他错误 (分散的生僻报错)"); // 或者 "其他"
                                    otherDto.setErrorCount(remainder);
                                    otherDto.setSubTotalErrorCount(subTotalErrorCount);

                                    double ratio = (double) remainder / subTotalErrorCount * 100;
                                    otherDto.setErrorRatio(String.format("%.2f%%", ratio));

                                    resultList.add(otherDto);
                                }
                            }
                        }
                    }
                }
            }
            // ================= 7. 【新增】Java 内存排序 =================
            // ES 聚合排序比较麻烦，直接在 Java 层对结果 List 排序最灵活
            if (StringUtils.isNotEmpty(reqVo.getSortField())) {
                Comparator<ApiErrorStatDto> comparator = null;
                String sortField = reqVo.getSortField();

                if ("errorCount".equals(sortField)) {
                    comparator = Comparator.comparingLong(ApiErrorStatDto::getErrorCount);
                } else if ("subTotalErrorCount".equals(sortField)) {
                    comparator = Comparator.comparingLong(ApiErrorStatDto::getSubTotalErrorCount);
                } else if ("apiName".equals(sortField)) {
                    comparator = Comparator.comparing(ApiErrorStatDto::getApiName, Comparator.nullsLast(String::compareTo));
                }

                if (comparator != null) {
                    if ("desc".equalsIgnoreCase(reqVo.getSortOrder())) {
                        comparator = comparator.reversed();
                    }
                    resultList.sort(comparator);
                }
            }

            return resultList;

        } catch (ElasticsearchException esEx) {
            log.error("ES聚合查询详细报错: {}", esEx.response().error().reason());
            if (esEx.response().error().rootCause() != null) {
                esEx.response().error().rootCause().forEach(cause -> {
                    log.error("Root Cause: Type=[{}], Reason=[{}]", cause.type(), cause.reason());
                });
            }
            throw new XdapWarningException(DeipaasExceptionEnum.SEARCH_FAIL, esEx);
        } catch (Exception e) {
            log.error("analyzeApiErrorStats error", e);
            throw new XdapWarningException(DeipaasExceptionEnum.SEARCH_FAIL, e);
        }
    }

    /**
     * 从数据字典解析聚合规则
     * 逻辑：
     * 1. 解析 headers，找到 "Value" (关键字) 和 "Value1" (分类名) 对应的 UUID
     * 2. 遍历 data，根据 UUID 提取数据
     */
    private List<Map<String, String>> getErrorAggregationRules() {
        List<Map<String, String>> rules = new ArrayList<>();
        try {
            // 1. 查库
            String jsonStr = apiDictionaryMapper.queryDictionaryData("755183065751356416", "Works_Error_Type");

            if (StringUtils.isEmpty(jsonStr)) {
                return rules;
            }

            // 2. 解析 JSON
            JsonNode root = objectMapper.readTree(jsonStr);
            JsonNode headers = root.path("headers");
            JsonNode data = root.path("data");

            // 3. 寻找列 ID (UUID)
            String targetColId = null;

            if (headers.isArray()) {
                for (JsonNode header : headers) {
                    String name = header.path("name").asText();
                    // 只要 Value1
                    if ("Value1".equals(name)) {
                        targetColId = header.path("id").asText();
                        break; // 找到了就退出循环
                    }
                }
            }

            // 3. 提取数据
            if (targetColId != null && data.isArray()) {
                for (JsonNode row : data) {
                    // 获取 Value1 的值
                    String content = row.path(targetColId).asText(null);

                    if (StringUtils.isNotBlank(content)) {
                        Map<String, String> map = new HashMap<>();
                        // 【修改】关键字和分类名都使用 Value1 的内容
                        map.put("keyword", content.trim());
                        map.put("category", content.trim());
                        rules.add(map);
                    }
                }
            }

            // 4. 按长度倒序排序 (防止 "订单已结算" 被 "订单" 抢先匹配)
            rules.sort((r1, r2) -> Integer.compare(r2.get("keyword").length(), r1.get("keyword").length()));

        } catch (Exception e) {
            log.error("解析聚合字典失败", e);
        }
        return rules;
    }
}
