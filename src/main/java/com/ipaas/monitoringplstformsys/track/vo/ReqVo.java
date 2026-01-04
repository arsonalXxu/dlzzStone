package com.ipaas.monitoringplstformsys.track.vo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

@Data
public class ReqVo  {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String targetId;
    private Integer page;
    private Integer pageSize;
    private String envId;
    private String indexName;

    private List<String> apiCodes;
    private String startTime;
    private String endTime;

}
