package com.example.theicecreamy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrders;
    private OrdersAdapter adapter;
    private List<Order> ordersList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        // Initialize RecyclerView
        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        // Initialize order list and adapter
        ordersList = new ArrayList<>();
        adapter = new OrdersAdapter(ordersList);
        recyclerViewOrders.setAdapter(adapter);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Fetch data from Firestore
        fetchOrdersFromDatabase();
    }

    private void fetchOrdersFromDatabase() {
        db.collection("orders")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ordersList.clear(); // Clear old data
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Map Firestore data to Order object
                            String name = document.getString("name");
                            String item = document.getString("item");
                            long quantity = document.getLong("quantity");
                            double price = document.getDouble("price");

                            // Create Order object
                            Order order = new Order(name, item, (int) quantity, price);
                            ordersList.add(order);
                        }
                        // Notify adapter about data changes
                        adapter.notifyDataSetChanged();
                    } else {
                        // Handle errors
                    }
                });
    }
}
