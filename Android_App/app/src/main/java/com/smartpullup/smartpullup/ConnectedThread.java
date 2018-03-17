package com.smartpullup.smartpullup;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Jorren on 16/03/2018.
 */

public class ConnectedThread extends Thread{
    private static final String TAG = "ConnectedThread";

    private Context context;

    private final InputStream mmInStream;
    private final OutputStream mmOutStream;

    private Handler bluetoothIn;
    final int handlerState = 0;//used to identify handler message

    //creation of the connect thread
    public ConnectedThread(BluetoothSocket socket, Handler bluetoothIn, Context context) {
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        this.bluetoothIn = bluetoothIn;
        this.context = context;

        try {
            //Create I/O streams for connection
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        byte[] buffer = new byte[256];
        int bytes;

        // Keep looping to listen for received messages
        while (true) {
            try {
                bytes = mmInStream.read(buffer);            //read bytes from input buffer
                String readMessage = new String(buffer, 0, bytes);
                // Send the obtained bytes to the UI Activity via handler
                bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
            } catch (IOException e) {
                break;
            }
        }
    }
    //write method
    public void write(String input) {
        byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
        try {
            mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
        } catch (IOException e) {
            //if you cannot write, close the application
            //TODO: this gets called everytime on login, if device is not connected with HC-05... needs to be fixed
            Toast.makeText(context, "Connection Failure", Toast.LENGTH_LONG).show();
            //finish();

        }
    }
}
