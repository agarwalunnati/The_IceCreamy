package com.example.theicecreamy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class cart extends AppCompatActivity {

    private TextView itemQuantity1, itemQuantity2, totalPrice;
    private int quantity1 = 1, quantity2 = 1;
    private int price1 = 30, price2 = 50; // Prices of items
    private int totalAmount = price1 + price2; // Initial total price

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize Views
        itemQuantity1 = findViewById(R.id.item_quantity_1);
        itemQuantity2 = findViewById(R.id.item_quantity_2);
        totalPrice = findViewById(R.id.total_price);

        Button increment1 = findViewById(R.id.increment_1);
        Button decrement1 = findViewById(R.id.decrement_1);
        Button increment2 = findViewById(R.id.increment_2);
        Button decrement2 = findViewById(R.id.decrement_2);
        Button clearCart = findViewById(R.id.pay_button); // Clear Cart Button

        // Update the total price initially
        updateTotalPrice();

        // Increment and Decrement Listeners for First Item
        increment1.setOnClickListener(v -> {
            quantity1++;
            itemQuantity1.setText(String.valueOf(quantity1));
            updateTotalPrice();
        });

        decrement1.setOnClickListener(v -> {
            if (quantity1 > 1) {
                quantity1--;
                itemQuantity1.setText(String.valueOf(quantity1));
                updateTotalPrice();
            }
        });

        // Increment and Decrement Listeners for Second Item
        increment2.setOnClickListener(v -> {
            quantity2++;
            itemQuantity2.setText(String.valueOf(quantity2));
            updateTotalPrice();
        });

        decrement2.setOnClickListener(v -> {
            if (quantity2 > 1) {
                quantity2--;
                itemQuantity2.setText(String.valueOf(quantity2));
                updateTotalPrice();
            }
        });

        // Clear Cart Button - Reset Quantities and Total Price
        clearCart.setOnClickListener(v -> {
            quantity1 = 1;
            quantity2 = 1;
            itemQuantity1.setText(String.valueOf(quantity1));
            itemQuantity2.setText(String.valueOf(quantity2));
            updateTotalPrice();
        });
    }

    // Method to update the total price
    private void updateTotalPrice() {
        totalAmount = (quantity1 * price1) + (quantity2 * price2);
        totalPrice.setText("Total: ₹" + totalAmount);
    }
}
