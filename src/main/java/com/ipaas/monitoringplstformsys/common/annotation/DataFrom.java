package com.ipaas.monitoringplstformsys.common.annotation;

import java.lang.annotation.*;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: nice
 * @since: 2024/12/2024/12/24 14:21
 * @history: 2024/12/2024/12/24 created by nice
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataFrom {
    String column();
    String table();
    String where();
}
