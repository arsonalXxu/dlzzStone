package com.ipaas.monitoringplstformsys.track.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
@ColumnWidth(20) // 设置默认列宽
public class ApiInfoExportVO {

    @ExcelProperty("请求ID")
    private String requestId;

    @ExcelProperty("请求时间")
    private String requestTime;

    @ExcelProperty("响应码")
    private String responseCode;

    @ExcelProperty("API名称")
    private String apiName;

    @ExcelProperty("API编码")
    private String apiCode;

    @ExcelProperty("所属应用系统")
    private String categoryName;

    @ExcelProperty("工厂")
    private String factory;

    @ExcelProperty("订单号")
    private String orderNumber;

    @ExcelProperty("请求报文")
    @ColumnWidth(50)
    private String requestBody;

    @ExcelProperty("响应报文")
    @ColumnWidth(50)
    private String responseBody;
}
