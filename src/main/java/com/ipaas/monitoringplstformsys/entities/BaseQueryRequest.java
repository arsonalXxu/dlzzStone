package com.ipaas.monitoringplstformsys.entities;

import lombok.Data;

@Data
public abstract class BaseQueryRequest implements BaseRequest {

    /**
     * 页码
     */
    private int page = 1;

    private int pageSize = 10;

    private String keyword = "";


}
