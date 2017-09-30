package com.example.super_singh.btconnect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import static android.R.attr.filter;


public class MainActivity extends AppCompatActivity {

    ImageView I1,I2;
    Button b1, b2, b3;
    private BluetoothAdapter BA;
    ListView lv;
    List<String>listDevices;
    ArrayAdapter<String>myAdapter;
    String[] devices=new String[]{

    };
    int myRequest=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.on);
        b2 = (Button) findViewById(R.id.off);
        b3 = (Button) findViewById(R.id.search);
        lv = (ListView) findViewById(R.id.myList);
        I1= (ImageView) findViewById(R.id.green);
        I2= (ImageView)findViewById(R.id.red);
        //for turning on/off bt
        BA = BluetoothAdapter.getDefaultAdapter();
        if(BA.isEnabled())
        {
            I1.setVisibility(View.VISIBLE);
        }
        //device array item will be added to listDevices
        listDevices=new ArrayList<String>(Arrays.asList(devices));

        //making adapter and attaching it with listDevices
        myAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listDevices);
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View  view, int position, long id)
            {

                Intent i=new Intent(getApplicationContext(),graph.class);
                startActivity(i);
            }
        });


    }


    public void turnOn(View v) {

        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, myRequest);
            Toast.makeText(getBaseContext(),"Turning On",Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Already On", Toast.LENGTH_SHORT).show();
        }

    }
    //for case: deny permission
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == myRequest)
        {
            if (resultCode == 0)
            {
                Toast.makeText(this, "! Bluetooth Access Denied", Toast.LENGTH_SHORT).show();
                I1.setVisibility(View.INVISIBLE);
                I2.setVisibility(View.VISIBLE);
            }
            else
            {
                I1.setVisibility(View.VISIBLE);
                I2.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void turnOff(View v) {
        I2.setVisibility(View.VISIBLE);
        I1.setVisibility(View.INVISIBLE);
        BA.disable();
        myAdapter.clear();
        Toast.makeText(getApplicationContext(), "Turned Off", Toast.LENGTH_SHORT).show();
    }

    public void Search(View v)
    {
        if(BA.isEnabled())
        {
            myAdapter.clear();
            Toast.makeText(getApplicationContext(), "Searching For Devices", Toast.LENGTH_SHORT).show();
            BA.startDiscovery();
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(bReciever, filter);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "! Turn On Your Bluetooth First", Toast.LENGTH_SHORT).show();

        }


    }

    BroadcastReceiver bReciever = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName=device.getName();
                myAdapter.add(deviceName);
            }
        }
    };
    protected void onDestroy() {
        super.onDestroy();

             unregisterReceiver(bReciever);
    }



}
