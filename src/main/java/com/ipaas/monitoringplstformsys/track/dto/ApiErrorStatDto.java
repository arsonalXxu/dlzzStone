package com.ipaas.monitoringplstformsys.track.dto;

import lombok.Data;

@Data
public class ApiErrorStatDto {
    /** 工厂 */
    private String factory;

    /** 接口名称 */
    private String apiName;

    /** 接口编码 (可选，建议带上区分同名接口) */
    private String apiCode;

    /** 报错信息 */
    private String msg;

    /** 该报错信息的数量 */
    private Long errorCount;

    /** 该接口的报错总数小计 */
    private Long subTotalErrorCount;

    /** 接口内占比 (字符串，例如 "25.00%") */
    private String errorRatio;
}
