package com.ipaas.monitoringplstformsys.track;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiInfoReq {
    private String envId;
    private String startTime;
    private String endTime;
    private List<String> requestIds;
    private String errorName;

    /** API 编码 (精确匹配) */
    private String apiCode;

    /** API 名称 (模糊匹配，可选) */
    private String apiName;

    /** 消费者编码 (精确匹配) - 注意：AggResultSearchReq 里叫 consumer，建议统一 */
    private String consumerCode;
    private String consumerName;

    /** 应用系统/分类编码 */
    private String categoryCode;
    private String categoryName;

    /** 结果状态列表 (用于筛选 成功/失败/业务失败) */
    private List<String> resultStatusList;

    /**
     * 工厂 (WERKS)
     */
    private String factory;

    /**
     * 订单号 (AUFNR)
     */
    private String orderNumber;
    private String orderItem;      // EBELP
    private String productNumber;  // AUFNR
    private String processNumber;  // AUFPL
    private String wbsNumber;      // MAT_PSPNR
    private String movementType;   // BWART
    private String msg;            // MSG
    private String requestBody;    // 请求体模糊搜
    private String responseBody;   // 响应体模糊搜

    // 分页参数
    private Integer page = 1;
    private Integer pageSize = 10;
}
