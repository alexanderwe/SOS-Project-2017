package logicElements.knowledge;

public class Action {

    private String resourceId;
    private String action;
    private AnalyzeTypes reason;



    public Action (String resourceId, String action, AnalyzeTypes reason){
        this.resourceId = resourceId;
        this.action = action;
        this.reason = reason;
    }
}
