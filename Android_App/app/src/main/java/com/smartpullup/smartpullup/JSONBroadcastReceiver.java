package com.smartpullup.smartpullup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jorren on 16/03/2018.
 */

public class JSONBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "JSONBroadcastReceiver";


    private String JSONStructureInput = "";
    JSONObject JSONInputData = null;
    JSONObject jsonObj = null;
    String jsonData = null;

    private String typeJsonData = null;
    private int machine_ID_JsonData;
    private int upJsonData;
    private int downJsonData;
    private int weightJsonData;

    Handler bluetoothIn;
    final int handlerState = 0;//used to identify handler message
    private StringBuilder recDataString = new StringBuilder();

    //TextView txtStringLength, sensorView0, upView, downView;


    public JSONBroadcastReceiver(Handler bluetoothIn) {
        this.bluetoothIn =new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {              //if string is what we want
                    String readMessage = (String)msg.obj;                                 // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                      //keep appending to string until }
                    int endOfLineIndex = recDataString.indexOf("}")+1;                    // determine the end-of-line and add the last }
                    if (endOfLineIndex > 0) {                                           // make sure there data before }
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        //int dataLength = dataInPrint.length();                          //get length of data received
                        //txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '{')                             //if it starts with { we know it is what we are looking for
                        {
                            jsonData = dataInPrint;             //get sensor value from string
                            try {

                                jsonObj = new JSONObject(jsonData);

                                String up = jsonObj.getString("up");

                                Log.d("Up data", up);

                                Log.d("Pull up bar", jsonObj.toString());

                            } catch (Throwable t) {
                                Log.e("Pull up bar", "Could not parse malformed JSON: \"" + jsonData + "\"");
                            }

                            //sensorView0.setText(jsonData);    //update the textviews with sensor values

                                /*try {
                                    upView.setText("Up count = " + jsonObj.getString("up"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    downView.setText("Down count = " + jsonObj.getString("down"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/

                        }
                        recDataString.delete(0, recDataString.length());                   //clear all string data

                        dataInPrint = " ";

                    }
                }
            }
        };
        String typeJsonData = null;
        int machine_ID_JsonData = 0;
        int upJsonData = 0;
        int downJsonData = 0;
        int weightJsonData = 0;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        JSONStructureInput = intent.getStringExtra(BTReceiverService.EXTRA_KEY_OUT);
//            Log.i(TAG, JSONStructureInput);
        //Making JSON obj from input string
        try {
            JSONInputData = new JSONObject(JSONStructureInput);

            typeJsonData = JSONInputData.getString("type");
            Log.i(TAG, "type= " + typeJsonData);
            machine_ID_JsonData = JSONInputData.getInt("machine_ID");
            Log.i(TAG, "machine ID= " + machine_ID_JsonData);
            upJsonData = JSONInputData.getInt("up");
            Log.i(TAG, "up= " + upJsonData);
            downJsonData = JSONInputData.getInt("down");
            Log.i(TAG, "down= " + downJsonData);
            weightJsonData = JSONInputData.getInt("weight");
            Log.i(TAG, "weight= " + weightJsonData);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
