package com.example.pantrymanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import com.example.pantrymanager.model.PantryItem;
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addPantryItem(String name, double quantity, String unit, String expiryDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiry_date", expiryDate);

        db.insert("pantry", null, values);
        db.close();
    }

    public ArrayList<PantryItem> getAllPantryItems() {
        ArrayList<PantryItem> pantryItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pantry", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                double quantity = cursor.getDouble(2);
                String unit = cursor.getString(3);
                String expiryDate = cursor.getString(4);
                PantryItem item = new PantryItem(id, name, quantity, unit, expiryDate);
                pantryItems.add(item);
                } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pantryItems;
    }
    public void updatePantryItem(int id, String name, double quantity, String unit, String expiryDate){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiry_date", expiryDate);

        db.update("pantry", values, "id=?", new String[]{
                        String.valueOf(id)
                });
        db.close();
    }
    public void deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("pantry", "id=?", new String[]{
                        String.valueOf(id)
                });
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createPantryTable =
                "CREATE TABLE pantry (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "quantity REAL NOT NULL," +
                        "unit TEXT," +
                        "expiry_date TEXT)";

        db.execSQL(createPantryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS pantry");

        onCreate(db);
    }
}