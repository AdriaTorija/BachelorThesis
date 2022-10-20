package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView editEmail,editPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting the components
        mAuth= FirebaseAuth.getInstance();
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        Button loginButton = (Button) findViewById(R.id.logbtn);
        TextView forgotPass= (TextView) findViewById(R.id.forgotpass);
        editEmail= (TextView) findViewById(R.id.email);
        editPassword= (TextView) findViewById(R.id.password);
        editEmail.setText("adriatorija@gmail.com");
        editPassword.setText("torija");

        //Forgot Password button
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forgotPassword();
            }
        });
        //Login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
    }

    /**
     * Checks the inputs of email and password
     * Checks the authorization with firebase
     * Makes the login getting the keys.
     */
    private void login(){

        editEmail = (TextView) findViewById(R.id.email);
        editPassword = (TextView) findViewById(R.id.password);

        String email= editEmail.getText().toString().trim();
        String password= editPassword.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

        if (email.isEmpty()){
            editEmail.setError("Email is required!");
            editEmail.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);

            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Enter a valid email!");
            editEmail.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);

            return;
        }
        if (password.isEmpty()){
            editPassword.setError("No password in the field");
            editPassword.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);

            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent =  new Intent(MainActivity.this,Select_SmartContract.class);
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(intent);

                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this,"Failed to login!",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    /**
     * Changes activity to forgotPassword
     */
    private void forgotPassword(){
        startActivity(new Intent(MainActivity.this,ForgotPassword.class));

    }

}

