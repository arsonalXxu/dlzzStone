package com.ipaas.monitoringplstformsys.track;

import lombok.Data;

import java.util.List;

@Data
public class ApiInfoReq {
    private String envId;
    private String startTime;
    private String endTime;
    private List<String> requestIds;

    /**
     * 工厂 (WERKS)
     */
    private String factory;

    /**
     * 订单号 (AUFNR 或 EBELN)
     */
    private String orderNumber;
}
