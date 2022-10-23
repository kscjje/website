package egovframework.com.cmm.util;

import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class to support to double submit preventer
 * 
 * @author Vincent Han
 * @since 2014.08.07
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일        수정자       수정내용
 *  -------       --------    ---------------------------
 *   2014.08.07	표준프레임워크센터	최초 생성
 *      </pre>
 */
@Slf4j
public class EgovDoubleSubmitHelper {

    public final static String SESSION_TOKEN_KEY = "egovframework.double.submit.preventer.session.key";

    public final static String PARAMETER_NAME = "egovframework.double.submit.preventer.parameter.name";

    public final static String DEFAULT_TOKEN_KEY = "DEFAULT";

    public static String getNewUUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static boolean checkAndSaveToken() {
        return checkAndSaveToken(DEFAULT_TOKEN_KEY);
    }

    public static boolean checkAndSaveToken(String tokenKey) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        // check session...
        if (session.getAttribute(EgovDoubleSubmitHelper.SESSION_TOKEN_KEY) == null) {
            // throw new RuntimeException("Double Submit Preventer TagLig isn't set. Check JSP.");
            try {
                throw new ServletException("Double Submit Preventer TagLig isn't set. Check JSP.");
            } catch (ServletException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        String parameter = request.getParameter(EgovDoubleSubmitHelper.PARAMETER_NAME);

        // check parameter
        if (parameter == null) {
            // throw new RuntimeException("Double Submit Preventer parameter isn't set. Check JSP.");

            try {
                throw new ServletException("Double Submit Preventer parameter isn't set. Check JSP.");
            } catch (ServletException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) session.getAttribute(EgovDoubleSubmitHelper.SESSION_TOKEN_KEY);

        if (parameter.equals(map.get(tokenKey))) {

            log.debug("[Double Submit] session token ({}) equals to parameter token.", tokenKey);

            map.put(tokenKey, getNewUUID());

            return true;
        }

        log.debug("[Double Submit] session token ({}) isn't equal to parameter token.", tokenKey);

        return false;
    }
}
