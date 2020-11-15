package cn.onekit.thekit;

import java.io.*;

public class FILE {
    public static boolean writeString(String path, String str) {
        File f = new File(path);

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {


            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f.getAbsoluteFile(), true);
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
        StringBuffer sbf = new StringBuffer();
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