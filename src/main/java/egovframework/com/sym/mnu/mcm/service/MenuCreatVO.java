package egovframework.com.sym.mnu.mcm.service;

/**
 * 메뉴생성 처리를 위한 VO 클래스르를 정의한다
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
public class MenuCreatVO {

    /** 메뉴번호 */
    private int menuNo;
    /** 맵생성ID */
    private String mapCreatId;
    /** 권한코드 */
    private String authorCode;

    /** 권한정보설정 */
    /** 권한명 */
    private String authorNm;
    /** 권한설명 */
    private String authorDc;
    /** 권한생성일자 */
    private String authorCreatDe;

    /** 기타VO변수 */
    /** 생성자ID **/
    private String creatPersonId;

    /** 사용자/관리자 구분 **/
    private String siteGubun;

    /** 입력 권한 */
    private String insYn;

    /** 수정 권한 */
    private String updYn;

    /** 삭제 권한 */
    private String delYn;

    /** 그룹코드 */
    private String groupId;

    /** 그룹명 */
    private String groupNm;

    /**
     * menuNo attribute를 리턴한다.
     * 
     * @return int
     */
    public int getMenuNo() {
        return menuNo;
    }

    /**
     * menuNo attribute 값을 설정한다.
     * 
     * @param menuNo
     *            int
     */
    public void setMenuNo(int menuNo) {
        this.menuNo = menuNo;
    }

    /**
     * mapCreatId attribute를 리턴한다.
     * 
     * @return String
     */
    public String getMapCreatId() {
        return mapCreatId;
    }

    /**
     * mapCreatId attribute 값을 설정한다.
     * 
     * @param mapCreatId
     *            String
     */
    public void setMapCreatId(String mapCreatId) {
        this.mapCreatId = mapCreatId;
    }

    /**
     * authorCode attribute를 리턴한다.
     * 
     * @return String
     */
    public String getAuthorCode() {
        return authorCode;
    }

    /**
     * authorCode attribute 값을 설정한다.
     * 
     * @param authorCode
     *            String
     */
    public void setAuthorCode(String authorCode) {
        this.authorCode = authorCode;
    }

    /**
     * authorNm attribute를 리턴한다.
     * 
     * @return String
     */
    public String getAuthorNm() {
        return authorNm;
    }

    /**
     * authorNm attribute 값을 설정한다.
     * 
     * @param authorNm
     *            String
     */
    public void setAuthorNm(String authorNm) {
        this.authorNm = authorNm;
    }

    /**
     * authorDc attribute를 리턴한다.
     * 
     * @return String
     */
    public String getAuthorDc() {
        return authorDc;
    }

    /**
     * authorDc attribute 값을 설정한다.
     * 
     * @param authorDc
     *            String
     */
    public void setAuthorDc(String authorDc) {
        this.authorDc = authorDc;
    }

    /**
     * authorCreatDe attribute를 리턴한다.
     * 
     * @return String
     */
    public String getAuthorCreatDe() {
        return authorCreatDe;
    }

    /**
     * authorCreatDe attribute 값을 설정한다.
     * 
     * @param authorCreatDe
     *            String
     */
    public void setAuthorCreatDe(String authorCreatDe) {
        this.authorCreatDe = authorCreatDe;
    }

    /**
     * creatPersonId attribute를 리턴한다.
     * 
     * @return String
     */
    public String getCreatPersonId() {
        return creatPersonId;
    }

    /**
     * creatPersonId attribute 값을 설정한다.
     * 
     * @param creatPersonId
     *            String
     */
    public void setCreatPersonId(String creatPersonId) {
        this.creatPersonId = creatPersonId;
    }

    /**
     * siteGubun attribute를 리턴한다.
     * 
     * @return String
     */
    public String getSiteGubun() {
        return siteGubun;
    }

    /**
     * siteGubun attribute 값을 설정한다.
     * 
     * @param siteGubun
     *            String
     */
    public void setSiteGubun(String siteGubun) {
        this.siteGubun = siteGubun;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupNm() {
        return groupNm;
    }

    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
    }

}