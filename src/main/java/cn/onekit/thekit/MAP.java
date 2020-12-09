package cn.onekit.thekit;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class MAP {
    public static Map<String,String> object2map(Object obj) throws IllegalAccessException {
        Map<String, String> result = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if(value!=null) {
                result.put(fieldName, value.toString());
            }
        }
        return result;
    }
}
