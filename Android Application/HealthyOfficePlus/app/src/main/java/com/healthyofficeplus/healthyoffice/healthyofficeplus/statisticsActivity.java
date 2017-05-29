package com.healthyofficeplus.healthyoffice.healthyofficeplus;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class statisticsActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "statisticsActivity";
    //private DatabaseReference mDatabase;
    private TextView breakTaken, breakMissed, percentage;
    private Button deleteButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("IpBBEfCob0c1GaYkvAzog9rVdKn1");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval);

        final ProgressBar breakProgress = (ProgressBar) findViewById(R.id.breakPeriod);
        breakTaken = (TextView) findViewById(R.id.breakTaken);
        breakMissed = (TextView) findViewById(R.id.breakMissed);
        percentage = (TextView) findViewById(R.id.percentage);
        deleteButton = (Button) findViewById(R.id.delete);

        deleteButton.setOnClickListener(statisticsActivity.this);



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer duration = 0;
                Integer count =0;
                Integer time = 0;
                if(dataSnapshot.hasChildren()){
                    for (DataSnapshot child : dataSnapshot.getChildren()){
                        duration = duration + Integer.parseInt(child.getValue(String.class));
                        count++;
                    }
                }
                if (count == 0){
                    percentage.setText("No data");
                    Log.v(TAG, "Empty value");
                }else{
                    time = (int) (100*((double)duration/(double)(count*5)));
                    percentage.setText(time+"%");
                }

                breakProgress.setProgress(time);
                breakTaken.setText("Break Taken: "+String.valueOf(duration+ "Mins "));
                breakMissed.setText("Break Missed: "+String.valueOf(count*5 - duration+ "Mins "));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
                //mNameView.setText("ERROR: Cannot read value");
            }
        });
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder altDial = new AlertDialog.Builder (statisticsActivity.this);
        altDial.setMessage("Do you want to remove all the data from our database? ").setCancelable(true)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myRef.removeValue();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = altDial.create();
        alert.setTitle("Delete all data? ");
        alert.show();

    }
}

