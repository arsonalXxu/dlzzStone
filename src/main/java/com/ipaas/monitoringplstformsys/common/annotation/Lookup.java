package com.ipaas.monitoringplstformsys.common.annotation;

import java.lang.annotation.*;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: skycloud
 * @since: 2021/6/29
 * @history: 1.2021/6/29 created by skycloud
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Lookup {

    String type();

    String code();

}
