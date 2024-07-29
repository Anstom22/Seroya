package com.example.seroya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    EditText username,email,password;
    Button signup,backtologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.uname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        signup = findViewById(R.id.signup);
        backtologin = findViewById(R.id.backtologin);

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this,Login.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()){
                    Toast.makeText(Signup.this,"Enter username",Toast.LENGTH_LONG).show();
                }
                else if(email.getText().toString().isEmpty()){
                    Toast.makeText(Signup.this,"Enter email",Toast.LENGTH_LONG).show();
                }
                else if(password.getText().toString().isEmpty()|| password.getText().toString().length()<6){
                    Toast.makeText(Signup.this,"Enter password having 6 or more characters",Toast.LENGTH_LONG).show();
                }
                else if (!(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()))
                {
                    Toast.makeText(Signup.this, "Please enter correct email address", Toast.LENGTH_SHORT).show();
                    return;

                }
                else
                {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener((task)->{
                        if(task.isSuccessful()){
                            Intent intent = new Intent(Signup.this,Login.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}