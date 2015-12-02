package btc.pijama.com.bluetoothclient;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.support.v7.app.ActionBar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Yoav on 11/30/2015.
 */
public class MainActivity extends Activity {

    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeToast("Started");

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            makeToast("Device is not supported");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        //text.setText("Discoverable!!");

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mDevice = device;
                makeToast("Device Paired!" + device.getName());
            }
        }

        ConnectThread mConnectThread = new ConnectThread(mDevice);
        mConnectThread.start();
    }

    private void makeToast(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private BluetoothDevice mmDevice;
        private BluetoothSocket clientSocket;
        private UUID MY_UUID; //= UUID.fromString("da893edf-f1c0-5990-9fcf-5ca6c68741ab"); //"00001101-0000-1000-8000-00805f9b34fb"
        // da893edf-f1c0-5990-9fcf-5ca6c68741ab
        public ConnectThread(BluetoothDevice device) {

            TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String uuid = tManager.getDeviceId();

            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

            Method getUuidsMethod = null;
            try {
                getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);
            } catch (Exception e) {}

            ParcelUuid[] uuids = null;

            try {
                uuids = (ParcelUuid[]) getUuidsMethod.invoke(adapter, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //MY_UUID = uuids[1].getUuid();
            MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            Log.d("LOGGGGG", MY_UUID.toString());

            //   MY_UUID = UUID.fromString(uuid); //"00001101-0000-1000-8000-00805f9b34fb"

           /* MY_UUID = new UUID(
                    new BigInteger(uuid.substring(0, 16), 16).longValue(),
                    new BigInteger(uuid.substring(16), 16).longValue()); */


            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
                Log.d("LOGGGGG", "Communication started");
                makeToast("Communication started");
            } catch (IOException e) {
                Log.d("LOGGGG", "FALIED 1 started");
            }
            mmSocket = tmp;
        }

        @Override
        public void run() {
/*
            //makeToast("Thread created");
            Log.d("LOGGGG", "Thread 1 started");

            //mBluetoothAdapter.cancelDiscovery();
            try {
                Log.d("LOGGGGG", "Before accept");
                clientSocket = mmSocket.accept();
                Log.d("LOGGGGG", "After Accept");
                makeToast("aCCEPTED");
            } catch (IOException connectException) {
                try {
                    mmSocket.close();
                    Log.d("LOGGGG", connectException.toString());
                } catch (IOException closeException) {
                    Log.d("LOGGGG", "Thread finished");
                }
                Log.d("LOGGGG", "Thread finished");
                return;
            }

            ConnectedThread mConnectedThread = new ConnectedThread(clientSocket);
            mConnectedThread.start();
            */
            BluetoothSocket socket = null;
            while (true) {
                try {
                    Log.d("LOGGGG", "before accept");
                    mmSocket.connect();
                    Log.d("LOGGGG", "after accept");
                } catch (IOException e) {
                    continue;
                }
                if (socket != null) {
                    Log.d("LOGGGG", "socket recognized");
                    try {
                        mmSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("LOGGGG", "done");
                    break;
                }
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        @Override
        public void run() {
            makeToast("Started Thread");
            byte[] buffer = new byte[1024];
            int begin = 0;
            int bytes = 0;

            while (true) {
                try {
                    makeToast("Started listenning");
                    bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);
                    makeToast("Recieved Bytes!: " + buffer.toString());
                    for(int i = begin; i < bytes; i++) {
                        if(buffer[i] == "#".getBytes()[0]) {
                            mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                            begin = i + 1;
                            if(i == bytes - 1) {
                                bytes = 0;
                                begin = 0;
                            }
                        }
                    }

                    makeToast("Recieved Bytes!: " + buffer.toString());
                } catch (IOException e) {
                    break;
                }
            }


        }
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }

        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                byte[] writeBuf = (byte[]) msg.obj;
                int begin = (int)msg.arg1;
                int end = (int)msg.arg2;
                makeToast("Handler started");
                switch(msg.what) {
                    case 1:
                        String writeMessage = new String(writeBuf);
                        writeMessage = writeMessage.substring(begin, end);
                        break;
                }
            }
        };

    }

}
