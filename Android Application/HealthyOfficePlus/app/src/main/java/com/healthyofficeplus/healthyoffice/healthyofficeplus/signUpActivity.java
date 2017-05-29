package com.healthyofficeplus.healthyoffice.healthyofficeplus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUpActivity extends AppCompatActivity {
    private static final String TAG = "signupActivity";
    private EditText emailAddress;
    private EditText password;
    private FirebaseAuth firebaseAuth;

    private String editTextEmail;
    private String editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailAddress = (EditText) findViewById(R.id.emailTxt);
        password = (EditText) findViewById(R.id.passwordTxt);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void registerUserOperation(View v) {
        editTextEmail = emailAddress.getText().toString();
        editTextPassword = password.getText().toString();

        if(editTextEmail.equals("") || editTextPassword.equals("")){
            Toast.makeText(signUpActivity.this, "Please Enter Username or Password", Toast.LENGTH_LONG).show();
            Log.v(TAG, "Empty Text Field");
        }else {
            final ProgressDialog progressDialog = ProgressDialog.show(signUpActivity.this, "Please wait...", "Processing...", true);
            (firebaseAuth.createUserWithEmailAndPassword(emailAddress.getText().toString(), password.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                Toast.makeText(signUpActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(signUpActivity.this, signInActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(signUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
