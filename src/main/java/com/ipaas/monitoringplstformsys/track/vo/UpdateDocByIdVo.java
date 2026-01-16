package com.ipaas.monitoringplstformsys.track.vo;

import lombok.Data;

import java.util.List;

@Data
public class UpdateDocByIdVo {

    private List<String> docIds;
    private String requestId;

    private String result;

    private String preResolveTime;


}
