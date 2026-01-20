package com.ipaas.monitoringplstformsys.track.service;

import cn.hutool.core.map.MapUtil;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
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
import com.ipaas.monitoringplstformsys.mapper.ApiRunTrackInfoMapper;
import com.ipaas.monitoringplstformsys.mapper.IApiRunTrackInfoService;
import com.ipaas.monitoringplstformsys.module.ApiRunTrackInfo;
import com.ipaas.monitoringplstformsys.track.AggResultSearchReq;
import com.ipaas.monitoringplstformsys.track.AggregationResult;
import com.ipaas.monitoringplstformsys.track.ApiInfoReq;
import com.ipaas.monitoringplstformsys.track.dto.AggregationResultComposite;
import com.ipaas.monitoringplstformsys.track.dto.AggregationResultDto;
import com.ipaas.monitoringplstformsys.track.dto.ApiConsumerStat;
import com.ipaas.monitoringplstformsys.track.dto.ApiRunReportsDto;
import com.ipaas.monitoringplstformsys.track.util.AggregationResultNewProcessor;
import com.ipaas.monitoringplstformsys.track.util.AggregationResultProcessor;
import com.ipaas.monitoringplstformsys.track.vo.ApiInfoExportVO;
import com.ipaas.monitoringplstformsys.track.vo.HitResultVo;
import com.ipaas.monitoringplstformsys.track.vo.UpdateDocByIdVo;
import com.ipaas.monitoringplstformsys.elasticsearch.dto.EsSearchBaseBo;
import com.ipaas.monitoringplstformsys.elasticsearch.dto.EsUpdateBaseBo;
import com.ipaas.monitoringplstformsys.elasticsearch.service.EsCommonService;
import com.ipaas.monitoringplstformsys.mapper.ApiApisMapper;
import com.ipaas.monitoringplstformsys.module.vo.SearchByCategoryDto;
import com.ipaas.monitoringplstformsys.service.ApiService;
import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;
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

import static com.ipaas.monitoringplstformsys.common.constant.DeipaasExceptionEnum.SEARCH_FAIL;

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
            log.error("queryApiUsageStats error:" , e);
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
                if ("业务失败".equals(baseInfo.getErrorName())){
                    String roleType = "business";
                    aggregationResultDto.setProcessor(apiApisMapper.queryProcessorByRoleType(aggregationResultDto.getApiCode(),roleType));

                }else{
                    String roleType = "technical";
                    aggregationResultDto.setProcessor(apiApisMapper.queryProcessorByRoleType(aggregationResultDto.getApiCode(),roleType));

                }
                aggregationResultDtoList.add(aggregationResultDto);

            });

            if (reqVo.getSortField() != null) {
                Comparator<AggregationResultDto> comparator = null;
                if (ERROR_COUNT.equals(reqVo.getSortField())){
                    comparator = Comparator.comparingLong(AggregationResultDto::getErrorCount);
                }
                if(TOTAL_COUNT.equals(reqVo.getSortField())){
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
            log.error("queryAggregationResult：" , e);
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
                    log.error("saveDocumentById：" , e);
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
            List<Map<String, Object>> resultList = new ArrayList<>();

            // 局部缓存 (避免循环查库)
            Map<String, List<String>> processorCache = new HashMap<>();

            boolQueryBuilderLogs.filter(f -> f.range(r -> r.date(n -> n.field(REQUEST_TIME).gte(reqVo.getStartTime()))));
            boolQueryBuilderLogs.filter(f -> f.range(r -> r.date(n -> n.field(REQUEST_TIME).lt(reqVo.getEndTime()))));
            //requestId精确查询
//            if (!CollectionUtils.isEmpty(reqVo.getRequestIds())) {
//                boolQueryBuilderLogs.filter(f -> f.terms(t -> t
//                        .field(REQUEST_ID)
//                        .terms(terms -> terms.value(reqVo.getRequestIds().stream().map(FieldValue::of).collect(Collectors.toList())))
//                ));
//            }

            // ================= 【修改后的搜索逻辑】 =================

            // 3. 工厂查询 (Factory -> WERKS)
            // 原理：使用短语匹配。ES 会自动分析查询语句，忽略标点，
            // 寻找 "WERKS" 后面紧跟 "A050" 的文档。
            if (StringUtils.isNotEmpty(reqVo.getFactory())) {
                boolQueryBuilderLogs.must(m -> m.matchPhrase(mp -> mp
                        .field("requestBody")
                        .query("WERKS " + reqVo.getFactory().trim())
                ));
            }

            // 4. 订单号查询 (OrderCode -> AUFNR 或 EBELN)
            if (StringUtils.isNotEmpty(reqVo.getOrderNumber())) {
                String code = reqVo.getOrderNumber().trim();
                boolQueryBuilderLogs.must(m -> m.bool(b -> b
                        .should(s -> s.matchPhrase(mp -> mp
                                .field("requestBody")
                                .query("AUFNR " + code) // 匹配 "AUFNR":"123"
                        ))
                        .should(s -> s.matchPhrase(mp -> mp
                                .field("requestBody")
                                .query("EBELN " + code) // 匹配 "EBELN":"123"
                        ))
                        .minimumShouldMatch("1")
                ));
            }

            Query query = new Query.Builder().bool(boolQueryBuilderLogs.build()).build();

            // ================= 【开始循环查询逻辑】 =================
            List<FieldValue> searchAfterValues = null; // 游标
            int pageSize = 2000; // 每次查 2000 条，分批拉取
            long totalHits = 0;  // 总条数

            while (true) {
                // 2. 构建 Search Request
                SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                        .index(gateIndexName)
                        .query(query)
                        .size(pageSize)
                        // 【关键】必须要有确定的排序，才能深分页
                        .sort(s -> s.field(f -> f.field("requestTime").order(SortOrder.Desc)))
                        .sort(s -> s.field(f -> f.field("_id").order(SortOrder.Desc))); // _id 兜底保证唯一性

                // 如果有游标，传给 ES，查下一页
                if (searchAfterValues != null) {
                    requestBuilder.searchAfter(searchAfterValues);
                }

                // 3. 执行查询
                SearchResponse<JsonNode> responseLogs = esCommonService.search(requestBuilder.build(), JsonNode.class);
                List<Hit<JsonNode>> hits = responseLogs.hits().hits();

                // 记录总数 (第一次查的时候拿 total)
                if (searchAfterValues == null) {
                    totalHits = responseLogs.hits().total().value();
                }

                // 如果没查到数据，跳出循环
                if (CollectionUtils.isEmpty(hits)) {
                    break;
                }

                // ================== 【批量处理 MySQL 数据 (当前批次)】 ==================
                // 收集当前批次的 requestIds
                List<String> requestIds = hits.stream()
                        .map(h -> h.source().path("requestId").asText())
                        .filter(StringUtils::isNotEmpty)
                        .collect(Collectors.toList());

                // 批量查询 MySQL
                Map<String, ApiRunTrackInfo> trackInfoMap = new HashMap<>();
                if (!CollectionUtils.isEmpty(requestIds)) {
                    List<ApiRunTrackInfo> trackInfos = apiRunTrackInfoMapper.selectList(
                            new QueryWrapper<ApiRunTrackInfo>().in("request_id", requestIds)
                    );
                    trackInfoMap = trackInfos.stream()
                            .collect(Collectors.toMap(ApiRunTrackInfo::getRequestId, v -> v, (k1, k2) -> k1));
                }
                // ================== 【批量处理结束】 ==================

                // 4. 处理数据解析 (循环当前批次)
                for (Hit<JsonNode> hit : hits) {
                    JsonNode source = hit.source();
                    Map<String, Object> item = new HashMap<>();

                    String requestId = source.path("requestId").asText();
                    String requestBodyStr = source.path("requestBody").asText();

                    // 填充基础字段
                    item.put("responseBody", source.path("responseBody").asText());
                    item.put("requestBody", requestBodyStr);
                    item.put("requestId", requestId);
                    item.put("requestTime", source.path("requestTime").asText());
                    item.put("responseCode", source.path("responseCode").asText());

                    // 填充 MySQL 数据
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

                    // 解析 API Info
                    JsonNode apiBaseInfo = source.path("apiBaseInfo");
                    if (!apiBaseInfo.isMissingNode()) {
                        Map<String, Object> apiInfoMap = new HashMap<>();
                        String apiCode = apiBaseInfo.path("apiCode").asText();

                        // 填充 API 基础信息
                        apiInfoMap.put("apiId", apiBaseInfo.path("apiId").asText());
                        apiInfoMap.put("apiCode", apiCode);
                        apiInfoMap.put("apiName", apiBaseInfo.path("apiName").asText());
                        apiInfoMap.put("categoryName", apiBaseInfo.path("categoryName").asText());

                        // --- 解析报文提取 Factory 和 OrderNumber ---
                        String factory = "";
                        String orderNumber = "";

                        if (StringUtils.isNotEmpty(requestBodyStr) && StringUtils.isNotEmpty(apiCode)) {
                            try {
                                JsonNode bodyNode = objectMapper.readTree(requestBodyStr);
                                if ("SAP_008".equals(apiCode)) {
                                    // 路径：IV_DATA -> ITEM (数组)
                                    JsonNode ivData = bodyNode.path("IV_DATA");
                                    if (!ivData.isMissingNode()) {
                                        JsonNode items = ivData.path("ITEM");
                                        if (items.isArray() && items.size() > 0) {
                                            JsonNode firstItem = items.get(0);

                                            // 1. 提取工厂
                                            factory = firstItem.path("WERKS").asText("");

                                            // 2. 提取订单号 (优先取 AUFNR，没有则取 EBELN)
                                            String aufnr = firstItem.path("AUFNR").asText("");
                                            String ebeln = firstItem.path("EBELN").asText("");

                                            if (StringUtils.isNotEmpty(aufnr)) {
                                                orderNumber = aufnr;
                                            } else {
                                                orderNumber = ebeln;
                                            }
                                        }
                                    }
                                } else if ("SAP_012".equals(apiCode) || "SAP_013".equals(apiCode)) {
                                    // 路径：IT_DATA -> item (数组) -> 注意小写
                                    JsonNode itData = bodyNode.path("IT_DATA");
                                    if (!itData.isMissingNode()) {
                                        JsonNode items = itData.path("item");
                                        if (items.isArray() && items.size() > 0) {
                                            JsonNode firstItem = items.get(0);

                                            // 1. 提取工厂
                                            factory = firstItem.path("WERKS").asText("");

                                            // 2. 提取订单号 (优先取 AUFNR，没有则取 EBELN)
                                            String aufnr = firstItem.path("AUFNR").asText("");
                                            String ebeln = firstItem.path("EBELN").asText("");

                                            if (StringUtils.isNotEmpty(aufnr)) {
                                                orderNumber = aufnr;
                                            } else {
                                                orderNumber = ebeln;
                                            }
                                        }
                                    }
                                } else if ("SAP_009".equals(apiCode)) {
                                    JsonNode orderData = bodyNode.path("IT_ORDER_NUMBER");
                                    if (!orderData.isMissingNode()) {
                                        JsonNode items = orderData.path("item"); // 小写 item
                                        if (items.isArray() && items.size() > 0) {
                                            JsonNode firstItem = items.get(0);

                                            // 尝试提取工厂 (虽然你提供的报文里没看到，但写上不报错)
                                            factory = firstItem.path("WERKS").asText("");

                                            // 2. 提取订单号 (优先取 AUFNR，没有则取 EBELN)
                                            String aufnr = firstItem.path("AUFNR").asText("");
                                            String ebeln = firstItem.path("EBELN").asText("");

                                            if (StringUtils.isNotEmpty(aufnr)) {
                                                orderNumber = aufnr;
                                            } else {
                                                orderNumber = ebeln;
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                // 解析失败不阻断主流程
                                log.warn("解析报文提取字段失败, apiCode: {}, requestId: {}", apiCode, source.path("requestId").asText());
                            }
                        }


                        // 将提取到的值放入 map
                        apiInfoMap.put("factory", factory);
                        apiInfoMap.put("orderNumber", orderNumber); // 【新增】放入结果集

                        List<String> processorNames = new ArrayList<>();

                        if (StringUtils.isNotEmpty(apiCode) && StringUtils.isNotEmpty(factory)) {
                            // 【修正】这里建议使用 bizState 动态判断 roleType，
                            // 但如果你非要用 reqVo.getErrorName()，也可以，但注意这是针对所有行生效
                            String roleType = "technical";
                            // 建议逻辑：优先看当前行的 bizState，如果没逻辑则看 reqVo
                            if ("业务失败".equals(reqVo.getErrorName())) {
                                roleType = "business";
                            } else {
                                // 更好的做法：检查当前行的 bizState
//                                JsonNode bizState = source.path("bizState");
//                                if (!bizState.isMissingNode()) {
//                                    JsonNode stateNode = bizState.isArray() && bizState.size() > 0 ? bizState.get(0) : bizState;
//                                    String stateName = stateNode.path("stateInfoName").asText();
//                                    if (StringUtils.isNotEmpty(stateName) && !"业务成功".equals(stateName)) {
                                        roleType = "business";
//                                    }
//                                }
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
                    resultList.add(item);
                }

                // 5. 更新游标，准备查下一页
                Hit<JsonNode> lastHit = hits.get(hits.size() - 1);
                List<FieldValue> sortValues = lastHit.sort(); // 获取 sort 数组
                if (sortValues != null && !sortValues.isEmpty()) {
                    // 【修改点 2】直接赋值，不需要再用 stream().map(...) 转换了
                    searchAfterValues = sortValues;
                } else {
                    break; // 没有 sort 值，无法继续
                }

                // 如果本次查回来的少于 pageSize，说明是最后一页了
                if (hits.size() < pageSize) {
                    break;
                }

                // 【安全阀】防止内存溢出，如果数据量太大(比如 > 5万)，强制停止
                if (resultList.size() >= 50000) {
                    log.warn("查询数据量过大，已截断至 50000 条");
                    break;
                }
            }
            // ================= 【循环查询结束】 =================

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

    public void exportApiInfo(ApiInfoReq reqVo, HttpServletResponse response) {
        ExcelWriter writer = null;
        try {
            // 1. 获取并转换数据
            Map<String, Object> queryResult = this.queryApiInfo(reqVo);
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) queryResult.get("data");
            if (dataList == null) dataList = new ArrayList<>();

            List<ApiInfoExportVO> exportData = dataList.stream().map(item -> {
                ApiInfoExportVO vo = new ApiInfoExportVO();
                vo.setRequestId(String.valueOf(item.getOrDefault("requestId", "")));
                vo.setRequestTime(String.valueOf(item.getOrDefault("requestTime", "")));
                vo.setResponseCode(String.valueOf(item.getOrDefault("responseCode", "")));
                // 这里虽然是 CSV 不限制长度，但建议还是对极度异常的数据做个兜底
                vo.setRequestBody(String.valueOf(item.getOrDefault("requestBody", "")));
                vo.setResponseBody(String.valueOf(item.getOrDefault("responseBody", "")));

                Map<String, Object> apiInfo = (Map<String, Object>) item.get("apiInfo");
                if (apiInfo != null) {
                    vo.setApiName((String) apiInfo.get("apiName"));
                    vo.setApiCode((String) apiInfo.get("apiCode"));
                    vo.setCategoryName((String) apiInfo.get("categoryName"));
                    vo.setFactory((String) apiInfo.get("factory"));
                    vo.setOrderNumber((String) apiInfo.get("orderNumber"));
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
}
