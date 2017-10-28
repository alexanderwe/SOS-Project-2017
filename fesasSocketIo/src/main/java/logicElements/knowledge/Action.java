package logicElements.knowledge;

public class Action {

    private String resourceId;
    private ActionType actionType;
    private String reason;



    public Action (String resourceId, ActionType actionType, String reason){
        this.resourceId = resourceId;
        this.actionType = actionType;
        this.reason = reason;
    }
}
