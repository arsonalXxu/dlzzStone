package com.ipaas.monitoringplstformsys.elasticsearch.dto;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @copyright: Shanghai Definesys Company.All rights reserved.
 * @description:
 * @author: 曾家豪
 * @since: 2022/11/30 16:47
 * @history: 2022/11/30 created by 曾家豪
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsSearchBaseBo implements Serializable {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String targetId;
    private Integer page;
    private Integer pageSize;
    private String indexName;
    private String sortField;

    private String aggregationName;

    private Aggregation aggregation;

    private Integer size;

    /**
     * 必须传排序规则，不然会报错
     */
//    private List<SortReqDto> definitionSort;
    private List<String> includes;
    private Query queryBuilder;

    public Integer getPage() {
        return page - 1;
    }
}
