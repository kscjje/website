package com.hisco.cmm.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

public class HpPrintTag extends AbstractTagLib {
    private static final long serialVersionUID = 1L;

    private String hp; // 연락처 번호

    public HpPrintTag() {

        super();
    }

    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if (request == null)
            return super.doStartTag();

        if (hp.length() == 11) {
            hp = hp.substring(0, 3) + "-" + hp.substring(3, 7) + "-" + hp.substring(7, 11);
        } else if (hp.length() == 10) {
            hp = hp.substring(0, 3) + "-" + hp.substring(3, 6) + "-" + hp.substring(6, 10);
        } else {
        }

        this.value = hp;

        return super.doEndTag();
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

}
