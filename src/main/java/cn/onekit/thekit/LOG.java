package cn.onekit.thekit;

public class LOG {
    public static String printStackTrace(Exception e) {
        StringBuffer sb = new StringBuffer();
        sb.append("{error:\"");
        sb.append(e.getMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
            sb.append(ste.toString());
        }
        sb.append("\"}");
        return sb.toString();
    }
}
