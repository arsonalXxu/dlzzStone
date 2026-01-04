package com.ipaas.monitoringplstformsys.common.enummodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author hehe.zhang
 * @date 2022/6/29 13:28
 */
@Getter
@AllArgsConstructor
public enum SortTypeEnum {
    /**
     * 正序
     */
    ASC("asc", "正序"),
    /**
     * 倒序
     */
    DESC("desc", "倒序"),
    /**
     * 中文正序
     */
    CHINESE_ASC("chineseAsc", "中文正序"),
    /**
     * 中文倒序
     */
    CHINESE_DESC("chineseDesc", "中文倒序"),
    ;
    /**
     * 编码
     */
    private String code;
    /**
     * 描述
     */
    private String desc;

    /**
     * 获取对应的描述
     *
     * @param code
     * @return
     */
    public static String getEnumByCode(String code) {
        SortTypeEnum[] values = SortTypeEnum.values();
        for (SortTypeEnum value : values) {
            if (StringUtils.equals(value.getCode(), code)) {
                return value.getDesc();
            }
        }
        return "";
    }
}
