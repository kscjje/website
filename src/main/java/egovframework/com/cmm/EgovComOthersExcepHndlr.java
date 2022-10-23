package egovframework.com.cmm;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EgovComOthersExcepHndlr implements ExceptionHandler {

    public void occur(Exception exception, String packageName) {
        log.error(" EgovComOthersExcepHndlr run...............");
        log.error(packageName, exception);
    }
}
