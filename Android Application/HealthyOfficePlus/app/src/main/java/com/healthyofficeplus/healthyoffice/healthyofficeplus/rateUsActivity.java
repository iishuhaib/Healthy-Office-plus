package com.healthyofficeplus.healthyoffice.healthyofficeplus;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class rateUsActivity extends AppCompatActivity {
    RatingBar rb;
    TextView value;
    private Button sendRating;
    private String rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        rb = (RatingBar) findViewById(R.id.ratingBar);
        value = (TextView) findViewById(R.id.valueTextView);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                rate = String.valueOf(rating);
                value.setText("Value : " + rating);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("feedback");

        sendRating = (Button) findViewById(R.id.button12);
        sendRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(rateUsActivity.this, "Please wait...", "Proccessing...", true);

                Map<String, String> userData = new HashMap<String, String>();
                userData.put("Rate", rate);

                myRef.push().setValue(userData);
                progressDialog.dismiss();
                Toast.makeText(rateUsActivity.this, "Rating Recorded", Toast.LENGTH_LONG).show();
            }
        });
    }
}
