package egovframework.com.cop.bbs.service;

import lombok.Data;

/**
 * 게시물 관리를 위한 VO 클래스
 * 
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------      --------    ---------------------------
 *   2009.3.19  이삼섭          최초 생성
 *   2009.06.29  한성곤		2단계 기능 추가 (댓글관리, 만족도조사)
 *      </pre>
 */

@Data
public class BoardVO extends Board {

    /** 검색시작일 */
    private String searchBgnDe = "";

    /** 검색조건 */
    private String searchCnd = "";

    /** 검색종료일 */
    private String searchEndDe = "";

    /** 검색단어 */
    private String searchWrd = "";

    /** 검색 카테고리 */
    private String searchCtg = "";

    /** 상태검색 */
    private String searchStatus;

    /** 정렬순서(DESC,ASC) */
    private long sortOrdr = 0L;

    /** 레코드 번호 */
    private int rowNo = 0;

    /** 최초 등록자명 */
    private String frstRegisterNm = "";

    /** 최종 수정자명 */
    private String lastUpdusrNm = "";

    /** 유효여부 */
    private String isExpired = "N";

    /** 상위 정렬 순서 */
    private String parntsSortOrdr = "";

    /** 상위 답변 위치 */
    private String parntsReplyLc = "";

    /** 게시판 유형코드 */
    private String bbsTyCode = "";

    /** 게시판 속성코드 */
    private String bbsAttrbCode = "";

    /** 게시판 명 */
    private String bbsNm = "";

    /** 파일첨부가능여부 */
    private String fileAtchPosblAt = "";

    /** 첨부가능파일숫자 */
    private int posblAtchFileNumber = 0;

    /** 답장가능여부 */
    private String replyPosblAt = "";

    /** 조회 수 증가 여부 */
    private boolean plusCount = false;

    /** 익명등록 여부 */
    private String anonymousAt = "";

    /** 하위 페이지 인덱스 (댓글 및 만족도 조사 여부 확인용) */
    private String subPageIndex = "";

    /** 게시글 댓글갯수 */
    private String commentCo = "";

    /** 게시글 댓글사용 */
    private String commentAt = "";

    /** 볼드체 여부 */
    private String sjBoldAt;

    /** 공지 여부 */
    private String noticeAt;

    /** 비밀글 여부 */
    private String secretAt;

    /** 새글 여부 */
    private String newAt;

    /** 카테고리 순서 (html css class 용으로 사용) */
    private String ctgSort;

    /** 학습동아리 전용 추가 */
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
    private String item6;
    private String item7;
    private String item8;
    private String item9;
    private String item10;

    /** 게시글 상태 (학습동아리 신청 상태 : 승인대기(1) / 승인(2) / 반려(3) ) */
    private String ntceStat;

    /** min 첨부파일 (이미지만..) */
    private String atchImg;

    /** 게시판별 고정 항목 추가 */
    private String surveyNo; // 설문번호
    private String urlLink; // URL링크
    private String tag0001; // 해쉬태그1
    private String tag0002; // 해쉬태그2
    private String tag0003; // 해쉬태그3
    private String tag0004; // 해쉬태그4
    private String tag0005; // 해쉬태그5
    private String edcProgram; // 강좌프로그램
    private String source; // 출처
    private String media; // 언론사
}
