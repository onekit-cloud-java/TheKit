package cn.onekit.thekit;

import com.google.gson.*;

import java.lang.reflect.Type;

@SuppressWarnings("unused")
public class JSON {
    public interface GsonEnum<E> {

        String serialize();
        E deserialize(String jsonEnum);

        class TypeAdapter<E> implements JsonSerializer<E>, JsonDeserializer<E> {

            private final GsonEnum<E>  gsonEnum;

        public TypeAdapter(GsonEnum<E> gsonEnum) {
                this.gsonEnum = gsonEnum;
            }

            @Override
            public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                    return gsonEnum.deserialize(json.getAsString());

            }
            @Override
            public JsonElement serialize(E ge, Type type, JsonSerializationContext jsonSerializationContext) {
                return null;
            }
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
    @SafeVarargs
    private  static  <E,GE extends GsonEnum<E>> Gson _gson(Class<GsonEnum<E>>... enumClasses){
        try {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .serializeNulls();

           for (Class<GsonEnum<E>> enumClass : enumClasses) {
                gsonBuilder.registerTypeAdapter(enumClass, new GsonEnum.TypeAdapter<>(enumClass.newInstance()));
            }
           return gsonBuilder.create();
        } catch (Exception e) {
            e.printStackTrace();
           throw new Error(e.getMessage());
        }
    }
    /////////////////////
    @SafeVarargs
    public static <E,GE extends GsonEnum<E>> JsonElement object2json(Object obj, Class<GsonEnum<E>>... enumClasses) {

            Gson gson = _gson(enumClasses);
            return gson.toJsonTree(obj);
    }
    //////////////
    @SafeVarargs
    public static <T,E,GE extends GsonEnum<E>> T json2object(JsonElement json, Class<T> clazz, Class<GsonEnum<E>>... enumClasses) {
        Gson gson = _gson(enumClasses);
        return gson.fromJson(json, clazz);
    }
    /////////////////////
    @SafeVarargs
    public static <E,GE extends GsonEnum<E>>  String object2string(Object obj, Class<GsonEnum<E>>... enumClasses) {
        Gson gson = _gson(enumClasses);
        return gson.toJson(obj);
    }
    //////////////////////
    @SafeVarargs
    public static <T,E,GE extends GsonEnum<E>> T string2object(String json, Class<T> clazz, Class<GsonEnum<E>>... enumClasses) {
        Gson gson = _gson(enumClasses);
        return gson.fromJson(json, clazz);
    }
}
