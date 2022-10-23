package egovframework.com.sym.mnu.mcm.service;

import java.sql.Date;

/**
 * 메뉴생성 생성을 위한 모델 클래스를 정의한다.
 * 
 * @author 개발환경 개발팀 이용
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  이  용          최초 생성
 *      </pre>
 */
public class MenuCreat {
    /** 메뉴번호 */
    private int menuNo;
    /** 맵생성ID */
    private String mapCreatId;
    /** 권한코드 */
    private String authorCode;

    /** 생성일 */
    private Date mapDreatDt;

    /** 입력 권한 */
    private String insYn;

    /** 수정 권한 */
    private String updYn;

    /** 삭제 권한 */
    private String delYn;

    public int getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(int menuNo) {
        this.menuNo = menuNo;
    }

    public String getMapCreatId() {
        return mapCreatId;
    }

    public void setMapCreatId(String mapCreatId) {
        this.mapCreatId = mapCreatId;
    }

    public String getAuthorCode() {
        return authorCode;
    }

    public void setAuthorCode(String authorCode) {
        this.authorCode = authorCode;
    }

    public Date getMapDreatDt() {
        return mapDreatDt;
    }

    public void setMapDreatDt(Date mapDreatDt) {
        this.mapDreatDt = mapDreatDt;
    }

    public String getInsYn() {
        return insYn;
    }

    public void setInsYn(String insYn) {
        this.insYn = insYn;
    }

    public String getUpdYn() {
        return updYn;
    }

    public void setUpdYn(String updYn) {
        this.updYn = updYn;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

}