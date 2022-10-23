package com.hisco.cmm.taglib;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.Config;

import egovframework.com.cmm.LoginVO;

public class OrgListTag extends AbstractTagLib {
    private static final long serialVersionUID = 1L;

    private String defaultNo; // 초기화 기관번호
    private String id;
    private String name;
    private String orgKind;
    private String cssClass;
    private String style;
    private String blankYn;
    private String nullValue = ""; // 빈값
    private boolean defaultSelect = false;

    public OrgListTag() {

        super();
    }

    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if (request == null)
            return super.doStartTag();

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        OrgInfoService orgInfoService = (OrgInfoService) wac.getBean("orgInfoService");

        // 로그인한 관리자의 세션에 저장된 기관 목록만 셀렉트 한다
        UserSession userSession = (UserSession) request.getSession().getAttribute(Config.USER_SESSION);

        
        
        if (userSession != null) {
            OrgInfoVO orgInfoVO = new OrgInfoVO();
            orgInfoVO.setComcd(Config.COM_CD);
            orgInfoVO.setMyOrgList(userSession.getMyOrgList());
            orgInfoVO.setOrgKind(orgKind);

            LoginVO user = userSession.getAdminInfo();

            /*슈퍼관리자가 아닐경우 해당 자치센터 설정*/
            if(user != null) {
            	System.out.println("user.getAuthorCode().toUpperCase().equals(\"ROLE_SUPER\")==" + user.getAuthorCode().toUpperCase());
            	if(user.getAuthorCode().toUpperCase().equals("ROLE_SUPER")) {
            		blankYn = "Y";
            	}
            	else {
            		blankYn = "N";
            	}
            }
            System.out.println("blankYn=="+blankYn);
            List<OrgInfoVO> orgList = (List<OrgInfoVO>) orgInfoService.selectOrgInfoList(orgInfoVO);

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
                html.append("<option value='" + nullValue + "'>기관 선택</option>");
            }

            if (userSession.getMyOrgList() != null && defaultSelect && (defaultNo == null || defaultNo.equals(""))) {
                if (userSession.getMyOrgList().size() > 0) {
                    defaultNo = userSession.getMyOrgList().get(0);
                }
            }

            if (orgList != null) {
                for (OrgInfoVO vo : orgList) {

                    html.append("<option  data-orgkind=\"");
                    html.append(vo.getOrgKindNm());
                    html.append("\" data-orgkind-cd=\"");
                    html.append(vo.getOrgKind());
                    html.append("\" value=\"");
                    html.append(vo.getOrgNo());
                    html.append("\"");
                    if (defaultNo != null && !defaultNo.equals("")) {
                        if (Integer.parseInt(defaultNo) == vo.getOrgNo())
                            html.append(" selected");
                    }
                    html.append(">");
                    html.append(vo.getOrgNm());
                    html.append("</option>");
                }
            }
            if (orgList == null || orgList.size() < 1) {
                html.append("<option value=''>등록된 기관이 없습니다.</option>");
            }

            html.append("</select>");
            this.value = html.toString();
        }

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

    public void setOrgKind(String orgKind) {
        this.orgKind = orgKind;
    }

    public boolean getDefaultSelect() {
        return defaultSelect;
    }

    public void setDefaultSelect(boolean defaultSelect) {
        this.defaultSelect = defaultSelect;
    }

}
