package customFramework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {

    /**
     * Load a properties file from the classpath
     *
     * @param propsName
     * @return Properties
     * @throws Exception
     */
    public static Properties load(String propsName) {
        Properties props = new Properties();
        InputStream in = ClassLoader.getSystemResourceAsStream(propsName);
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

}