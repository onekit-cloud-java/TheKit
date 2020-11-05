package cn.onekit.thekit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JSON {

    public static JsonElement parse(String str) {
        return new JsonParser().parse(str);
    }

    public static String stringify(JsonElement json) {
        return json.toString();
    }

    public static JsonElement object2json(Object obj) {
        return new Gson().toJsonTree(obj);
    }
    public static String object2string(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T json2object(JsonElement json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }
}
