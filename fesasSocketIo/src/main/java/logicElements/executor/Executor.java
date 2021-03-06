package logicElements.executor;

import com.google.gson.Gson;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.AbstractLogic;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.LogicType;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.IExecutorLogic;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.information.InformationType;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.IKnowledgeRecord;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.KnowledgeRecord;
import logicElements.knowledge.Action;

/**
 * Description from meta data:
 * <p>
 * This file has been generated by the FESAS development tool set.
 *
 * @author FESAS
 */
public class Executor extends AbstractLogic implements IExecutorLogic {

    public Executor() {
        super();
        supportedInformationTypes.add(InformationType.Planning_DEFAULT);

        this.informationType = InformationType.Executing_DEFAULT;
        type = LogicType.EXECUTOR;
        shortName = "Executor";
    }

    // do not change anything above this line (except of adding import statements)

    @Override
    public String callLogic(IKnowledgeRecord data) {
        if (data instanceof KnowledgeRecord) {
            if (data.getData() instanceof Action) {

                // Purely pipe the Action event further to the SocketIOEffector
                this.sendData(new Gson().toJson(data.getData()));
            }
            return "Not the expected data type! It is: " + data.getData().getClass().getSimpleName();
        }
        return "Not a KnowledgeRecord! It is: " + data.getClass().getSimpleName();
    }

    // add further methods if needed

}
