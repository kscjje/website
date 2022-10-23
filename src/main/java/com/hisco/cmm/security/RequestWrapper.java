package com.hisco.cmm.security;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public final class RequestWrapper extends HttpServletRequestWrapper {

    private static Pattern[] patterns = new Pattern[] { Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE), Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL), Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL), Pattern.compile("</script>", Pattern.CASE_INSENSITIVE), Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL), Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL), Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL), Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE), Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE), Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };

    public RequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    public String[] getParameterValues(String parameter) {

        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }

    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return cleanXSS(value);

    }

    private String cleanXSS(String value) {
        // //You'll need to remove the spaces from the html entities below
        // value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        // value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        // value = value.replaceAll("'", "& #39;");
        // value = value.replaceAll("eval\\((.*)\\)", "");
        // value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        // value = value.replaceAll("script", "");

        String tempVal = value;
        if (tempVal != null) {

            // NOTE: It's highly recommended to use the ESAPI library and
            // uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);
            // Avoid null characters
            tempVal = tempVal.replaceAll("\0", "");

            // Remove all sections that match a pattern
            for (Pattern scriptPattern : patterns) {
                if (scriptPattern.matcher(tempVal).find()) {
                    //// log.debug("stripXSS match: "+value);
                    tempVal = tempVal.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                    //// log.debug("result: "+value);
                }
            }
        }
        // log.debug("result: "+value);
        return tempVal;
    }
}
