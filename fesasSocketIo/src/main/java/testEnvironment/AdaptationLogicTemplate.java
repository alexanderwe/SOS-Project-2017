package testEnvironment;

import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.exceptions.LogicRepositoryNotFoundException;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.contract.Contract;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.ILogic;
import de.mannheim.wifo2.fesas.sasStructure.adaptationLogic.IAdaptationLogic;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.communication.CommunicationInformation;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.information.InformationCategory;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.IKnowledgeRecord;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.KnowledgeRecord;

import java.util.ArrayList;
import java.util.HashMap;

//bla
public class AdaptationLogicTemplate implements IAdaptationLogic {

    private ArrayList<ILogic> logics;
    private AdaptationLogicTemplate successor;
    private String name;
    private InformationCategory infoCategory;

    public AdaptationLogicTemplate(String name, AdaptationLogicTemplate successor, InformationCategory infoCategory) {
        this.name = name;
        this.logics = new ArrayList<ILogic>();
        this.successor = successor;
        this.infoCategory = infoCategory;
    }

    @Override
    public void prepareDataForSending(Object data, String type) {
        if (successor != null) {
            PseudoLogger.log(getClass().getName(), "prepareDataForSending()", "Call logic in : " + name);
            KnowledgeRecord k = new KnowledgeRecord(data, type, infoCategory.name(), name, System.currentTimeMillis());
            successor.callLogic(k);
        } else {
            PseudoLogger.log(name, "prepareDataForSending()", "Last element reached in chain. Data received : ");
            PseudoLogger.log(name, "prepareDataForSending()", data.getClass().getName() + " - " + data.toString());
        }
    }


    private void callLogic(IKnowledgeRecord knowledgeRecord) {


        for (ILogic logic : logics) {
            if (logic.isCompatibleDataType(knowledgeRecord.getType())) {
                System.out.println();
                PseudoLogger.log(getClass().getName(), "callLogic()", "**************");
                PseudoLogger.log(getClass().getName(), "callLogic()", "Data received in : " + name);
                PseudoLogger.log(getClass().getName(), "callLogic()", "Open logic : " + logic.getShortName());
                PseudoLogger.log(getClass().getName(), "callLogic()", knowledgeRecord.toString());
                PseudoLogger.log(getClass().getName(), "callLogic()", "**************");
                System.out.println();
                logic.callLogic(knowledgeRecord);
            }
        }
    }


    public void start(IKnowledgeRecord knowledgeRecord) {
        PseudoLogger.log(getClass().getName(), "start()", "Name AL : " + name);
        this.callLogic(knowledgeRecord);

    }


    public void implementLogic(ILogic logicToImplement) throws LogicRepositoryNotFoundException {
        logicToImplement.setAL(this);
        logicToImplement.setInformationCategory(infoCategory);
        logics.add(logicToImplement);
    }


    @Override
    public String getName() {
        return name;
    }


    public AdaptationLogicTemplate getSuccessor() {
        return successor;
    }

    public String toString() {
        String information = "";
        information += "name : " + name + "\n";
        information += "# logics : " + logics.size() + "\n";
        if (getSuccessor() == null) {
            information += "successor : null \n";
        } else {
            information += "successor : " + successor.getSuccessor() + "\n";
        }

        return information;
    }

	/*
	 * 
	 * 
	 * All methods below are intended to be empty - not used for testing
	 *
	 *
	 */


    @Override
    public void implementInputCommunicationLogic(String communicationType, String communicationID) throws Exception {
        // intended to be empty - not used for testing

    }

    @Override
    public void implementOutputCommunicationLogic(String communicationType, String communicationID) throws Exception {
        // intended to be empty - not used for testing

    }


    @Override
    public void addCommunicationToByType(String receiver, CommunicationInformation properties) {
        // intended to be empty - not used for testing

    }

    @Override
    public void addCommunicationFromByType(String sender, CommunicationInformation properties) {
        // intended to be empty - not used for testing

    }

    @Override
    public void addCommunicationToByCategory(String receiver, CommunicationInformation properties) {
        // intended to be empty - not used for testing

    }

    @Override
    public void addCommunicationFromByCategory(String sender, CommunicationInformation properties) {
        // intended to be empty - not used for testing

    }

    @Override
    public String saveKnowledge(IKnowledgeRecord knowledge) {
        // intended to be empty - not used for testing
        return null;
    }

    @Override
    public IKnowledgeRecord getKnowledge(String knowledgeID) {
        // intended to be empty - not used for testing
        return null;
    }


    @Override
    public String getFesasID() {
        // intended to be empty - not used for testing
        return null;
    }

    @Override
    public HashMap<String, String> getProperties() {
        // intended to be empty - not used for testing
        return null;
    }

    @Override
    public void setProperties(HashMap<String, String> properties) {
        // intended to be empty - not used for testing

    }

    @Override
    public void addProperty(String key, String value) {
        // intended to be empty - not used for testing

    }

    @Override
    public void externalRequest(String sucessor) {
        // intended to be empty - not used for testing

    }

    @Override
    public void start() {
        // intended to be empty - not used for testing

    }

    @Override
    public Object receiveData(String sender, String informationType, Object data) {
        // intended to be empty - not used for testing
        return null;
    }

    @Override
    public void implementLogic(Contract arg0) throws LogicRepositoryNotFoundException {
        // intended to be empty - not used for testing
    }

    @Override
    public boolean deplementLogic(Contract arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean stop() {
        // TODO Auto-generated method stub
        return false;
    }

}
