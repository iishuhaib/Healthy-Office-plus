package com.healthyofficeplus.healthyoffice.healthyofficeplus;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class homeActivity extends AppCompatActivity {
    private TextView userEmail;
    private TextView timerDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userEmail = (TextView) findViewById(R.id.userEmailID);
        timerDisplay = (TextView) findViewById(R.id.timer);
        userEmail.setText(getIntent().getExtras().getString("Email"));

        new CountDownTimer(1200000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerDisplay.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                final String title1="Break!";
                String subject1="It is time to take a break";
                String body1="20 minutes have passed";

                timerDisplay.setText("Break Taken");
                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).setContentTitle(title1).setContentText(body1).
                        setContentTitle(subject1).setSmallIcon(R.mipmap.iclauncher).build();

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);
            }
        }.start();
    }

    public void rateusOperation (View v){
        Intent intent = new Intent(homeActivity.this,rateUsActivity.class);
        startActivity(intent);
    }

    public void dailyOffersOperation (View v){
        Intent intent = new Intent(homeActivity.this,dailyOffersActivity.class);
        startActivity(intent);
    }

    public void statisticsOperation (View v){
        Intent intent = new Intent(homeActivity.this,statisticsActivity.class);
        startActivity(intent);
    }

    public void privacyPolicyOperation (View v){
        Intent intent = new Intent(homeActivity.this,privacyPolicyActivity.class);
        startActivity(intent);
    }

    public void logoutOperation (View v){
        AlertDialog.Builder altDial = new AlertDialog.Builder (homeActivity.this);
        altDial.setMessage("Are You Sure ? ").setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(homeActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = altDial.create();
        alert.setTitle("You want to Logout ");
        alert.show();
    }
}
