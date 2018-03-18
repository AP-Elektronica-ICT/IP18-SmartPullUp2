package com.smartpullup.smartpullup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by moham on 18/03/2018.
 */

public class JSONBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "DataTransmissionService";

    public static final String ACTION_MyIntentService = "com.smartpullup.smartpullup.UPDATE";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";

    private String JSONStructureInput = "";
    JSONObject JSONInputData = null;

    public String typeJsonData = "";
    private int machine_ID_JsonData = 0;
    public int upJsonData = 0;
    private int downJsonData = 0;
    private int weightJsonData = 0;

    //MY_PREFS_NAME - a static String variable like:
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    //SharedPreferences getSharedPreferences;



    @Override
    public void onReceive(Context context, Intent intent) {
        JSONStructureInput = intent.getStringExtra(BTReceiverService.EXTRA_KEY_OUT);
//            Log.i(TAG, JSONStructureInput);
        //Making JSON obj from input string
        try {
            JSONInputData = new JSONObject(JSONStructureInput);

            typeJsonData = JSONInputData.getString("type");
            //Log.i(TAG, "type= " + typeJsonData);
            machine_ID_JsonData = JSONInputData.getInt("machine_ID");
            //Log.i(TAG, "machine ID= " + machine_ID_JsonData);
            upJsonData = JSONInputData.getInt("up");
            //Log.i(TAG, "up= " + upJsonData);
            downJsonData = JSONInputData.getInt("down");
            //Log.i(TAG, "down= " + downJsonData);
            weightJsonData = JSONInputData.getInt("weight");
            //Log.i(TAG, "weight= " + weightJsonData);

            SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
            editor.putString("type", typeJsonData);
            editor.putInt("up", upJsonData);
            editor.commit();





        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
