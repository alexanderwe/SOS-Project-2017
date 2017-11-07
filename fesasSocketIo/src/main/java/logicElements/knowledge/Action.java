package logicElements.knowledge;


/**
 * Class for specifying an action, with it's reason and the source resourceId
 */
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
