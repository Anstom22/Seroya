package com.example.seroya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Details extends AppCompatActivity {
    ImageView img;
    TextView textView2,price2,amount,subtotal,name1;
    Button qty1,qty2,addtocart,checkout;
    String description,price,name,userid,image,tot;
    ImageButton cart, logout;
    int qty =1;
    float total,stotal;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        cart= findViewById(R.id.cart);
        logout = findViewById(R.id.logout);
        img = findViewById(R.id.imageView);
        image = getIntent().getStringExtra("image").toString();
        Glide.with(this).load(image).into(img);
        textView2 = findViewById(R.id.textView2);
        price2=findViewById(R.id.price2);
        description = getIntent().getStringExtra("desc").toString();
        textView2.setText(description);
        name = getIntent().getStringExtra("name").toString();
        name1=findViewById(R.id.name);
        name1.setText(name);
        price=getIntent().getStringExtra("price").toString();
        price2.setText("$"+price);
        qty1=findViewById(R.id.qty1);
        qty2=findViewById(R.id.qty2);
        amount=findViewById(R.id.amount);
        subtotal=findViewById(R.id.subtotal);
        addtocart=findViewById(R.id.addtocart);
        tot= String.valueOf(price);
        subtotal.setText("$"+price);

        qty1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty= Integer.parseInt(amount.getText().toString());
                if(qty==1){
                    Toast.makeText(Details.this,"Quantity should be 1 or more",Toast.LENGTH_SHORT).show();
                }
                else{
                    qty--;
                    amount.setText(String.valueOf(qty));
                    total= Float.parseFloat(price);
                    stotal = qty*total;
                    tot=String.valueOf(stotal);
                    subtotal.setText("$"+tot);
                }

            }
        });

        qty2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty= Integer.parseInt(amount.getText().toString());
                qty++;
                amount.setText(String.valueOf(qty));
                total= Float.parseFloat(price);
                stotal = qty*total;
                tot=String.format("%.2f",stotal);
                subtotal.setText("$"+tot);
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userid= firebaseAuth.getCurrentUser().getUid();
                CartModel cartModel= new CartModel(image,name,tot,amount.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Cart").child(userid).child(name).setValue(cartModel);

                Intent intent= new Intent(Details.this,Cart.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Details.this,Cart.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(Details.this,Login.class);
                startActivity(intent);
            }
        });

    }
}