package demo.demo;

import cn.onekit.thekit.STRING;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        String str = STRING.format("https://{serverRoot}/bot/{apiVersion}/{chatbotId}",new HashMap<String,Object>(){{
            put("serverRoot","xx");
            put("apiVersion",123);
            put("chatbotId",null);
        }});
        System.out.println(str);
    }

}
