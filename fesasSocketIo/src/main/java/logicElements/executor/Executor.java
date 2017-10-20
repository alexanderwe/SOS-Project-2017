package logicElements.executor;

import java.util.HashMap;

import com.google.gson.JsonObject;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.AbstractLogic;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.LogicType;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.IExecutorLogic;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.information.InformationType;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.IKnowledgeRecord;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.KnowledgeRecord;

/**
 * Description from meta data: 
 * 
 * This file has been generated by the FESAS development tool set.
 * 
 * @author FESAS 
 *
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

	//add variables here
	
	@Override
	public void initializeLogic(HashMap<String, String> properties) {
		//use this method for initializing variables, etc.
		// if there is nothing to do, delete it
	}
	
	@Override
	public String callLogic(IKnowledgeRecord data) {
		if (data instanceof KnowledgeRecord) { //substitute Object with the expected data type
			if (data.getData() instanceof JsonObject) { //substitute OBJECT with the expected data type
				//data.getData() return the actual data. The other properties of data is metadata (e.g., time stamps).
				// use 
				// this.sendData(Object); //for sending an object
				// or
				// this.sendArrayList(List); // for a list
				// return sth. as status message (displayed by the AL
				
				
				this.sendData("ExampleResult");
			}
			return "Not the expected data type! It is: " + data.getData().getClass().getSimpleName();
		}
		return "Not a KnowledgeRecord! It is: " + data.getClass().getSimpleName();
	}
	
	// add further methods if needed

}
