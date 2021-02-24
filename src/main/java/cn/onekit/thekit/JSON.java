package cn.onekit.thekit;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

@SuppressWarnings("unused")
public class JSON {
    public interface GsonEnum<E> {

        String serialize();
        E deserialize(String jsonEnum);

    }
    public static class GsonEnumTypeAdapter<E> implements JsonSerializer<E>, JsonDeserializer<E> {

        private final GsonEnum<E> gsonEnum;

        public GsonEnumTypeAdapter(GsonEnum<E> gsonEnum) {
            this.gsonEnum = gsonEnum;
        }

        @Override
        public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
            if (null != src && src instanceof GsonEnum) {
                return new JsonPrimitive(((GsonEnum) src).serialize());
            }
            return null;
        }

        @Override
        public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (null != json) {
                return gsonEnum.deserialize(json.getAsString());
            }
            return null;
        }

    }
    @SuppressWarnings("WeakerAccess")
    public static JsonElement parse(String str) {
        return new JsonParser().parse(str);
    }
    @SuppressWarnings("WeakerAccess")
    public static String stringify(JsonElement json) {
        return json.toString();
    }
    //////////////////////
    private  static  <E extends GsonEnum> Gson _gson(Class<GsonEnum<E>>... enumClasses){
        try {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .serializeNulls();
            for (Class<GsonEnum<E>> enumClass : enumClasses) {
                gsonBuilder.registerTypeAdapter(enumClass, new GsonEnumTypeAdapter<>((E) enumClass.newInstance()));
            }
           return gsonBuilder.create();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static <E extends GsonEnum> JsonElement object2json(Object obj,Class<GsonEnum<E>>... enumClasses) {

            Gson gson = _gson(enumClasses);
            return gson.toJsonTree(obj);
    }
    public static <T,E extends GsonEnum> T json2object(JsonElement json, Class<T> clazz,Class<GsonEnum<E>>... enumClasses) {
        Gson gson = _gson(enumClasses);
        return gson.fromJson(json, clazz);
    }
    /////////////////////
    public static String object2string(Object obj) {
        return new Gson().toJson(obj);
    }
    public static <T> T string2object(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }
}
