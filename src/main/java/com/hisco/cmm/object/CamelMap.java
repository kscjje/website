package com.hisco.cmm.object;

import org.apache.commons.collections.map.ListOrderedMap;
import com.hisco.cmm.util.CamelUtil;

/**
 * Camel 형태의 Map
 * 
 * @author 전영석
 * @since 2020.08.07
 * @version 1.0, 2020.08.07
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.07 최초작성
 */
public class CamelMap extends ListOrderedMap {

    private static final long serialVersionUID = 5887062987154427551L;

    public Object put(Object key, Object value) {
        return super.put(CamelUtil.convert2CamelCase((String) key), value);
    }

}
