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
import com.google.firebase.database.DatabaseError;

public class signInActivity extends AppCompatActivity {
    private static final String TAG = "signinActivity";
    private EditText emailLogin;
    private EditText passwordLogin;
    private FirebaseAuth firebaseAuth;

    private String editTextEmail;
    private String editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailLogin = (EditText) findViewById(R.id.emailTxt);
        passwordLogin = (EditText) findViewById(R.id.passwordTxt);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void confirmationOperation(View v) {
        editTextEmail = emailLogin.getText().toString();
        editTextPassword = passwordLogin.getText().toString();

        if(editTextEmail.equals("") || editTextPassword.equals("")){
            Toast.makeText(signInActivity.this, "Please Enter Username or Password", Toast.LENGTH_LONG).show();
            Log.v(TAG, "Empty Text Field");
        }else {

            final ProgressDialog progressDialog = ProgressDialog.show(signInActivity.this, "Please wait...", "Proccessing...", true);

            (firebaseAuth.signInWithEmailAndPassword(emailLogin.getText().toString(), passwordLogin.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                Toast.makeText(signInActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(signInActivity.this, homeActivity.class);
                                i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                                startActivity(i);
                                finish();
                            } else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(signInActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }
    }

    public void resetOperation(View v){
        Intent intent = new Intent(signInActivity.this,ResetPasswordActivity.class);
        startActivity(intent);
        finish();
    }
}
