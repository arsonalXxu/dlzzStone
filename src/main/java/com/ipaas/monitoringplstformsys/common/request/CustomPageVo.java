package com.ipaas.monitoringplstformsys.common.request;

import com.ipaas.monitoringplstformsys.common.response.PageQueryResult;
import lombok.Data;

import java.util.List;


@Data
public class CustomPageVo<T> {
    private List<T> table;
    private long num;
    private long total;
    private long successNum;
    private long failNum;

    public static CustomPageVo packData(PageQueryResult queryResult) {
        CustomPageVo vo = new CustomPageVo();
        vo.setTable(queryResult.getResult());
        vo.setTotal(queryResult.getCount());
        return vo;
    }

    public CustomPageVo setData(List<T> table) {
        setTable(table);
        return this;
    }
}
