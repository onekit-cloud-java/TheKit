package demo.demo;

import cn.onekit.thekit.AJAX;
import cn.onekit.thekit.JSON;
import cn.onekit.thekit.STRING;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.plugin2.message.Message;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class DemoApplication {
    @JsonAdapter(BaseAdapter.class)
public static abstract class Base{
        Base(String contentType){
            this.contentType=contentType;
        }
    public String getContentType() {
        return contentType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    private  String contentType;

    public Base(){
    }
int x=0;
    }

    static  public class A extends  Base{
int a;

        A() {
            super("AA");
        }
    }
    static  public class B extends  Base{
        B() {
            super("BB");
        }
        int b;
    }
    public    class MM{
        public int getQ() {
            return q;
        }

        public void setQ(int q) {
            this.q = q;
        }

        public List<Base> getList() {
            return list;
        }

        public void setList(List<Base> list) {
            this.list = list;
        }

        public int q;
     //   @Expose(deserialize = false)

        public  List<Base> list;
    }

    static public class  BaseAdapter  implements JsonSerializer<Base>, JsonDeserializer<Base> {

        @Override
        public Base deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject json = (JsonObject)jsonElement;
            switch (json.get("contentType").getAsString()){
                case "AA":
                    return JSON.json2object(json,A.class);
                case "BB":
                    return JSON.json2object(json,B.class);
                default:
                    return null;
            }
        }

        @Override
        public JsonElement serialize(Base mm, Type type, JsonSerializationContext jsonSerializationContext) {
            return JSON.object2json(mm);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
String json = "{\"q\":222,\"list\":[{\"contentType\":\"AA\",\"a\":123},{\"contentType\":\"BB\",\"b\":456}]}";
Object z = JSON.string2object(json,MM.class/*,new Class[]{BaseAdapter.class}*/);
System.out.println(z);
//
//
//        try {
//            String deleteurl = "http://maap.5g-msg.com:30001/bot/v1/medias/fid/524395264112967680";
//            String result = null;
//            String url = "http://maap.5g-msg.com:30001/bot/v1/sip%3A2021020501%40botplatform.rcs.chinaunicom.cn/medias/delete";
//            AJAX.headers = new HashMap<String, String>(){{
//                put("Authorization","accessToken 1c898f39-19cc-48a5-b565-01254884d3cf");
//                put("url",deleteurl);
//            }};
//            result =AJAX.request(url,"delete","");
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



//        try {
//            String url = "http://maap.5g-msg.com:30001/bot/v1/sip%3A2021020501%40botplatform.rcs.chinaunicom.cn/medias/upload";
//            File file = new File("E:\\result.png");
//            BufferedImage read = null;
//            read = ImageIO.read(file);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(read, "png", baos);
//            byte[] bytes = baos.toByteArray();
//            AJAX.headers = new HashMap<String, String>(){{
//                put("Authorization","accessToken 77d90288-ba87-4c9e-b56b-53ea25ced51b");
//             //   put("Content-Type","multipart/form-data");
//                put("UploadMode","temp");
//            }};
//            String result = AJAX.upload(url,new HashMap<String, byte[]>(){{
//                put("file1",bytes);
//            }});
////            String result = AJAX.upload(url,new HashMap<String, File>(){{
////                put("file1",file);
////            }});
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

}
