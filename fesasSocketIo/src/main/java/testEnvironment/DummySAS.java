package testEnvironment;

import java.util.ArrayList;

import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.IALMMonitorLogic;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.IALMSensorLogic;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.information.InformationCategory;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.information.InformationType;
import de.mannheim.wifo2.fesas.sasStructure.data.adaptationLogic.knowledge.KnowledgeRecord;
import logicElements.sensor.SocketIoSensor;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.ISensorLogic;
import logicElements.monitor.Monitor;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.IMonitorLogic;
import logicElements.analyzer.Analyzer;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.IAnalyzerLogic;
import logicElements.planner.Planner;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.IPlannerLogic;
import logicElements.executor.Executor;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.IExecutorLogic;
import logicElements.effector.SocketIoEffector;
import de.mannheim.wifo2.fesas.logicRepositoryStructure.data.metadata.logic.logicInterfaces.IEffectorLogic;



public class DummySAS {

	private static DummySAS instance;
	
	private ArrayList<KnowledgeRecord> testData = new ArrayList<KnowledgeRecord>();
	
	private ISensorLogic sensorLogic;
	private IMonitorLogic monitorLogic;
	private IAnalyzerLogic analyzerLogic;
	private IPlannerLogic plannerLogic;
	private IExecutorLogic executorLogic;
	private IEffectorLogic effectorLogic;
	

	
	private AdaptationLogicTemplate monitor;
	private AdaptationLogicTemplate analyzer;
	private AdaptationLogicTemplate planner;
	private AdaptationLogicTemplate executor;
	private AdaptationLogicTemplate sensor;
	private AdaptationLogicTemplate effector;


	
	
	private DummySAS() {
		initializeTestData();
		initializeLogicElements();
		initializeAL();
		sensorLogic.initializeLogic(null);
	}
	
	public static DummySAS getInstance() {
		if(instance == null) {
			instance = new DummySAS();
		}
		return instance;
	}

	public void start() {
		/*
		 * please specify that element that should start the test.
		 * In case there is some specific data assumed, please edit the 
		 * variable "probeData".
		 */
		AdaptationLogicTemplate startingComponent = sensor;
		//runTest(startingComponent);
		System.out.println("start test");
	}

	
	private void initializeTestData() {
		String ownerID = "fesasID-000_1_007";
		long timeStamp = 1456129173400l;
		System.out.println("Init test data");
		
		String testData1 = "{}";
		buildKnowledgeRecord(InformationType.DEFAULT, InformationCategory.DEFAULT, ownerID, timeStamp, testData1);


	}
	
	private void buildKnowledgeRecord(InformationType infoType, InformationCategory infoCat,
			String ownerID, long timeStamp, Object data) {
		KnowledgeRecord probeData = new KnowledgeRecord(data, 
				infoType.toString(), infoCat.toString(), ownerID, timeStamp);		
		testData.add(probeData);
	}
	
	private void initializeLogicElements() {
		
		sensorLogic = new SocketIoSensor();
		monitorLogic = new Monitor();
		monitorLogic.initializeLogic(null);
		analyzerLogic = new Analyzer();
		analyzerLogic.initializeLogic(null);
		plannerLogic = new Planner();
		plannerLogic.initializeLogic(null);
		executorLogic = new Executor();
		executorLogic.initializeLogic(null);
		effectorLogic = new SocketIoEffector();
		effectorLogic.initializeLogic(null);


	}
	
	private void initializeAL() {
		
		effector = new AdaptationLogicTemplate("effector", null, InformationCategory.EFFECTOR);
		effector.implementLogic(effectorLogic);
		executor = new AdaptationLogicTemplate("executor", effector, InformationCategory.EXECUTOR);
		executor.implementLogic(executorLogic);
		planner = new AdaptationLogicTemplate("planner", executor, InformationCategory.PLANNER);
		planner.implementLogic(plannerLogic);
		analyzer = new AdaptationLogicTemplate("analyzer", planner, InformationCategory.ANALYZER);
		analyzer.implementLogic(analyzerLogic);
		monitor = new AdaptationLogicTemplate("monitor", analyzer, InformationCategory.MONITOR);
		monitor.implementLogic(monitorLogic);
		sensor = new AdaptationLogicTemplate("sensor", monitor, InformationCategory.SENSOR);
		sensor.implementLogic(sensorLogic);


	}
	
	
	private void runTest(AdaptationLogicTemplate startingPoint) {
		
		for(int i = 0; i < 1; i++) {
			for (int j = 0; j < testData.size(); j++) {
				PseudoLogger.log(getClass().getName(), "*******************************************************************", null);
				PseudoLogger.log(getClass().getName(), "Run : " + j, "");
				PseudoLogger.log(getClass().getName(), "Data : " + testData.get(j).getData().toString(), null);
				startingPoint.start(testData.get(j));
				
				try {
					Thread.sleep(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
