package egovframework.com.cmm.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import egovframework.com.cmm.service.EgovProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * EgovWebServletContextListener 클래스
 * <Notice>
 * 데이터베이스 설정을 spring.profiles.active 방식으로 처리
 * (공통컴포넌트 특성상 데이터베이스별 분리/개발,검증,운영서버로 분리 가능)
 * <Disclaimer>
 * N/A
 *
 * @author 장동한
 * @since 2016.06.23
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  -------      -------------  ----------------------
 *   2016.06.23  장동한           최초 생성
 *   2017.03.03     조성원 	시큐어코딩(ES)-오류 메시지를 통한 정보노출[CWE-209]
 *      </pre>
 */

@Slf4j
public class EgovWebServletContextListener implements ServletContextListener {

    public EgovWebServletContextListener() {
        setEgovProfileSetting();
    }

    public void contextInitialized(ServletContextEvent event) {
        if (System.getProperty("spring.profiles.active") == null) {
            setEgovProfileSetting();
        }
    }

    public void contextDestroyed(ServletContextEvent event) {

        if (System.getProperty("spring.profiles.active") != null) {

            try {
                // System.setProperty("spring.profiles.active", null);
                System.clearProperty("spring.profiles.active");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void setEgovProfileSetting() {

        try {

            if (System.getProperty("spring.profiles.myactive") == null) {
                log.info("not setting spring.profiles.myactive");
                System.setProperty("spring.profiles.myactive", "local");
            }

            log.debug("===========================Start EgovServletContextLoad START ===========");
            log.debug("Setting spring.profiles.myactive > " + System.getProperty("spring.profiles.myactive"));

            System.setProperty("spring.profiles.active", EgovProperties.getProperty("Globals.DbType") + "," + EgovProperties.getProperty("Globals.Auth") + "," + EgovProperties.getProperty("Globals.Mode"));

            log.debug("Setting spring.profiles.active > " + System.getProperty("spring.profiles.active"));
            log.debug("===========================END   EgovServletContextLoad END ===========");

        } catch (IllegalArgumentException e) {
            log.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : " + e.getMessage());
        } catch (Exception e) {
            log.error("[" + e.getClass() + "] search fail : " + e.getMessage());
        }
    }
}
