package com.hisco.cmm.modules;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ObjectUtil {

    /**
     * Object 를 Map으로 변환 처리
     * 
     * @param object
     * @return
     */
    public static Map<String, Object> ConvertMap(Object object) {
        if (object == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        Method[] methods = object.getClass().getMethods();
        String strFieldName;
        for (int i = 0; i < methods.length; i++) {
            String strMethodName = methods[i].getName();

            if (strMethodName.startsWith("get") || strMethodName.startsWith("is")) {
                if (strMethodName.startsWith("get")) {
                    strFieldName = strMethodName.replaceFirst("get", "");
                    strFieldName = strFieldName.substring(0, 1).toLowerCase() + strFieldName.substring(1);
                } else if (strMethodName.startsWith("is")) {
                    strFieldName = strMethodName.replaceFirst("is", "");
                    strFieldName = strFieldName.substring(0, 1).toLowerCase() + strFieldName.substring(1);
                } else {
                    strFieldName = null;
                }

                if (StringUtil.IsEmpty(strFieldName) || strFieldName.equals("class"))
                    continue;

                try {
                    map.put(strFieldName, methods[i].invoke(object));
                } catch (Exception e) {
                }
            }
        }

        return map;
    }

    /**
     * JSON 으로 변환
     * 
     * @param object
     * @return
     */
    public static String ConvertJsonString(Object object) {
        return JsonUtil.Object2String(object);
    }

    /**
     * 데이터 값 복사
     * 
     * @param target
     * @param data
     */
    public static void ObjectValueCopy(Object target, Object data) {
        if (target == null)
            return;
        if (data == null)
            return;

        Method[] methods = data.getClass().getMethods();
        String strMethodName;

        for (int i = 0; i < methods.length; i++) {
            strMethodName = methods[i].getName();
            try {
                if (strMethodName.startsWith("get")) {
                    target.getClass().getMethod(strMethodName.replaceFirst("get", "set"), methods[i].getReturnType()).invoke(target, methods[i].invoke(data));
                } else if (strMethodName.startsWith("is")) {
                    target.getClass().getMethod(strMethodName.replaceFirst("is", "set"), methods[i].getReturnType()).invoke(target, methods[i].invoke(data));
                }
            } catch (NoSuchMethodException e) {
            } catch (IllegalArgumentException e) {
            } catch (Exception e) {
            }
        }
    }
}
