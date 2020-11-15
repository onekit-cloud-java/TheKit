package cn.onekit.thekit;

import com.google.gson.JsonObject;
import java.io.File;

public class DB {
    private static String _path(String table){
        return String.format("%s/%s.json",System.getProperties().getProperty("user.home"),table);
    }
    public static String get(String table,String key) {
        String path = _path(table);
        JsonObject db;
        if (!new File(path).exists()) {
            return null;
        }
        db = (JsonObject) JSON.parse(FILE.readString(path));
        return db.get(key).getAsString();
    }
    public static  void set(String table,String key,String value){
        String path = _path(table);
        JsonObject db;
        if(new File(path).exists()){
            db = (JsonObject) JSON.parse(FILE.readString(path));
        }else{
            db = new JsonObject();
        }
        db.addProperty(key,value);
        FILE.writeString(path,JSON.stringify(db));
    }
}
