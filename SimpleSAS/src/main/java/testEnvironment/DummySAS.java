package testEnvironment;

public class DummySAS {
	
	private static DummySAS instance;

	/*
	 * This class can be generated with the FESAS menu
	 */
	private DummySAS() {

	}

	public static DummySAS getInstance() {
		if(instance == null) {
			instance = new DummySAS();
		}
		return instance;
	}
	
	public void start() {
		
	}
}
