package com.example.pantrymanager;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.pantrymanager.database.DatabaseHelper;
import android.util.Log;
import com.example.pantrymanager.model.PantryItem;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);
        db.getWritableDatabase();

        db.addPantryItem("Tomatos", 5, "each", "2026-11-11");
        ArrayList<PantryItem> items = db.getAllPantryItems();

        for(PantryItem item : items) {
            Log.d("PANTRY", item.getName() + " " + item.getQuantity());
        }
        /*
        db.addPantryItem("Tomatos", 5, "each", "2026-11-11");
        */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}