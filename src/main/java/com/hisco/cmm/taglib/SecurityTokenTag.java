package com.hisco.cmm.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.hisco.cmm.taglib.AbstractTagLib;
import com.hisco.cmm.security.SecurityToken;
import com.hisco.cmm.util.StringUtil;

public class SecurityTokenTag extends AbstractTagLib {
    private static final long serialVersionUID = 1L;

    private String name; // 토큰명

    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if (request == null)
            return super.doStartTag();

        StringBuffer html = new StringBuffer();

        html.append("<input type=\"hidden\" name=\"").append(SecurityToken.TOKEN_NAME).append("\" value=\"").append(StringUtil.HTMLEncode(SecurityToken.Make(request, name))).append("\"/>\n");

        this.PrintHtml(html.toString());

        return super.doEndTag();
    }

    public void setName(String name) {
        this.name = name;
    }
}
