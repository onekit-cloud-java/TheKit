package cn.onekit.thekit;

import com.google.gson.JsonObject;
import java.io.File;
import java.util.Date;

public class FileDB {
    public static class Data{
        String value;
        long time;
    }
    private static String _path(String table){
        return String.format("%s/%s.json",System.getProperties().getProperty("user.home"),table);
    }
    public static Data get(String table,String key) {
        String path = _path(table);
        JsonObject db;
        if (!new File(path).exists()) {
            return null;
        }
        db = (JsonObject) JSON.parse(FILE.readString(path));
        if( !db.has(key)){
            return null;
        }
        JsonObject json = db.getAsJsonObject(key);
        Data data = new Data();
        data.value = json.get("value").getAsString();
        data.time = json.get("time").getAsLong();
        return data;
    }
    public static  void set(String table,String key,String value){
        String path = _path(table);
        JsonObject db;
        if(new File(path).exists()){
            db = (JsonObject) JSON.parse(FILE.readString(path));
        }else{
            db = new JsonObject();
        }
        JsonObject data = new JsonObject();
        data.addProperty("value",value);
        data.addProperty("time",new Date().getTime());
        db.add(key,data);
        FILE.writeString(path,JSON.stringify(db));
    }
}
