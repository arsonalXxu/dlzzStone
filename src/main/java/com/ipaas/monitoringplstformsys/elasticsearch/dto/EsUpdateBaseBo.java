package com.ipaas.monitoringplstformsys.elasticsearch.dto;


import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsUpdateBaseBo implements Serializable {

    /** 索引名称（必填） */
    private String indexName;

    /** 查询条件（用于筛选需要更新的文档，必填） */
    private Query queryBuilder;

    private Script script;


}
