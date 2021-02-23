package demo.demo;

import cn.onekit.thekit.AJAX;
import cn.onekit.thekit.STRING;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@SpringBootApplication
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        try {
            String url = "http://maap.5g-msg.com:30001/bot/v1/sip%3A2021020501%40botplatform.rcs.chinaunicom.cn/medias/upload";
            File file = new File("E:\\result.png");
            BufferedImage read = null;
            read = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(read, "png", baos);
            byte[] bytes = baos.toByteArray();
            AJAX.headers = new HashMap<String, String>(){{
                put("Authorization","accessToken 77d90288-ba87-4c9e-b56b-53ea25ced51b");
             //   put("Content-Type","multipart/form-data");
                put("UploadMode","temp");
            }};
            String result = AJAX.upload(url,new HashMap<String, byte[]>(){{
                put("file1",bytes);
            }});
//            String result = AJAX.upload(url,new HashMap<String, File>(){{
//                put("file1",file);
//            }});
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
