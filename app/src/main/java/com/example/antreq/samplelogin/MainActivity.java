package com.example.antreq.samplelogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private TextView Register;
    private int counter =5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         Name = findViewById(R.id.etName);
         Password = findViewById(R.id.etPassword);
         Info = findViewById(R.id.tvInfo);
         Login = findViewById(R.id.btnLogin);
         Register = findViewById(R.id.tvRegister);

        Info.setText("No. of attempts remaining: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
//            finish();
//            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }

         Login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 validate(Name.getText().toString(),Password.getText().toString());
             }
         });

         Register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                 startActivity(intent);
             }
         });
    }

    private void validate(String userName, String userPassword){

        progressDialog.setMessage("Authenticating ...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter --;

                    Info.setText("No. of attempts remaining: "+counter);
                    if(counter ==0){
                        Login.setEnabled(false);
                    }
                }
            }
        });
    }
}
