package com.hisco.cmm.taglib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hisco.admin.comctgr.service.ComCtgrService;
import com.hisco.admin.comctgr.vo.ComCtgrVO;
import com.hisco.cmm.object.CamelMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CateListTag extends AbstractTagLib {
    private static final long serialVersionUID = 1L;

    private String defaultNo; // 초기화 기관번호
    private String id;
    private String name;
    private String cssClass;
    private String style;
    private String blankYn;
    private String nullValue = ""; // 빈값

    public CateListTag() {

        super();
    }

    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if (request == null)
            return super.doStartTag();

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        ComCtgrService comCtgrService = (ComCtgrService) wac.getBean("comCtgrService");

        ComCtgrVO ctgrVO = new ComCtgrVO();

        List<CamelMap> cateList = null;

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("firstIndex", 1);
            paramMap.put("recordCountPerPage", 100);
            cateList = (List<CamelMap>) comCtgrService.selectComctgrList(paramMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        StringBuffer html = new StringBuffer();
        html.append("<select id=\"");
        html.append(id);
        html.append("\" name=\"");
        html.append((name == null || name.equals("")) ? id : name);
        html.append("\"");
        html.append(" class=\"form-control select2");

        if (cssClass != null && !cssClass.equals("")) {
            html.append(" ");
            html.append(cssClass);
        }
        html.append("\"");
        if (style != null && !style.equals("")) {
            html.append(" style=\"");
            html.append(style);
            html.append("\"");
        }
        html.append(">");

        if (blankYn == null || blankYn.equals(""))
            blankYn = "Y";

        if (blankYn.equals("Y")) {
            html.append("<option value='" + nullValue + "'>분야 선택</option>");
        }

        if (cateList != null && !cateList.isEmpty()) {
            for (CamelMap vo : cateList) {
                html.append("<option  data-pctgcd=\"");
                html.append(vo.get("comPrnctgcd"));
                html.append("\"  data-tctgcd=\"");
                html.append(vo.get("comTopctgcd"));
                html.append("\" value=\"");
                html.append(vo.get("CtgCd"));
                html.append("\"");
                if (defaultNo != null && !defaultNo.equals("")) {
                    if (vo.get("CtgCd").equals(defaultNo))
                        html.append(" selected");
                }
                html.append(">");
                html.append(vo.get("comCtgnm"));
                html.append("</option>");
            }
        } else {
            html.append("<option value=''>등록된 분류 없습니다.</option>");
        }

        html.append("</select>");
        this.value = html.toString();

        return super.doEndTag();
    }

    public void setDefaultNo(String defaultNo) {
        this.defaultNo = defaultNo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setBlankYn(String blankYn) {
        this.blankYn = blankYn;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

}
