package com.example.seroya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Checkout extends AppCompatActivity {

    TextView price;
    Button pay;
    ImageButton cart, logout;
    String uid;

    EditText name,address,phno,email,cardno,cardname,date,cvv;

    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        pay= findViewById(R.id.pay);
        price = findViewById(R.id.cost);
        cart= findViewById(R.id.cart);
        logout = findViewById(R.id.logout);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        date=findViewById(R.id.date);
        cardno=findViewById(R.id.cardno);
        phno=findViewById(R.id.phno);
        email=findViewById(R.id.email);
        cardname=findViewById(R.id.cardname);
        cvv=findViewById(R.id.cvv);

        String cost = getIntent().getStringExtra("price");
        price.setText(cost);
        uid = firebaseAuth.getCurrentUser().getUid();
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Checkout.this,Cart.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(Checkout.this,Login.class);
                startActivity(intent);
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(name.getText().toString().isEmpty()){
                    Toast.makeText(Checkout.this,"Please enter your name",Toast.LENGTH_SHORT).show();
                    return;
                } else if (email.getText().toString().isEmpty()) {
                    Toast.makeText(Checkout.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (phno.getText().toString().isEmpty()) {
                    Toast.makeText(Checkout.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (cardno.getText().toString().isEmpty()) {
                    Toast.makeText(Checkout.this, "Please enter your card number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (address.getText().toString().isEmpty()) {
                    Toast.makeText(Checkout.this, "Please enter your address", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (cvv.getText().toString().isEmpty()) {
                    Toast.makeText(Checkout.this, "Please enter the CVV", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (date.getText().toString().isEmpty()) {
                    Toast.makeText(Checkout.this, "Please enter the expiry date", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (cardname.getText().toString().isEmpty()) {
                    Toast.makeText(Checkout.this, "Please enter the card holder's name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!(Patterns.PHONE.matcher(cvv.getText().toString()).matches()) || (cvv.getText().toString().length() != 3))
                {
                    Toast.makeText(Checkout.this, "Please enter 3 digit CVV", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if (!(Patterns.PHONE.matcher(date.getText().toString()).matches()) || (date.getText().toString().length() != 4))
                {
                    Toast.makeText(Checkout.this, "Please enter expiry date in MMYY format", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if (!(Patterns.PHONE.matcher(cardno.getText().toString()).matches()) || (cardno.getText().toString().length() != 16))
                {
                    Toast.makeText(Checkout.this, "Please enter 16 digit card number", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if (!(Patterns.PHONE.matcher(phno.getText().toString()).matches()) || (phno.getText().toString().length() != 10))
                {
                    Toast.makeText(Checkout.this, "Please enter 10 digit phone number", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if (!(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()))
                {
                    Toast.makeText(Checkout.this, "Please enter correct email address", Toast.LENGTH_SHORT).show();
                    return;

                }
                else{
                    FirebaseDatabase.getInstance().getReference("Cart").child(uid).removeValue();
                    Intent fin =new Intent(Checkout.this,Final.class);
                    startActivity(fin);
                }


            }
        });

    }
}