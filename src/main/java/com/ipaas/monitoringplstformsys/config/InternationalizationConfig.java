package com.ipaas.monitoringplstformsys.config;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ipaas.monitoringplstformsys.common.annotation.TranslationAspect;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class InternationalizationConfig {

    public static Map<String, Map<String, JSONObject>> DATA_TRANS_MAP = new HashMap<>();

    public static Map<String, Map<String, String>> MESSAGE_MAP = new HashMap<>();

    /**
     * 异常信息翻译
     * @param key key 是 packagePath+code 保证唯一
     * @param message 消息
     * @return
     */
    public static String getMessage(String key, String message) {
        String languageCode = LanguageCodeHolder.getLanguageCode();
        if  (!MESSAGE_MAP.containsKey(languageCode)) {
            return message;
        }
        //获取对应语言的翻译信息
        Map<String, String> lanauageMessageMap = MESSAGE_MAP.get(languageCode);
        String newMessage = lanauageMessageMap.get(key);
        return StringUtils.isBlank(newMessage) ? message: newMessage;
    }

    /**
     * 数据库信息翻译
     * @param code fieldCode 数据库id
     * @param data 翻译数据信息
     * @return
     */
    public static String getDataTrans(String code, String data) {
        String languageCode = LanguageCodeHolder.getLanguageCode();
        if  (!DATA_TRANS_MAP.containsKey(languageCode)) {
            return data;
        }
        //获取对应语言的翻译信息
        Map<String, JSONObject> lanauageDataTransMap = DATA_TRANS_MAP.get(languageCode);
        JSONObject dataJson = lanauageDataTransMap.get(code);
        if (dataJson == null) {
            return data;
        }
        String transText = dataJson.getString("transText");
        return StringUtils.isBlank(transText) ? data: transText;
    }

    /**
     * 加载所有国际化语音包内容
     * @param dataTransMap 数据库数据
     * @param messageMap 异常信息数据
     */
    public void loadingMultiLanguage(Map<String, Map<String, JSONObject>> dataTransMap, Map<String, Map<String, String>> messageMap) {
        DATA_TRANS_MAP = dataTransMap;
        MESSAGE_MAP = messageMap;
    }

    /**
     * 添加新添加的国际化语言包内容
     * @param dataTransMap 数据库数据
     * @param messageMap 异常信息数据
     */
    public void addMultiLanguage(Map<String, Map<String, JSONObject>> dataTransMap, Map<String, Map<String, String>> messageMap) {
        DATA_TRANS_MAP.putAll(dataTransMap);
        MESSAGE_MAP.putAll(messageMap);
    }

    public static String getTranslateSpecialConstant(String constantPath,String value){
        return getMessage(constantPath + "." + TranslationAspect.getConstantCode(constantPath, value), value);
    }
}
