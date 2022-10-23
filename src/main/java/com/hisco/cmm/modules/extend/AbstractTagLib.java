package com.hisco.cmm.modules.extend;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.hisco.cmm.modules.StringUtil;

public abstract class AbstractTagLib extends TagSupport implements BodyTag {

    private static final long serialVersionUID = 1L;

    protected String var;
    protected Object value;
    protected Object target;
    protected String property;

    protected BodyContent bodyContent;

    public AbstractTagLib() {
        super();
        init();
    }

    private void init() {
        var = null;
        value = null;
        target = null;
        property = null;

        bodyContent = null;
    }

    public void release() {
        init();
    }

    public String getBodyContentString() {
        if (this.bodyContent == null)
            return null;
        else if (this.bodyContent.getString() == null)
            return null;
        else
            return bodyContent.getString().trim();
    }

    @Override
    public void doInitBody() throws JspException {
    }

    @Override
    public int doAfterBody() throws JspException {
        return super.doAfterBody();
    }

    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int doEndTag() throws JspException {
        if (!StringUtil.IsEmpty(var)) {
            // if (!StringUtil.IsEmpty(value))
            if (value != null) {
                // pageContext.setAttribute(var, value);
                pageContext.getRequest().setAttribute(var, value);
            } else {
                // pageContext.removeAttribute(var);
                pageContext.getRequest().removeAttribute(var);
            }
        } else if (!StringUtil.IsEmpty(target)) {
            try {
                if (target instanceof Map) {
                    if (value == null)
                        ((Map) target).remove(property);
                    else
                        ((Map) target).put(property, value);
                }
            } catch (Exception e) {
                throw new JspException(e);
            }
        } else {
            if (!StringUtil.IsEmpty(value))
                this.PrintHtml((String) value);
        }

        return EVAL_PAGE;
    }

    /**
     * 빈 가지고 오기
     * 
     * @param name
     *            빈 이름
     * @return Object
     */
    @SuppressWarnings("unchecked")
    protected <T> T getBean(String name) {
        ApplicationContext applicationContext1 = RequestContextUtils.getWebApplicationContext(pageContext.getRequest(), pageContext.getServletContext());
        ApplicationContext applicationContext2 = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        WebApplicationContext applicationContext3 = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext(), FrameworkServlet.SERVLET_CONTEXT_PREFIX.concat("appServlet"));

        if (applicationContext1.containsBean(name))
            return (T) applicationContext1.getBean(name);
        else if (applicationContext2.containsBean(name))
            return (T) applicationContext2.getBean(name);
        else if (applicationContext3.containsBean(name))
            return (T) applicationContext3.getBean(name);
        else
            return null;
    }

    /**
     * HTML 출력 처리
     * 
     * @param html
     * @throws JspException
     * @throws IOException
     */
    protected void PrintHtml(String html) throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.print(html);
            if (out.isAutoFlush())
                out.flush();
        } catch (IOException e) {
            throw new JspException("IO Exception is catched");
        }
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setBodyContent(BodyContent bodyContent) {
        this.bodyContent = bodyContent;
    }
}
