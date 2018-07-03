package com.ci;

public class Base {
	
	private static String javaPath = "C:\\Program Files (x86)\\Java\\jdk1.8.0_65\\bin" + "\\java.exe";
	private static String jarPath = getProjectLocation()+"\\SeleniumDriver.jar";
	private static String runId = "0";
	private static String runName = "ArtemisCI";
	private static String dataBase = "Access";
	private static String tool = "Selenium";
    private static InstanceID InstanceID = new InstanceID();
	
	public int runTest(String instanceId,String browserName) {
		
		System.out.println("Artemis CI: Java location is hardcoded in this project, current location is "+javaPath+". Please modify if needed!");
		String executeCmd = "\""+javaPath+"\""+" -jar "+"\""+jarPath+"\""+" "+runId+" "+runName+" "+instanceId+" "+dataBase+" "+tool+" "+browserName;
		int retValue=-1;
			try {
				InstanceID.addInstanceID(instanceId);
				retValue = ProcessHandler.runCmd(executeCmd);
			}
			catch(Exception e) {
				retValue=1;
			}
		return retValue;
	}
	
	public static String getProjectLocation() {
		try {			
			String PackageLocation = Base.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			
			String[] PackageLocationSplit = PackageLocation.split("/");
			
			String CodePath = "";
			
			if (!PackageLocation.toLowerCase().contains(".jar"))
				for (int i = 1;i<PackageLocationSplit.length-3;i++)
					CodePath = CodePath + PackageLocationSplit[i] + "/";
			else				
				for (int i = 1;i<PackageLocationSplit.length-1;i++)
					CodePath = CodePath + PackageLocationSplit[i] + "/";
			return CodePath.trim();		
		} catch (Exception e) {
			System.out.println("Error while fetching the project location, Reason: "+e.getMessage());
			return null;
		}
	}
}
