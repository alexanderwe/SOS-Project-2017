package logicElements.knowledge;

public enum SensorType {
    SENSOR_TYPE_LIGHT ("SENSOR_TYPE_LIGHT_LEVEL"),
    SENSOR_TYPE_LIGHT_BULB ("SENSOR_TYPE_LIGHT_BULB"),
    SENSOR_TYPE_WINDOW("SENSOR_TYPE_WINDOW"),
    SENSOR_TYPE_HUMIDITY("SENSOR_TYPE_HUMIDITY"),
    SENSOR_TYPE_PERSON("SENSOR_TYPE_PERSON");

    public String value;

    SensorType(String value) {
        this.value = value;
    }

    public static SensorType byValue(String value) {
        for (SensorType type : SensorType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }

        return null;
    }

}
