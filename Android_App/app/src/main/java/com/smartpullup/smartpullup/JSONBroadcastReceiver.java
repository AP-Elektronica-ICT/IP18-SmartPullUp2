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

    private String JSONStructureInput = "";
    JSONObject JSONInputData = null;

    public static final String MY_PREFS_NAME = "DataFromPullUpBar";

    @Override
    public void onReceive(Context context, Intent intent) {
        JSONStructureInput = intent.getStringExtra(BTReceiverService.EXTRA_KEY_OUT);

        Log.i(TAG, "JSONStructureInput= " + JSONStructureInput);

        //Making JSON obj from input string
        try {
            JSONInputData = new JSONObject(JSONStructureInput);

            String typeJsonData = JSONInputData.getString("type");
            //Log.i(TAG, "type= " + typeJsonData);
            int machine_ID_JsonData = JSONInputData.getInt("machine_ID");
            //Log.i(TAG, "machine ID= " + machine_ID_JsonData);
            int upJsonData = JSONInputData.getInt("up");
            Log.i(TAG, "up= " + upJsonData);
            int downJsonData = JSONInputData.getInt("down");
            Log.i(TAG, "down= " + downJsonData);
            double weightJsonData = JSONInputData.getDouble("weight");
            //Log.i(TAG, "weight= " + weightJsonData);

            SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
            editor.putString("type", typeJsonData);
            editor.putInt("machine_ID", machine_ID_JsonData);
            editor.putInt("up", upJsonData);
            editor.putInt("down", downJsonData);
            editor.putInt("weight", (int) weightJsonData);
            editor.commit();
            editor.clear();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
