package dependencies;

import java.util.HashMap;
import java.util.List;

public class MyContext {


    private HashMap<SensorType, String> context;

    public MyContext() {
        this.context = new HashMap<>();
    }


    public void put(SensorType sensorType, String data) {
        this.context.put(sensorType, data);
    }




}
