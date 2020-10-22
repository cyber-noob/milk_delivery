package com.example.milk_delivery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

//Product description page (When the user clicks onto different products on the
// home page it takes them to product attributes page)[model class: descModel, ViewHolder: descViewHolder]

public class description extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseRecyclerAdapter<descModel, descViewHolder> adapter1;
    String Id = "";
    String image = "";
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdescriptor);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("description");

        recyclerView = findViewById(R.id.productRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        if(getIntent()!=null){
            Id = getIntent().getStringExtra("value");
            image = getIntent().getStringExtra("image");
            name =  getIntent().getStringExtra("name");
        }
        if(!Id.isEmpty() && Id!=null){
            loadDesc(Id,image,name);
        }
    }

    private void loadDesc(String Id, final String image, final String name) {

        FirebaseRecyclerOptions<descModel> options =
                new FirebaseRecyclerOptions.Builder<descModel>()
                        .setQuery(reference.child(Id), descModel.class)
                        .build();

        adapter1 = new FirebaseRecyclerAdapter<descModel, descViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull descViewHolder holder, int position, @NonNull descModel model) {

                Log.d("txt :", model.getValue());
                holder.value.setText(model.getValue());
                holder.name.setText(model.getName());
                holder.desc.setText(model.getDesc());

                TextView Name = findViewById(R.id.descName);
                Name.setText(name);

                final descModel clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast toast = Toast.makeText(description.this,""+clickItem.getName(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }

            @NonNull
            @Override
            public descViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.description,parent,false);
                return new descViewHolder(view);
            }
        };

        adapter1.startListening();
        recyclerView.setAdapter(adapter1);
    }
}