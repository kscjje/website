package egovframework.com.cmm;

import javax.annotation.Resource;

import com.hisco.admin.log.service.LogService;
import com.hisco.admin.log.vo.ErrorLogVO;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.ExceptionUtil;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : EgovComExcepHndlr.java
 * @Description : 공통서비스의 exception 처리 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2009. 3. 13. 이삼섭
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 13.
 * @version
 * @see
 */
@Slf4j
public class EgovComExcepHndlr implements ExceptionHandler {

    @Resource(name = "logService")
    private LogService logService;

    /*
     * @Resource(name = "otherSSLMailSender")
     * private SimpleSSLMail mailSender;
     */
    /**
     * 발생된 Exception을 처리한다.
     */
    public void occur(Exception ex, String packageName) {
        log.error(" EgovComExcepHndlr run...............> {}", ExceptionUtil.getErrorLine(ex));

        try {
            ErrorLogVO errorWebLog = new ErrorLogVO();

            errorWebLog.setComcd(Config.COM_CD);
            errorWebLog.setConnectUrl(packageName);
            errorWebLog.setConectId("");
            errorWebLog.setConectIp("");
            errorWebLog.setErrormsg(ex.toString());
            errorWebLog.setParamVal("");
            errorWebLog.setRefUrl("");

            logService.insertErrorLog(errorWebLog);
        } catch (Exception e) {
        }

        /*
         * try {
         * mailSender. send(ex, packageName);
         * log.debug(" sending a alert mail  is completed ");
         * } catch (Exception e) {
         * log.error(packageName, ex);
         * }
         */

        // log.error(packageName, ex);
    }
}
