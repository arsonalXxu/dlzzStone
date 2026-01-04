package com.ipaas.monitoringplstformsys.common.request;

import lombok.Data;

import java.util.List;

@Data
public class BasePageQueryReqVo extends BaseQueryRequest {
    private String keyword = "";
    /**
     * 零件号
     */
    private List<String> linPn;

    @Override
    public void validator() {

    }
}
