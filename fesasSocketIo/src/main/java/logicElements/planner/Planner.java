package logicElements.planner;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.AbstractLogic;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.LogicType;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.IPlannerLogic;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.information.InformationType;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.IKnowledgeRecord;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.KnowledgeRecord;
import logicElements.knowledge.Action;
import logicElements.knowledge.AnalyzeTypes;

/**
 * Description from meta data: 
 * 
 * This file has been generated by the FESAS development tool set.
 * 
 * @author FESAS 
 *
 */
public class Planner extends AbstractLogic implements IPlannerLogic {
	
	public Planner() {
		super();	
		supportedInformationTypes.add(InformationType.Analyzing_DEFAULT);
	
		this.informationType = InformationType.Planning_DEFAULT;
		type = LogicType.PLANNER;
		shortName = "Planner";
	}

	// do not change anything above this line (except of adding import statements)

	//add variables here
	
	@Override
	public void initializeLogic(HashMap<String, String> properties) {
		//use this method for initializing variables, etc.
		// if there is nothing to do, delete it
	}
	
	@Override
	public String callLogic(IKnowledgeRecord data) {
		if (data instanceof KnowledgeRecord) {
			if (data.getData() instanceof JsonObject) {

				JsonObject analyzeResult = (JsonObject) data.getData();
				String resourceId = analyzeResult.get("resourceId").getAsString();
				AnalyzeTypes analyzeTypes = AnalyzeTypes.valueOf(analyzeResult.get("result").getAsString());

				switch (analyzeTypes) {
					//TODO: Make this json creation a bit more practical than manually creating the strings
					case LIGHT_LEVEL_LOW: this.sendData(new Action(resourceId, "Turn on light", analyzeTypes));break;
					case LIGHT_LEVEL_HIGH: this.sendData(new Action(resourceId, "Turn off light", analyzeTypes));break;
					default: break;
				}
			}
			return "Not the expected data type! It is: " + data.getData().getClass().getSimpleName();
		}
		return "Not a KnowledgeRecord! It is: " + data.getClass().getSimpleName();
	}
	
	// add further methods if needed

}
