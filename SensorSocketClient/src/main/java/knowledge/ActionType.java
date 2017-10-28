package knowledge;

public enum ActionType {
    LIGHT_TURN_ON("LIGHT_TURN_ON"),
    LIGHT_TURN_OFF("LIGHT_TURN_OFF"),
    WINDOW_OPEN("WINDOW_OPEN"),
    WINDOW_CLOSE("WINDOW_CLOSE"),
    TURN_OFF_ALL("TURN_OFF_ALL"),
    DO_NOTHING("DO_NOTHING");

    public String value;

    ActionType(String value) {
        this.value = value;
    }

    public static ActionType byValue(String value) {
        for (ActionType type : ActionType.values()) {
            System.out.println(value);
            if (type.value.equals(value)) {
                return type;
            }
        }

        return null;
    }
}
