package com.ipaas.monitoringplstformsys.service;

import com.ipaas.monitoringplstformsys.common.constant.DeipaasConstant;
import com.ipaas.monitoringplstformsys.common.request.BasePageVo;
import com.ipaas.monitoringplstformsys.common.response.PageQueryResult;
import com.ipaas.monitoringplstformsys.dao.TenantUsersDao;
import com.ipaas.monitoringplstformsys.module.FndTenantUsers;
import com.ipaas.monitoringplstformsys.module.dto.TenantUserReqDto;
import com.ipaas.monitoringplstformsys.module.vo.TenantUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
public class TenantUserService {
    @Resource
    private TenantUsersDao tenantUsersDao;
    public BasePageVo<TenantUserVo> queryAllUser(TenantUserReqDto reqDto, HttpServletRequest httpServletRequest) {
        BasePageVo<TenantUserVo> basePageVo = new BasePageVo<>();
        PageQueryResult<FndTenantUsers> result = tenantUsersDao.queryAllUser(reqDto,httpServletRequest);
        ArrayList<TenantUserVo> tenantUserVos = new ArrayList<>();
        result.getResult().forEach(fndTenantUser->{
            TenantUserVo tenantUserVo = new TenantUserVo();
            BeanUtils.copyProperties(fndTenantUser, tenantUserVo);
            tenantUserVos.add(tenantUserVo);
        });
        basePageVo.setTotal(result.getCount());
        basePageVo.setData(tenantUserVos);
        return basePageVo;
    }
}
