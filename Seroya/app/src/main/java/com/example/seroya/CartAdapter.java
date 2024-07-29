package com.example.seroya;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartAdapter extends FirebaseRecyclerAdapter<CartModel,CartAdapter.myViewHolder> {
    String uid;

    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CartAdapter(@NonNull FirebaseRecyclerOptions<CartModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CartAdapter.myViewHolder holder, int position, @NonNull CartModel model) {
            holder.name.setText(model.getName());
            holder.price.setText("$"+model.getSubtotal());
            holder.quantity.setText(model.getQuantity());
            holder.quantity.setText(model.getQuantity());

        Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);

        uid = firebaseAuth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Cart").child(uid).child(model.getName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.qt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String quantity = String.valueOf(snapshot.child("quantity").getValue(String.class));
                        int amt =Integer.parseInt(quantity);
                        String st =String.valueOf(snapshot.child("subtotal").getValue(String.class));
                        double sub = Double.parseDouble(st);
                        double tot = sub/amt;
                        amt--;
                        if(amt==0){
                            FirebaseDatabase.getInstance().getReference("Cart").child(uid).child(model.getName()).removeValue();
                        }
                        else{
                            String qt= String.valueOf(amt);

                            double cost = amt*tot;
                            String total = String.format("%.2f",cost);
                            FirebaseDatabase.getInstance().getReference("Cart").child(uid).child(model.getName()).child("quantity").setValue(qt);

                            FirebaseDatabase.getInstance().getReference("Cart").child(uid).child(model.getName()).child("subtotal").setValue(total);

                        }
                    }
                });

                holder.qt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String quantity = String.valueOf(snapshot.child("quantity").getValue(String.class));
                        int amt =Integer.parseInt(quantity);
                        String st =String.valueOf(snapshot.child("subtotal").getValue(String.class));
                        double sub = Double.parseDouble(st);
                        double tot = sub/amt;
                        amt++;
                        String qt= String.valueOf(amt);

                        double cost = amt*tot;
                        String total = String.format("%.2f",cost);
                        FirebaseDatabase.getInstance().getReference("Cart").child(uid).child(model.getName()).child("quantity").setValue(qt);

                        FirebaseDatabase.getInstance().getReference("Cart").child(uid).child(model.getName()).child("subtotal").setValue(total);
                    }
                });

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference("Cart").child(uid).child(model.getName()).removeValue();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    @NonNull
    @Override
    public CartAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlayout,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView quantity,name,price;
        Button qt1,qt2;
        ImageButton delete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img);
            quantity= itemView.findViewById(R.id.qty);
            qt1=itemView.findViewById(R.id.qt1);
            qt2=itemView.findViewById(R.id.qt2);
            price=itemView.findViewById(R.id.price);
            name=itemView.findViewById(R.id.name);
            delete=itemView.findViewById(R.id.delete);

        }
    }
}
