package com.hisco.admin.edumovie.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hisco.admin.edumovie.vo.EduMovieVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 동영상강좌 Mapper
 *
 * @author 김범수
 * @since 2022.10.14
 * @version 1.0, 2022.10.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김범수 2022.10.14 최초작성
 */
@Mapper("eduMovieAdmMapper")
public interface EduMovieAdmMapper {

	List<HashMap<String, Object>> selectEduMovieList(EduMovieVO searchVO);
	
	int selectEduMovieCount(EduMovieVO searchVO);

	int insertEduMovie(EduMovieVO eduMovieVO);

	float selectEduMovieIdx(EduMovieVO eduMovieVO);

	int updateEduMovie(EduMovieVO eduMovieVO);

	int deleteEduMovie(EduMovieVO eduMovieVO);

	List<EgovMap> selectRsvnsetNameList(EduMovieVO eduMovieVO);
	
	List<EduMovieVO> selectEduMovieUserAjax(EduMovieVO eduMovieVO);
}
