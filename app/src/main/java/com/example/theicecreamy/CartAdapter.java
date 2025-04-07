package com.example.theicecreamy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theicecreamy.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;

    // Constructor
    public CartAdapter(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cart_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemPrice.setText("$" + item.getPrice());
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    // ViewHolder for individual cart items
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, itemPrice;

        public CartViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.itemName);
            itemPrice = view.findViewById(R.id.itemPrice);
        }
    }
}
