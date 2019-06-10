package com.aman.androidfoodie.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aman.androidfoodie.Common.Common;
import com.aman.androidfoodie.Interface.ItemClickListener;
import com.aman.androidfoodie.R;

/**
 * Created by Aman on 4/25/2019.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
    public TextView food_name;
    public ImageView food_image;
    private ItemClickListener itemClickListener;
    public FoodViewHolder(View itemView) {
        super(itemView);
        food_name=(TextView)itemView.findViewById(R.id.food_name);
        food_image =itemView.findViewById(R.id.food_image);
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
