package cn.onekit.thekit;

import java.io.*;

@SuppressWarnings("WeakerAccess")
public class FILE {

    @SuppressWarnings("UnusedReturnValue")
    public static boolean writeString(String path, String str) {
        File f = new File(path);

        FileWriter fw;
        BufferedWriter bw = null;
        try {


            if (!f.exists()) {
                //noinspection ResultOfMethodCallIgnored
                f.createNewFile();
            }
            fw = new FileWriter(f.getAbsoluteFile(), false);
            bw = new BufferedWriter(fw);
            bw.write(str);
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static String readString(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        StringBuilder sbf = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}