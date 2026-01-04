package com.ipaas.monitoringplstformsys.controller;

import com.ipaas.monitoringplstformsys.common.constant.DeipaasConstant;
import com.ipaas.monitoringplstformsys.common.constant.ReturnMessageConstant;
import com.ipaas.monitoringplstformsys.common.request.BasePageQueryReqVo;
import com.ipaas.monitoringplstformsys.common.request.BasePageVo;
import com.ipaas.monitoringplstformsys.common.response.ResponseVo;
import com.ipaas.monitoringplstformsys.module.dto.ApiOwnerDTO;
import com.ipaas.monitoringplstformsys.module.dto.ApiPageReqDto;
import com.ipaas.monitoringplstformsys.module.vo.ApiResVo;
import com.ipaas.monitoringplstformsys.service.ApiService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/queryApi")
public class ApiController {
    private final ApiService apiService;


    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/queryApiList")
    @ApiOperation(value = "查询api列表", notes = "查询api列表")
    public ResponseVo queryApiList(@RequestBody ApiPageReqDto request, HttpServletRequest httpServletRequest) {
        BasePageVo<ApiResVo> result = apiService.queryApiList(request, httpServletRequest);
        return ResponseVo.ok().setData(result);
    }
    @PostMapping("/saveApiOwners")
    @ApiOperation(value = "业务负责人或者技术负责人新增、修改", notes = "业务负责人或者技术负责人新增、修改")
    public ResponseVo saveApiOwners(@RequestBody List<ApiOwnerDTO> apiOwnerDTOList) {
        apiService.saveOrUpdateApiOwners(apiOwnerDTOList);
        return ResponseVo.ok().setMessage(ReturnMessageConstant.SAVE_SUCCESS);
    }

    @GetMapping("/queryAllApiCodes")
    @ApiOperation(value = "查询所有API编码", notes = "获取系统中所有API的编码集合")
    public ResponseVo queryAllApiCodes(HttpServletRequest httpServletRequest) {
        String tenantId = httpServletRequest.getHeader(DeipaasConstant.TENANT_ID);
        List<String> apiCodes = apiService.queryAllApiCodes(tenantId);
        return ResponseVo.ok().setData(apiCodes);
    }

    @PostMapping("/queryApiName")
    @ApiOperation(value = "查询所有API名称",notes = "查询所有API名称")
    public ResponseVo queryAllApiNames(@RequestBody BasePageQueryReqVo reqVo, HttpServletRequest httpServletRequest){
        String tenantId = httpServletRequest.getHeader(DeipaasConstant.TENANT_ID);

        return ResponseVo.ok().setData(apiService.queryAllApiNames(reqVo,tenantId)).setMessage(ReturnMessageConstant.QUERY_SUCCESS);
    }
    @PostMapping("/queryCategoryName")
    @ApiOperation(value = "查询所有应用系统",notes = "查询所有应用系统")
    public ResponseVo queryAllCategoryNames(@RequestBody BasePageQueryReqVo reqVo,HttpServletRequest httpServletRequest){
        String tenantId = httpServletRequest.getHeader(DeipaasConstant.TENANT_ID);
        return ResponseVo.ok().setData(apiService.queryAllCategoryNames(reqVo,tenantId)).setMessage(ReturnMessageConstant.QUERY_SUCCESS);
    }
    @PostMapping("/queryConsumerName")
    @ApiOperation(value = "查询所有应用系统")
    public ResponseVo queryConsumerNames(@RequestBody BasePageQueryReqVo reqVo,HttpServletRequest httpServletRequest){
        String tenantId = httpServletRequest.getHeader(DeipaasConstant.TENANT_ID);
        return ResponseVo.ok().setData(apiService.queryConsumerNames(reqVo,tenantId)).setMessage(ReturnMessageConstant.QUERY_SUCCESS);
    }

}
