package dependencies;

public enum SensorType {
    SENSOR_TYPE_LIGHT ("SENSOR.TYPE_LIGHT"),
    SENSOR_TYPE_PERSON("SENSOR.TYPE_PERSON");

    String value;

    SensorType(String value) {
        this.value = value;
    }

    static SensorType byValue(String value) {
        for (SensorType type : SensorType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }

        return null;
    }

}
