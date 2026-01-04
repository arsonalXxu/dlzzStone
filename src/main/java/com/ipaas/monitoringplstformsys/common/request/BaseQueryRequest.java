package com.ipaas.monitoringplstformsys.common.request;

import lombok.Data;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: 李绍刚
 * @since: 2021/6/1
 * @history: 2021/6/1 created by 李绍刚
 */

@Data
public abstract class BaseQueryRequest implements BaseRequest {

    /**
     * 页码
     */
    private int page = 1;

    private int pageSize = 10;

    private String keyword = "";


}
