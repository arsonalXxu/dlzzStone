package com.ipaas.monitoringplstformsys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ApiDictionaryMapper {

    /**
     * 根据 Code 查询字典的 JSON 字符串 (DATA_ITEM)
     */
    @Select("SELECT DATA_ITEM FROM API_DICTIONARY " +
            "WHERE TENANT_ID = #{tenantId} " +
            "  AND CODE = #{code} " +
            "LIMIT 1")
    String queryDictionaryData(@Param("tenantId") String tenantId, @Param("code") String code);
}
