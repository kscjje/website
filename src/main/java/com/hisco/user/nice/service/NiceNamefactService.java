package com.hisco.user.nice.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.hisco.cmm.modules.pg.nice.NiceNamefactDto;
import com.hisco.cmm.modules.site.thissite.module_config.ModuleConfigVo;
import com.hisco.cmm.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("niceNamefactService")
public class NiceNamefactService {

    private final String SESSION_NAME = "SESSION-NAMEFACT-RESULT";
    private final String SESSION_TYPE_NAME = "SESSION-NAMEFACT-TYPE";
    private final String DEFAULT_KEY_NAME = "normal";

    public void setType(HttpServletRequest request, String type) {
        if (StringUtil.IsEmpty(type))
            request.getSession().setAttribute(SESSION_TYPE_NAME, DEFAULT_KEY_NAME);
        else
            request.getSession().setAttribute(SESSION_TYPE_NAME, type);
    }

    public String getType(HttpServletRequest request) {
        String mode = (String) request.getSession().getAttribute(SESSION_TYPE_NAME);
        if (StringUtil.IsEmpty(mode))
            return DEFAULT_KEY_NAME;
        else
            return mode;
    }

    /**
     * 본인인증 정보
     * 
     * @param request
     *            리퀘스트
     * @param type
     *            구분명
     * @return
     */
    @SuppressWarnings("unchecked")
    public NiceNamefactDto getData(HttpServletRequest request, String type) {

        Map<String, NiceNamefactDto> datas = (Map<String, NiceNamefactDto>) request.getSession().getAttribute(SESSION_NAME);
        if (datas != null && datas.size() > 0 && datas.containsKey(type)) {
            NiceNamefactDto data = datas.get(type);

            if (data != null) {
                log.debug("NiceNamefactDto data :: data.getName() " + data.getName());
            }

            if (data != null && !StringUtil.IsEmpty(data.getType()) && (!StringUtil.IsEmpty(data.getCrc_data_ci()) || !StringUtil.IsEmpty(data.getCrc_data_di())) && !StringUtil.IsEmpty(data.getName())) {
                return data;
            }
        }

        return null;
    }

    /**
     * 본인인증 정보(기본)
     * 
     * @param request
     *            리퀘스트
     * @return
     */
    public NiceNamefactDto getData(HttpServletRequest request) {
        return getData(request, DEFAULT_KEY_NAME);
    }

    /**
     * 본인인증 정보 확인
     * 
     * @param request
     *            리퀘스트
     * @param type
     *            구분명
     * @return
     */
    public boolean existsData(HttpServletRequest request, String type) {
        return getData(request, type) != null;
    }

    /**
     * 본인인증 정보 확인(기본)
     * 
     * @param request
     *            리퀘스트
     * @return
     */
    public boolean existsData(HttpServletRequest request) {
        return existsData(request, DEFAULT_KEY_NAME);
    }

    /**
     * 본인인증 정보 저장
     * 
     * @param request
     *            리퀘스트
     * @param type
     *            구분명
     * @param data
     *            본인인증 정보
     */
    @SuppressWarnings("unchecked")
    public void saveData(HttpServletRequest request, String type, NiceNamefactDto data) {
        Map<String, NiceNamefactDto> datas = (Map<String, NiceNamefactDto>) request.getSession().getAttribute(SESSION_NAME);
        if (datas == null)
            datas = new HashMap<String, NiceNamefactDto>();

        datas.put(type, data);

        request.getSession().setAttribute(SESSION_NAME, datas);
    }

    /**
     * 본인인증 정보 저장(기본)
     * 
     * @param request
     *            리퀘스트
     * @param data
     *            본인인증 정보
     */
    public void saveData(HttpServletRequest request, NiceNamefactDto data) {
        saveData(request, DEFAULT_KEY_NAME, data);
    }

    /**
     * 본인인증 정보 전체 제거
     * 
     * @param request
     */
    public void clear(HttpServletRequest request) {
        request.getSession().removeAttribute(SESSION_NAME);
    }

    /**
     * 본인인증 정보 제거
     * 
     * @param request
     *            리퀘스트
     * @param type
     *            구분명
     */
    @SuppressWarnings("unchecked")
    public void clear(HttpServletRequest request, String type) {
        Map<String, NiceNamefactDto> datas = (Map<String, NiceNamefactDto>) request.getSession().getAttribute(SESSION_NAME);
        if (datas == null)
            datas = new HashMap<String, NiceNamefactDto>();

        datas.remove(type);

        request.getSession().setAttribute(SESSION_NAME, datas);
    }

    /**
     * 모듈 설정 정보
     * 
     * @param module_config_srl
     *            사이트 고유번호
     * @return
     * @throws SQLException
     */
    public ModuleConfigVo CacheSelect(long module_config_srl) throws SQLException {

        /*
         * JYS
         * CheckCache();
         * List<ModuleConfigVo> list = this.CacheList();
         * if (list != null && list.size() > 0)
         * {
         * for (ModuleConfigVo data : list)
         * {
         * if (data.getModule_config_srl() == module_config_srl) return data;
         * }
         * }
         */

        return null;
    }
}
