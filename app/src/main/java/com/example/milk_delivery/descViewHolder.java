package com.example.milk_delivery;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class descViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView value,name,desc;

    private ItemClickListener itemClickListener;

    public descViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.desc);
        value = itemView.findViewById(R.id.value);
        desc = itemView.findViewById(R.id.description);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
