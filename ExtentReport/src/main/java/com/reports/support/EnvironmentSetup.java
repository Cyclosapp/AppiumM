package com.reports.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EnvironmentSetup {
	
	private static Connection databaseConnection;
	private static Statement statement;
	private static Statement statementResults;

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
			System.out.println("Artemis CI: Error while retrieving the project location");
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
					String ConnectionString = "jdbc:ucanaccess://"  + getProjectLocation() + "BackEnd.accdb";
					databaseConnection = DriverManager.getConnection(ConnectionString);
					
					/*
					 * This is the old code , can be used for JAVA version less than 1.8
					 * TestAttributes.conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + TestAttributes.ProjectLocation + "BackEnd.accdb");
					 */	
					break;
			}			
		} catch(Exception e) {
			System.out.println("Error while making the database connection");
		}
	}
	
	public Statement getStatement() throws SQLException {
		statement = databaseConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		return statement;
	}
	
	public Statement getStatementResults() throws SQLException {
		statementResults = databaseConnection.createStatement();
		return statementResults;
	}
}
