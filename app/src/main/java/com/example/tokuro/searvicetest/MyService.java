package com.example.tokuro.searvicetest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {
    int sum;  // 計算回数

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return START_REDELIVER_INTENT;
    }

    private ICharges.Stub mStub = new ICharges.Stub(){
        private RemoteCallbackList<IChargeCallback> mCallbacks =
                new RemoteCallbackList<IChargeCallback>();


        @Override
        public void registerCallback(IChargeCallback callback) throws RemoteException {
            Log.d("log", "registerCallback");
            mCallbacks.register(callback);
            int n = mCallbacks.beginBroadcast();
            mCallbacks.getBroadcastItem(0).ResultSum(sum);
            /*for (int i = 0; i < n; i++) {
                try {
                    mCallbacks.getBroadcastItem(i).ResultSum(sum);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }*/
            mCallbacks.finishBroadcast();
        }

        @Override
        public void unregisterCallback(IChargeCallback callback) throws RemoteException {
            Log.d("log", "UnregisterCallback");
            mCallbacks.unregister(callback);
        }

        @Override
        public void add(int x) throws RemoteException {
            Log.d("log","AddSuccess");
            sum += x;
        }

        @Override
        public int resultsum() throws RemoteException{
            Log.d("log","ResultSuccess");
            return sum;
        }


        @Override
        public void update() throws RemoteException{
            int n = mCallbacks.beginBroadcast();
            mCallbacks.getBroadcastItem(0).ResultSum(sum);
            /*for (int i = 0; i < n; i++) {
                try {
                    mCallbacks.getBroadcastItem(i).ResultSum(sum);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }*/
            mCallbacks.finishBroadcast();
        }

        /*public void basicTypes(int anInt, long aLong, boolean aBoolean,
                               float aFloat, double aDouble, String aString) {
            // Does nothing
        }*/

    };

    public void onCreate(){
        super.onCreate();
        sum = 0;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }



    @Override
    public boolean onUnbind(Intent intent) {
        boolean result = super.onUnbind(intent);
        return result;
    }

}
