package com.smartpullup.smartpullup;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DataTransmissionService";

    //final int handlerState = 0;                        //used to identify handler message
    //private BluetoothAdapter btAdapter = null;
    //private BluetoothSocket btSocket = null;

    //private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    //private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    //private static String address = "98:D3:36:81:05:F3";

    @SuppressLint("HandlerLeak")
    private FirebaseAuth mAuth;

    public final ExerciseFragment ExerciseFragment = new ExerciseFragment();

    //TextView pullUp_TextView = null;

    //private JSONBroadcastReceiver JSONBroadcastReceiver;
    //Handler bluetoothIn;

    private JSONBroadcastReceiver JSONBroadcastReceiver;
    private String JSONStructureInput = "";
    JSONObject JSONInputData = null;

    private String typeJsonData = "";
    private int machine_ID_JsonData = 0;
    public int upJsonData = 0;
    private int downJsonData = 0;
    private int weightJsonData = 0;


    private SectionsPagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        //navigation
        mSectionsStatePagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager =(ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.bottomNav);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_podium);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_person);

        // get Bluetooth adapter
        //btAdapter = BluetoothAdapter.getDefaultAdapter();
        //checkBTState();

        //Start BroadcastReceiver
        JSONBroadcastReceiver = new JSONBroadcastReceiver();

        //register BroadcastReceiver
        IntentFilter intentFilter = new IntentFilter(BTReceiverService.ACTION_MyIntentService);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(JSONBroadcastReceiver, intentFilter);

//        //Toast.makeText(this, upJsonData,Toast.LENGTH_SHORT).show();
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.activity_main, this, false);
        //TextView textView = (TextView) findViewById(R.id.pullUpCounter_textView);
        //textView.setText("test");


        //typeJsonData = JSONInputData.getString("type");
        Log.i(TAG, "type= " + typeJsonData);
        //machine_ID_JsonData = JSONInputData.getInt("machine_ID");
        Log.i(TAG, "machine ID= " + machine_ID_JsonData);
        //upJsonData = JSONInputData.getInt("up");
        Log.i(TAG, "up= " + upJsonData);
        //downJsonData = JSONInputData.getInt("down");
        Log.i(TAG, "down= " + downJsonData);
        //weightJsonData = JSONInputData.getInt("weight");
        Log.i(TAG, "weight= " + weightJsonData);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExerciseFragment());
        adapter.addFragment(new LeaderboardFragment());
        adapter.addFragment(new ProfileFragment());
        viewPager.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(JSONBroadcastReceiver);
    }

    public void ConnectToBar(View view) {
        Intent intentConnect = new Intent(MainActivity.this, ConnectBarActivity.class);
        startActivity(intentConnect);
    }

    public class JSONBroadcastReceiver extends BroadcastReceiver {

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

//    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
//
//        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
//        //creates secure outgoing connecetion with BT device using UUID
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        //Get MAC address from DeviceListActivity via intent
//        //Intent intent = getIntent();
//
//        //Get the MAC address from the DeviceListActivty via EXTRA
//        //address = intent.getStringExtra(ConnectBarActivity.EXTRA_DEVICE_ADDRESS);
//
//        //create device and set the MAC address
//        BluetoothDevice device = btAdapter.getRemoteDevice(address);
//
//        try {
//            btSocket = createBluetoothSocket(device);
//        } catch (IOException e) {
//            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
//        }
//        // Establish the Bluetooth socket connection.
//        try
//        {
//            btSocket.connect();
//        } catch (IOException e) {
//            try
//            {
//                btSocket.close();
//            } catch (IOException e2)
//            {
//                //insert code to deal with this
//            }
//        }
//        mConnectedThread = new ConnectedThread(btSocket, bluetoothIn, MainActivity.this);
//        mConnectedThread.start();
//
//        //I send a character when resuming.beginning transmission to check device is connected
//        //If it is not an exception will be thrown in the write method and finish() will be called
//        mConnectedThread.write("x");
//    }

    @Override
    public void onPause()
    {
        super.onPause();
//        try
//        {
//            //Don't leave Bluetooth sockets open when leaving activity
//            btSocket.close();
//        } catch (IOException e2) {
//            //insert code to deal with this
//        }
        Intent MyIntentService = new Intent(this, BTReceiverService.class);
        stopService(MyIntentService );
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
//    private void checkBTState() {
//
//        if(btAdapter==null) {
//            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
//        } else {
//            if (btAdapter.isEnabled()) {
//            } else {
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, 1);
//            }
//        }
//    }
}
