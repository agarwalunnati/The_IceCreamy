package com.example.theicecreamy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder> {

    private List<FoodItem> foodItemList;
    private Context context;

    public FoodItemAdapter(List<FoodItem> foodItemList, Context context) {
        this.foodItemList = foodItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_food, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        FoodItem foodItem = foodItemList.get(position);
        holder.tvName.setText(foodItem.getName());
        holder.tvPrice.setText("Price: $" + foodItem.getPrice());
        holder.tvDescription.setText(foodItem.getDescription());

        holder.itemView.setOnClickListener(v ->
                Toast.makeText(context, "Clicked: " + foodItem.getName(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public static class FoodItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvDescription;

        public FoodItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvFoodName);
            tvPrice = itemView.findViewById(R.id.tvFoodPrice);
            tvDescription = itemView.findViewById(R.id.tvFoodDescription);
        }
    }
}
