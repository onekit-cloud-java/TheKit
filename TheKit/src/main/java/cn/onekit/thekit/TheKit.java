package cn.onekit.thekit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TheKit {

    public static JSONObject dict2json(Map<String, String> dict) {
        JSONObject json = new JSONObject();
        for(Map.Entry<String,String> entry: dict.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Iterable){
                json.put(key,array2json((Iterable)value));
            }else if(value instanceof Map){
                json.put(key,dict2json((Map)value));
            }else {
                json.put(key,value);
            }
        }
        return json;
    }
    public static JSONArray array2json(Iterable array) {
        JSONArray json = new JSONArray();
        for(Object value : array){
            if (value instanceof Iterable){
                json.add(array2json((Iterable)value));
            }else if(value instanceof Map){
                json.add(dict2json((Map)value));
            }else {
                json.add(value);
            }
        }
        return json;
    }
}
