package com.hisco.cmm.modules.extend;

import java.util.HashMap;
import java.util.Map;

import com.hisco.cmm.modules.JsonUtil;
import com.hisco.cmm.modules.StringUtil;

/**
 * 추가 정보 처리
 */
public abstract class ExtraData extends DefaultObject {
    private String extra_data; // 추가정보 Json형태
    private Map<String, Object> extra_datas; // 추가 정보 Map형태

    private String extra_data_search; // 추가정보 검색

    @Override
    public void push(Object data) {
        super.push(data);
        ExtraCheck();
    }

    public void ExtraCheck() {
        if ((StringUtil.IsEmpty(extra_data) || extra_data.equals("{}")) && (extra_datas != null && extra_datas.size() > 0)) {
            extra_data = JsonUtil.Map2String(extra_datas);
        } else if ((extra_datas == null || extra_datas.size() == 0) && (!StringUtil.Equals(extra_data, "{}") && !StringUtil.IsEmpty(extra_data))) {
            extra_datas = JsonUtil.String2Map(extra_data);
        } else if ((StringUtil.IsEmpty(extra_data) || extra_data.equals("{}")) && (extra_datas == null || extra_datas.size() <= 0)) {
            extra_data = null;
            extra_datas = null;
        }
    }

    public void addExtra_datas(Map<String, Object> extra_datas) {
        if (extra_datas == null || extra_datas.size() == 0)
            return;
        else if (this.extra_datas == null)
            setExtra_datas(extra_datas);
        else
            this.extra_datas.putAll(extra_datas);

        // ExtraCheck();
        this.extra_data = JsonUtil.Map2String(this.extra_datas);
    }

    public void overwriteExtra_datas(Map<String, Object> extra_datas) {
        if (extra_datas == null || extra_datas.size() == 0) {
            return;
        } else if (this.extra_data == null || this.extra_datas == null || this.extra_datas.size() <= 0) {
            setExtra_datas(extra_datas);
        } else {
            extra_datas.putAll(this.extra_datas);
            setExtra_datas(extra_datas);
        }
    }

    public String getExtra_data() {
        return extra_data;
    }

    public void setExtra_data(String extra_data) {
        StringBuffer sb = new StringBuffer();

        if (StringUtil.IsEmpty(extra_data))
            sb.append("{}");
        else
            sb.append(extra_data);

        this.extra_datas = JsonUtil.String2Map(sb.toString());

        if (this.extra_datas == null)
            this.extra_data = null;
        else if (this.extra_datas.size() == 0)
            this.extra_data = "{}";
        else
            this.extra_data = sb.toString();
    }

    public void setExtra_data_char(String extra_data) {
        this.extra_data = extra_data;
    }

    public Map<String, Object> getExtra_datas() {
        return extra_datas;
    }

    public void setExtra_datas(Map<String, Object> extra_datas) {
        this.extra_datas = extra_datas;

        if (extra_datas == null)
            this.extra_data = null;
        else
            this.extra_data = JsonUtil.Map2String(extra_datas);

        if (this.extra_data == null)
            this.extra_datas = null;
        else if ("{}".equals(this.extra_data) && this.extra_datas != null)
            this.extra_datas.clear();
        else if ("{}".equals(this.extra_data) && this.extra_datas == null)
            this.extra_datas = new HashMap<String, Object>();
    }

    public Object getExtra_datas(String key) {
        if (this.extra_datas == null || this.extra_datas.size() <= 0 || !this.extra_datas.containsKey(key))
            return null;
        return this.extra_datas.get(key);
    }

    public String getExtra_datas_String(String key) {
        return getExtra_datas(key) == null ? null : String.valueOf(getExtra_datas(key));
    }

    public int getExtra_datas_int(String key, int defaultValue) {
        return StringUtil.String2Int(getExtra_datas_String(key), defaultValue);
    }

    public long getExtra_datas_long(String key, long defaultValue) {
        return StringUtil.String2Long(getExtra_datas_String(key), defaultValue);
    }

    public float getExtra_datas_float(String key, float defaultValue) {
        return StringUtil.String2Float(getExtra_datas_String(key), defaultValue);
    }

    public void setExtra_datas(String key, Object value) {
        if (this.extra_datas == null)
            this.extra_datas = new HashMap<String, Object>();

        this.extra_datas.put(key, value);
        this.extra_data = JsonUtil.Map2String(this.extra_datas);
    }

    public boolean isExtra_datas(String key) {
        if (this.extra_datas == null)
            return false;

        return this.extra_datas.containsKey(key);
    }

    public void removeExtra_datas(String key) {
        if (isExtra_datas(key)) {
            this.extra_datas.remove(key);
            this.extra_data = JsonUtil.Map2String(this.extra_datas);
        }
    }

    public String getExtra_data_search() {
        return extra_data_search;
    }

    public void setExtra_data_search(String extra_data_search) {
        this.extra_data_search = extra_data_search;
        this.extra_data = extra_data_search;
    }
}
