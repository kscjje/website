package egovframework.com.sym.mnu.mpm.service;

import java.io.Serializable;
import java.util.ArrayList;

import com.hisco.cmm.object.CamelMap;

/**
 * 메뉴목록관리 처리를 위한 VO 클래스르를 정의한다
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
 *   2021.03.23  진수진         hisco 컬럼 추가
 *      </pre>
 */

public class MenuManageVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 메뉴정보 */
    /** 메뉴번호 */
    private long menuNo;
    /** 메뉴순서 */
    private int menuOrdr;

    /** 메뉴순서 */
    private int menuNextOrdr;

    /** 메뉴 계층 */
    private int menuDepth;

    /** 메뉴명 */
    private String menuNm;

    /** 사용자/관리자 구분 */
    private String siteGubun;

    /** 링크 주소 */
    private String menuUrl;

    /** 사용여부 */
    private String useYn;

    /** 상위메뉴번호 */
    private int upperMenuNo;

    /** 상위메뉴이름 */
    private String upperMenuNm;

    /** 메뉴설명 */
    private String menuDc;
    /** 관련이미지경로 */
    private String relateImagePath;
    /** 관련이미지명 */
    private String relateImageNm;
    /** 프로그램파일명 */
    private String progrmFileNm;
    /** 메인 메뉴 여부 */
    private String mainYn;

    /** 부모 메인 메뉴 여부 */
    private String upperMainYn;

    private int childCnt;

    /** 롤 패턴 */
    private String rolePttrn;

    /** 사이트맵 */
    /** 생성자ID **/
    private String creatPersonId;

    /** 권한정보설정 */
    /** 권한코드 */
    private String authorCode;

    /** 메뉴 접근 수 */
    private long menuCount;
    private ArrayList<CamelMap> logCount;

    /** 기타VO변수 */
    private String tempValue;
    private int tempInt;

    /** 입력 권한 */
    private String insYn;

    /** 수정 권한 */
    private String updYn;

    /** 삭제 권한 */
    private String delYn;

    /** 관리그룹 코드 */
    private int groupId;

    /** Login 메뉴관련 VO변수 */
    /** tmp_Id */
    private String tmpId;
    /** tmp_Password */
    private String tmpPassword;
    /** tmp_Name */
    private String tmpName;
    /** tmp_UserSe */
    private String tmpUserSe;
    /** tmp_Email */
    private String tmpEmail;
    /** tmp_OrgnztId */
    private String tmpOrgnztId;
    /** tmp_UniqId */
    private String tmpUniqId;
    /** tmp_Cmd */
    private String tmpCmd;

    private String upperMenuId;

    private String frontGnbmenuyn;

    /** 컨텐츠 자동 처리 */
    private String contentsAutoRow;

    /** 메뉴 종류 */
    private String menuKind;

    private int tabMenuCnt;

    public int getTabMenuCnt() {
        return tabMenuCnt;
    }

    public void setTabMenuCnt(int tabMenuCnt) {
        this.tabMenuCnt = tabMenuCnt;
    }

    public String getMenuKind() {
        return menuKind;
    }

    public void setMenuKind(String menuKind) {
        this.menuKind = menuKind;
    }

    public String getContentsAutoRow() {
        return contentsAutoRow;
    }

    public void setContentsAutoRow(String contentsAutoRow) {
        this.contentsAutoRow = contentsAutoRow;
    }

    /**
     * menuNo attribute를 리턴한다.
     *
     * @return int
     */
    public long getMenuNo() {
        return menuNo;
    }

    /**
     * menuNo attribute 값을 설정한다.
     *
     * @param menuNo
     *            int
     */
    public void setMenuNo(long menuNo) {
        this.menuNo = menuNo;
    }

    /**
     * menuOrdr attribute를 리턴한다.
     *
     * @return int
     */
    public int getMenuOrdr() {
        return menuOrdr;
    }

    /**
     * menuOrdr attribute 값을 설정한다.
     *
     * @param menuOrdr
     *            int
     */
    public void setMenuOrdr(int menuOrdr) {
        this.menuOrdr = menuOrdr;
    }

    /**
     * menuNm attribute를 리턴한다.
     *
     * @return String
     */
    public String getMenuNm() {
        return menuNm;
    }

    /**
     * menuNm attribute 값을 설정한다.
     *
     * @param menuNm
     *            String
     */
    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    /**
     * upperMenuId attribute를 리턴한다.
     *
     * @return int
     */
    public int getUpperMenuNo() {
        return upperMenuNo;
    }

    /**
     * upperMenuId attribute 값을 설정한다.
     *
     * @param upperMenuId
     *            int
     */
    public void setUpperMenuNo(int upperMenuNo) {
        this.upperMenuNo = upperMenuNo;
    }

    /**
     * menuDc attribute를 리턴한다.
     *
     * @return String
     */
    public String getMenuDc() {
        return menuDc;
    }

    /**
     * menuDc attribute 값을 설정한다.
     *
     * @param menuDc
     *            String
     */
    public void setMenuDc(String menuDc) {
        this.menuDc = menuDc;
    }

    /**
     * relateImagePath attribute를 리턴한다.
     *
     * @return String
     */
    public String getRelateImagePath() {
        return relateImagePath;
    }

    /**
     * relateImagePath attribute 값을 설정한다.
     *
     * @param relateImagePath
     *            String
     */
    public void setRelateImagePath(String relateImagePath) {
        this.relateImagePath = relateImagePath;
    }

    /**
     * relateImageNm attribute를 리턴한다.
     *
     * @return String
     */
    public String getRelateImageNm() {
        return relateImageNm;
    }

    /**
     * relateImageNm attribute 값을 설정한다.
     *
     * @param relateImageNm
     *            String
     */
    public void setRelateImageNm(String relateImageNm) {
        this.relateImageNm = relateImageNm;
    }

    /**
     * progrmFileNm attribute를 리턴한다.
     *
     * @return String
     */
    public String getProgrmFileNm() {
        return progrmFileNm;
    }

    /**
     * progrmFileNm attribute 값을 설정한다.
     *
     * @param progrmFileNm
     *            String
     */
    public void setProgrmFileNm(String progrmFileNm) {
        this.progrmFileNm = progrmFileNm;
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
     * tmp_Id attribute를 리턴한다.
     *
     * @return String
     */
    public String getTmpId() {
        return tmpId;
    }

    /**
     * tmp_Id attribute 값을 설정한다.
     *
     * @param tmp_Id
     *            String
     */
    public void setTmpId(String tmpId) {
        this.tmpId = tmpId;
    }

    /**
     * tmp_Password attribute를 리턴한다.
     *
     * @return String
     */
    public String getTmpPassword() {
        return tmpPassword;
    }

    /**
     * tmp_Password attribute 값을 설정한다.
     *
     * @param tmp_Password
     *            String
     */
    public void setTmpPassword(String tmpPassword) {
        this.tmpPassword = tmpPassword;
    }

    /**
     * tmp_Name attribute를 리턴한다.
     *
     * @return String
     */
    public String getTmpName() {
        return tmpName;
    }

    /**
     * tmp_Name attribute 값을 설정한다.
     *
     * @param tmp_Name
     *            String
     */
    public void setTmpName(String tmpName) {
        this.tmpName = tmpName;
    }

    /**
     * tmp_UserSe attribute를 리턴한다.
     *
     * @return String
     */
    public String getTmpUserSe() {
        return tmpUserSe;
    }

    /**
     * tmp_UserSe attribute 값을 설정한다.
     *
     * @param tmp_UserSe
     *            String
     */
    public void setTmpUserSe(String tmpUserSe) {
        this.tmpUserSe = tmpUserSe;
    }

    /**
     * tmp_Email attribute를 리턴한다.
     *
     * @return String
     */
    public String getTmpEmail() {
        return tmpEmail;
    }

    /**
     * tmp_Email attribute 값을 설정한다.
     *
     * @param tmp_Email
     *            String
     */
    public void setTmpEmail(String tmpEmail) {
        this.tmpEmail = tmpEmail;
    }

    /**
     * tmp_OrgnztId attribute를 리턴한다.
     *
     * @return String
     */
    public String getTmpOrgnztId() {
        return tmpOrgnztId;
    }

    /**
     * tmp_OrgnztId attribute 값을 설정한다.
     *
     * @param tmp_OrgnztId
     *            String
     */
    public void setTmpOrgnztId(String tmpOrgnztId) {
        this.tmpOrgnztId = tmpOrgnztId;
    }

    /**
     * tmp_UniqId attribute를 리턴한다.
     *
     * @return String
     */
    public String getTmpUniqId() {
        return tmpUniqId;
    }

    /**
     * tmp_UniqId attribute 값을 설정한다.
     *
     * @param tmp_UniqId
     *            String
     */
    public void setTmpUniqId(String tmpUniqId) {
        this.tmpUniqId = tmpUniqId;
    }

    /**
     * tmp_Cmd attribute를 리턴한다.
     *
     * @return String
     */
    public String getTmpCmd() {
        return tmpCmd;
    }

    /**
     * tmp_Cmd attribute 값을 설정한다.
     *
     * @param tmp_Cmd
     *            String
     */
    public void setTmpCmd(String tmpCmd) {
        this.tmpCmd = tmpCmd;
    }

    /**
     * tempValue attribute를 리턴한다.
     *
     * @return String
     */
    public String getTempValue() {
        return tempValue;
    }

    /**
     * tempValue attribute 값을 설정한다.
     *
     * @param tempValue
     *            String
     */
    public void setTempValue(String tempValue) {
        this.tempValue = tempValue;
    }

    /**
     * tempInt attribute를 리턴한다.
     *
     * @return int
     */
    public int getTempInt() {
        return tempInt;
    }

    /**
     * tempInt attribute 값을 설정한다.
     *
     * @param tempInt
     *            int
     */
    public void setTempInt(int tempInt) {
        this.tempInt = tempInt;
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
     * @param String
     */
    public void setSiteGubun(String siteGubun) {
        this.siteGubun = siteGubun;
    }

    /**
     * menuUrl attribute를 리턴한다.
     *
     * @return String
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * menuUrl attribute 값을 설정한다.
     *
     * @param String
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    /**
     * useYn attribute를 리턴한다.
     *
     * @return String
     */
    public String getUseYn() {
        return useYn;
    }

    /**
     * useYn attribute 값을 설정한다.
     *
     * @param String
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * menuDepth attribute를 리턴한다.
     *
     * @return int
     */
    public int getMenuDepth() {
        return menuDepth;
    }

    /**
     * menuDepth attribute 값을 설정한다.
     *
     * @param int
     */
    public void setMenuDepth(int menuDepth) {
        this.menuDepth = menuDepth;
    }

    /**
     * upperMenuNm attribute를 리턴한다.
     *
     * @return String
     */
    public String getUpperMenuNm() {
        return upperMenuNm;
    }

    /**
     * upperMenuNm attribute 값을 설정한다.
     *
     * @param String
     */
    public void setUpperMenuNm(String upperMenuNm) {
        this.upperMenuNm = upperMenuNm;
    }

    /**
     * rolePttrn attribute를 리턴한다.
     *
     * @return String
     */
    public String getRolePttrn() {
        return rolePttrn;
    }

    /**
     * rolePttrn attribute 값을 설정한다.
     *
     * @param String
     */
    public void setRolePttrn(String rolePttrn) {
        this.rolePttrn = rolePttrn;
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

    public String getMainYn() {
        return mainYn;
    }

    public void setMainYn(String mainYn) {
        this.mainYn = mainYn;
    }

    public String getUpperMainYn() {
        return upperMainYn;
    }

    public void setUpperMainYn(String upperMainYn) {
        this.upperMainYn = upperMainYn;
    }

    public long getMenuCount() {
        return menuCount;
    }

    public void setMenuCount(long menuCount) {
        this.menuCount = menuCount;
    }

    public int getMenuNextOrdr() {
        return menuNextOrdr;
    }

    public void setMenuNextOrdr(int menuNextOrdr) {
        this.menuNextOrdr = menuNextOrdr;
    }

    public ArrayList<CamelMap> getLogCount() {

        if (logCount == null) {
            logCount = new ArrayList<CamelMap>();
            return logCount;
        } else {
            return logCount;
        }
    }

    public void setLogCount(ArrayList<CamelMap> logCount) {
        this.logCount = logCount;
    }

    public int getChildCnt() {
        return childCnt;
    }

    public void setChildCnt(int childCnt) {
        this.childCnt = childCnt;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getUpperMenuId() {
        return upperMenuId;
    }

    public void setUpperMenuId(String upperMenuId) {
        this.upperMenuId = upperMenuId;
    }

    public String getFrontGnbmenuyn() {
        return frontGnbmenuyn;
    }

    public void setFrontGnbmenuyn(String frontGnbmenuyn) {
        this.frontGnbmenuyn = frontGnbmenuyn;
    }

}