package com.ipaas.monitoringplstformsys.elasticsearch.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: huangjinrao
 * @since: 2022/12/8 18:29
 * @history: 2022/12/8 18:29 created by huangjinrao
 */
public class DateUtil {


    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String HH_MM_SS = "HH:mm:ss";

    /**
     * 格式化到纳秒
     *
     * @param dateString
     * @return
     */
    public static String toNanosecond(String dateString) {
        SimpleDateFormat format1 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        SimpleDateFormat format2 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);
        Date date = null;
        try {
            date = format1.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return format2.format(date);
    }

    /**
     * 日期只截取时间
     *
     * @param dateString
     * @return
     */
    public static String interceptTime(String dateString) {
        SimpleDateFormat format1 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);
        SimpleDateFormat format2 = new SimpleDateFormat(HH_MM_SS);
        Date date = null;
        try {
            date = format1.parse(StringUtils.isNotBlank(dateString) ? dateString : format1.format(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return format2.format(date);
    }
}
