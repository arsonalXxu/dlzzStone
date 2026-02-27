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

    @ExcelProperty("采购订单号")
    private String orderNumber;

    @ExcelProperty("采购订单行项目") // EBELP
    private String orderItem;

    @ExcelProperty("生产订单号") // AUFNR
    private String productNumber;

    @ExcelProperty("工序号") // AUFPL
    private String processNumber;

    @ExcelProperty("WBS") // MAT_PSPNR
    private String wbsNumber;

    @ExcelProperty("移动类型") // BWART
    private String movementType;

    @ExcelProperty("响应MSG") // MSG
    private String msg;

    @ExcelProperty("请求报文")
    @ColumnWidth(50)
    private String requestBody;

    @ExcelProperty("响应报文")
    @ColumnWidth(50)
    private String responseBody;

    @ExcelProperty("处理人")
    private String processor;

    @ExcelProperty("预计处理时间")
    private String preResolveTime;

    @ExcelProperty("处理结果")
    private String result;
}
