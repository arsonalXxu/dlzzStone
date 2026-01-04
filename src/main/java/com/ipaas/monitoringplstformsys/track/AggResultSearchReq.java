package com.ipaas.monitoringplstformsys.track;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class AggResultSearchReq implements Serializable {
    private String sortField; // 排序字段，如 "errorCount"
    private String sortOrder; // 排序方向，"asc" 或 "desc"
    private String apiRunReports;//API系统运行报告,用于区分是哪个系统下的API调用情况
    // 查询参数
    private String apiCode;
    private String apiName;
    private String category;
    private String consumer;
    private String factory; // 工厂
    private List<String> resultStatusList;

    private Map<String, String> afterKey; // 用于分页的游标

    private String startTime;

    private String endTime;

    private String preResolveStartTime;

    private String preResolveEndTime;

    // 分页参数
    private Integer page = 1;
    private Integer pageSize = 10;

    // 索引名称（可选）
    private String indexName;
}
