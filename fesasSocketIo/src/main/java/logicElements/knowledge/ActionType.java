package logicElements.knowledge;

/**
 * Enum for the possible actions of the system
 */
public enum ActionType {
    LIGHT_TURN_ON("LIGHT_TURN_ON"),
    LIGHT_TURN_OFF("LIGHT_TURN_OFF"),
    WINDOW_OPEN("WINDOW_OPEN"),
    WINDOW_CLOSE("WINDOW_CLOSE"),
    TURN_OFF_ALL("TURN_OFF_ALL"),
    SECURITY_LEVEL_RISE("SECURITY_LEVEL_RISE"),
    SECURITY_LEVEL_DROP("SECURITY_LEVEL_DROP"),
    DO_NOTHING("DO_NOTHING");

    public String value;

    ActionType(String value) {
        this.value = value;
    }

    /**
     * Return the action type by its string value
     * @param value
     * @return
     */
    public static ActionType byValue(String value) {
        for (ActionType type : ActionType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }

        return null;
    }
}
