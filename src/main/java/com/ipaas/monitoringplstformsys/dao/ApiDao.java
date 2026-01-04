package com.ipaas.monitoringplstformsys.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipaas.monitoringplstformsys.common.request.BasePageQueryReqVo;
import com.ipaas.monitoringplstformsys.common.response.PageQueryResult;
import com.ipaas.monitoringplstformsys.mapper.ApiApisMapper;
import com.ipaas.monitoringplstformsys.module.*;
import com.ipaas.monitoringplstformsys.module.dto.ApiPageReqDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;

@Repository
public class ApiDao {
    private static final String RUN_STATUS = "RUN_STATUS";
    private static final String USER_NAME = "USER_NAME";

    @Resource
    private ApiApisMapper apiApisMapper;

    /**
     * 分页查询api列表
     * @return
     */
    public PageQueryResult<ApiApis> queryApiList( ApiPageReqDto request, String tenantId) {
        Page<ApiApis> page = new Page<>(request.getPage(),request.getPageSize());
        Page<ApiApis> selectPage = apiApisMapper.queryApiList(page, request.getKeyword(), tenantId);
        return new PageQueryResult<>(selectPage.getTotal(),selectPage.getRecords());

    }


    public List<ApiApis> queryAllApi(String tenantId) {
        return apiApisMapper.queryAllApi(tenantId);
    }

    public int countByApiId(String apiId) {
        return apiApisMapper.countByApiId(apiId);
    }

    public void deleteByApiId(String apiId) {
        apiApisMapper.deleteByApiId(apiId);
    }

    public void insertApiOwner(ApiUserRelation relation) {
        apiApisMapper.insertApiOwner(relation);
    }

    public Map<String, List<ApiOwnerInfo>> batchGetApiOwners(List<String> apiIds) {
        if (CollectionUtils.isEmpty(apiIds)) {
            return Collections.emptyMap();
        }
        // 1. 查询数据库
        List<ApiUserRelation> relations = apiApisMapper.selectOwnersByApiIds(apiIds);

        // 2. 按API ID分组
        Map<String, List<ApiOwnerInfo>> resultMap = new HashMap<>();

        relations.forEach(relation -> {
            String apiId = relation.getApiId();
            String factory = relation.getFactory();

            // 获取该 apiId 对应的 List<ApiOwnerInfo>
            List<ApiOwnerInfo> ownerList = resultMap.computeIfAbsent(apiId, k -> new ArrayList<>());

            // 查找是否已有该 factory 的节点
            ApiOwnerInfo info = null;
            for (ApiOwnerInfo apiOwnerInfo : ownerList) {
                if (Objects.equals(factory, apiOwnerInfo.getFactory())) {
                    info = apiOwnerInfo;
                    break;
                }
            }

            // 如果不存在，创建新节点
            if (info == null) {
                info = new ApiOwnerInfo();
                info.setFactory(factory);
                info.setBusinessOwners(new ArrayList<>());
                info.setTechnicalOwners(new ArrayList<>());
                ownerList.add(info);
            }

            // 添加角色
            String username = apiApisMapper.queryUserName(relation.getUserAccount());

            // 3. 按角色类型分组
            if ("business".equals(relation.getRoleType())) {
                info.getBusinessOwners().add(relation.getUserAccount());
            } else if ("technical".equals(relation.getRoleType())) {
                info.getTechnicalOwners().add(relation.getUserAccount());
            }
        });

        return resultMap;
    }

    public PageQueryResult<ApiApis> queryApiApi(BasePageQueryReqVo reqVo, String tenantId) {
        if (reqVo.getKeyword() == null) {
            reqVo.setKeyword("");
        }
        Page<ApiApis> page = new Page<>(reqVo.getPage(), reqVo.getPageSize());
        Page<ApiApis> apiApisPage = apiApisMapper.queryApiApi(page,"%" + reqVo.getKeyword() + "%", tenantId);
        return new PageQueryResult<>(apiApisPage.getTotal(), apiApisPage.getRecords());

    }

    public PageQueryResult<ApiCategories> queryApiCategories(BasePageQueryReqVo reqVo, String tenantId) {
        if (reqVo.getKeyword() == null) {
            reqVo.setKeyword("");
        }
        Page<ApiCategories> page = new Page<>(reqVo.getPage(), reqVo.getPageSize());
        Page<ApiCategories> apiCategoriesPage = apiApisMapper.queryApiCategories(page,"%" + reqVo.getKeyword() + "%", tenantId);
        return new PageQueryResult<>(apiCategoriesPage.getTotal(), apiCategoriesPage.getRecords());
    }

    public PageQueryResult<FndConsumers> queryFndConsumers(BasePageQueryReqVo reqVo, String tenantId) {
        if (reqVo.getKeyword() == null) {
            reqVo.setKeyword("");
        }
        Page<FndConsumers> page = new Page<>(reqVo.getPage(), reqVo.getPageSize());
        Page<FndConsumers> fndConsumersPage = apiApisMapper.queryFndConsumers(page,"%" + reqVo.getKeyword() + "%", tenantId);
        return new PageQueryResult<>(fndConsumersPage.getTotal(), fndConsumersPage.getRecords());
    }
}
