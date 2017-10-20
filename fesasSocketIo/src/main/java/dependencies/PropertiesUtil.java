package dependencies;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

    final static Logger logger = LogManager.getLogger(PropertiesUtil.class);


    public static Map loadFromClasspath(String filename) throws Exception {
        Map<String, String> propertyMap = new HashMap();
        logger.info("loading file: " +  filename);
        InputStream stream = PropertiesUtil.class.getClassLoader().getResourceAsStream(filename);
        Properties p = new Properties();
        p.load(stream);

        Enumeration keys = p.propertyNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            propertyMap.put(key, p.getProperty(key));
        }

        stream.close();
        return propertyMap;
    }
}
