package com.example.seroya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    TextView total;
    String userid,subcost;
    FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
    Button checkout;
    ImageButton cart,logout;
    float sum =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userid=firebaseAuth.getCurrentUser().getUid();
        total =findViewById(R.id.subtotvalue);
        checkout = findViewById(R.id.checkout);
        cart= findViewById(R.id.cart);
        logout = findViewById(R.id.logout);
        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child(userid),CartModel.class).build();

        cartAdapter= new CartAdapter(options);
        recyclerView.setAdapter(cartAdapter);

        FirebaseDatabase.getInstance().getReference("Cart").child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sum =0;
                for(DataSnapshot d : snapshot.getChildren()){
                    Map<String,Object> map = (Map<String, Object>) d.getValue();

                    String tp = String.valueOf(map.get("subtotal"));
                    float ftp = Float.parseFloat(tp);
                    sum += ftp;


                }
                subcost= String.format("%.2f",sum);
                total.setText("$"+subcost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sum == 0){
                    Toast.makeText(Cart.this,"Your bag is empty",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(Cart.this,Checkout.class);
                    intent.putExtra("price",subcost);
                    startActivity(intent);
                }

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Cart.this,Cart.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(Cart.this,Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cartAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cartAdapter.stopListening();
    }
}