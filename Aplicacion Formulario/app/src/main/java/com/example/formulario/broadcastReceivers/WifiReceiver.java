package com.example.formulario.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WifiReceiver extends BroadcastReceiver {
    private static final String TAG = "WifiReceiver";

    //en funcion si esta conectada a la wifi o no, imprima un log
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo(); //contiene info de la red

        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            if (networkInfo.isConnected()) {
                Log.d(TAG, "WiFi conectada");
            } else {
                Log.d(TAG, "WiFi desconectada");
            }
        } else {
            Log.d(TAG, "WiFi desconectada (sin red activa)"); //si no recibe la network info
        }
    }
}
