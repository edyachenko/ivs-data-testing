package config;

import java.io.*;
import java.util.Properties;

public class CustomConfig {

    static Properties properties;
    private CustomConfig() {}

    public static Properties getPropertyInstance(){
        if (properties == null){
            return getProperties();

        }else{
            return properties;
        }

    }
    private static Properties getProperties(){

        try {
            properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/config.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;

    }



}
