package com.ipaas.monitoringplstformsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ipaas.monitoringplstformsys.module.FndTenantUsers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FndTenantUsersMapper extends BaseMapper<FndTenantUsers> {


    @Select("<script>" +
            "SELECT fu.USER_ACCOUNT AS userAccount,\n" +
            "       fu.USER_NAME AS userName,\n" +
            "       fu.ENABLE_STATUS AS userEnableStatus,\n" +
            "       ftu.*\n" +
            "FROM FND_USERS fu\n" +
            "JOIN FND_TENANT_USERS ftu ON fu.ID = ftu.USER_ID\n" +
            "WHERE ftu.TENANT_ID = #{tenantId} \n" +
            "<if test='keyword != null and keyword != \"\"'>\n" +
            "  AND (fu.USER_ACCOUNT LIKE CONCAT('%', #{keyword}, '%') OR fu.USER_NAME LIKE CONCAT('%', #{keyword}, '%'))\n" +
            "</if>\n" +
            "AND ftu.ENABLE_STATUS = 'ENABLED'\n" +
            "ORDER BY fu.ENABLE_STATUS DESC, ftu.CREATION_DATE DESC" +
            "</script>")
    Page<FndTenantUsers>  queryAllUser(@Param("page") Page<FndTenantUsers> page, @Param("keyword") String keyword, @Param("tenantId") String tenantId);}
