package com.ipaas.monitoringplstformsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipaas.monitoringplstformsys.module.ApiApis;
import com.ipaas.monitoringplstformsys.module.ApiCategories;
import com.ipaas.monitoringplstformsys.module.ApiUserRelation;
import com.ipaas.monitoringplstformsys.module.FndConsumers;
import com.ipaas.monitoringplstformsys.module.dto.ApiOwnerDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ApiApisMapper extends BaseMapper<ApiApis> {

    @Select("<script>" +
            "SELECT \n" +
            "    api.*,categories.category_code AS categoryCode, \n" +
            "    categories.category_name AS categoryName\n" +
            "FROM API_APIS api\n" +
            "LEFT JOIN API_CATEGORIES categories \n" +
            "    ON api.category_id = categories.id\n" +
            "WHERE api.tenant_id = #{tenantId}" +
            "AND api.deleted = 'N'" +
            "<if test='keyword != null and keyword != \"\"'> \n" +
            "AND (api.api_name LIKE CONCAT('%', #{keyword}, '%') \n" +
            "     OR api.api_code LIKE CONCAT('%', #{keyword}, '%')) \n" +
            "</if>" +
            "ORDER BY api.last_update_date DESC" +
            "</script>")
    Page<ApiApis> queryApiList(@Param("page") Page<ApiApis> page, @Param("keyword") String keyword, @Param("tenantId") String tenantId);


    @Select("SELECT * FROM API_APIS WHERE tenant_id = #{tenantId} AND deleted = 'N'")
    List<ApiApis> queryAllApi(@Param("tenantId") String tenantId);


    @Select("SELECT COUNT(*) FROM api_user_relation WHERE api_id = #{apiId}")
    int countByApiId(@Param("apiId") String apiId);

    @Delete("DELETE FROM api_user_relation WHERE api_id = #{apiId}")
    void deleteByApiId(@Param("apiId") String apiId);

    @Insert("INSERT INTO api_user_relation (id, api_id, api_code, user_account, role_type, factory) " +
            "VALUES (#{relation.id}, #{relation.apiId}, #{relation.apiCode}, #{relation.userAccount}, #{relation.roleType}, #{relation.factory})")
    void insertApiOwner(@Param("relation") ApiUserRelation relation);

    @Select("<script>" +
            "SELECT api_id, role_type, user_account, factory " +
            "FROM api_user_relation " +
            "WHERE api_id IN " +
            "<foreach item='id' collection='apiIds' open='(' separator=',' close=')'>" +
            "   #{id}" +
            "</foreach>" +
            "</script>")
    List<ApiUserRelation> selectOwnersByApiIds(@Param("apiIds") List<String> apiIds);

    @Select("<script>" +
            "SELECT fu.user_name " +
            "FROM api_user_relation aur " +
            "JOIN FND_USERS fu ON aur.user_account = fu.user_account " +
            "WHERE aur.api_code = #{apiCode}" +
            "</script>")
    List<String> queryProcessor(@Param("apiCode") String apiCode);
    @Select("<script>" +
            "SELECT fu.user_name " +
            "FROM api_user_relation aur " +
            "JOIN FND_USERS fu ON aur.user_account = fu.user_account " +
            "WHERE aur.api_code = #{apiCode}" +
            "AND aur.role_type = #{roleType}" +
            "</script>")
    List<String> queryProcessorByRoleType(@Param("apiCode") String apiCode,@Param("roleType")String roleType);
    @Select("<script>" +
            "SELECT user_name from FND_USERS where user_account = #{userAccount}" +
            "</script>")
    String queryUserName(@Param("userAccount") String userAccount);

    @Select("<script>" +
            "SELECT * FROM API_APIS WHERE tenant_id = #{tenantId}" +
            "<if test='keyword != null and keyword != \"\"'>\n" +
            "  AND (API_NAME LIKE CONCAT('%', #{keyword}, '%'))\n" +
            "</if>" +
            " AND deleted = 'N'" +
            "</script>")
    Page<ApiApis> queryApiApi(@Param("page") Page<ApiApis> page, @Param("keyword") String keyword, @Param("tenantId") String tenantId);

    @Select("<script>" +
            "SELECT * FROM API_CATEGORIES WHERE tenant_id = #{tenantId}" +
            "<if test='keyword != null and keyword != \"\"'>\n" +
            "  AND (CATEGORY_NAME LIKE CONCAT('%', #{keyword}, '%'))\n" +
            "</if>" +
            " AND deleted = 'N'" +
            "</script>")
    Page<ApiCategories> queryApiCategories(@Param("page") Page<ApiCategories> page, @Param("keyword") String keyword, @Param("tenantId") String tenantId);
    @Select("<script>" +
            "SELECT * FROM  FND_CONSUMERS WHERE tenant_id = #{tenantId}" +
            "<if test='keyword != null and keyword != \"\"'>\n" +
            "  AND (CONSUMER_NAME LIKE CONCAT('%', #{keyword}, '%'))\n" +
            "</if>" +
            " AND deleted = 'N'" +
            "</script>")
    Page<FndConsumers> queryFndConsumers(@Param("page") Page<FndConsumers> page, @Param("keyword") String keyword, @Param("tenantId") String tenantId);

    // ==================== 新增的方法 ====================
    @Select("SELECT role_type, user_account " +
            "FROM api_user_relation " +
            "WHERE api_code = #{apiId} AND factory = #{factory}")
    List<ApiUserRelation> queryRelationByFactory(@Param("apiCode") String apiCode, @Param("factory") String factory);

    /**
     * 根据 apiCode 和 factory 查询负责人账号 (直接返回 user_account)
     */
    @Select("SELECT user_account " +
            "FROM api_user_relation " +
            "WHERE api_code = #{apiCode} AND factory = #{factory}")
    List<String> queryUserAccountByFactory(@Param("apiCode") String apiCode, @Param("factory") String factory);

    // 【新增】根据 apiCode, factory, roleType 查询 user_account
    @Select("SELECT user_account " +
            "FROM api_user_relation " +
            "WHERE api_code = #{apiCode} " +
            "  AND factory = #{factory} " +
            "  AND role_type = #{roleType}")
    List<String> queryUserAccountByFactoryAndRole(@Param("apiCode") String apiCode,
                                                  @Param("factory") String factory,
                                                  @Param("roleType") String roleType);

    /**
     * 【修改】根据 apiCode, factory, roleType 查询负责人【姓名】
     * 关联 FND_USERS 表获取 user_name
     */
    @Select("SELECT u.user_name " +
            "FROM api_user_relation r " +
            "JOIN FND_USERS u ON r.user_account = u.user_account " +
            "WHERE r.api_code = #{apiCode} " +
            "  AND r.factory = #{factory} " +
            "  AND r.role_type = #{roleType}")
    List<String> queryUserNameByFactoryAndRole(@Param("apiCode") String apiCode,
                                               @Param("factory") String factory,
                                               @Param("roleType") String roleType);
}
