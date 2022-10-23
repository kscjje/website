package com.hisco.cmm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Class Name : EMailUtil.java
 * @Description : 메일 전송 관련 유틸리티
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- -------- ---------------------------
 *               2020.09.06 전영석 최초 작성
 * @author 전영석
 * @since 2020.09.06
 * @version 1.0
 * @see
 */

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.hisco.cmm.object.EmailTemplateFactory;

import egovframework.com.cmm.service.EgovProperties;

@Component
public class EMailUtil {

    //// private Log logger = LogFactory.getLog(getClass());

    public EMailUtil() {
    }

    public HashMap<String, String> sendToEmail(Map<String, Object> paramMap, List<MultipartFile> paramMultFile)
            throws Exception {

        //// log.debug("paramMap = " + paramMap);

        HashMap<String, String> reMap = new HashMap<String, String>();

        try {

            String strNsmEMailHost = EgovProperties.getProperty("web.email.host");
            String strNsmEMailPort = EgovProperties.getProperty("web.email.port");
            String strNsmEMailUserId = EgovProperties.getProperty("web.email.userid");
            String strNsmEMailUserPw = EgovProperties.getProperty("web.email.userpw");
            String strNsmUserName = "노원수학문화관"; // 보내는사람 이름

            // log.debug("strNsmEMailHost = " + strNsmEMailHost);
            // log.debug("strNsmEMailPort = " + strNsmEMailPort);
            // log.debug("strNsmEMailUserId = " + strNsmEMailUserId);
            // log.debug("strNsmEMailUserPw = " + strNsmEMailUserPw);

            String gStrMailAuth = "true";
            String gStrMailSSLEnable = "true";
            String gStrMailSSLTrust = strNsmEMailHost;

            // log.debug("메일 전송 ------------------------------------------------------------------시작");

            String strRecvMailId = String.valueOf(paramMap.get("MAIL_ID")); // 받는 사람 이메일
            String strRecvMailTle = String.valueOf(paramMap.get("MAIL_TITLE")); // 이메일 제목
            String sbMailBody = this.getEmailBody(paramMap); // 내용

            // log.debug("===========메일내용==========");
            // log.debug(sbMailBody);

            // log.debug(sbMailBody.toString());

            // ---------------------------------------------------------------E-Mail 실제 전송 시작
            Properties props = new Properties();

            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", strNsmEMailHost);
            props.put("mail.smtp.port", strNsmEMailPort);
            props.put("mail.smtp.auth", gStrMailAuth);

            props.put("mail.smtp.quitwait", "false");
            props.put("mail.smtp.socketFactory.port", strNsmEMailPort);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "true");

            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(strNsmEMailUserId, strNsmEMailUserPw);
                }
            });

            session.setDebug(false);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(strNsmEMailUserId, MimeUtility.encodeText(strNsmUserName, "UTF-8", "B")));

            // message.RecipientType.TO 수신자
            // message.RecipientType.CC 참조
            // message.RecipientType.BCC 숨은 참조

            // message.setContent(sbMailBody.toString(), "text/html; charset=utf-8");
            // message.setRecipient(Message.RecipientType.TO, new InternetAddress(strRecvMailId));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(strRecvMailId, false));
            message.setSubject(MimeUtility.encodeText(strRecvMailTle, "UTF-8", "B"));
            message.setSentDate(new Date());
            // mimeMessage.setText(strMailBody);

            Multipart mParts = new MimeMultipart();
            MimeBodyPart mTextPart = new MimeBodyPart();

            mTextPart.setText(sbMailBody, "UTF-8", "html");
            mParts.addBodyPart(mTextPart);

            // ----------------------------------------------------------------------------------------첨부 파일 시작

            if (paramMultFile != null && paramMultFile.size() >= 1) {
                // log.debug("첨부 파일-----------------------------------------S");
                for (MultipartFile mf : paramMultFile) {

                    File file = fn_MultipartToFile(mf);

                    if (file != null) {

                        String strFileName = file.getName();

                        // log.debug("strFileName = " + strFileName);

                        MimeBodyPart mFilePart = new MimeBodyPart();
                        mFilePart.setFileName(MimeUtility.encodeText(strFileName, "UTF-8", "B"));

                        FileDataSource fds = new FileDataSource(strFileName);
                        DataHandler dataHandler = new DataHandler(fds);
                        mFilePart.setDataHandler(dataHandler);

                        Path path = Paths.get(file.getCanonicalPath());
                        String strMimeType = Files.probeContentType(path);
                        mFilePart.setHeader("Content-Type", strMimeType);

                        mFilePart.setDescription(strFileName.split("\\.")[0], "UTF-8");

                        mParts.addBodyPart(mFilePart);
                    }

                }
                // log.debug("첨부 파일-----------------------------------------E");

            }
            // ------------------------------------------------------------------------------------------첨부 파일 끝

            message.setContent(mParts);

            MailcapCommandMap mailcapCmdMap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mailcapCmdMap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mailcapCmdMap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mailcapCmdMap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mailcapCmdMap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mailcapCmdMap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
            CommandMap.setDefaultCommandMap(mailcapCmdMap);

            Transport.send(message);

            // log.debug("Transport.send....=> " + strRecvMailId);
            // ---------------------------------------------------------------E-Mail 실제 전송 끝

            // log.debug("메일 전송 ------------------------------------------------------------------끝");

            // log.debug("");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reMap;

    }

    private String getEmailBody(Map<String, Object> parmMap) throws Exception {

        // log.debug("=====================getEmailBody");
        String strMailCONTENTS = String.valueOf(parmMap.get("MAIL_CONTENTS"));

        // log.debug("=====================TEMPLATE_ID:" + parmMap.get("TEMPLATE_ID"));

        if (parmMap.containsKey("TEMPLATE_ID") && !"".equals((String) parmMap.get("TEMPLATE_ID"))) {
            EmailTemplateFactory emailTemplateFactory = EmailTemplateFactory.getInstance();
            strMailCONTENTS = emailTemplateFactory.read((String) parmMap.get("TEMPLATE_ID"));

            Set<String> keys = parmMap.keySet();
            for (String key : keys) {
                Object value = parmMap.get(key);
                strMailCONTENTS = strMailCONTENTS.replaceAll("%" + key.toUpperCase() + "%", String.valueOf(value));
            }

            return strMailCONTENTS;
        } else {

            return strMailCONTENTS;
        }
    }

    protected boolean fn_FileSizeCheck(String strFilename) {

        if (new File(strFilename).length() > (1024 * 1024 * 2.5)) {
            return false;
        } else {
            return true;
        }

    }

    protected File fn_MultipartToFile(MultipartFile mFile) throws IOException {

        if (mFile.getOriginalFilename().isEmpty()) {
            return null;
        } else {
            File file = new File(mFile.getOriginalFilename());
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(mFile.getBytes());
            fos.close();

            return file;
        }
    }

}
