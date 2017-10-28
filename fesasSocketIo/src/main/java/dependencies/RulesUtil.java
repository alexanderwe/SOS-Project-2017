package dependencies;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RulesUtil {

    final static Logger logger = LogManager.getLogger(RulesUtil.class);


    public static JsonObject loadFromClasspath(String filename) throws Exception {
        Map<String, String> propertyMap = new HashMap();
        logger.info("loading file: " +  filename);
        InputStream stream = RulesUtil.class.getClassLoader().getResourceAsStream(filename);
        Reader reader = new InputStreamReader(stream, "UTF-8");
        JsonObject rules = new JsonParser().parse(reader).getAsJsonObject();
        stream.close();
        return rules.get("rules").getAsJsonObject();
    }
}
