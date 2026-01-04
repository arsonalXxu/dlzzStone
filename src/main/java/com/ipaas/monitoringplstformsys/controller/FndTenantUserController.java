package com.ipaas.monitoringplstformsys.controller;

import com.ipaas.monitoringplstformsys.common.constant.DeipaasConstant;
import com.ipaas.monitoringplstformsys.common.constant.ReturnMessageConstant;
import com.ipaas.monitoringplstformsys.common.request.BasePageVo;
import com.ipaas.monitoringplstformsys.common.response.ResponseVo;
import com.ipaas.monitoringplstformsys.module.dto.ApiPageReqDto;
import com.ipaas.monitoringplstformsys.module.dto.TenantUserReqDto;
import com.ipaas.monitoringplstformsys.module.vo.ApiResVo;
import com.ipaas.monitoringplstformsys.module.vo.TenantUserVo;
import com.ipaas.monitoringplstformsys.service.TenantUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tenantUser")
public class FndTenantUserController {
    private final TenantUserService tenantUserService;

    public FndTenantUserController(TenantUserService tenantUserService) {
        this.tenantUserService = tenantUserService;
    }


    /**
     * 查询组织下成员
     *
     * @param reqDto
     * @return
     */
    @PostMapping("/queryAll")
    @ApiOperation(value = "查询组织下成员", notes = "查询组织下成员")
    public ResponseVo queryAll(@RequestBody TenantUserReqDto reqDto,HttpServletRequest httpServletRequest) {
        BasePageVo<TenantUserVo> tenantUserVoBasePageVo = tenantUserService.queryAllUser(reqDto,httpServletRequest);
        return ResponseVo.ok().setData(tenantUserVoBasePageVo).setMessage(ReturnMessageConstant.QUERY_SUCCESS);
    }
}
