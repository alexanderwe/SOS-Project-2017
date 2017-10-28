package logicElements.monitor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.AbstractLogic;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.LogicType;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.IMonitorLogic;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.information.InformationType;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.IKnowledgeRecord;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.KnowledgeRecord;
import logicElements.knowledge.ContextWrapper;
import logicElements.knowledge.SensorType;

/**
 * Description from meta data: 
 * 
 * This file has been generated by the FESAS development tool set.
 * 
 * @author FESAS 
 *
 */
public class Monitor extends AbstractLogic implements IMonitorLogic {
	
	public Monitor() {
		super();	
		supportedInformationTypes.add(InformationType.Sensor_DEFAULT);
	
		this.informationType = InformationType.Monitoring_DEFAULT;
		type = LogicType.MONITOR;
		shortName = "Monitor";
	}

	// do not change anything above this line (except of adding import statements)

	/**
	 * Adds the sensor data to the current context
	 * Converts string into JsonObject and sends this to the analyzer component
	 * @param data
	 * @return
	 */
	@Override
	public String callLogic(IKnowledgeRecord data) {
		if (data instanceof KnowledgeRecord) {
			if (data.getData() instanceof String) {

				JsonObject jsonObject = new JsonParser().parse((String) data.getData()).getAsJsonObject();
				try {

					String resourceId = jsonObject.get("resourceId").getAsString();
					SensorType sensorType = SensorType.byValue(jsonObject.get("sensorType").getAsString());
					JsonObject jsonData = jsonObject.getAsJsonObject("data");

					System.out.println(resourceId);
					System.out.println(sensorType);
					System.out.println(jsonData);


					ContextWrapper.getInstance().put(resourceId,sensorType ,jsonData); // Add the retrieved data to the state management

					this.sendData(jsonObject);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
}
