package com.example.unitywifiplugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

public class PluginInstance extends Activity {

    private static Activity unityActivity;
    public static List<ScanResult> resultList;
    public static List<String> resultToString;

    public static void receiveUnityActivity(Activity tActivity){
        unityActivity = tActivity;
    }



    public String scanWifis(Context context) {

        WifiManager wifiManager = (WifiManager)
                context.getSystemService(Context.WIFI_SERVICE);

        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    resultList = wifiManager.getScanResults();
                    //Toast.makeText(unityActivity, result, Toast.LENGTH_LONG).show();
                } else {
                    resultList = wifiManager.getScanResults();
                    //Toast.makeText(unityActivity, result, Toast.LENGTH_LONG).show();
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanReceiver, intentFilter);

        boolean success = wifiManager.startScan();
        if (!success) {
            resultList = wifiManager.getScanResults();
            //Toast.makeText(unityActivity, result, Toast.LENGTH_LONG).show();
        }

        JSONArray json_array= new JSONArray();
        for (ScanResult r: resultList
        ) {
            json_array.put(r.BSSID);
        }

        return json_array.toString();
    }


}
