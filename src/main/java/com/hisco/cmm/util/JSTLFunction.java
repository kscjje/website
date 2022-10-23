package com.hisco.cmm.util;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.vo.CodeVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JSTLFunction {

    /**
     * ApplicationContext 빈 가져오기
     * 
     * @param bean
     * @return
     */
    private static Object getBean(String bean) {
        return SpringApplicationContext.getBean(bean);
    }

    private static CodeVO selectCodeDetail(String groupcd, String cd) {
        CodeService codeService = (CodeService) getBean("codeService");
        CodeVO code = null;

        try {
            code = codeService.selectCodeDetail(Config.COM_CD, groupcd, cd);
        } catch (Exception e) {
            log.error(e.toString());
        }

        return code;
    }

    /**
     * 코드명을 반환합니다.
     * 
     * @param group
     * @param id
     * @return
     * @throws Exception
     */
    public static String getCodeName(String groupcd, String cd) {
        CodeVO code = selectCodeDetail(groupcd, cd);

        if (code == null)
            return cd;

        return code.getCdNm();
    }

    public static String getNvlCodeName(String groupcd, String cd, String defaultStr) {

        if (StringUtils.isBlank(groupcd) || StringUtils.isBlank(cd)) {
            return defaultStr;
        }

        CodeVO code = selectCodeDetail(groupcd, cd);
        String returnValue = code.getCdNm();

        // codeValue 와 returnValue 가 같다는 의미는
        // code name이 null 이란 의미이다
        if (StringUtils.isBlank(returnValue) || cd.equals(returnValue))
            return defaultStr;

        return returnValue;
    }

    /**
     * 코드목록을 반환합니다.
     * 
     * @param code_group
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<CodeVO> getCodeList(String groupcd) {
        CodeService codeService = (CodeService) getBean("codeService");
        List<CodeVO> list = Collections.EMPTY_LIST;
        try {
            list = codeService.selectCodeList(Config.COM_CD, groupcd);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return list;
    }

    /**
     * 코드그룹명을 받아 해당 코드리스트를 select box 안의 option 으로 변환합니다.
     * 
     * @param code_group
     *            코드그룹명
     * @param defaultText
     *            기본텍스트 (EMPTY: 디폴트 없음)
     * @param selectValue
     *            선택 되어진 텍스트 ( 만약 선택 값이 존재한다면)
     * @param type
     *            = 1:NM(Default) , 2:ID.NM ,3:ETC
     * @return
     */
    public static String makeOptions(String groupcd, String defaultText, String selectValue, String type) {
        List<CodeVO> list = getCodeList(groupcd);

        StringBuffer data = new StringBuffer();

        if (!"EMPTY".equals(defaultText)) {
            data.append("<option value=''>" + defaultText + "</option>");
        }

        if (list == null)
            return data.toString();

        boolean isALL = false;
        if (type != null && type.indexOf("ALL_") == 0) {
            isALL = true;
            type = type.substring(4);
        }

        for (CodeVO ci : list) {
            if (!isALL && "N".equals(ci.getUseYn()))
                continue;

            String val = ci.getCd();
            String selected = "";
            if (!StringUtils.isBlank(ci.getCd()) && ci.getCd().equals(selectValue)) {
                selected = "selected";
            } else if ("DEFAULT".equals(selectValue)) {
                selected = "selected";
            }

            // logger.debug("dbCd:selVal:optionVal = {}:{}:{}", ci.getCd(), selectValue, val);

            if (type.equals("ID.NM"))
                data.append("<option value='" + val + "' " + selected + ">" + ci.getCd() + "." + ci.getCdNm() + "</option>");
            else
                data.append("<option value='" + val + "' " + selected + ">" + ci.getCdNm() + "</option>");
        }

        return data.toString();

    }

    public static String makeMultipleOptions(String groupcd, String selectValue, String type) {

        List<CodeVO> list = getCodeList(groupcd);

        StringBuffer data = new StringBuffer();

        data.append("<option data-divider='true'></option>");

        if (list == null)
            return data.toString();

        boolean isALL = false;
        if (type != null && type.indexOf("ALL_") == 0) {
            isALL = true;
            type = type.substring(4);
        }

        for (CodeVO ci : list) {

            if (!isALL && "N".equals(ci.getUseYn()))
                continue;

            String val = ci.getCd();
            if (!StringUtils.isBlank(ci.getCd()) && ci.getCd().equals(selectValue)) {
                val += "' selected='selected";
            } else if ("DEFAULT".equals(selectValue)) {
                val += "' selected='selected";
            }

            if (type.equals("ID.NM"))
                data.append("<option value='" + val + "'>" + ci.getCd() + "." + ci.getCdNm() + "</option>");
            else
                data.append("<option value='" + val + "'>" + ci.getCdNm() + "</option>");
        }

        return data.toString();

    }

    /**
     * 코드그룹과 코드이름으로 옵션을 만듭니다.
     * 
     * @param code_group
     * @param defaultText
     * @param selectName
     * @param type
     * @return
     */
    public static String makeOptionsName(String groupcd, String defaultText, String selectName, String type) {

        List<CodeVO> list = getCodeList(groupcd);

        StringBuffer data = new StringBuffer();

        data.append("<option value=''>" + defaultText + "</option>");
        /* data.append("<option data-divider='true'></option>"); */

        if (list == null)
            return data.toString();

        boolean isALL = false;
        if (type != null && type.indexOf("ALL_") == 0) {
            isALL = true;
            type = type.substring(4);
        }

        for (CodeVO ci : list) {

            if (!isALL && "N".equals(ci.getUseYn()))
                continue;

            String val = ci.getCdNm();
            if (!StringUtils.isBlank(ci.getCdNm()) && ci.getCdNm().equals(selectName)) {
                val += "' selected='selected";
            } else if ("DEFAULT".equals(selectName)) {
                val += "' selected='selected";
            }

            if (type.equals("ID.NM"))
                data.append("<option value='" + val + "'>" + ci.getCd() + "." + ci.getCdNm() + "</option>");
            else
                data.append("<option value='" + val + "'>" + ci.getCdNm() + "</option>");
        }

        return data.toString();

    }

    // Funciton Overloading
    public static String makeOptions(String groupcd, String defaultText, String selectValue) {
        return makeOptions(groupcd, defaultText, selectValue, "NM");
    }

    public static String makeMultipleOptions(String groupcd, String selectValue) {
        return makeMultipleOptions(groupcd, selectValue, "NM");
    }

    public static String makeYearOptions(String defaultText, Integer loopCount, String selectYear) {
        StringBuffer data = new StringBuffer();

        if (StringUtils.isNotBlank(defaultText)) {
            data.append("<option value=''>" + defaultText + "</option>");
        }

        int curYear = Integer.parseInt(DateUtil.getTodate("yyyy"));
        int selYear = curYear;

        if (StringUtils.isNotBlank(selectYear)) {
            selYear = Integer.parseInt(selectYear);
        }

        for (int i = -1; i < (loopCount - 1); i++) {
            int loopYear = curYear - i;
            data.append(String.format("<option value='%s' %s>%s</option>", loopYear, loopYear == selYear
                    ? "selected='selected'" : "", loopYear));
        }

        /*
         * int loopYear = curYear + 1;
         * data.append(String.format("<option value='%s' %s>%s</option>", loopYear, loopYear == selYear
         * ? "selected='selected'" : "", loopYear));
         */
        return data.toString();
    }

    public static String makeMonthOptions(String defaultText, String selectMonth) {
        StringBuffer data = new StringBuffer();

        if (StringUtils.isNotBlank(defaultText)) {
            data.append("<option value=''>" + defaultText + "</option>");
        }

        String curMonth = DateUtil.getTodate("MM");
        if (StringUtils.isBlank(selectMonth)) {
            selectMonth = curMonth;
        }

        for (int i = 1; i < 13; i++) {
            String month = i < 10 ? "0" + i : "" + i;
            data.append(String.format("<option value='%s' %s>%s</option>", month, month.equals(selectMonth)
                    ? "selected='selected'" : "", month));
        }

        return data.toString();
    }

}
