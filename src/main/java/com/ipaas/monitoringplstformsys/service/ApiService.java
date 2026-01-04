package com.ipaas.monitoringplstformsys.service;

import com.ipaas.monitoringplstformsys.common.constant.DeipaasConstant;
import com.ipaas.monitoringplstformsys.common.request.BasePageQueryReqVo;
import com.ipaas.monitoringplstformsys.common.request.BasePageVo;
import com.ipaas.monitoringplstformsys.common.response.PageQueryResult;
import com.ipaas.monitoringplstformsys.dao.ApiDao;
import com.ipaas.monitoringplstformsys.module.*;
import com.ipaas.monitoringplstformsys.module.dto.ApiOwnerChildDTO;
import com.ipaas.monitoringplstformsys.module.vo.ApiCategoryNameReqVo;
import com.ipaas.monitoringplstformsys.module.vo.ApiConsumerNameReqVo;
import com.ipaas.monitoringplstformsys.module.vo.ApiNameReqVo;
import com.ipaas.monitoringplstformsys.module.dto.ApiOwnerDTO;
import com.ipaas.monitoringplstformsys.module.dto.ApiPageReqDto;
import com.ipaas.monitoringplstformsys.module.vo.ApiResVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiService {
    @Resource
    ApiDao apiDao;

    /**
     * 分页查询api
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    public BasePageVo<ApiResVo> queryApiList(ApiPageReqDto request, HttpServletRequest httpServletRequest) {
        String tenantId = httpServletRequest.getHeader(DeipaasConstant.TENANT_ID);
        PageQueryResult<ApiApis> apisPageQueryResult = apiDao.queryApiList(request, tenantId);

        BasePageVo<ApiResVo> responseListVo = new BasePageVo<>();
        responseListVo.setTotal(apisPageQueryResult.getCount());

        List<ApiResVo> apiResVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(apisPageQueryResult.getResult())) {
            // 1. 获取所有API ID列表
            List<String> apiIds = apisPageQueryResult.getResult().stream()
                    .map(ApiApis::getId)
                    .collect(Collectors.toList());

            // 2. 批量查询API关联的人员信息
            Map<String, List<ApiOwnerInfo>> ownerInfoMap = apiDao.batchGetApiOwners(apiIds);

            // 3. 构建API返回对象
            apisPageQueryResult.getResult().forEach(apiApis -> {
                ApiResVo apiResVo = new ApiResVo();
                // 原有字段设置
                apiResVo.setApiCode(apiApis.getApiCode());
                apiResVo.setApiName(apiApis.getApiName());
                apiResVo.setId(apiApis.getId());
                apiResVo.setCategoryName(apiApis.getCategoryName());
                apiResVo.setCategoryId(apiApis.getCategoryId());

                // 设置负责人信息
                List<ApiOwnerInfo> ownerInfoList = ownerInfoMap.get(apiApis.getId());
                apiResVo.setChildren(ownerInfoList);

                apiResVos.add(apiResVo);
            });
        }
        responseListVo.setTable(apiResVos);
        return responseListVo;
    }


    public List<String> queryAllApiCodes(String tenantId) {
        List<String> apiCodes = new ArrayList<>();
        List<ApiApis> apiApis = apiDao.queryAllApi(tenantId);
        if (!CollectionUtils.isEmpty(apiApis)) {
            apiApis.forEach(apiApi -> {
                String apiCode = apiApi.getApiCode();
                apiCodes.add(apiCode);
            });
        }
        return apiCodes;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateApiOwners(List<ApiOwnerDTO> apiOwnerDTOList) {

        for (ApiOwnerDTO apiOwnerDTO : apiOwnerDTOList) {
            // 参数验证
            if (StringUtils.isEmpty(apiOwnerDTO.getId())) {
                throw new IllegalArgumentException("API ID不能为空");
            }
            if (StringUtils.isEmpty(apiOwnerDTO.getApiCode())) {
                throw new IllegalArgumentException("API编码不能为空");
            }

            // 先删除该API所有现有的负责人关系
            apiDao.deleteByApiId(apiOwnerDTO.getId());

            // 从子节点中拿到对应的负责人
            List<ApiOwnerChildDTO> children = apiOwnerDTO.getChildren();
            if (!children.isEmpty()) {
                for (ApiOwnerChildDTO apiOwnerChildDTO : children) {
                    // 保存业务负责人
                    saveOwners(apiOwnerDTO, apiOwnerChildDTO.getBusinessOwners(), "business", apiOwnerChildDTO.getFactory());

                    // 保存技术负责人
                    saveOwners(apiOwnerDTO, apiOwnerChildDTO.getTechnicalOwners(), "technical", apiOwnerChildDTO.getFactory());
                }
            }
        }
    }

    private void saveOwners(ApiOwnerDTO apiOwnerDTO, List<String> owerAccount, String roleType, String factory) {
        if (owerAccount != null && !owerAccount.isEmpty()) {
            for (String userAccount : owerAccount) {
                ApiUserRelation relation = new ApiUserRelation();
                relation.setId(UUID.randomUUID().toString()); // 生成唯一ID
                relation.setApiId(apiOwnerDTO.getId());
                relation.setApiCode(apiOwnerDTO.getApiCode());
                relation.setUserAccount(userAccount);
                relation.setRoleType(roleType);
                relation.setFactory(factory);
                apiDao.insertApiOwner(relation);
            }
        }
    }

    public int countApiOwners(String apiId) {
        return apiDao.countByApiId(apiId);
    }


    public BasePageVo queryAllApiNames(BasePageQueryReqVo reqVo, String tenantId) {
        BasePageVo<ApiNameReqVo> basePageVo = new BasePageVo<>();
        PageQueryResult<ApiApis> result = apiDao.queryApiApi(reqVo, tenantId);
        List<ApiNameReqVo> apiNameReqVos = new ArrayList<>();
        result.getResult().forEach(apiApis -> {
            ApiNameReqVo apiNameReqVo = new ApiNameReqVo();
            apiNameReqVo.setApiName(apiApis.getApiName());
            apiNameReqVos.add(apiNameReqVo);
        });
        basePageVo.setTotal(result.getCount());
        basePageVo.setData(apiNameReqVos);

        return basePageVo;
    }

    public BasePageVo queryAllCategoryNames(BasePageQueryReqVo reqVo, String tenantId) {
        BasePageVo<ApiCategoryNameReqVo> basePageVo = new BasePageVo<>();
        PageQueryResult<ApiCategories> result = apiDao.queryApiCategories(reqVo, tenantId);
        List<ApiCategoryNameReqVo> apiCategoryNameReqVos = new ArrayList<>();
        result.getResult().forEach(apiCategories -> {
            ApiCategoryNameReqVo apiCategoryNameReqVo = new ApiCategoryNameReqVo();
            apiCategoryNameReqVo.setApiCategoryName(apiCategories.getCategoryName());
            apiCategoryNameReqVos.add(apiCategoryNameReqVo);
        });
        basePageVo.setTotal(result.getCount());
        basePageVo.setData(apiCategoryNameReqVos);

        return basePageVo;
    }

    public BasePageVo queryConsumerNames(BasePageQueryReqVo reqVo, String tenantId) {
        BasePageVo<ApiConsumerNameReqVo> basePageVo = new BasePageVo<>();
        PageQueryResult<FndConsumers> result = apiDao.queryFndConsumers(reqVo, tenantId);
        List<ApiConsumerNameReqVo> apiConsumerNameReqVos = new ArrayList<>();
        result.getResult().forEach(apiCategories -> {
            ApiConsumerNameReqVo apiConsumerNameReqVo = new ApiConsumerNameReqVo();
            apiConsumerNameReqVo.setApiConsumerName(apiCategories.getConsumerName());
            apiConsumerNameReqVos.add(apiConsumerNameReqVo);
        });
        basePageVo.setTotal(result.getCount());
        basePageVo.setData(apiConsumerNameReqVos);

        return basePageVo;
    }
}
