package com.hisco.cmm.modules;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HiscoWebTool {

    /**
     * 실제 셋업 경로
     */
    public static final String _SETUP_ROOT = "_setup";

    // 모듈 정보 초기화 여부
    public static boolean MODULES_INFO_INIT = false;

    // 모듈 정보
    public static final ModuleInfo MODULES_INFO = ModuleInfo.getInstance();

    // 모듈 클래스 경로
    // public static final String _MODULE_CLASS_ROOT =
    // FileUtil.GetRealPath("/WEB-INF/classes/com/knsoft/kntool/MODULES");
    public static final String _MODULE_CLASS_ROOT = FileUtil.GetRealPath("/WEB-INF/modules");

    /**
     * 초기 셋업 루트 URL 경로
     */
    public static String SETUP_ROOT = "setup";

    // 실제 셋업 경로
    public static final String _ADMIN_ROOT = "_mathadm";

    /**
     * 관리자 루트 경로
     */
    public static String ADMIN_ROOT = "mathadm";

    /**
     * 모듈 정보 불러오기
     * 
     * @param request
     */
    public static void LoadModuleInfo(HttpServletRequest request) {

        if (MODULES_INFO_INIT)
            return;

        MODULES_INFO.clear();

        File moduleRoot = new File(HiscoWebTool._MODULE_CLASS_ROOT);

        if (!moduleRoot.exists()) {
            log.debug("=======================================================================================>");
            log.debug("_MODULE_CLASS_ROOT = " + _MODULE_CLASS_ROOT);
            log.debug("=======================================================================================> 파일이 없습니다.");
        } else {
            log.debug("=======================================================================================>");
            log.debug("_MODULE_CLASS_ROOT = " + _MODULE_CLASS_ROOT);
            log.debug("=======================================================================================> 파일이 존재합니다.");
        }

        if (moduleRoot != null && moduleRoot.exists() && moduleRoot.isDirectory()) {
            File[] dirs = moduleRoot.listFiles();
            if (dirs != null && dirs.length > 0) {
                for (File dir : dirs) {

                    if (dir != null && dir.exists() && dir.isDirectory()) {
                        MODULES_INFO.put(dir.getName(), new ModuleInfoDto(request, dir.getName(), dir.getAbsolutePath()));
                    }
                }
            }
        }

        // 모듈 인덱스로 정렬
        MODULES_INFO.sort();

        MODULES_INFO_INIT = true;

        log.debug("MODULES_INFO ************************************************************************* ");
        log.debug(MODULES_INFO.toString());

    }

    /**
     * 모듈 정보 불러오기
     * 
     * @param moduleName
     * @return
     */
    public static ModuleInfoDto LoadModuleInfo(String moduleKey) {

        log.debug("LoadModuleInfo -> moduleKey = " + moduleKey);
        log.debug("MODULES_INFO = " + MODULES_INFO);

        if (MODULES_INFO != null && MODULES_INFO.size() > 0) {
            if (MODULES_INFO.containsKey(moduleKey)) {

                log.debug("LoadModuleInfo -> moduleKey = " + moduleKey + " 찾았음!!!!!!!!");

                return MODULES_INFO.get(moduleKey);
            }
        }

        return null;
    }

    /**
     * 모듈 전체 정보 불러오기
     * 
     * @return
     */
    public static List<ModuleInfoDto> LoadModuleInfo() {
        return MODULES_INFO == null ? null : MODULES_INFO.getPool();
    }

}