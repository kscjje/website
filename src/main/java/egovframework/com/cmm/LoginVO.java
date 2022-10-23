package egovframework.com.cmm;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.StringUtils;

import com.hisco.cmm.util.DateUtil;

/**
 * @Class Name : LoginVO.java
 * @Description : Login VO class
 * @Modification Information
 * @
 *   @ 수정일 수정자 수정내용
 *   @ ------- -------- ---------------------------
 *   @ 2009.03.03 박지욱 최초 생성
 *   @ 2020.08.01 진수진 과학관에 맞춰 수정
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.03
 * @version 1.0
 * @see
 */
public class LoginVO extends ComDefaultVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8274004534207618049L;

    /** 아이디 */
    private String id;
    /** 이름 */
    private String name;
    /** 핸드폰 */
    private String ihidNum;
    /** 이메일주소 */
    private String email;
    /** 비밀번호 */
    private String password;
    /** 비밀번호 힌트 */
    private String passwordHint;
    /** 비밀번호 정답 */
    private String passwordCnsr;
    /** 사용자구분 */
    private String userSe;
    /** 사용자구분코드 슈퍼관리자, 일반/중간관리자 */
    private String userSeCode; 
    /** 조직(부서)ID */
    private String orgnztId;
    /** 조직(부서)명 */
    private String orgnztNm;
    /** 고유아이디 */
    private String uniqId;
    /** 로그인 후 이동할 페이지 */
    private String url;
    /** 사용자 IP정보 */
    private String ip;
    /** GPKI인증 DN */
    private String dn;
    /** 권한명 */
    private String authorNm;
    /** 권한코드*/
    private String authorCode;
    /** SNS ID */
    private String snsId;

    /** SNS EMAIL */
    private String snsEmail;

    /** SNS 구분 */
    private String snsKind;

    /** DB 암호화 코드 */
    private String dbEncKey;

    /** 연간회원여부 */
    private String yearYn;

    /** 연간회원종료일 */
    private String anlmbEdate;

    /** 특별회원여부 */
    private String specialYn;

    /** 본인인증 키 */
    private String hpcertno;

    /** 회원 상태 */
    private String status;

    /** 탈퇴 또는 휴면 전환일 */
    private Date outDate;

    /** 회원구분 */
    private String memGbn;
    /** 비밀모드 */
    private String secretMode;

    /** 성별 */
    private String gender;

    /** 권한그룹 코드 */
    private int groupId;

    /** 생년월일 **/
    private String birthDate;

    /** 주소 (우편번호) **/
    private String homeZipNo;

    /** 주소1 **/
    private String homeAddr1;

    /** 주소2 **/
    private String homeAddr2;

    /** 컨텐츠 바로 수정 가능 여부 **/
    private String contentsYn;

    private String piAuthkey;

    private String piAuthtype;

    private String piPAuthtype;

    private String userType;
    
    private String  smsYn;
    
    private String  salt;
    
    /**약관 동의여부**/
    private String  agreeYn;
    /**약관 동의 최근날짜**/
    private String  agreeLastDate;
    
    private Date regdate;

    public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date rEGDATE) {
		regdate = rEGDATE;
	}

	public String getAgreeYn() {
		return agreeYn;
	}

	public void setAgreeYn(String agreeYn) {
		this.agreeYn = agreeYn;
	}

	public String getAgreeLastDate() {
		return agreeLastDate;
	}

	public void setAgreeLastDate(String agreeLastDate) {
		this.agreeLastDate = agreeLastDate;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSmsYn() {
		return smsYn;
	}

	public void setSmsYn(String smsYn) {
		this.smsYn = smsYn;
	}

	public String getPiPAuthtype() {
        return piPAuthtype;
    }

    public void setPiPAuthtype(String piPAuthtype) {
        this.piPAuthtype = piPAuthtype;
    }

    public String getPiAuthtype() {
        return piAuthtype;
    }

    public void setPiAuthtype(String piAuthtype) {
        this.piAuthtype = piAuthtype;
    }

    public String getPiAuthkey() {
        return piAuthkey;
    }

    public void setPiAuthkey(String piAuthkey) {
        this.piAuthkey = piAuthkey;
    }

    public String getContentsYn() {
        return contentsYn;
    }

    public void setContentsYn(String contentsYn) {
        this.contentsYn = contentsYn;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        if (StringUtils.isBlank(birthDate) || birthDate.length() <= 4)
            return 0;
        int birthYear = Integer.parseInt(birthDate.substring(0, 4));
        int nowYear = Integer.parseInt(DateUtil.getTodate("yyyy"));
        return nowYear - birthYear + 1;
    }

    public int getAge(String ageGbn) {
        if ("2001".equals(ageGbn)) {
            // 만 나이 계산
            if (StringUtils.isBlank(birthDate) || birthDate.length() <= 4)
                return 0;

            birthDate = birthDate.replaceAll("-", "");

            int birthYear = Integer.parseInt(birthDate.substring(0, 4));
            int nowYear = Integer.parseInt(DateUtil.getTodate("yyyy"));
            return nowYear - birthYear - ((Integer.parseInt(DateUtil.getTodate("MMdd")) - Integer.parseInt(birthDate.substring(4, 8))) > 0
                    ? 0 : 1); // 생일이 안지났으면 1살을 더 뺀다
        } else {
            return getAge();
        }
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getHomeZipNo() {
        return homeZipNo;
    }

    public void setHomeZipNo(String homeZipNo) {
        this.homeZipNo = homeZipNo;
    }

    public String getHomeAddr1() {
        return homeAddr1;
    }

    public void setHomeAddr1(String homeAddr1) {
        this.homeAddr1 = homeAddr1;
    }

    public String getHomeAddr2() {
        return homeAddr2;
    }

    public void setHomeAddr2(String homeAddr2) {
        this.homeAddr2 = homeAddr2;
    }

    public String getMemGbn() {
        return memGbn;
    }

    public void setMemGbn(String memGbn) {
        this.memGbn = memGbn;
    }

    /**
     * id attribute 를 리턴한다.
     *
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * id attribute 값을 설정한다.
     *
     * @param id
     *            String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * name attribute 를 리턴한다.
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * name attribute 값을 설정한다.
     *
     * @param name
     *            String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ihidNum attribute 를 리턴한다.
     *
     * @return String
     */
    public String getIhidNum() {
        return ihidNum;
    }

    /**
     * ihidNum attribute 값을 설정한다.
     *
     * @param ihidNum
     *            String
     */
    public void setIhidNum(String ihidNum) {
        this.ihidNum = ihidNum;
    }

    /**
     * email attribute 를 리턴한다.
     *
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * email attribute 값을 설정한다.
     *
     * @param email
     *            String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * password attribute 를 리턴한다.
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * password attribute 값을 설정한다.
     *
     * @param password
     *            String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * passwordHint attribute 를 리턴한다.
     *
     * @return String
     */
    public String getPasswordHint() {
        return passwordHint;
    }

    /**
     * passwordHint attribute 값을 설정한다.
     *
     * @param passwordHint
     *            String
     */
    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    /**
     * passwordCnsr attribute 를 리턴한다.
     *
     * @return String
     */
    public String getPasswordCnsr() {
        return passwordCnsr;
    }

    /**
     * passwordCnsr attribute 값을 설정한다.
     *
     * @param passwordCnsr
     *            String
     */
    public void setPasswordCnsr(String passwordCnsr) {
        this.passwordCnsr = passwordCnsr;
    }

    /**
     * userSe attribute 를 리턴한다.
     *
     * @return String
     */
    public String getUserSe() {
        return userSe;
    }

    /**
     * userSe attribute 값을 설정한다.
     *
     * @param userSe
     *            String
     */
    public void setUserSe(String userSe) {
        this.userSe = userSe;
    }

    /**
     * orgnztId attribute 를 리턴한다.
     *
     * @return String
     */
    public String getOrgnztId() {
        return orgnztId;
    }

    /**
     * orgnztId attribute 값을 설정한다.
     *
     * @param orgnztId
     *            String
     */
    public void setOrgnztId(String orgnztId) {
        this.orgnztId = orgnztId;
    }

    /**
     * uniqId attribute 를 리턴한다.
     *
     * @return String
     */
    public String getUniqId() {
        return uniqId;
    }

    /**
     * uniqId attribute 값을 설정한다.
     *
     * @param uniqId
     *            String
     */
    public void setUniqId(String uniqId) {
        this.uniqId = uniqId;
    }

    /**
     * url attribute 를 리턴한다.
     *
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * url attribute 값을 설정한다.
     *
     * @param url
     *            String
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * ip attribute 를 리턴한다.
     *
     * @return String
     */
    public String getIp() {
        return ip;
    }

    /**
     * ip attribute 값을 설정한다.
     *
     * @param ip
     *            String
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * dn attribute 를 리턴한다.
     *
     * @return String
     */
    public String getDn() {
        return dn;
    }

    /**
     * dn attribute 값을 설정한다.
     *
     * @param dn
     *            String
     */
    public void setDn(String dn) {
        this.dn = dn;
    }

    /**
     * @return the orgnztNm
     */
    public String getOrgnztNm() {
        return orgnztNm;
    }

    /**
     * @param orgnztNm
     *            the orgnztNm to set
     */
    public void setOrgnztNm(String orgnztNm) {
        this.orgnztNm = orgnztNm;
    }

    /**
     * @return the authorNm
     */
    public String getAuthorNm() {
        return authorNm;
    }

    /**
     * @param authorNm
     *            the authorNm to set
     */
    public void setAuthorNm(String authorNm) {
        this.authorNm = authorNm;
    }

    public String getUserSeCode() {
		return userSeCode;
	}

	public void setUserSeCode(String userSeCode) {
		this.userSeCode = userSeCode;
	}

	public String getAuthorCode() {
		return authorCode;
	}

	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}

	public String getSnsId() {
        return snsId;
    }

    public void setSnsId(String snsId) {
        this.snsId = snsId;
    }

    public String getSnsKind() {
        return snsKind;
    }

    public void setSnsKind(String snsKind) {
        this.snsKind = snsKind;
    }

    public String getDbEncKey() {
        return dbEncKey;
    }

    public void setDbEncKey(String dbEncKey) {
        this.dbEncKey = dbEncKey;
    }

    public String getYearYn() {
        return yearYn;
    }

    public void setYearYn(String yearYn) {
        this.yearYn = yearYn;
    }

    public String getSpecialYn() {
        return specialYn;
    }

    public void setSpecialYn(String specialYn) {
        this.specialYn = specialYn;
    }

    public String getHpcertno() {
        return hpcertno;
    }

    public void setHpcertno(String hpcertno) {
        this.hpcertno = hpcertno;
    }

    public String getAnlmbEdate() {
        return anlmbEdate;
    }

    public void setAnlmbEdate(String anlmbEdate) {
        this.anlmbEdate = anlmbEdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public boolean isMember() {
        if (this.uniqId == null || this.uniqId.equals("") || this.uniqId.equals("00000000")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean getIsMember() {
        return isMember();
    }

    public String getSecretMode() {
        return secretMode;
    }

    public void setSecretMode(String secretMode) {
        this.secretMode = secretMode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSnsEmail() {
        return snsEmail;
    }

    public void setSnsEmail(String snsEmail) {
        this.snsEmail = snsEmail;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getThisObjAllValue() {

        String strAllValue = "";

        strAllValue = strAllValue + "\n" + "id=" + id + "\n";
        strAllValue = strAllValue + "name=" + name + "\n";
        strAllValue = strAllValue + "ihidNum=" + ihidNum + "\n";
        strAllValue = strAllValue + "email=" + email + "\n";
        strAllValue = strAllValue + "password=" + password + "\n";
        strAllValue = strAllValue + "passwordHint=" + passwordHint + "\n";
        strAllValue = strAllValue + "passwordCnsr=" + passwordCnsr + "\n";
        strAllValue = strAllValue + "userSe=" + userSe + "\n";
        strAllValue = strAllValue + "orgnztId=" + orgnztId + "\n";
        strAllValue = strAllValue + "orgnztNm=" + orgnztNm + "\n";
        strAllValue = strAllValue + "uniqId=" + uniqId + "\n";
        strAllValue = strAllValue + "url=" + url + "\n";
        strAllValue = strAllValue + "ip=" + ip + "\n";
        strAllValue = strAllValue + "dn=" + dn + "\n";
        strAllValue = strAllValue + "authorNm=" + authorNm + "\n";
        strAllValue = strAllValue + "authorCode=" + authorCode + "\n";
        strAllValue = strAllValue + "snsId=" + snsId + "\n";
        strAllValue = strAllValue + "snsEmail=" + snsEmail + "\n";
        strAllValue = strAllValue + "snsKind=" + snsKind + "\n";
        strAllValue = strAllValue + "dbEncKey=" + dbEncKey + "\n";
        strAllValue = strAllValue + "yearYn=" + yearYn + "\n";
        strAllValue = strAllValue + "anlmbEdate=" + anlmbEdate + "\n";
        strAllValue = strAllValue + "specialYn=" + specialYn + "\n";
        strAllValue = strAllValue + "hpcertno=" + hpcertno + "\n";
        strAllValue = strAllValue + "status=" + status + "\n";
        strAllValue = strAllValue + "memGbn=" + memGbn + "\n";
        strAllValue = strAllValue + "secretMode=" + secretMode + "\n";
        strAllValue = strAllValue + "gender=" + gender + "\n";
        strAllValue = strAllValue + "birthDate=" + birthDate + "\n";
        strAllValue = strAllValue + "homeZipNo=" + homeZipNo + "\n";
        strAllValue = strAllValue + "homeAddr1=" + homeAddr1 + "\n";
        strAllValue = strAllValue + "homeAddr2=" + homeAddr2 + "\n";
        strAllValue = strAllValue + "contentsYn=" + contentsYn + "\n";
        strAllValue = strAllValue + "userType=" + userType + "\n";

        return strAllValue;
    }

}
