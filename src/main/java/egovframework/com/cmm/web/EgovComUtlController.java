package egovframework.com.cmm.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

// import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name : EgovComUtlController.java
 * @Description : 공통유틸리티성 작업을 위한 Controller
 * @Modification Information
 * @
 *   @ 수정일 수정자 수정내용
 *   @ ---------- -------- ---------------------------
 *   2009.03.02 조재영 최초 생성
 *   2011.10.07 이기하 .action -> .do로 변경하면서 동일 매핑이 되어 삭제처리
 *   2015.11.12 김연호 한국인터넷진흥원 웹 취약점 개선
 *   2019.04.25 신용호 moveToPage() 화이트리스트 처리
 * @author 공통서비스 개발팀 조재영
 * @since 2009.03.02
 * @version 1.0
 * @see
 */
@Slf4j
@Controller
public class EgovComUtlController {

    // @Resource(name = "egovUserManageService")
    // private EgovUserManageService egovUserManageService;

    @Resource(name = "egovPageLinkWhitelist")
    protected List<String> egovWhitelist;

    /** EgovPropertyService */
    /*
     * @Resource(name = "propertiesService")
     * protected EgovPropertyService propertiesService;
     */

    /**
     * validato rule dynamic Javascript
     */
    @GetMapping({ "#{dynamicConfig.adminRoot}/validator", "#{dynamicConfig.managerRoot}/validator" })
    public String validate() {

        log.debug("call EgovComUtlController :: validate()");

        return "egovframework/com/cmm/validator";
    }

}