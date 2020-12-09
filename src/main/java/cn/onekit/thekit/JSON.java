package cn.onekit.thekit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@SuppressWarnings("unused")
public class JSON {

    @SuppressWarnings("WeakerAccess")
    public static JsonElement parse(String str) {
        return new JsonParser().parse(str);
    }
    @SuppressWarnings("WeakerAccess")
    public static String stringify(JsonElement json) {
        return json.toString();
    }
    //////////////////////
    public static JsonElement object2json(Object obj) {
        return new Gson().toJsonTree(obj);
    }
    public static <T> T json2object(JsonElement json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }
    /////////////////////
    public static String object2string(Object obj) {
        return new Gson().toJson(obj);
    }
    public static <T> T string2object(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }
}
