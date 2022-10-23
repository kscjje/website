package com.hisco.admin.contents.mapper;

import java.util.List;
import java.util.Map;

import com.hisco.admin.contents.vo.ContentsVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 교육프로그램 Mapper
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Mapper("contentsMapper")
public interface ContentsMapper {

    /**
     * 컨텐츠 목록
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectContentList(ContentsVO paramMap);

    /**
     * 컨텐츠 상세 조회
     *
     * @param Map
     * @return ContentsVO
     * @throws Exception
     */
    public ContentsVO selectContentsDetail(Map<String, Object> paramMap);

    /**
     * 컨텐츠 신규 등록한다.
     *
     * @param Map
     * @return int
     * @throws Exception
     */
    public int insertContents(ContentsVO paramMap);

    /**
     * 컨텐츠 수정한다.
     *
     * @param Map
     * @return int
     * @throws Exception
     */
    public int updateContents(ContentsVO paramMap);

    /**
     * 컨텐츠 파일 아이디를 수정한다.
     *
     * @param Map
     * @return int
     * @throws Exception
     */
    public int updateContentsFileId2(ContentsVO paramMap);

    /**
     * 컨텐츠 와 연결된 메뉴 주소를 수정한다
     *
     * @param Map
     * @return int
     * @throws Exception
     */
    public int updateContentsUrl(ContentsVO paramMap);

    /**
     * 컨텐츠 삭제한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int deleteContents(ContentsVO paramMap);

    public ContentsVO selectMenuTitle(Map<String, Object> paramMap);

}
