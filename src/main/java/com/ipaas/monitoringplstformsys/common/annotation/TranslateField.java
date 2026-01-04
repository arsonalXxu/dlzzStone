package com.ipaas.monitoringplstformsys.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于翻译字段
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TranslateField {

    String tableName() default "";

    String columnName() default "";

    String key() default "";

    String constant() default "";

    boolean isEnum() default false;
}
