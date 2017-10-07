package de.mannheim.wifo2.fesas;

import java.io.File;
import java.io.IOException;

import de.mannheim.wifo2.fesas.mwImplBase.FesasCentralInstance;
import de.mannheim.wifo2.fesas.ui.FesasGUI;
import jodd.util.ClassLoaderUtil;

public class FesasWrapper {

	//	1) Remote Repository Config File ersetzen
	//	2) FESAS Settings Config File ersetzen
	//	3) Jar bauen (Runtime Arguments!)
	//	4) LogicElements einbauen
	//	5) Testen

	public static void main(String args[]) {
		
//		boolean useUi = true;
	
		
		//		 ClassLoader loader = initClassLoader();
		//		 try {
		//			Class<?> startClass = loader.loadClass("de.mannheim.wifo2.fesas.mwImplBase.FesasCentralInstance");
		//			Runnable mpClass = (Runnable) startClass.newInstance();
		//			if (mpClass instanceof FesasCentralInstance) {
		//				((FesasCentralInstance) mpClass).
		//			}
		//			mpClass.setArgs()
		//		 } catch (ClassNotFoundException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (InstantiationException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (IllegalAccessException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		link: http://stackoverflow.com/questions/5809139/custom-urlclassloader-noclassdeffounderror-when-run


		//		Properties props = System.getProperties();
		//		props.setProperty("java.system.class.loader", 
		//				"de.mannheim.wifo2.fesas.tools.classloader.MyClassLoader");

//		System.out.println(ClassLoader.getSystemClassLoader());
		File repositoryFolder = getRepositoryFolder();
		emptyRepositoryFolder(repositoryFolder);
		initClassLoader(repositoryFolder);

//		if (args.length == 0) {
//			args = getProps
//		} else {
//			args = getProps(args[0]);
//		}
		
		args = getProps();
		
		//create a local instance of the object and export the service
		if (FesasWraperProperties.USE_GUI == true) {
			FesasGUI insertLogicConsole = new FesasGUI(FesasWraperProperties.SCENARIO_NAME);
			insertLogicConsole.setVisible(true);
			insertLogicConsole.startUI();
		}
		
		System.out.println("Load FESAS Wrapper");
		String classpath = System.getProperty("java.class.path");
		System.out.println("Class path : " + classpath);
		
		FesasCentralInstance.main(args);
	}


	private static boolean emptyRepositoryFolder(File repositoryFolder) {
//		File repositoryFolderFile = new File(repositoryFolder);
		if (repositoryFolder.exists()) {
			repositoryFolder.delete();
		}
		boolean result = repositoryFolder.mkdir();
		return result;
	}


	private static void initClassLoader(File moduleFolder) {
		
		ClassLoaderUtil.addFileToClassPath(moduleFolder,ClassLoader.getSystemClassLoader());
		
//		File moduleDirectory = new File(moduleFolder);
//		String classpath = System.getProperty("java.class.path");
//		System.out.println("Class path : " + classpath);
//		
//		classpath += ";" + moduleDirectory;
//		System.setProperty("java.class.path",classpath);
		
//		URLClassLoader classLoader = 
//				(URLClassLoader) ClassLoader.getSystemClassLoader();
//		Class<URLClassLoader> clazz= URLClassLoader.class;

		// Use reflection
		
//		Method method;
//		try {
//			method = clazz.getDeclaredMethod("addURL", new Class[] { URL.class });
//			method.setAccessible(true);
//			method.invoke(classLoader, new Object[] { moduleDirectory.toURI().toURL() });
//		} catch (NoSuchMethodException | SecurityException | 
//				IllegalAccessException | MalformedURLException | 
//				IllegalArgumentException | InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		
		
//		File[] moduleFiles = moduleDirectory.listFiles();
//		   for (int i = 0; i < moduleFiles.length; i++){
//		      File moduleFile = driverFiles[i];
//		         if (moduleFile.getName().endsWith(".jar")){
//		            if(classpath.indexOf(moduleFiles[i].getName()) == -1){
//		            try{
//		            addFile(moduleFiles[i]);
//		            }catch(IOException e){}
//		         }
//		         }else{
//		         moduleFile.delete();
//		         }
//		      }
//		   }

	}



	private static String[] getProps() {
		
		FesasWraperProperties.getInstance().initializeProps();
		
		//		System.out.println("Class Loader in main: " +
		//				FesasWrapper.class.getClassLoader());
		//		
		//		System.out.println("System Class Loader in main: " +
		//				ClassLoader.getSystemClassLoader());

		//2 m�glichkeiten: args �ber Kommandozeile vs. args �ber config file

		/*
		 * args[0] = DEVICE_ID;
		 * args[1] = AL config file
		 * args{2] = communication config file
		 * args[3] = scenario name (= folder for config and log files)
		 * args[4] = delay before initialization of AL in ms
		 * args[5] = use of ALM -> load ALM proxy
		 */
		String[] args = new String[8];
		args[0] = FesasWraperProperties.DEVICE_ID;
		args[1] = FesasWraperProperties.AL_CONFIG_FILE;
		args[2] = FesasWraperProperties.COMMUNICATION_CONFIG_FILE;
		args[3] = FesasWraperProperties.SCENARIO_NAME;
		args[4] = FesasWraperProperties.DELAY;
		args[5] = FesasWraperProperties.USE_ALM;
		args[6] = "" + FesasWraperProperties.USE_GUI;
		args[7] = "" + FesasWraperProperties.ONE_THREADED;

		return args;
	}

	private static File getRepositoryFolder() {
		File current = new File(".");
		//		System.out.println("Classloader in FesasWrapper: " + current.getClass().getClassLoader());
		File repositoryFolder;
		try {
			repositoryFolder = new File(current.getCanonicalPath() + File.separator + "FESASRepository");
			return repositoryFolder;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
