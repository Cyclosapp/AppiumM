package utilities;

import java.sql.DriverManager;
import java.sql.ResultSet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import SeleniumPackage.TestAttributes;

public class EnvironmentSetup {
	
	private static WebDriver webDriver;
	
	public WebDriver getWebDriver(String browserName) {
		
		String osType = System.getProperty("os.name");
		String webDriversLocation = TestAttributes.ProjectLocation  + "Web Drivers/";
		
		switch(browserName.trim().toLowerCase()) {
		
		case "firefox":		
			
			if (osType.trim().toLowerCase().contains("windows")) {
				System.setProperty("webdriver.gecko.driver", webDriversLocation  + "geckodriver.exe");
				// Driver initialization code for firefox Windows *************
				webDriver = new FirefoxDriver();
				
			} else { 
				
				// Driver initialization code for firefox Mac or Some other OS *************
				webDriver = new FirefoxDriver();
			}
			
			break;
			
		case "chrome":
			
			if (osType.trim().toLowerCase().contains("windows")) {
				
				// Driver initialization code for firefox Windows *************
				System.setProperty("webdriver.chrome.driver", webDriversLocation  + "chromedriver-win.exe");
				
			} else { 
				
				// Driver initialization code for firefox Mac or Some other OS *************
				System.setProperty("webdriver.chrome.driver", webDriversLocation  + "chromedriver-mac");
			}
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			webDriver = new ChromeDriver(options);
			break;	
			
		case "ie":
			System.setProperty("webdriver.ie.driver", webDriversLocation + "IEDriverServer.exe");					
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();  
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			//webDriver = new InternetExplorerDriver();
			webDriver = new InternetExplorerDriver(ieCapabilities);
			break;
		
		case "safari":
			webDriver = new SafariDriver();
			break;
		}
		
		return webDriver;
		
	}
	
	public String getAutomationApplication(String toolName) {
		String aut;
		if (toolName.trim().equalsIgnoreCase("selenium")) {
			aut = "Web";
		}else if (toolName.trim().equalsIgnoreCase("seetest")) {
			aut = "Mobile";
		}else if (toolName.trim().equalsIgnoreCase("appium")) {
			aut = "Mobile";
		}else {
			aut = null;
		}
		return aut;
	}

	public String getProjectLocation() {
		try {			
			String PackageLocation = EnvironmentSetup.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			
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
			TestAttributes.InitialSetUpErrorMessage = TestAttributes.InitialSetUpErrorMessage + "Error while retrieving the project location. ";
			return null;
		}
	}

	public void createDatabaseConnection(String database) {
		try {
			switch(database.trim().toLowerCase()) {
				case "access":
					
					/*
					 * After Java 1.8 there is no in built support from JAVA to connect Microsoft Access
					 * Hence testers are requested to use some other open source toold to connect MS Access
					 * Here we are gonna use "UCanAccess" library to connect Access
					 */
					
					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
					String ConnectionString = "jdbc:ucanaccess://"  + TestAttributes.ProjectLocation + "BackEnd.accdb";
					TestAttributes.conn = DriverManager.getConnection(ConnectionString);
					
					/*
					 * This is the old code , can be used for JAVA version less than 1.8
					 * TestAttributes.conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + TestAttributes.ProjectLocation + "BackEnd.accdb");
					 */
					
					break;
			}	
			
			TestAttributes.stmtTests = TestAttributes.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			TestAttributes.stmtSteps = TestAttributes.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			TestAttributes.stmtsid = TestAttributes.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			TestAttributes.stmt = TestAttributes.conn.createStatement();			
			TestAttributes.stmtSDS = TestAttributes.conn.createStatement();
			TestAttributes.stmtTDS = TestAttributes.conn.createStatement();
			
		} catch(Exception e) {
			TestAttributes.InitialSetUpErrorMessage = TestAttributes.InitialSetUpErrorMessage + "Error while making the database connection. ";
		}
	}
	
}
