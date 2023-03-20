package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "basicChannel";

    private static final int NOTIFY_ID = 101;

    private static NotificationManager notificationManager;

    private static DatabaseHelper databaseHelper;

    private static Cursor userCursor;
    private static SQLiteDatabase db;

    private static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        databaseHelper.create_db();

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("My channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);

            Button notifyBtn = findViewById(R.id.alertBtn);
            Button dialogBtn = findViewById(R.id.dialogBtn);
            Button changeBtn = findViewById(R.id.change_btn);
            Button getBtn = findViewById(R.id.getData);
            Button apiBtn = findViewById(R.id.getApi);

            getBtn.setOnClickListener(view -> onGetDataBtnClick());

            dialogBtn.setOnClickListener(view -> {
                FragmentManager manager = getSupportFragmentManager();
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.show(manager, "myDialog");
            });


            notifyBtn.setOnClickListener(view -> Spend());

            changeBtn.setOnClickListener(view -> startUserActivity(view));

            apiBtn.setOnClickListener(view -> {
                textView = findViewById(R.id.textView);
                textView.setText("Загрузка...");
                new Thread(() -> {
                    try{
                        String content = getContent("https://jsonplaceholder.typicode.com/posts/1");
                        textView.post(() -> textView.setText(content));
                    }
                    catch (IOException ex){
                        textView.post(() -> {
                            textView.setText("Ошибка: " + ex.getMessage());
                            Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                        });
                    }
                }).start();
            });
        }
    }

    public void onGetDataBtnClick() {
        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
        textView = findViewById(R.id.textView);
        textView.setText("");
        while (userCursor.moveToNext()) {
            String id = userCursor.getString(1);
            String address = userCursor.getString(0);

            textView.append("id: " + id + "\naddress: " + address + "\n");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void Spend() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Уведомление")
                        .setContentText("Заявка отправлена");

        Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void startUserActivity(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    private String getContent(String path) throws IOException {
        BufferedReader reader = null;
        InputStream stream = null;
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(path);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            return (buf.toString());
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
        userCursor.close();
    }

}

