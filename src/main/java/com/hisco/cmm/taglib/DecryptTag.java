package com.hisco.cmm.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;

public class DecryptTag extends AbstractTagLib {
    private static final long serialVersionUID = 1L;

    private String encrypt; // 암호화 된 값

    public DecryptTag() {

        super();
    }

    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if (request == null)
            return super.doStartTag();

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        EgovEnvCryptoService cryptoService = (EgovEnvCryptoService) wac.getBean("egovEnvCryptoService");

        this.value = cryptoService.decryptNone(encrypt);

        return super.doEndTag();
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }
}
