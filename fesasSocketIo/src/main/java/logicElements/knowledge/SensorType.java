package logicElements.knowledge;


/**
 * Enum for the different sensor types of the system
 */
public enum SensorType {
    SENSOR_TYPE_LIGHT ("SENSOR_TYPE_LIGHT_LEVEL"),
    SENSOR_TYPE_LIGHT_BULB ("SENSOR_TYPE_LIGHT_BULB"),
    SENSOR_TYPE_WINDOW("SENSOR_TYPE_WINDOW"),
    SENSOR_TYPE_HUMIDITY("SENSOR_TYPE_HUMIDITY"),
    SENSOR_TYPE_SECURITY("SENSOR_TYPE_SECURITY"),
    SENSOR_TYPE_PERSON("SENSOR_TYPE_PERSON");

    public String value;

    SensorType(String value) {
        this.value = value;
    }

    /**
     * Return the sensor type by its string value
     * @param value
     * @return
     */
    public static SensorType byValue(String value) {
        for (SensorType type : SensorType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }

        return null;
    }

}
