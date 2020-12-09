package cn.onekit.thekit;

@SuppressWarnings("unused")
public class LOG {
    public static String printStackTrace(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append("{error:\"");
        sb.append(e.getMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
            sb.append(ste.toString());
        }
        sb.append("\"}");
        return sb.toString();
    }
}
