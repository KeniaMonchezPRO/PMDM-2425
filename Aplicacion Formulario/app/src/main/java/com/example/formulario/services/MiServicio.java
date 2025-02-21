package com.example.formulario.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MiServicio extends Service {
    private boolean isRunning = false;
    private Thread thread;
    //este servicio hace que que c/x tiempo vaya escribiendo en el log

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MeuServizo", "Creouse o servizo");
    }

    //va la logica del sericio
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isRunning) {
            isRunning = true;
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //imprime en el log cat cuando pasan 30s vuelve a hacerlo otra vez
                    //no hay interaccion del usuario para iniciarlo aunq podria hacerse
                    while (isRunning) {
                        Log.d("MeuServizo", "Aquí seguimos...");
                        try {
                            Thread.sleep(30000); // Agarda 30 segundos
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }
        return START_STICKY; // Manten o servizo en execución
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Log.d("MeuServizo", "Servizo destruido");
    }

    // Se se precisara comunicar con algunha Activity é decir se require interaccion con usuario
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
