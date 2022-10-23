/**
 * 개요
 * - 배너에 대한 Vo 클래스를 정의한다.
 * 상세내용
 * - 배너의 목록 항목을 관리한다.
 * 
 * @author 이문준
 * @version 1.0
 * @created 03-8-2009 오후 2:07:13
 */

package egovframework.com.uss.ion.bnr.service;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class BannerVO extends Banner {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    List<BannerVO> bannerList; // 배너 목록
    String[] delYn; // 삭제대상 목록
    String resultType = "horizontal"; // 결과 반영 타입 (vertical : 세로 , horizontal : 가로)
    private String comCd;
    private String searchGubun; // 배너 위치 검색
    private String isOrg; // 공통배너. 기관별 배너 구분
    private String searchGubunNm; // 배너위치 이름
    private String isActive; // 활성화여부
}
