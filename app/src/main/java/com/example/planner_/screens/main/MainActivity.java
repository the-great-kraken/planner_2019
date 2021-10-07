package com.example.planner_.screens.main;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.widget.Toolbar;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.util.Log;

import com.example.planner_.NotificationPublisher;
import com.example.planner_.R;
import com.example.planner_.screens.note.NoteEditor;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    private static final String EXTRA_NOTE = "NoteEditor.EXTRA_NOTE";

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            //FragmentContainerView navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_fragment);

            NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
            NavigationView navigationView = findViewById(R.id.nav_view);
            NavigationUI.setupWithNavController(navigationView, navController);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).setDrawerLayout(drawer).build();
            Toolbar toolbar =  findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            Intent intent = getIntent();
            String extra = intent.getStringExtra("Extra");
            //assert extra != null;
            if (extra != null && extra.equals(EXTRA_NOTE)) {
                String textNote = intent.getStringExtra("text");

                Bundle bundle = new Bundle();
                bundle.putString("text", textNote);
                NoteEditor fragobj = new NoteEditor();
                fragobj.setArguments(bundle);
                //navController.navigate(R.id.noteEditor);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.test, fragobj)
                        .addToBackStack(null).commitAllowingStateLoss();

            } else if (extra == "") {
                String txt = intent.getStringExtra("txt");
                String title = intent.getStringExtra("title");
                int delay = intent.getIntExtra("delay", 0);

                scheduleNotification(getNotification(title, txt), delay);
            }

            NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        } catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        NavigationView navigationView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).setDrawerLayout(drawer).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void scheduleNotification(Notification notification, int delay) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotification(String Title, String content) {

        String CHANNEL_ID = "my_channel_01";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
       // NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(Title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setPriority(Notification.PRIORITY_MAX);

        //builder.setChannelId(CHANNEL_ID);
        return builder.build();
    }

}
