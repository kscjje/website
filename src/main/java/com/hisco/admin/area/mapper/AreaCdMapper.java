package com.hisco.admin.area.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hisco.admin.area.vo.AreaCdVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 지역관리  Mapper
 *
 * @author 진수진
 * @since 2021.10.21
 * @version 1.0, 2021.10.21
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.10.21 최초작성
 */
@Mapper("areaCdMapper")
public interface AreaCdMapper {

    public List<?> selectAreaCdList(AreaCdVO areaCdVO);

    public List<?> selectAreaCdSubList(@Param("parentAreaCd") int parentAreaCd);


    public AreaCdVO selectAreaCdDetail(AreaCdVO areaCdVO);

    public void insertAreaCd(AreaCdVO areaCdVO);

    public void updateAreaCd(AreaCdVO areaCdVO);

    public void deleteAreaCd(AreaCdVO areaCdVO);

}
