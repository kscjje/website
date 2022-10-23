package com.hisco.cmm.advice;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Value;

import com.hisco.cmm.util.SessionUtil;

/**
 * 등록자, 수정자 값을 세션에서 추출하여 설정
 */
@SuppressWarnings("unused")
public class BeforeCommonSetterAdvice implements MethodBeforeAdvice {

    private static final Logger log = LoggerFactory.getLogger(BeforeCommonSetterAdvice.class);

    /**
     * @Value("#{channel}")
     * private String channel;
     **/

    // 암/복호화 키
    @Value("${Globals.DbEncKey}")
    String DbEncKey;

    private static final String SET_PROPERTY_FOR_GET_LIST = "channel";
    private static final String[] GET_METHOD_PREFIX_LIST = { "get", "select", "retrieve", "list" };
    private static final String[] SET_PROPERTY_LIST = { "reguser", "moduser", "myOrgList", "dbEncKey" };

    public BeforeCommonSetterAdvice() {
    }

    public void before(Method method, Object args[], Object target) throws Throwable {
        if (args == null || args.length < 1) { // arguments가 존재하지 않는 경우
            return;
        }

        String userId = SessionUtil.getLoginId();

        for (int i = 0; i < args.length; i++) {
            for (String propertyName : SET_PROPERTY_LIST) {
                if (propertyName.equals("myOrgList")) {
                    setPropertyValue(args[i], propertyName, SessionUtil.getMyOrgList());
                } else if (propertyName.equals("dbEncKey")) {
                    setPropertyValue(args[i], propertyName, DbEncKey);
                } else if(userId != null) {
                    setPropertyValue(args[i], propertyName, userId);
                }

            }
        }
    }

    private void setPropertyValue(Object arg, String propertyName, Object userId) {
        if (arg == null)
            return;

        if (PropertyUtils.isWriteable(arg, propertyName)) { // setter가 존재하는 경우
            try {
                PropertyUtils.setProperty(arg, propertyName, userId);
                log.debug("set {} as {}", propertyName, userId);
            } catch (Exception e) {
                log.error("argument type:errmsg = {}:{}" + Object.class.getName(), e.getMessage());
            }
        }
    }

    private boolean isGetTypeMethod(String methodName) {
        for (String prefix : GET_METHOD_PREFIX_LIST) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
