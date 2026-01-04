package com.ipaas.monitoringplstformsys.common.annotation;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ipaas.monitoringplstformsys.config.InternationalizationConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.util.ReflectUtil.hasField;


/**
 * @copyright: Shanghai Definesys Company.All rights reserved.
 * @description:
 * @author: yunzhi.wang
 * @since: 2024/3/6
 * @history: 2024/3/6 11:06 created by yunzhi.wang
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class TranslationAspect {
    private List<Field> getAllDeclaredFields(Class<?> objClazz) {
        List<Field> fieldList = new ArrayList<>();

        while (objClazz != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            List<Field> superClassFields = new ArrayList(Arrays.asList(objClazz.getDeclaredFields()));
            for (Field field : fieldList) {
                superClassFields.removeIf(superField -> superField.getName().equals(field.getName()));
            }

            fieldList.addAll(superClassFields);
            objClazz = objClazz.getSuperclass(); //得到父类,然后赋给自己
        }

        return fieldList;
    }

    private boolean isPrimite(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == String.class;
    }

    private void getTranslateField(Object result, boolean returnJson) {
        // 如果对象为空，则不需要翻译，直接返回
        if (result == null) {
            return;
        }
        // 如果对象是数组，则翻译每条数据
        if (result instanceof List || result instanceof ArrayList) {
            List list = ((List) result);
            // 每条数据递归翻译
            for (Object obj : list) {
                this.getTranslateField(obj, false);
            }
        } else if (result instanceof JSONObject && returnJson) {
            JSONObject jsonObject = (JSONObject) result;
            String resultStatus = jsonObject.getString("resultStatus");
            if (StringUtils.isNotBlank(resultStatus)) {
                String constantPath = "com.definesys.deipaas.apiflow.console.module.elasticsearch.constant.DeipaasElasticsearchConstant";
                String constantCode = getConstantCode(constantPath, resultStatus);
                String key = constantPath + "." + constantCode;
                String dataTrans = InternationalizationConfig.getMessage(key, resultStatus);
                jsonObject.put("resultStatus", dataTrans);

            }
        }  else if (result instanceof Map) {
            Map map = (Map) result;
            List arrayList = new ArrayList<>(map.values());
            for (Object obj : arrayList) {
                this.getTranslateField(obj, false);
            }
        } else {
            Class<?> resultClazz = result.getClass();
            // 如果不是项目中自开发的类，例如Java标准类，则不需要翻译，直接返回
            if (!resultClazz.getTypeName().startsWith("com.definesys")) {
                return;
            }

            List<Field> declaredFields = this.getAllDeclaredFields(resultClazz);
            for (Field field : declaredFields) {
                field.setAccessible(true);

                try {
                    // 如果对象是简单类型，判断是否存在@TranslateField注解，进行翻译
                    if (isPrimite(field.getType())) {
                        TranslateField translateField = field.getAnnotation(TranslateField.class);
                        if (translateField != null && (
                                (StringUtils.isNotBlank(translateField.tableName()) && StringUtils.isNotBlank(translateField.columnName()) )
                                        || StringUtils.isNotBlank(translateField.constant()) )) {
                            if (StringUtils.isNotBlank(translateField.constant())) {
                                //获取当前要翻译字段的值
                                Field fieldColumn = resultClazz.getDeclaredField(field.getName());
                                fieldColumn.setAccessible(true);
                                String value = fieldColumn.get(result).toString();
                                // 常量类数据翻译
                                String constantPath = translateField.constant();
                                String constantCode = null;
                                String[] constantPaths = constantPath.split(",");

                                boolean isEnum = translateField.isEnum();
                                for (String path : constantPaths) {
                                    if (isEnum) {
                                        constantCode = getEnumValue(path, value);
                                    } else {
                                        constantCode = getConstantCode(path, value);
                                    }
                                    if (StringUtils.isNotBlank(constantCode)) {
                                        constantPath = path;
                                        break;
                                    }
                                }
                                // 先锁定对应的翻译
                                String key = constantPath + "." + constantCode;
                                String dataTrans = InternationalizationConfig.getMessage(key, value);
                                // 设置翻译信息
                                fieldColumn.set(result, dataTrans);
                            } else {
                                // 如果存在联表查询的ID，则需要通过key的去获取当前实体类执行的主键属性去翻译
                                Field fieldCode = null;
                                if (StringUtils.isNotBlank(translateField.key())) {
                                    String key = translateField.key();
                                    try {
                                        fieldCode = resultClazz.getDeclaredField(key);
                                    } catch (NoSuchFieldException e) {
                                        fieldCode = resultClazz.getSuperclass().getDeclaredField(key);
                                    }
                                } else {
                                    // 获取数据库id
                                    fieldCode = getField(resultClazz,"id");
                                }
                                if (fieldCode == null) {
                                    continue;
                                }
                                fieldCode.setAccessible(true);
                                String code = (String) fieldCode.get(result);

                                Field fieldColumn = getField(resultClazz, field.getName());
                                if (fieldColumn == null) {
                                    continue;
                                }
                                fieldColumn.setAccessible(true);
                                // 多个数据用一个实体类
                                String[] tables = translateField.tableName().split(",");
                                for (String table : tables) {
                                    String key = table + "." + translateField.columnName() + "." + code;
                                    String value = null;
                                    // 原本的值
                                    if (fieldColumn.get(result) != null) {
                                        value = fieldColumn.get(result).toString();
                                    }
                                    // 翻译后的值
                                    String dataTrans = InternationalizationConfig.getDataTrans(key, value);
                                    // 内容不同需要替换
                                    if (value != null && !value.equals(dataTrans)) {
                                        // 设置翻译信息
                                        fieldColumn.set(result, dataTrans);
                                    }
                                }
                            }
                        }
                    } else if (field.getType() == List.class && field.getAnnotation(TranslateField.class) != null) {
                        // 处理 属性为List集合
                        TranslateField translateField = field.getAnnotation(TranslateField.class);
                        if (translateField.constant() != null) {
                            setBaseListTranslateData(result, resultClazz, field, translateField);
                        }
                    } else if (field.getType() == JSONArray.class && field.getAnnotation(TranslateField.class) != null) {
                        // 处理 JSONArray
                        TranslateField translateField = field.getAnnotation(TranslateField.class);
                        if (translateField.constant() != null) {
                            setJSONArrayTranslateData(result, resultClazz, field, translateField);
                        }
                    } else if (field.getType().isEnum()) {
                        // nothing
                    } else { // 如果对象是复杂类型，则继续递归判断
                        Object obj = field.get(result);
                        this.getTranslateField(obj, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (NoSuchFieldError e) {
                    // do nothing
                }
            }
        }
    }

    private void setJSONArrayTranslateData(Object result, Class<?> resultClazz, Field field, TranslateField translateField) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        String constantPath = translateField.constant();
        Field fieldColumn = resultClazz.getDeclaredField(field.getName());
        fieldColumn.setAccessible(true);
        JSONArray jsonArray = (JSONArray) fieldColumn.get(result);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String value = jsonObject.getString("label");
            String constantCode = getConstantCode(constantPath, value);
            // 锁定对应的翻译
            String key = constantPath + "." + constantCode;
            String dataTrans = InternationalizationConfig.getMessage(key, value);
            jsonObject.fluentPut("label", dataTrans);
            jsonArray.set(i, jsonObject);
        }
        // 设置翻译信息
        fieldColumn.set(result, jsonArray);
    }

    private void setBaseListTranslateData(Object result, Class<?> resultClazz, Field field, TranslateField translateField) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        String constantPath = translateField.constant();
        Field fieldColumn = resultClazz.getDeclaredField(field.getName());
        fieldColumn.setAccessible(true);
        List<String> list = (ArrayList) fieldColumn.get(result);
        for (int i = 0; i < list.size(); i++) {
            String value = list.get(i);
            String constantCode = getConstantCode(constantPath, value);
            // 先锁定对应的翻译
            String key = constantPath + "." + constantCode;
            String dataTrans = InternationalizationConfig.getMessage(key, value);
            list.set(i, dataTrans);
        }
        // 设置翻译信息
        fieldColumn.set(result, list);
    }

    private Field getField(Class<?> resultClazz, String fieldName) throws NoSuchFieldException {
        try {
            // 尝试获取指定名称的字段，如果找不到会抛出 NoSuchFieldException 异常
            return resultClazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // 查看其父类是否有该字段
            if (resultClazz.getSuperclass() != null && hasField(resultClazz.getSuperclass(),fieldName)) {
                return resultClazz.getSuperclass().getDeclaredField(fieldName);
            }
        }
        return null;
    }

    public static String getConstantCode(String constantPath,String value)  {
        try {
            // 使用类加载器加载接口类
            Class<?> interfaceClass = Class.forName(constantPath);
            // 获取接口中的常量名称和值
            Field[] fields = interfaceClass.getDeclaredFields();
            for (Field fieldProperty : fields) {
                if (fieldProperty.getType().equals(String.class)) { // 假设常量的类型是 String，根据实际情况修改
                    try {
                        String constantValue = (String) fieldProperty.get(null); // 因为常量是静态的，所以传入 null 表示不关联具体对象
                        if (constantValue.equals(value)) {
                            return fieldProperty.getName();
                        }
                    } catch (Exception e) {
                        //do nothing
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            // dp nothing
        }
        return null;
    }

    public static String getEnumValue(String enumPath, String enumValue) throws Exception {
        // 使用反射获取枚举类的 Class 对象
        Class<?> enumClass = Class.forName(enumPath);
        // 获取所有枚举值
        Enum<?>[] enumValues = (Enum<?>[]) enumClass.getMethod("values").invoke(null);
        // 遍历枚举值，找到匹配的枚举值并返回
        for (Enum<?> enumVal : enumValues) {
            String desc = (String) enumClass.getMethod("getDesc").invoke(enumVal);
            if (desc.equals(enumValue)) {
                return enumVal.toString();
            }
        }
        return null; // 如果未找到匹配的枚举值，返回 null
    }

}
