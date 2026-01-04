package com.ipaas.monitoringplstformsys.entities;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ipaas.monitoringplstformsys.common.json.MpaasDateTimeDeserializer;
import com.ipaas.monitoringplstformsys.common.json.MpaasDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: skycloud
 * @since: 2021/1/14
 * @history: 1.2021/1/14 created by skycloud
 */
@Getter
@Setter
public class NoTenBasePojo implements Serializable {
    @TableField(fill = FieldFill.INSERT)
    String owner;

    @TableField(fill = FieldFill.INSERT)
    String createdBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    String lastUpdatedBy;

    @JsonDeserialize(using = MpaasDateTimeDeserializer.class)
    @JsonSerialize(using = MpaasDateTimeSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    Date creationDate;

    @JsonDeserialize(using = MpaasDateTimeDeserializer.class)
    @JsonSerialize(using = MpaasDateTimeSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    Date lastUpdateDate;

    @Version
    @TableField(fill = FieldFill.INSERT)
    Integer objectVersionNumber;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    String userOperationId;
}
