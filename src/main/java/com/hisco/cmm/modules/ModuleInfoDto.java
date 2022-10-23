package com.hisco.cmm.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class ModuleInfoDto extends DefaultObject {
    private String key; // 모듈키
    private int align; // 모듈 정렬 인덱스
    private String path; // 모듈경로
    private boolean required; // 필수 여부
    private boolean manager; // 관리툴 여부
    private boolean module_config; // 모듈 설정 가능 여부
    private boolean database; // 데이터베이스 테이블 생성 여부
    private String name; // 모듈명
    private String description; // 모듈 설명
    private String serviceName; // 모듈 서비스명
    private boolean download; // 모듈 다운로드 여부
    private boolean initialize; // 모듈 초기화 여부(service bean 체크)
    private boolean create; // 모듈 관련 데이터베이스 생성여부
    private float version; // 버전
    private float server_version; // 서버 버전
    private List<String> dependent_module; // 이전 필수 모듈
    private Map<String, String> process_action; // 모듈 액션명 정보

    private Map<String, Object> properties; // 설정 정보

    // private List<String> skin; // 스킨 목록
    private Map<String, String> skin; // 스킨 목록

    public ModuleInfoDto() {
    }

    public ModuleInfoDto(HttpServletRequest request, String key, String path) {
        this.key = key;
        this.path = path;

        this.align = Integer.MAX_VALUE;
        this.required = false;
        this.manager = false;
        this.module_config = false;
        this.database = false;
        this.name = "";
        this.description = "";
        this.serviceName = "";
        this.download = false;
        this.initialize = false;
        this.create = false;
        this.version = 0;
        this.server_version = 0;
        this.dependent_module = null;
        this.process_action = null;
        this.properties = null;

        // this.skin = new ArrayList<String>();
        this.skin = new TreeMap<String, String>();

        LoadInfo(request);
    }

    public void LoadInfo(HttpServletRequest request) {

        if (StringUtil.IsEmpty(this.path)) {
            this.name = "오류";
            this.description = "모듈 경로 없음.";
            this.serviceName = null;

            return;
        }

        File infoFile = new File(this.path + "/info.txt");
        if (infoFile != null && infoFile.exists() && infoFile.isFile()) {

            StringBuffer stringBuffer = new StringBuffer();

            FileInputStream fis = null;
            InputStreamReader isr = null;
            BufferedReader bufferedReader = null;

            try {

                fis = new FileInputStream(infoFile);
                isr = new InputStreamReader(fis, "UTF8");
                bufferedReader = new BufferedReader(isr);

                String tempString = "";
                /*
                 * while ((tempString = bufferedReader.readLine()) != null) {
                 * stringBuffer.append(tempString +"\n");
                 * }
                 */

                while (true) {

                    tempString = bufferedReader.readLine();
                    if (tempString == null) {
                        break;
                    }

                    stringBuffer.append(tempString + "\n");
                }

            } catch (IOException e) {
                // e.printStackTrace();

                this.name = "오류";
                this.description = "정보 파일 불러오기 실패.";
                this.serviceName = null;
                this.download = false;
            } finally {
                if (bufferedReader != null)
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                    }
                if (isr != null)
                    try {
                        isr.close();
                    } catch (IOException e) {
                    }
                if (fis != null)
                    try {
                        fis.close();
                    } catch (IOException e) {
                    }
            }

            if (stringBuffer.length() > 0) {

                ModuleInfoDto data = (ModuleInfoDto) JsonUtil.String2Object(stringBuffer.toString(), ModuleInfoDto.class);

                if (data != null) {

                    String key = this.key;
                    int align = this.align;
                    String path = this.path;
                    float server_version = this.server_version;

                    ObjectUtil.ObjectValueCopy(this, data);

                    this.key = key;
                    if (this.align <= 0)
                        this.align = align;
                    this.path = path;
                    this.download = true;
                    this.server_version = server_version;
                }
            }

            if (!this.database || StringUtil.IsEmpty(serviceName)) {
                this.initialize = true;
                this.create = true;
            } else if (!StringUtil.IsEmpty(serviceName)) {
                try {
                    this.initialize = CheckService(request);
                    this.create = CheckTable(request);
                } catch (Exception e) {
                    this.initialize = false;
                    this.create = false;
                }
            }

            // 스킨 정보 수집
            // if (this.skin == null) this.skin = new ArrayList<String>();
            if (this.skin == null)
                this.skin = new TreeMap<String, String>();

            String skin_path = FileUtil.GetRealPath("/WEB-INF/views/modules/" + this.key + "/skin");
            File skin_root = new File(skin_path);
            if (skin_root.exists() && skin_root.isDirectory()) {

                this.skin.clear();

                File[] files = skin_root.listFiles();
                if (files != null && files.length > 0) {
                    for (File skin : files) {
                        if (skin.exists() && skin.isDirectory() && !skin.getName().startsWith("_")) {
                            // this.skin.add(skin.getName());
                            this.skin.put(skin.getName(), loadSkinInfo(skin));
                        }
                    }
                }
            }
        } else {
            this.name = "오류";
            this.description = "정보 파일이 없습니다.";
            this.serviceName = null;
        }
    }

    // 스킨 정보 추출
    private String loadSkinInfo(File skinPath) {

        if (skinPath == null || !skinPath.exists() || !skinPath.isDirectory()) {
            return null;
        }

        File[] files = skinPath.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (StringUtil.EqualsNotCase("info.txt", file.getName())) {
                    try {
                        String info = FileUtil.LoadFileSource(file, "UTF-8");
                        if (!StringUtil.IsEmpty(info)) {
                            if (!StringUtil.IsEmpty(info.trim())) {
                                String[] info_split = StringUtil.StringSplit(info.trim(), "[\n\r]{1,}");

                                if (info_split != null && info_split.length > 0 && !StringUtil.IsEmpty(info_split[0].trim())) {
                                    StringBuffer ret = new StringBuffer();
                                    ret.append(info_split[0]).append(" [").append(skinPath.getName()).append("]");

                                    if (info_split.length > 1 && !StringUtil.IsEmpty(info_split[1].trim())) {
                                        for (int i = 1; i < info_split.length; i++) {
                                            if (!StringUtil.IsEmpty(info_split[i].trim()))
                                                ret.append(" ").append(info_split[i].trim());
                                        }
                                    }

                                    return ret.toString();
                                } else {
                                    return skinPath.getName();
                                }
                            }
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }
        // FileUtil.LoadFileSource(file, charset)

        return null;
    }

    // 서비스 체크
    private boolean CheckService(HttpServletRequest request) {
        boolean ret = false;
        ApplicationContext applicationContext = RequestContextUtils.getWebApplicationContext(request);

        if (applicationContext != null)
            ret = applicationContext.containsBean(serviceName);

        return ret;
    }

    // 테이블 체크
    private boolean CheckTable(HttpServletRequest request) {
        boolean ret = false;
        ApplicationContext applicationContext = RequestContextUtils.getWebApplicationContext(request);

        if (applicationContext != null && applicationContext.containsBean(serviceName)) {
            InterfaceService service = (InterfaceService) applicationContext.getBean(serviceName);
            if (service != null) {
                try {
                    ret = service.Exists();
                } catch (Exception e) {
                    // e.printStackTrace();

                    ret = false;
                }
            }
        }

        return ret;
    }

    // 테이블 생성 처리
    public boolean CreateTable(HttpServletRequest request) {
        boolean ret = false;
        ApplicationContext applicationContext = RequestContextUtils.getWebApplicationContext(request);

        if (applicationContext != null && applicationContext.containsBean(serviceName)) {
            InterfaceService service = (InterfaceService) applicationContext.getBean(serviceName);
            if (service != null) {
                try {
                    if (!service.Exists()) {
                        ret = service.Create();
                    }
                } catch (Exception e) {
                    // e.printStackTrace();

                    ret = false;
                }
            }
        }

        this.create = CheckTable(request);

        return ret;
    }

    // 테이블 삭제 처리
    public boolean DropTable(HttpServletRequest request) {
        boolean ret = false;
        ApplicationContext applicationContext = RequestContextUtils.getWebApplicationContext(request);

        if (applicationContext != null && applicationContext.containsBean(serviceName)) {
            InterfaceService service = (InterfaceService) applicationContext.getBean(serviceName);
            if (service != null) {
                try {
                    if (service.Exists()) {
                        ret = service.Drop();
                    }
                } catch (Exception e) {
                    // e.printStackTrace();

                    ret = false;
                }
            }
        }

        this.create = CheckTable(request);

        return ret;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean isModule_config() {
        return module_config;
    }

    public void setModule_config(boolean module_config) {
        this.module_config = module_config;
    }

    public boolean isDatabase() {
        return database;
    }

    public void setDatabase(boolean database) {
        this.database = database;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public boolean isInitialize() {
        return initialize;
    }

    public void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public float getServer_version() {
        return server_version;
    }

    public void setServer_version(float server_version) {
        this.server_version = server_version;
    }

    public List<String> getDependent_module() {
        return dependent_module;
    }

    public void setDependent_module(List<String> dependent_module) {
        this.dependent_module = dependent_module;
    }

    public Map<String, String> getProcess_action() {
        return process_action;
    }

    public void setProcess_action(Map<String, String> process_action) {
        // this.process_action = process_action;
        TreeMap<String, String> buf = new TreeMap<String, String>();
        buf.putAll(process_action);

        this.process_action = buf;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, String> getSkin() {
        return skin;
    }

    public void setSkin(Map<String, String> skin) {
        this.skin = skin;
    }
}
