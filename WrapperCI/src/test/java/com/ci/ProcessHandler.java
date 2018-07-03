package com.ci;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessHandler extends Thread {
    
	InputStream inputStream;
    String streamType;
    
    public ProcessHandler(InputStream inputStream, String streamType) {
        this.inputStream = inputStream;
        this.streamType = streamType;
    }

    public void run() {
        try {
            InputStreamReader inpStrd = new InputStreamReader(inputStream);
            BufferedReader buffRd = new BufferedReader(inpStrd);
            String line = null;
            while ((line = buffRd.readLine()) != null) {
                System.out.println(streamType+ "::" + line);
            }
            buffRd.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int runCmd(String executeCmd) {
    	int retValue=-1;
    	try {
    		
    		/* executing the command with environments set. */
    		Process proc = Runtime.getRuntime().exec(executeCmd);
    		
    		/* handling the streams so that dead lock situation never occurs. */
    		ProcessHandler inputStream = new ProcessHandler(proc.getInputStream(), "Artemis CI");
    		ProcessHandler errorStream = new ProcessHandler(proc.getErrorStream(), "Artemis CI");
    		
    		/* start the stream threads */
    		inputStream.start();
    		errorStream.start();
    		proc.waitFor();
    		retValue=proc.exitValue();
    		proc.destroy();
    	}
    	catch(Exception e) {
    		retValue=1;
    	}
    	return retValue;
    	}
    }

