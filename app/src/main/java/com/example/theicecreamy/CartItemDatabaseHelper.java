package com.example.theicecreamy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CartItemDatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "cart_items.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public CartItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for cart items
        String CREATE_TABLE = "CREATE TABLE cart_items (id INTEGER PRIMARY KEY, name TEXT, price REAL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle upgrading database if needed (e.g., schema changes)
        db.execSQL("DROP TABLE IF EXISTS cart_items");
        onCreate(db);
    }

    // Add methods for data manipulation (CRUD operations)
    public void addCartItem(CartItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("price", item.getPrice());
        db.insert("cart_items", null, values);
        db.close();
    }

    public List<CartItem> getAllCartItems() {
        List<CartItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("cart_items", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Fetch data using valid indices
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));

                // Add the item to the list
                itemList.add(new CartItem(id, name, price));
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.e("DatabaseError", "Cursor is null or empty.");
        }

        return itemList;
    }
}
