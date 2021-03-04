package cn.onekit.thekit;

public class ERROR {
    public static String toString(Exception e) {
        StringBuilder reuslt = new StringBuilder();
        reuslt.append(e.getMessage()+"\n");
        for( StackTraceElement stackTrace : e.getStackTrace()){
            reuslt.append(stackTrace.toString()+"\n");
        }
        return reuslt.toString();
    }
}
