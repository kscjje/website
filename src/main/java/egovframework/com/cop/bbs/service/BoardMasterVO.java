package egovframework.com.cop.bbs.service;

import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Data;

/**
 * 게시판 속성 정보를 관리하기 위한 VO 클래스
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
 *  -------    --------    ---------------------------
 *   2009.3.12  이삼섭          최초 생성
 *      </pre>
 */
@Data
public class BoardMasterVO extends BoardMaster {

    /** 검색시작일 */
    private String searchBgnDe = "";

    /** 검색조건 */
    private String searchCnd = "";

    /** 검색종료일 */
    private String searchEndDe = "";

    /** 검색단어 */
    private String searchWrd = "";

    /** 정렬순서(DESC,ASC) */
    private String sortOrdr = "";

    /** rowNo */
    private int rowNo = 0;

    /** 최초 등록자명 */
    private String frstRegisterNm = "";

    /** 게시판유형 코드명 */
    private String bbsTyCodeNm = "";

    /** 템플릿 명 */
    private String tmplatNm = "";

    /** 최종 수정자명 */
    private String lastUpdusrNm = "";

    /** 권한지정 여부 */
    private String authFlag = "";

    /** 템플릿경로 */
    private String tmplatCours = "";

    /** 게시글 갯수 */
    private int articleCnt = 0;

    /** 게스글 등록 최종일 */
    private Date articleDate = null;

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
