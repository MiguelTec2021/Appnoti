package com.example.notificacioneswear;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.core.app.NotificationCompat;

import com.example.notificacioneswear.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private ActivityMainBinding binding;
    private Button simpleButton;
    private static String id = "test_channel_01";
    int notificationID = 1;

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        simpleButton = binding.simpleButton;

    }

    public void simpleButtonClick(View view){
        Intent viewIntent = new Intent(this, MainActivity2.class);
        viewIntent.putExtra("NotiID", "NotificationID: es" + notificationID);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent,0);

        //Se obtiene una instacia del servicio de notificaciones
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 26){
            Log.d(TAG, "La versión del sdk es 26 o mayor");

            String description = "a descripción del canal de notificaciones";
            int importance = NotificationManager.IMPORTANCE_LOW;
            //Se define el canal de notificaciones
            NotificationChannel channel = new NotificationChannel(id, description, importance);
            manager.createNotificationChannel(channel);

            //Creación de la notificacion

            Notification notification = new Notification.Builder(MainActivity.this, id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Título de la notificación")
                    .setContentText("Contenido de la notificación")
                    .setContentIntent(viewPendingIntent)
                    .setAutoCancel(true)
                    .build();
            Log.d(TAG, "Se creó la notificación");

            //Enviar la notificacion al administrador de notificaciones

            manager.notify(1, notification);

            Log.d(TAG, "Se envio la notificacion al administrador de notificaciones");
        }
        else {
            Log.d(TAG, "La versión es menor de la 26");

            //Creación de la notificación
            Notification notification = new NotificationCompat.Builder(MainActivity.this)
                    .setContentTitle("Título de la notificación")
                    .setContentText("Contenido de la notificación")
                    .setContentIntent(viewPendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();

            //Enivar notificacion al manager
            manager.notify(1, notification);

        }

        startActivity(viewIntent);


    }
}