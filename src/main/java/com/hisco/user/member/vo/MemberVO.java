package com.hisco.user.member.vo;

import java.sql.Timestamp;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.service.EgovProperties;
import lombok.Data;

/**
 * @Class Name : MemberVO.java
 * @Description : 회원정보 처리를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 8. 14 김희택
 * @author 김희택
 * @since 2020. 8. 14
 * @version
 * @see
 */
@Data
@SuppressWarnings("serial")
public class MemberVO extends ComDefaultVO {

    /* 회원번호 */
    private String memNo;
    /* 회원성명 */
    private String memNm;
    /* 영문성명 */
    private String engNm;
    /* 회원구분 */
    private String memGbn;
    /* 성별 */
    private String gender;
    /* 웹아이디 */
    private String id;
    /* 웹패스워드(암호화) */
    private String pw;
    /* 암호화 salt*/
    private String salt;
    /* 최초가입일자 */
    private String joinDate;
    /* 생년월일+성별구분 */
    private String ssn;
    /* 생년월일 */
    private String birthDate;
    /* 생일월일 */
    private String birthMmdd;
    /* 양음구분 */
    private String birthSec;
    /* 핸드폰번호(암호화) */
    private String hp;
    /* 이메일(암호화) */
    private String email;
    /* 이메일발송동의여부 */
    private String emailYn;
    /* sms발송동의여부 */
    private String smsYn;
    /* 개인정보및이용약관동의여부 */
    private String agreeYn;
    /* 개인정보및이용약관마지막동의일자 */
    private String agreeLastDate;
    /* 차량번호 */
    private String carNo;
    /* 비고 */
    private String remark;
    /* 상태 */
    private String status;
    /* 본인가상식별번호 */
    private String piIpin;
    /* 본인확인인증방법 */
    private String piAuthtype;
    /* 본인인증중복키 */
    private String piAuthkey;
    /* 본인확인인증일시 */
    private Timestamp piAuthdatetime;
    /* 부모본인인증확인방식 */
    private String piPAuthtype;
    /* 부모본인확인중복키 */
    private String piPAuthkey;
    /* 부모본인확인인증일시 */
    private Timestamp piPAuthdatetime;
    /* 기존회원번호 */
    private String oldMemNo;
    /* 로그인실패횟수 */
    private String failCnt;
    /* 웹로그인ID계정잠금여부 */
    private String lockedYn;
    /* sns계정연결여부 */
    private String snsRegistyn;
    /* 등록일시 */
    private Timestamp regdate;
    /* 등록자 */
    private String reguser;
    /* 수정일시 */
    private Timestamp moddate;
    /* 수정자 */
    private String moduser;
    /* 등록수정아이피주소 */
    private String userIp;
    /* 디비 암호화 key */
    private String dbEncKey = EgovProperties.getProperty("Globals.DbEncKey");
    /* 탈퇴사유 */
    private String outReason;

    /* 연간회원여부 */
    private String yearYn;

    /* 특별회원 여부 */
    private String specialYn;

    /* 연간회원 종료일 */
    private String anlmbEdate;

    /* 거주지역 */
    private String resdncGbn;

    // 기관정보
    private String resdncOrgname;

    // 가족 대표자 번호
    private String familyMemNo;

    // 기타 바인딩용 변수
    private String email1;
    private String email2;
    private String payYn;
    MemberCarVO carVO; // 차량정보
    MemberInstVO instVO; // 관심뷴야
    MemberSnsVO snsVO; // sns정보
    private String type; // normal , child (14세 이상,미만)

    private String address; // 주소
    private String addressMsg;

    private String postNum;
    private String addr1;
    private String addr2;

    
    // 중복확인키
    private String dupcheckKey;

    private Timestamp loginLastdate;

    private Timestamp pwdChangeDate;

    private String statusNm;

    private Timestamp outDate;

}
