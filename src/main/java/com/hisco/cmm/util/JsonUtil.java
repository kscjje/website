package com.hisco.cmm.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    public static void ObjectTypeAdapter(GsonBuilder gsonBuilder) {
        class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
            @Override
            public Date deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
                    throws JsonParseException {
                String text = arg0.getAsJsonPrimitive().getAsString();
                return DateUtil.string2dateTight(text);
            }

            @Override
            public JsonElement serialize(Date arg0, Type arg1, JsonSerializationContext arg2) {
                String text = DateUtil.printDatetime(arg0);
                return new JsonPrimitive(text);
            }
        }

        class MapDeserializerDoubleAsIntFix implements JsonDeserializer<Map<String, Object>> {
            @Override
            @SuppressWarnings("unchecked")
            public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return (Map<String, Object>) read(json);
            }

            public Object read(JsonElement in) {
                if (in.isJsonArray()) {
                    List<Object> list = new ArrayList<Object>();
                    JsonArray arr = in.getAsJsonArray();
                    for (JsonElement anArr : arr) {
                        list.add(read(anArr));
                    }
                    return list;
                } else if (in.isJsonObject()) {
                    Map<String, Object> map = new LinkedTreeMap<String, Object>();
                    JsonObject obj = in.getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> entitySet = obj.entrySet();
                    for (Map.Entry<String, JsonElement> entry : entitySet) {
                        map.put(entry.getKey(), read(entry.getValue()));
                    }
                    return map;
                } else if (in.isJsonPrimitive()) {
                    JsonPrimitive prim = in.getAsJsonPrimitive();
                    if (prim.isBoolean()) {
                        return prim.getAsBoolean();
                    } else if (prim.isString()) {
                        if (DateUtil.string2dateTight(prim.getAsString()) != null)
                            return DateUtil.string2dateTight(prim.getAsString());
                        else
                            return prim.getAsString();
                    } else if (prim.isNumber()) {
                        Number num = prim.getAsNumber();
                        // here you can handle double int/long values
                        // and return any type you want
                        // this solution will transform 3.0 float to long values
                        if (Math.ceil(num.doubleValue()) == num.longValue())
                            return num.longValue();
                        else
                            return num.doubleValue();
                    }
                }
                return null;
            }
        }

        if (gsonBuilder == null)
            return;

        gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
        gsonBuilder.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
            @Override
            public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == src.intValue())
                    return new JsonPrimitive(src.intValue());
                else if (src == src.longValue())
                    return new JsonPrimitive(src.longValue());
                else
                    return new JsonPrimitive(src);
            }
        });

        gsonBuilder.registerTypeAdapter(new TypeToken<Map<String, Object>>() {
        }.getType(), new MapDeserializerDoubleAsIntFix());
        gsonBuilder.registerTypeAdapter(new TypeToken<List<Object>>() {
        }.getType(), new MapDeserializerDoubleAsIntFix());

        // null 출력 처리
        if (log.isDebugEnabled())
            gsonBuilder.serializeNulls();

        // gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        // gsonBuilder.serializeSpecialFloatingPointValues();

        /*
         * gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
         * gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
         * gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm");
         * gsonBuilder.setDateFormat("yyyy-MM-dd");
         */
    }

    /**
     * Map 을 Json 문자열로 변환
     * 
     * @param mapData
     *            Map<String, Object> 데이터
     * @return JSON 문자열
     */
    public static String Map2String(Map<String, String> mapData) {
        if (mapData == null || mapData.isEmpty()) {
            return "{}";
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            ObjectTypeAdapter(gsonBuilder);

            Gson gson = gsonBuilder.create();
            if (gson == null)
                return null;
            return gson.toJson(mapData);
        }
    }

    /**
     * Json 문자열을 Map으로 변환
     * 
     * @param text
     *            JSON 문자열
     * @return MAP 데이터
     */
    public static Map<String, Object> String2Map(String text) {
        if (StringUtil.IsEmpty(text)) {
            return null;
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            ObjectTypeAdapter(gsonBuilder);

            Gson gson = gsonBuilder.create();
            if (gson == null)
                return null;
            return gson.fromJson(text, new TypeToken<Map<String, Object>>() {
            }.getType());
        }
    }

    /**
     * Json 문자열을 Map으로 변환 : 형변환 처리 안함
     * 
     * @param text
     * @return
     */
    public static Map<String, Object> String2MapOrigin(String text) {
        if (StringUtil.IsEmpty(text)) {
            return null;
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            // ObjectTypeAdapter(gsonBuilder);

            Gson gson = gsonBuilder.create();
            if (gson == null)
                return null;
            return gson.fromJson(text, new TypeToken<Map<String, Object>>() {
            }.getType());
        }
    }

    /**
     * List 를 Json 문자열로 변환
     * 
     * @param listData
     *            List 객체
     * @return Json 문자열로 바뀐 List 자료
     */
    public static String List2String(List<?> listData) {
        if (listData == null)
            return "{}";
        else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            ObjectTypeAdapter(gsonBuilder);

            Gson gson = gsonBuilder.create();
            if (gson == null)
                return null;
            return gson.toJson(listData);
        }
    }

    /**
     * Json 문자열을 List로 변환
     * 
     * @param text
     *            Json 문자열
     * @param classObject
     *            데이터 자료형
     * @return List 객체
     */
    @SuppressWarnings("rawtypes")
    public static List<?> String2List(String text, Class classObject) {
        if (StringUtil.IsEmpty(text)) {
            return null;
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            ObjectTypeAdapter(gsonBuilder);

            Gson gson = gsonBuilder.create();
            if (gson == null)
                return null;
            return gson.fromJson(text, new TypeToken<List<?>>() {
            }.getType());
        }
    }
    /**
     * Object 를 Json 문자열로 변환
     * 
     * @param obj
     *            객체
     * @return Json 문자열로 바뀐 Object 자료
     */
    public static String Object2String(Object obj) {
        if (obj == null) {
            return "{}";
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            ObjectTypeAdapter(gsonBuilder);

            Gson gson = gsonBuilder.create();
            if (gson == null)
                return null;
            return gson.toJson(obj);
        }
    }

    /**
     * Json 문자열을 Object 로 변환
     * 
     * @param text
     *            Json 문자열
     * @param classObject
     *            데이터 자료형
     * @return Object 객체
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object String2Object(String text, Class classObject) {
        if (StringUtil.IsEmpty(text)) {
            return null;
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            ObjectTypeAdapter(gsonBuilder);

            Gson gson = gsonBuilder.create();
            if (gson == null)
                return null;
            return gson.fromJson(text, classObject);
        }
    }

    public static String toPrettyJson(Object obj) {
        if ("local".equals(CommonUtil.getProfile())) {
            return new GsonBuilder().setPrettyPrinting().create().toJson(obj);
        } else {
            return new GsonBuilder().create().toJson(obj);
        }
    }
    
	/**
	 * Json 문자열을 List로 변환
	 * @param text
	 * @return
	 */
	public static <T> T String2List(String text)
	{
		if(StringUtil.IsEmpty(text))
		{
			return null;
		}
		else
		{
			GsonBuilder gsonBuilder = new GsonBuilder();
			ObjectTypeAdapter(gsonBuilder);
			
			Gson gson = gsonBuilder.create();
			if(gson == null) return null;
			return gson.fromJson(text, new TypeToken<T>(){}.getType());
		}
	}
	
    
}
