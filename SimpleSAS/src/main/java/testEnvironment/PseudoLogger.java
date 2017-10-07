package testEnvironment;

public class PseudoLogger {

	public static void log(String className, String methodName, String msg) {
		System.out.println(className + " - " + methodName + " : " + msg);
	}
}
