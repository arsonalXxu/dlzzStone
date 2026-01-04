package com.ipaas.monitoringplstformsys.module.entity;
import com.ipaas.monitoringplstformsys.module.vo.ApiConsumerStat;
import lombok.Data;
import java.util.List;

@Data
public class EsAggregationResult {
    private List<ApiConsumerStat> stats;
}
