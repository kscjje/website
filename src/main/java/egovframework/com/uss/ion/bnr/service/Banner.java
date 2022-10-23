/**
 * 개요
 * - 배너에 대한 model 클래스를 정의한다.
 * 상세내용
 * - 배너의 일련번호, 배너명, 링크URL, 배너설명, 반영여부 항목을 관리한다.
 *
 * @author 이문준
 * @version 1.0
 * @created 03-8-2009 오후 2:07:10
 */

package egovframework.com.uss.ion.bnr.service;

import java.sql.Date;

import com.hisco.cmm.util.Config;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class Banner extends ComDefaultVO {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private String comcd = Config.COM_CD; // 기관코드
    private String bannerId; // 배너 ID
    private String bannerNm; // 배너 명
    private String linkUrl; // 링크 URL
    private String linkTarget; // 링크 타겟
    private String bannerImage; // 배너 이미지
    private String bannerImageFile; // 배너 이미지 파일
    private String bannerDc; // 배너 설명
    private String linkType; // 배너 설명
    private String linkContent; // 배너 설명
    private String sortOrdr; // 정렬 순서
    private String reflctAt; // 반영여부
    private String userId; // 사용자 ID
    private String frstRegisterId; // 등록자
    private Date frstRegistPnttm; // 등록일
    private String lastUpdusrId; // 수정자
    private Date lastUpdtPnttm; // 수정일
    private boolean isAtchFile; // 파일첨부여부
    private String ntceBgnde; // 게시시작일
    private String ntceEndde; // 게시종료일
    private String ntceBgndeYMD; // 게시시작일(ymd)
    private String ntceBgndeHH; // 게시시작일(시간)
    private String ntceBgndeMM; // 게시시작일(분)
    private String ntceEnddeYMD; // 게시종료일(ymd)
    private String ntceEnddeHH; // 게시종료일(시간)
    private String ntceEnddeMM; // 게시종료일(분)
    private String bannerGbn; // 배너 위치 구분
    private String filePath; // 업로드 파일 폴더
    private String fileName;
    private String orginFileName;

}
