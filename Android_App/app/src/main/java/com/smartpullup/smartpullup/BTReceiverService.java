package com.smartpullup.smartpullup;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.UUID;


public class BTReceiverService extends IntentService {

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "DataTransmissionService";

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private InputStream inputStream = null;
    private BluetoothDevice device = null;

    // String for MAC address
    private static String address;

    String inputString = null;


    public BTReceiverService() {
        super("BTReceiverService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.


        cleanup();

        if(intent != null){
            btAdapter = BluetoothAdapter.getDefaultAdapter();
            address = intent.getStringExtra("Command");

            try {
                Log.d(TAG, address);
                device = btAdapter.getRemoteDevice(address);
                Log.d(TAG, "Device bond state : " + device.getBondState());

            } catch (Exception e) {
                Log.e(TAG, "Invalid address: " + e.getMessage());
                return;
            }

            try {
                btSocket = createBluetoothSocket(device);
            } catch (IOException e) {
                Log.e(TAG, "Socket creation failed: " + e.getMessage());
                return;
            }

            try {

                if (!btSocket.isConnected()) {
                    btSocket.connect();
                    Log.d(TAG, "Connected");
                } else {
                    Log.d(TAG, "Already Connected");  //flow never reaches here for any use case
                }

            } catch (IOException e) {
                Log.e(TAG, "btSocket.connect() failed : " + e.getMessage());
                return;
            }

            try {
                inputStream = btSocket.getInputStream();

            } catch (IOException e) {
                Log.e(TAG, "Failed to get input stream:" + e.getMessage());
                return;
            }

            ReceiveData();

            //sendData("test");
            //cleanup();   called in onDestroy()

        }
    }

    @Override
    public void onDestroy(){
        cleanup();
        //notify ui
        super.onDestroy();
    }

    private void cleanup(){

        try {
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to close output stream : " + e.getMessage());
        }

        try {
            if (btSocket != null) {
                btSocket.close();
                btSocket = null;
            }
        }catch (Exception e) {
            Log.e(TAG, "Failed to close connection : " + e.getMessage());
        }

    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice bluetoothDevice) throws IOException {

        try {
            Method m = bluetoothDevice.getClass().getMethod(
                    "createRfcommSocket", new Class[] { int.class });
            btSocket = (BluetoothSocket) m.invoke(bluetoothDevice, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return btSocket;
    }

    private void ReceiveData() {


        byte[] buffer = new byte[256];  // buffer store for the stream
        int bytes; // bytes returned from read()

        try {
            bytes = inputStream.read(buffer);
            inputString = new String(buffer,0,bytes);
            Log.d(TAG, "Receiving : " + inputString);
        } catch (IOException e) {
            Log.e(TAG, "failed to reade " + inputString);
        }
    }


}