package com.ipaas.monitoringplstformsys.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipaas.monitoringplstformsys.common.constant.DeipaasConstant;
import com.ipaas.monitoringplstformsys.common.response.PageQueryResult;
import com.ipaas.monitoringplstformsys.mapper.FndTenantUsersMapper;
import com.ipaas.monitoringplstformsys.module.FndTenantUsers;
import com.ipaas.monitoringplstformsys.module.dto.TenantUserReqDto;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Repository
public class TenantUsersDao {
    @Resource
    private FndTenantUsersMapper fndTenantUsersMapper;

    public PageQueryResult<FndTenantUsers> queryAllUser(TenantUserReqDto reqDto, HttpServletRequest httpServletRequest) {
        String tenantId = httpServletRequest.getHeader(DeipaasConstant.TENANT_ID);
        if (reqDto.getKeyword() == null) {
            reqDto.setKeyword("");
        }
        Page<FndTenantUsers> page = new Page<>(reqDto.getPage(), reqDto.getPageSize());
        Page<FndTenantUsers> fndTenantUsersPage = fndTenantUsersMapper.queryAllUser(page, "%" + reqDto.getKeyword() + "%", tenantId);
        return new PageQueryResult<>(fndTenantUsersPage.getTotal(), fndTenantUsersPage.getRecords());
    }
}
