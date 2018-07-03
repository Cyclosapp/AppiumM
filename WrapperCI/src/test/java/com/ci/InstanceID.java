package com.ci;

import java.util.Iterator;
import java.util.LinkedList;

public class InstanceID {
	
	private static LinkedList<String> instanceIDs;
	
	public InstanceID() {
		instanceIDs = new LinkedList<String>(); 
	}

	public String getAllInstanceIDs() {
	
		String allInstanceIDs = "";
		Iterator<String> i = instanceIDs.iterator();
		int firstTime = 0;
		while(i.hasNext()) {
			if(firstTime == 0) {
				allInstanceIDs = i.next();
				firstTime+=1;
			}else {
				allInstanceIDs = allInstanceIDs + "," + i.next();
			}
		}
		return allInstanceIDs;
	}

	public void addInstanceID(String id) {
		instanceIDs.add(id);
	}
}
