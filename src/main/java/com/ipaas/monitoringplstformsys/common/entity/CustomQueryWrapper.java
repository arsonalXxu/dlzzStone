package com.ipaas.monitoringplstformsys.common.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @copyright: Shanghai Definesys Company.All rights reserved.
 * @description:
 * @author: 舒胜
 * @since: 2025/1/14 17:29
 * @history: 2025/1/14 created by 舒胜
 */
public class CustomQueryWrapper<T> extends QueryWrapper<T> {
    /**
     * 动态添加 eq 条件
     *
     * @param column 列名
     * @param value  值（当值为 null 时跳过）
     * @return 当前实例
     */
    public CustomQueryWrapper<T> eqIfNotNull(String column, Object value) {
        if (value != null) {
            this.eq(column, value);
        }
        return this;
    }

    /**
     * 动态添加 like 条件
     *
     * @param column 数据库列名
     * @param value  列值（null 或空字符串时不添加）
     * @return 当前 QueryWrapper 实例
     */
    public CustomQueryWrapper<T> likeIfNotNull(String column, String value) {
        if (value != null && !value.isEmpty()) {
            this.like(column, value);
        }
        return this;
    }
}
