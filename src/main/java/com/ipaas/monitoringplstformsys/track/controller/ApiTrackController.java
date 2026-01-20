package com.ipaas.monitoringplstformsys.track.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;

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
        apiTrackService.saveOrUpdateApiRunTrackInfo(updateDocByIdVos);
        return Response.ok();
    }

    @PostMapping("/queryApiInfo")
    public Response queryApiInfo(@RequestBody Map<String, Object> rawMap){
        // 2. 在这一行打断点
        System.out.println(rawMap);

        // 3. 这里的 debug 就能看到前端传来的所有 key-value，包括你没定义的

        // 为了不破坏后面代码，可以手动转回去（可选）
         ApiInfoReq reqVo = new ObjectMapper().convertValue(rawMap, ApiInfoReq.class);
         return Response.ok().setData(apiTrackService.queryApiInfo(reqVo));

//        return null; // 临时测试完就还原代码
//        return Response.ok().setData(apiTrackService.queryApiInfo(reqVo));
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

