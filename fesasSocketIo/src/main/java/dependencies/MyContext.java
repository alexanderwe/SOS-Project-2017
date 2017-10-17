package dependencies;

import com.google.common.collect.HashMultimap;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class MyContext {

    private HashMap<String, HashMultimap<String,JsonObject>> context;

    public MyContext() {
        this.context = new HashMap<>();
    }


    public void put(String resourceId, SensorType sensorType, JsonObject data) {
        if (this.context.get(resourceId) == null) {
            HashMultimap<String, JsonObject> resourceEntries = HashMultimap.create();
            resourceEntries.put(sensorType.value, data);
            this.context.put(resourceId, resourceEntries);
        } else {
            this.context.get(resourceId).put(sensorType.value ,data);
        }
        System.out.println(this.toString());

    }
    public HashMap<String, HashMultimap<String,JsonObject>> getContext() {
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
