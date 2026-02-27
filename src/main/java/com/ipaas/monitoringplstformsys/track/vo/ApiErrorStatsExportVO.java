package com.ipaas.monitoringplstformsys.track.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ApiErrorStatsExportVO {

    @ExcelProperty("工厂")
    private String factory;

    @ExcelProperty("API分类")
    private String categoryName;

    @ExcelProperty("API编码")
    private String apiCode;

    @ExcelProperty("API名称")
    private String apiName;

    @ExcelProperty("错误分类")
    private String errorType;

    @ExcelProperty("错误次数")
    private Long errorCount;

    @ExcelProperty("统计开始时间")
    private String startTime;

    @ExcelProperty("统计结束时间")
    private String endTime;
}

