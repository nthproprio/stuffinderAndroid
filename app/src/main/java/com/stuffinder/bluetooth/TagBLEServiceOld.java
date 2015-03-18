package com.stuffinder.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

//import com.stuffinder.R;
import com.stuffinder.exceptions.TagNotDetectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TagBLEServiceOld extends Activity {


    private String UUID_Tag = "F9:1F:24:D3:1B:D4" ;//adresse du TAG actuel

    private BluetoothAdapter mBluetoothAdapter;
    public static List<BluetoothDevice> mDevices = new ArrayList<BluetoothDevice>();
    private static HashMap<BluetoothDevice,Integer> listRssi = new HashMap<BluetoothDevice, Integer>();
    private static final long SCAN_PERIOD = 10000; //durée d'un scan

    private Boolean presence = false;
    private int puissance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) { //alerte support BLE
            Toast.makeText(this, "BLE NOT SUPPORTED", Toast.LENGTH_SHORT).show();
            finish();
        }

        final BluetoothManager bluetoothManager =   //initialisation du bluetooth manager
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) { //activation du ble sur le terinal
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
        }

        /************ exemple d'utilisation
        attention : il est préférable d'utiliser isconnected() juste avant distance()
        le scan est lancé dans isConnected, les donnés de distance se réfère au dernier scan lancé
        Notez aussi que isconnected prend du temps (10s - la durée du scan)  */
    }

    public boolean isConnected(String id_Tag){ //"F9:1F:24:D3:1B:D4"

        presence = false;

        scanLeDevice();

        for (int i=0; i < mDevices.size(); i++) {
            if (mDevices.get(i).getAddress().equals(id_Tag)) {
                presence = true;
            }
        }

        listRssi.clear();
        mDevices.clear();

        return presence;
    }

    public int distance(String id_Tag) //"F9:1F:24:D3:1B:D4"
            throws TagNotDetectedException {

        int indicePuissance=3;

        if (!presence)
            throw new TagNotDetectedException();

        else {
            for (int i = 0; i < mDevices.size(); i++) {
                if (mDevices.get(i).getAddress().equals(id_Tag)) {
                    puissance = listRssi.get(mDevices.get(i)).intValue();
                }
            }

            if (puissance < 60)
                indicePuissance = 1;

            else if (puissance < 80 && puissance > 59)
                indicePuissance = 2;
        }

        return indicePuissance;
    }

    private void scanLeDevice() { //timer pour le scan
        new Thread() {

            @Override
            public void run() {
                mBluetoothAdapter.startLeScan(mLeScanCallback);

                try {
                    Thread.sleep(SCAN_PERIOD);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }.start();
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {

            if (device != null) {
                if (mDevices.indexOf(device) == -1)
                    mDevices.add(device);
                    listRssi.put(device, new Integer(rssi));

                if (device.getAddress().equals(UUID_Tag)) {
                    presence = true;
                    puissance = rssi;
                }
            }

        }
    };


}
