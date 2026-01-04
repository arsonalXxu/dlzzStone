package com.ipaas.monitoringplstformsys.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Length {

    String fieldName() default "";
    String message() default "";

    int min() default 0;

    int max() default Integer.MAX_VALUE;

}
