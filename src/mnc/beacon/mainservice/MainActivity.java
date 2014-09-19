package mnc.beacon.mainservice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.xml.sax.Parser;

import mnc.beacon.*;

import mnc.beacon.beacon.*;
import mnc.beacon.ble.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import mnc.beacon.beacon.*;

import mnc.beacon.server.Http;

import mnc.beacon.survey.kalman;

public class MainActivity extends Activity {
	Button beaconNumBtn, fingerbutton;
	EditText coldat, fingertext;
	TextView beaconNumText;
	// EditText
	BeaconPacket beacon;
	BeaconManager beaconManager;

	private Intent intent;
	private Intent intent1;


	public kalman kal;
	public double updat[] = new double[1];
	public double resuldat[] = new double[1];
	double init[] = new double[1];

	

	
	Object obj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_activity);
		
		beaconNumBtn = (Button) findViewById(R.id.beaconNumBtn);
		
		fingerbutton = (Button) findViewById(R.id.fingerbutton);
	
		kal = new kalman();

		Double a;

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		a = 3.0;
		// coldat = (EditText) findViewById(R.id.editText1);
		beaconNumText = (TextView) findViewById(R.id.beaconNumText);
		fingertext = (EditText) findViewById(R.id.fingerText);
		beaconManager = BeaconManager.instance();

		intent = new Intent(this, DeviceScanService.class);
		intent1 = new Intent(this, BeaconListForwardService.class);
		
		startService(intent1);
		startService(intent);


	}

	public void mOnClick(View v) throws IOException {

		switch (v.getId()) {
		case R.id.beaconNumBtn:

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			int num = beaconManager.beaconList.size();
			beaconNumText.setText(" Current Beacon Count  : " + num + " .");


			break;
		

		case R.id.fingerbutton:
			String fingerNum;
			fingerNum = fingertext.getText().toString();

			StrictMode.ThreadPolicy policy1 = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy1);

			for (int i = 0; i < beaconManager.beaconList.size(); ++i) {
				FingerDataCollectThread thread = new FingerDataCollectThread(beaconManager.beaconList.get(i).getMajor(), fingerNum);
				thread.setDaemon(true);
				thread.start();
			}

		
			break;
	
		}
	}
	

	
	}

