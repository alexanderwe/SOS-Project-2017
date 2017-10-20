package logicElements.knowledge;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for wrapping the context for the application. Implemented as a singleton, as it should serve like a state management resource, compared to things like
 * Flux or Redux
 */
public class ContextWrapper {


    final static Logger logger = LogManager.getLogger(ContextWrapper.class);
    private HashMap<String, Map<String, ArrayList<JsonObject>>> context;
    private static ContextWrapper instance;

    private ContextWrapper() {
        context = new HashMap<>();
    }

    public static ContextWrapper getInstance () {
        if (ContextWrapper.instance == null) {
            ContextWrapper.instance =  new ContextWrapper();
        }
        return ContextWrapper.instance;
    }

    /**
     * Add the data from a resource ID based on the sensor type to the context
     * @param resourceId
     * @param sensorType
     * @param data
     */
    public void put(String resourceId, SensorType sensorType, JsonObject data) {
        if (this.context.get(resourceId) == null) {
            Map<String, ArrayList<JsonObject>> resourceEntries = new HashMap<>();
            ArrayList<JsonObject> datas = new ArrayList();
            datas.add(data);
            resourceEntries.put(sensorType.value, datas);
            this.context.put(resourceId, resourceEntries);
        } else {
            System.out.println("We already have that resource");
            System.out.println(sensorType);
            System.out.println(this.context.get(resourceId).get(sensorType.value));

            if (this.context.get(resourceId).get(sensorType.value) == null) {
                ArrayList<JsonObject> datas = new ArrayList();
                datas.add(data);
                this.context.get(resourceId).put(sensorType.value, datas);
            } else {
                this.context.get(resourceId).get(sensorType.value).add(data);
            }


        }
    }
    public HashMap<String, Map<String, ArrayList<JsonObject>>> getContext() {
        return this.context;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String resourceId: this.context.keySet()){
            stringBuilder.append("ResourceId: " +  resourceId);
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("############################");
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(this.context.get(resourceId));
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("############################");
            stringBuilder.append(System.lineSeparator());

        }
        return stringBuilder.toString();

    }


}
