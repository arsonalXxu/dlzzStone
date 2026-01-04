package com.ipaas.monitoringplstformsys.elasticsearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import com.definesys.deipaas.template.annotation.ES;
import com.definesys.deipaas.template.service.ElasticsearchService;
import com.ipaas.monitoringplstformsys.elasticsearch.dto.EsSearchBaseBo;
import com.ipaas.monitoringplstformsys.elasticsearch.dto.EsUpdateBaseBo;
import com.ipaas.monitoringplstformsys.elasticsearch.service.EsCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;


/**
 * @author zhongyuanxie
 * @version 1.0
 * @description: TODO
 * @date 2024/12/16 11:21
 */
@Component
@Slf4j
@Service
public class EsCommonServiceImpl implements EsCommonService {
    @Resource
    private ElasticsearchService elasticsearchService;

//    @Value("${deipaas.es-index-prefix:}")
//    public String indexPrefixName = "";

    public static final String OLD = "_old";
    public static final String HOT = "_hot";


    @Override
    public <T> SearchResponse<T> pageSearch(EsSearchBaseBo esPageSearch, Class<T> clazz) throws IOException {

        // 构建 SearchRequest
        SearchRequest searchRequest = SearchRequest.of(sr -> {
            sr.index(esPageSearch.getIndexName())
                    .query(esPageSearch.getQueryBuilder())
                    .from((esPageSearch.getPage()) * esPageSearch.getPageSize())
                    .size(esPageSearch.getSize());

            // 只有当聚合名称和聚合参数都不为空时才添加聚合查询
            if (StringUtils.hasText(esPageSearch.getAggregationName())
                    && esPageSearch.getAggregation() != null) {
                sr.aggregations(esPageSearch.getAggregationName(), esPageSearch.getAggregation());
            }

            if (!CollectionUtils.isEmpty(esPageSearch.getIncludes())) {
                sr.source(SourceConfig.of(i -> i.filter(f -> f.includes(esPageSearch.getIncludes()))));
            }
            return sr;
        });
        return this.elasticsearchService.search(searchRequest, clazz);
    }

    public <T> IndexResponse save(String index, T document) throws IOException {
        return this.elasticsearchService.save(index, document);
    }

    public <T> IndexResponse save(String index, String id, T document) throws IOException {
        return this.elasticsearchService.save(index, id, document);
    }

    public <T> BulkResponse save(String index, List<T> documents) throws IOException {
        return this.elasticsearchService.save(index, documents);
    }

    public <T> BulkResponse saveByNoId(String index, List<T> documents) throws IOException {
        return this.elasticsearchService.saveByNoId(index, documents);
    }

    public UpdateByQueryResponse updateByQuery(UpdateByQueryRequest request) throws IOException, ElasticsearchException {
        return this.elasticsearchService.getClient().updateByQuery(request);
    }

    public final UpdateByQueryResponse updateByQuery(Function<UpdateByQueryRequest.Builder, ObjectBuilder<UpdateByQueryRequest>> fn) throws IOException, ElasticsearchException {
        return this.elasticsearchService.getClient().updateByQuery(fn);
    }

    public UpdateByQueryResponse updateByQuery(EsUpdateBaseBo updateBO) throws IOException {
        UpdateByQueryRequest request =   UpdateByQueryRequest.of(sr->{
            sr.index(updateBO.getIndexName())
                    .query(updateBO.getQueryBuilder())
                    .script(updateBO.getScript());
            return sr;
        });
        return this.elasticsearchService.getClient().updateByQuery(request);
    }

    public <T> SearchResponse<T> search(SearchRequest searchRequest, Class<T> clazz) throws IOException {
        return this.elasticsearchService.getClient().search(searchRequest, clazz);
    }

    public GetResponse<JsonData> get(String index, String id) throws IOException {
        return this.elasticsearchService.get(index, id);
    }

}
