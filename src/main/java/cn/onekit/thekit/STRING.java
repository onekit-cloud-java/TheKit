package cn.onekit.thekit;

import java.util.Map;

@SuppressWarnings("unused")
public class STRING {
    public static boolean isEmpty(String str){
        return str==null || str.equals("");
    }
    public static String format(String format, Map<String,Object> args){
        String result = new String(format);
        for(Map.Entry<String,Object> entry : args.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            String str = String.format("\\{%s\\}",key);
            result=result.replaceAll(str,value!=null?value.toString():"");
        }
        return result;
    }
}
