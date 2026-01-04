package com.ipaas.monitoringplstformsys.module.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ipaas.monitoringplstformsys.track.dto.ApiRunReportsDto;
import lombok.Data;

import java.util.List;


@Data
public class SearchByCategoryDto {

    /**
     * 应用系统编码
     */
    @JsonProperty("categoryCode")
    private String categoryCode;

    /**
     * 应用系统编码
     */
    @JsonProperty("categoryName")
    private String categoryName;
    /**
     * 总调用次数
     */
    @JsonProperty("totalCalls")
    private Long totalCalls;

    /**
     * 错误次数
     */
    @JsonProperty("errorCount")
    private Long errorCounts;
    @JsonProperty("basicInfos")
    private List<ApiRunReportsDto> basicInfos;
}
