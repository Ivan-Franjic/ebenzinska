package com.example.kv_ivanfranjic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EquipmentActivity extends AppCompatActivity {

    DatabaseReference EquipRef;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        bottomNavigationView.setSelectedItemId(R.id.ic_equipment);
        EquipRef=FirebaseDatabase.getInstance().getReference().child("Oprema");
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_fuel:
                        Intent intent3 = new Intent(EquipmentActivity.this, FuelActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_equipment:

                        break;

                    case R.id.ic_fastfood:
                        Intent intent4 = new Intent(EquipmentActivity.this, FoodActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.ic_cart:
                        Intent intent2 = new Intent(EquipmentActivity.this, CartActivity.class);
                        startActivity(intent2);
                        break;
                }


                return false;
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerOptions<Equipment> options = new FirebaseRecyclerOptions.Builder<Equipment>().setQuery(EquipRef, Equipment.class).build();

        FirebaseRecyclerAdapter<Equipment, MyAdapter2> adapter = new FirebaseRecyclerAdapter<Equipment, MyAdapter2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter2 holder, int position, @NonNull Equipment model) {
                holder.equipproductname.setText(model.getNaziv());
                holder.equipproductprice.setText(model.getCijena());
                Picasso.get().load(model.getSlika()).into(holder.equipproductimg);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(EquipmentActivity.this, EquipmentDetailsActivity.class);
                        intent.putExtra("id", model.getId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyAdapter2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview2, parent, false);
                MyAdapter2 holder=new MyAdapter2(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.search)
        {
            Intent intent2 = new Intent(EquipmentActivity.this, CartActivity.class);
            startActivity(intent2);
        }
        else if(id==R.id.filter)
        {
            Intent intent = new Intent(EquipmentActivity.this, CartActivity.class);
            startActivity(intent);
        }
        return true;
    }

}