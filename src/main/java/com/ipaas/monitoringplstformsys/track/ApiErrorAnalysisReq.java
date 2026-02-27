package com.ipaas.monitoringplstformsys.track;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ApiErrorAnalysisReq implements Serializable {

    /** 环境ID */
    private String envId;

    /** 开始时间 (yyyy-MM-dd HH:mm:ss) */
    private String startTime;

    /** 结束时间 (yyyy-MM-dd HH:mm:ss) */
    private String endTime;

    /** API编码列表 (支持多选) */
    private String apiCode;

    /** API名称列表 (支持多选) */
    private List<String> apiName;

    /** 系统/分类名称 (模糊查询) */
    private String category;

    private String factory;

    /** 报错信息 (搜索关键词) */
    private String msg;

    /** 排序字段: "errorCount"(报错数), "subTotalErrorCount"(总数), "apiName" */
    private String sortField;

    /** 排序方式: "asc", "desc" */
    private String sortOrder;
}
