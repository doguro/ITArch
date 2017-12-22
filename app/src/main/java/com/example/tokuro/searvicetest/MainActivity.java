package com.example.tokuro.searvicetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private static final String t = "LifeCycle";
    private EditText ResultText;
    private ICharges mService;
    private IChargeCallback mCallback = new IChargeCallback.Stub() {
        @Override
        public void ResultSum(final int value) throws RemoteException {

            ResultText.setText("課金合計額" + value + "円");


        }
    };



    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ICharges.Stub.asInterface(service);
            try {
                mService.registerCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    };

    //¥5000円チャージボタン
    public class ButtonClick implements View.OnClickListener{

        public void onClick(View v){
            try {
                mService.add(5000);

            }catch(RemoteException e) {
                Log.d("log", "re");
            }catch(NullPointerException e){
                Log.d("log", "nu");
            }

            try {
                mService.update();
            }catch(RemoteException e) {
                Log.d("log", "Re");
            }catch(NullPointerException e){
                Log.d("log", "Nu");
            }
/*
            try {
                ResultText.setText("課金合計額" + mService.resultsum() + "円");
            }catch(RemoteException e) {
                Log.d("log", "Re");
            }catch(NullPointerException e){
                Log.d("log", "Nu");
            }*/

        }
    }

    //¥10000円チャージボタン
    public class ButtonClick2 implements View.OnClickListener{

        public void onClick(View v){
            try {
                mService.add(10000);
                mService.update();
            }catch(RemoteException e) {
                e.printStackTrace();
            }

            /*
            try {
                ResultText.setText("課金合計額" + mService.resultsum() + "円");
            }catch(RemoteException e) {
                e.printStackTrace();
            }*/
        }
    }

    //おみくじ画面への遷移
    public class ButtonClick3 implements View.OnClickListener{

        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, Omikuji.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResultText = (EditText)findViewById(R.id.textView);
        Button btn = (Button)findViewById(R.id.button);
        Button btn2 = (Button)findViewById(R.id.button2);
        Button btn3 = (Button)findViewById(R.id.button3);
        btn.setOnClickListener(new ButtonClick());
        btn2.setOnClickListener(new ButtonClick2());
        btn3.setOnClickListener(new ButtonClick3());

        startService(new Intent(this,MyService.class));
        bindService(new Intent(this,MyService.class),
                mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(t,"onStart()");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(t,"onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(t,"onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(t,"onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(t,"onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(t,"onDestroy()");
        if (mService != null) {
            unbindService(mServiceConnection);
        }
    }
}
