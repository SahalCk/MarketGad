package com.marketgad.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterCartItem extends RecyclerView.Adapter<AdapterCartItem.Holdercartitem>{

    private Context context;
    ArrayList<CartViewActivity> cartitems;

    public AdapterCartItem(Context context, ArrayList<CartViewActivity> cartitems) {
        this.context = context;
        this.cartitems = cartitems;
    }

    @NonNull
    @Override
    public Holdercartitem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_view,parent,false);
        return new Holdercartitem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holdercartitem holder, int position) {
        CartViewActivity modelcartitem = cartitems.get(position);
        String Serialno = modelcartitem.getSerialno();
        String price = modelcartitem.getPrice();

        holder.serialno.setText(""+Serialno);
        holder.price.setText(""+price);

    }

    @Override
    public int getItemCount() {
        return cartitems.size();
    }

    class Holdercartitem extends RecyclerView.ViewHolder{

        private TextView price,serialno;
        private ImageView prodimage;

        public Holdercartitem(@NonNull View itemView) {
            super(itemView);


            price = itemView.findViewById(R.id.price);
            serialno = itemView.findViewById(R.id.serialno);
            prodimage = itemView.findViewById(R.id.prodimage);

        }
    }
}
