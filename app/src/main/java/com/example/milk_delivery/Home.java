package com.example.milk_delivery;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

//Home Page [model class: homeModel, ViewHolder: modelViewHolder]

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<homeModel, modelViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference homemodel;
    LinearLayout map;
    Button heart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        map = this.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(Home.this,"Click",Toast.LENGTH_LONG);
                toast.show();

                openMap();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        homemodel = database.getReference("products");
        
        loadHome();
    }

    private void loadHome() {

        FirebaseRecyclerOptions<homeModel> options =
                new FirebaseRecyclerOptions.Builder<homeModel>()
                        .setQuery(homemodel, homeModel.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<homeModel, modelViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull modelViewHolder holder, int position, @NonNull homeModel model) {

                holder.name.setText(model.getName());
                holder.price.setText(model.getPrice());
                heart = holder.itemView.findViewById(R.id.heart);
                Picasso.with(getBaseContext()).load(model.getImage()).fit().into(holder.image);
                final homeModel clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isClick) {
                        Toast toast = Toast.makeText(Home.this,""+clickItem.getName(),Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent = new Intent(Home.this,description.class);
                        intent.putExtra("value",adapter.getRef(position).getKey());
                        intent.putExtra("image",clickItem.getImage());
                        intent.putExtra("name",clickItem.getName());
                        startActivity(intent);
                    }
                });
                final Handler mHandler = new Handler();
                heart.setOnClickListener(new View.OnClickListener() {

                    int count = 0;

                    @Override
                    public void onClick(final View view) {

                        boolean pressed = false;
                        if (view.getTag() instanceof Boolean)
                            pressed = (boolean) view.getTag();
                        final boolean newPressed = !pressed;
                        // setTag to store state
                        view.setTag(newPressed);
                        final View vRun = view;
                        Runnable run = new Runnable() {
                            @Override
                            public void run() {
                                vRun.setPressed(newPressed);
                                switch (count%2) {
                                    case 0:
                                        vRun.setBackgroundResource(R.drawable.blue_heart);
                                        count++;
                                        Toast toast = Toast.makeText(Home.this,""+count,Toast.LENGTH_LONG);
                                        toast.show();
                                        break;
                                    case 1:
                                        vRun.setBackgroundResource(R.drawable.heart);
                                        count++;
                                        Toast toast1 = Toast.makeText(Home.this,""+count,Toast.LENGTH_LONG);
                                        toast1.show();
                                        break;
                                }
                            }
                        };
                        mHandler.post(run);
                        //mHandler.postDelayed(run, 5);
                    }
                });
            }

            @NonNull
            @Override
            public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.products, parent, false);

                return new modelViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    private void openMap() {
        Intent intent = new Intent(this,Map.class);
        startActivity(intent);
    }
}
