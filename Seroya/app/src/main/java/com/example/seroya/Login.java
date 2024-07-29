package com.example.seroya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    EditText email,password;
    Button login,backtoreg;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        backtoreg = findViewById(R.id.backtoreg);


        backtoreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(email.getText().toString().isEmpty()){
                    Toast.makeText(Login.this,"Enter email id",Toast.LENGTH_LONG).show();
                }

               else if(!(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()))
                {
                    Toast.makeText(Login.this,"Enter correct email id",Toast.LENGTH_LONG).show();
                }

                else if(password.getText().toString().isEmpty() || password.getText().toString().length()<6){
                    Toast.makeText(Login.this,"Enter 6 or more character password",Toast.LENGTH_LONG).show();
                }
                else
                {
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener((task)->{
                        if(task.isSuccessful()){
                            Intent intent = new Intent(Login.this,productlist.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Login.this,"Email id or password is wrong",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });




    }
}