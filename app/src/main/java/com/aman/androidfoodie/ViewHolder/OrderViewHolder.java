package com.aman.androidfoodie.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aman.androidfoodie.Interface.ItemClickListener;
import com.aman.androidfoodie.R;

/**
 * Created by Aman on 4/25/2019.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtOrderAddress;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);
        txtOrderAddress=itemView.findViewById(R.id.order_address);
        txtOrderPhone=itemView.findViewById(R.id.order_phone);

        txtOrderStatus=itemView.findViewById(R.id.order_status);

        txtOrderId=itemView.findViewById(R.id.order_id);

itemView.setOnClickListener(this);

    }

    public TextView getTxtOrderId() {
        return txtOrderId;
    }

    public void setTxtOrderId(TextView txtOrderId) {
        this.txtOrderId = txtOrderId;
    }

    public TextView getTxtOrderStatus() {
        return txtOrderStatus;
    }

    public void setTxtOrderStatus(TextView txtOrderStatus) {
        this.txtOrderStatus = txtOrderStatus;
    }

    public TextView getTxtOrderPhone() {
        return txtOrderPhone;
    }

    public void setTxtOrderPhone(TextView txtOrderPhone) {
        this.txtOrderPhone = txtOrderPhone;
    }

    public TextView getTxtOrderAddress() {
        return txtOrderAddress;
    }

    public void setTxtOrderAddress(TextView txtOrderAddress) {
        this.txtOrderAddress = txtOrderAddress;
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
      itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
