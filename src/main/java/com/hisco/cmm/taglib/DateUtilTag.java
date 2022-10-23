package com.hisco.cmm.taglib;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.hisco.cmm.util.DateUtil;

public class DateUtilTag extends AbstractTagLib {
    private static final long serialVersionUID = 1L;

    private String datestr; // yyyyMMddHHmm
    private String format;

    public DateUtilTag() {

        super();
    }

    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if (request == null)
            return super.doStartTag();

        Date dd = new Date();

        if (datestr == null || datestr.equals("")) {
            this.value = "";

        } else {
            if (datestr.length() == 8) {
                dd = DateUtil.setDate(Integer.parseInt(datestr.substring(0, 4)), Integer.parseInt(datestr.substring(4, 6)), Integer.parseInt(datestr.substring(6, 8)));
            } else if (datestr.length() == 12) {
                dd = DateUtil.setDate(Integer.parseInt(datestr.substring(0, 4)), Integer.parseInt(datestr.substring(4, 6)), Integer.parseInt(datestr.substring(6, 8)), Integer.parseInt(datestr.substring(8, 10)), Integer.parseInt(datestr.substring(10, 12)));
            } else if (datestr.length() == 14) {
                dd = DateUtil.setDate(Integer.parseInt(datestr.substring(0, 4)), Integer.parseInt(datestr.substring(4, 6)), Integer.parseInt(datestr.substring(6, 8)), Integer.parseInt(datestr.substring(8, 10)), Integer.parseInt(datestr.substring(10, 12)), Integer.parseInt(datestr.substring(12, 14)));

            } else if (datestr.length() == 4) {
                dd = DateUtil.setDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONDAY), Calendar.getInstance().get(Calendar.DATE), Integer.parseInt(datestr.substring(0, 2)), Integer.parseInt(datestr.substring(2, 4)));
            }

            this.value = DateUtil.printDatetime(dd, format);
        }

        return super.doEndTag();
    }

    public void setDatestr(String datestr) {
        this.datestr = datestr;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
