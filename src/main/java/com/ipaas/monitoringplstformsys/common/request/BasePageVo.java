package com.ipaas.monitoringplstformsys.common.request;

import com.ipaas.monitoringplstformsys.common.response.PageQueryResult;
import lombok.Data;

import java.util.List;

@Data
public class BasePageVo<T> {

    private List<T> table;
    private long total;

    public static BasePageVo packData(PageQueryResult queryResult) {
        BasePageVo vo = new BasePageVo();
        vo.setTable(queryResult.getResult());
        vo.setTotal(queryResult.getCount());
        return vo;
    }

    public BasePageVo setData(List<T> table) {
        setTable(table);
        return this;
    }

}
