package com.ipaas.monitoringplstformsys.elasticsearch.service;


import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.json.JsonData;
import com.ipaas.monitoringplstformsys.elasticsearch.dto.EsSearchBaseBo;
import com.ipaas.monitoringplstformsys.elasticsearch.dto.EsUpdateBaseBo;

import java.io.IOException;
import java.util.List;

public interface EsCommonService {


    <T> SearchResponse<T> pageSearch(EsSearchBaseBo esPageSearch, Class<T> clazz) throws IOException;

    <T> IndexResponse save(String index, T document) throws IOException;

    <T> IndexResponse save(String index, String id, T document) throws IOException;

    <T> BulkResponse save(String index, List<T> documents) throws IOException;

    <T> BulkResponse saveByNoId(String index, List<T> documents) throws IOException;

    UpdateByQueryResponse updateByQuery(EsUpdateBaseBo updateBO) throws IOException;

    <T> SearchResponse<T> search(SearchRequest searchRequest, Class<T> clazz) throws IOException;

    GetResponse<JsonData> get(String index, String id) throws IOException;
}
