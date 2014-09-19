package mnc.beacon.mainservice;

import java.util.HashMap;
import java.util.Map;

import mnc.beacon.beacon.BeaconManager;
import mnc.beacon.server.Http;
import mnc.beacon.survey.kalman;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

public class FingerDataCollectThread extends Thread {
	Handler handler;
	private int index;
	private String fingerNum;
	public double sum;
	public int count = 0;
	public kalman kal;
	public double updat[] = new double[1];
	public double resuldat[] = new double[1];
	double init[] = new double[1];
	BeaconManager beaconManager;
	Http fingerhttp;
	Map fingerdata;
	JSONObject fingerObject ;
	JSONArray fingerobjArray;
	JSONObject obj ;

	public FingerDataCollectThread(int i, String fingerNum) {
		this.index = i;
		this.fingerNum = fingerNum;
		kal = new kalman();
		 fingerhttp = new Http();
		 fingerdata = new HashMap();
		 fingerObject = new JSONObject();
		 fingerobjArray = new JSONArray();
		 obj = new JSONObject();
		 beaconManager = BeaconManager.instance();
		
	}

	public void run() {
		
		StrictMode.ThreadPolicy policy1 = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy1);
		Log.i("testfinger", "fingerdata");
		int fingercount = 0;
		
		init[0] = (double) beaconManager.findBeaconByMajor(index).getRssi();
		
		kal.init(init, 1);

		for (int k = 0; k < 20; ++k) {
			Log.i("testfinger", "fingerdata1");
			Double measuredata = beaconManager.findBeaconByMajor(index)
					.getRssi();

			updat[0] = measuredata.doubleValue();
			kal.update(updat, 2);
			Double kalmeasuredata = (double) kal.getCorrectedValues().clone()[0];
			sum = sum + kalmeasuredata;
			++fingercount;
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		Double avdat = (double) (sum / (double) fingercount);

		obj.put("PNUM", fingerNum + Integer.toString(index));
		obj.put("MAJOR", index);
		obj.put("RSSI", avdat.toString());

		fingerdata.put("fingerdata", obj);
		String fingerresult = fingerhttp.get(
				"http://164.125.34.173:8080/finger.jsp", fingerdata);

	}

}
