package mnc.beacon.beacon;

import java.util.*;

import mnc.beacon.mainservice.*;

public class BeaconManager {
	
	public static int weight1;
	public static int weight2;
	public static int weight3;
	public static int weight4;
	public static int weight5;
	public static int weight6;
	public static int weight7;
	public static int weight8;
	public String currentbeacon;
	
	public ArrayList<BeaconPacket> beaconList;
	public static BeaconManager beaconManager;

	public static BeaconManager instance() {
		if (beaconManager == null) {
			beaconManager = new BeaconManager();
		}
		return beaconManager;
	}

	public BeaconManager() {
		beaconList = new ArrayList();
	}

	// for duplicated Beacon removal
	public boolean checkDouble(BeaconPacket object) {
		for (int i = 0; i < beaconList.size(); i++) {
			if (beaconList.get(i).getMajor() == object.getMajor()) {
				beaconList.get(i).BeaconUpdate(object.getRssi(), object.getPower());
				//beaconList.get(i).addTimer();
				
				return true;
			}
			
		}
		return false;
	}

	public BeaconPacket findBeaconByMajor(int major) {
		for (int i = 0; i < beaconList.size(); i++) {
			if (beaconList.get(i).getMajor() == major) {

				return beaconList.get(i);
			}
		}
		return null;

	}
	
	public BeaconPacket findBeaconByMinor(int minor) {
		for (int i = 0; i < beaconList.size(); i++) {
			if (beaconList.get(i).getMinor() == minor) {

				return beaconList.get(i);
			}
		}
		return null;

	}
	
	public void removeRowRssi(){
		
		
		for (int i = 0; i < beaconList.size(); i++) {
			
				beaconList.get(i).reduceTimer();
						
				if(beaconList.get(i).getRssi()<-90){
				beaconList.remove(i);
				}
				
		}
		
			
	}
	
	public String checkCurrentbeacon(){
		currentbeacon=null;
		for (int i = 0; i < beaconList.size(); i++) {
			
			currentbeacon+=" "+beaconList.get(i).getMajor();
			
		}
		return currentbeacon;
	}
	

	
}
