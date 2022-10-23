package com.hisco.cmm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class JsonData {
	
	public JsonData()
	{
		super();
	}
	
	public String MakeJsonDataError(HttpServletRequest request, String message, String description)
	{
		Map<String, Object> json = new HashMap<String, Object>();
		
		json.put("error", true);
		json.put("message", message);
		json.put("description", description);
		
		return MakeJsonData(request, json);
	}
	
	public String MakeJsonData(HttpServletRequest request, final Object data)
	{
		if(request == null) return null;
		
		String callback = StringUtil.EscapeJavaScript(request.getParameter("callback"));
		StringBuffer sb = new StringBuffer();
		
		if(!StringUtil.IsEmpty(callback)) sb.append(callback).append("(");
		
		if(data == null)
		{
			sb.append("null");
		}
		else if(data instanceof String)
		{
			sb.append(data);
		}
		else if(data instanceof StringBuffer)
		{
			sb.append(data.toString());
		}
		else if(data instanceof Map)
		{
			sb.append(JsonUtil.Map2String((Map) data));
		}
		else if(data instanceof List)
		{
			sb.append(JsonUtil.List2String((List) data));
		}
		else
		{
			sb.append(JsonUtil.Object2String(data));
		}
		
		if(!StringUtil.IsEmpty(callback)) sb.append(")");
		
		return sb.toString();
	}
}
