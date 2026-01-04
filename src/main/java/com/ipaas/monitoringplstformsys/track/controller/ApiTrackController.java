package com.ipaas.monitoringplstformsys.track.controller;


import com.ipaas.monitoringplstformsys.common.response.Response;
import com.ipaas.monitoringplstformsys.track.AggResultSearchReq;
import com.ipaas.monitoringplstformsys.track.ApiInfoReq;
import com.ipaas.monitoringplstformsys.track.service.ApiTrackService;
import com.ipaas.monitoringplstformsys.track.vo.HitResultVo;
import com.ipaas.monitoringplstformsys.track.vo.UpdateDocByIdVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/es")
public class ApiTrackController {

    @Resource
    private ApiTrackService apiTrackService;


    @PostMapping("/stats")
    public Response getApiUsageStats() {
        apiTrackService.queryApiUsageStats();
        return Response.ok();
    }

    @PostMapping("/save")
    public Response getApiUsageStats(@RequestBody HitResultVo reqVo) {
        apiTrackService.saveApiUsageStats(reqVo);
        return Response.ok();
    }

    @PostMapping("/aggregationResult")
    public Response getAggregationResult(@RequestBody AggResultSearchReq reqVo) {
        return Response.ok().setData(apiTrackService.queryAggregationResult(reqVo));
    }

    @PostMapping("/saveDocumentById")
    public Response saveDocumentById(@RequestBody List<UpdateDocByIdVo> updateDocByIdVos) {
        apiTrackService.saveDocumentById(updateDocByIdVos);
        return Response.ok();
    }

    @PostMapping("/queryApiInfo")
    public Response queryApiInfo(@RequestBody ApiInfoReq reqVo){
        return Response.ok().setData(apiTrackService.queryApiInfo(reqVo));
    }

    @PostMapping("/ApiInfoExport")
    public void export(@RequestBody ApiInfoReq reqVo, HttpServletResponse response) {
        apiTrackService.exportApiInfo(reqVo, response);
    }

    @PostMapping("/aggregationResultByCategory")
    public Response aggregationResultByCategory(@RequestBody AggResultSearchReq reqVo){
        return Response.ok().setData(apiTrackService.aggregationResultByCategory(reqVo));
    }



}

