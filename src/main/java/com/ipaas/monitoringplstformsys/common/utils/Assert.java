package com.ipaas.monitoringplstformsys.common.utils;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import com.ipaas.monitoringplstformsys.common.exception.base.BaseExceptionEnumInterface;
import com.ipaas.monitoringplstformsys.common.exception.base.XDapBizException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public abstract class Assert {
    public Assert() {
    }

    public static void isTrue(boolean expression, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (!expression) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void isNull(Object object, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (object != null) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void isNotNull(Object object, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (object == null) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void hasLength(String text, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (StringUtils.isEmpty(text)) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void hasText(String text, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (StringUtils.isBlank(text)) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void doesNotContain(String textToSearch, String substring, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (StringUtils.isNotBlank(textToSearch) && StringUtils.isNotBlank(substring) && textToSearch.contains(substring)) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void notEmpty(Object[] array, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (ObjectUtils.isEmpty(array)) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void noNullElements(Object[] array, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (array != null) {
            Object[] var3 = array;
            int var4 = array.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object element = var3[var5];
                if (element == null) {
                    throw new XDapBizException(exceptionEnumInterface, args);
                }
            }
        }

    }

    public static void notEmpty(Collection<?> collection, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void notEmpty(Map<?, ?> map, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (CollectionUtils.isEmpty(map)) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        isNotNull(type, exceptionEnumInterface);
        if (!type.isInstance(obj)) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        isNotNull(superType, exceptionEnumInterface);
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void isBlank(String text, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (StringUtils.isNotBlank(text)) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void isNotEmpty(String text, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (text == null || text.isEmpty()) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void isNotBlank(String text, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (StringUtils.isBlank(text)) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void min(Integer value, Integer min, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        isNotNull(value, exceptionEnumInterface);
        if (value < min) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void max(Integer value, Integer max, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        isNotNull(value, exceptionEnumInterface);
        if (value > max) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void range(Integer value, Integer min, Integer max, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        min(value, min, exceptionEnumInterface, args);
        max(value, max, exceptionEnumInterface, args);
    }

    public static void min(Float value, Float min, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        isNotNull(value, exceptionEnumInterface, args);
        if (value < min) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void max(Float value, Float max, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        isNotNull(value, exceptionEnumInterface, args);
        if (value > max) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void range(Float value, Float min, Float max, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        min(value, min, exceptionEnumInterface, args);
        max(value, max, exceptionEnumInterface, args);
    }

    public static void min(Double value, Double min, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        isNotNull(value, exceptionEnumInterface, args);
        if (value < min) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void max(Double value, Double max, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        isNotNull(value, exceptionEnumInterface, args);
        if (value > max) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void range(Double value, Double min, Double max, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        min(value, min, exceptionEnumInterface, args);
        max(value, max, exceptionEnumInterface, args);
    }

    public static void length(String text, Integer min, Integer max, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        isNotNull(text, exceptionEnumInterface, args);
        if (min != null && text.length() < min) {
            throw new XDapBizException(exceptionEnumInterface, args);
        } else if (max != null && text.length() > max) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }

    public static void future(Date date, String key, BaseExceptionEnumInterface exceptionEnumInterface, Object... args) {
        if (date != null && date.compareTo(new Date()) <= 0) {
            throw new XDapBizException(exceptionEnumInterface, args);
        }
    }


}
