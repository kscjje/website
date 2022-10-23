package egovframework.com.cmm;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.Data;

/**
 * @Class Name : ComDefaultVO.java
 * @Description : ComDefaultVO class
 * @Modification Information
 * @
 *   @ 수정일 수정자 수정내용
 *   @ ------- -------- ---------------------------
 *   @ 2009.02.01 조재영 최초 생성
 *   @ 2020.08.06 진수진 기관 아이디 comcd 추가
 * @author 공통서비스 개발팀 조재영
 * @since 2009.02.01
 * @version 1.0
 * @see
 */
@Data
@SuppressWarnings("serial")
public class ComDefaultVO implements Serializable {

    /** 기관 아이디 */
    private String comcd = Config.COM_CD;

    /** 검색시작일자 */
    private String searchStartDts = "";

    /** 검색종료일자 */
    private String searchEndDts = "";

    /** 검색조건 */
    private String searchCondition = "";

    /** 검색Keyword */
    private String searchKeyword = "";

    /** 검색정렬조건 */
    private String searchKind = "";

    /** 검색정렬종류 */
    private String searchOrder = "";

    /** 검색정렬방향 */
    private String searchOrderDir = "";

    /** 검색조건:신청유무 */
    private String searchStat = "";

    /** 검색사용여부 */
    private String searchUseYn = "";

    /** 현재페이지 */
    private int pageIndex = 1;

    /** 페이지갯수 */
    private int pageUnit = Config.PAGE_UNIT;

    /** 페이지사이즈 */
    private int pageSize = Config.PAGE_SIZE;

    /** firstIndex */
    private int firstIndex = 0;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;

    /** 검색KeywordFrom */
    private String searchKeywordFrom = "";

    /** 검색KeywordTo */
    private String searchKeywordTo = "";

    private String excelyn = "";

    /**
     * 관리 기업 목록
     */
    private String searchOrgNo;
    private List<String> myOrgList;

    public void setPaginationInfo(PaginationInfo paginationInfo) {
        /* 페이징 처리 */
        this.firstIndex = paginationInfo.getFirstRecordIndex();
        this.lastIndex = paginationInfo.getLastRecordIndex();
        this.recordCountPerPage = paginationInfo.getRecordCountPerPage();
    }

    public void setPageIndex(String pageIndex) {
        if (pageIndex == null || pageIndex.equals("")) {
            this.pageIndex = 1;
        } else {
            this.pageIndex = Integer.parseInt(pageIndex);
        }
    }

    public void setPageSize(String pageSize) {
        if (pageSize == null || pageSize.equals("")) {
            this.pageSize = Config.PAGE_SIZE;
        } else {
            this.pageSize = Integer.parseInt(pageSize);
        }

        this.recordCountPerPage = this.pageSize;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
