package com.hisco.cmm.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionUtil {

    public static String getErrorLine(Exception e) {
        ByteArrayOutputStream out = null;
        PrintStream pinrtStream = null;
        StringBuffer buf = new StringBuffer();

        try {
            out = new ByteArrayOutputStream();
            pinrtStream = new PrintStream(out);
            e.printStackTrace(pinrtStream);
            String[] stacks = out.toString().split("[\n]");
            for (String str : stacks) {
                if (str.contains("invoke"))
                    break;
                buf.append(str);
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        } finally {
            try {
                if (pinrtStream != null)
                    pinrtStream.close();
                if (out != null)
                    out.close();
            } catch (Exception ex) {
            }
        }

        return buf.toString();
    }

    public static String getSQLErrorMessage(Exception e, String delemeter) {
        ByteArrayOutputStream out = null;
        PrintStream pinrtStream = null;
        String message = null;

        try {
            out = new ByteArrayOutputStream();
            pinrtStream = new PrintStream(out);
            e.printStackTrace(pinrtStream);
            String[] stacks = out.toString().split("[\n]");
            for (String str : stacks) {
                if (str.contains("invoke"))
                    break;
                if (str.contains(delemeter)) {
                    message = str.substring(str.indexOf(delemeter) + delemeter.length());
                    break;
                }
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        } finally {
            try {
                if (pinrtStream != null)
                    pinrtStream.close();
                if (out != null)
                    out.close();
            } catch (Exception ex) {
            }
        }

        return message;
    }
}