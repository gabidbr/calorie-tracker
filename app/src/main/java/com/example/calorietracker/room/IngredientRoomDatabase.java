package com.example.calorietracker.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.calorietracker.room.dao.IngredientDao;
import com.example.calorietracker.room.entity.Ingredient;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ingredient.class}, version = 1, exportSchema = false)
public abstract class IngredientRoomDatabase extends RoomDatabase {
    public abstract IngredientDao ingredientDao();

    private static volatile IngredientRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static IngredientRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (IngredientRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IngredientRoomDatabase.class, "ingredient_room_database1")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                IngredientDao dao = INSTANCE.ingredientDao();
                dao.deleteAll();

                Ingredient ingredient = new Ingredient("I1","patrunjel",200, "U1", Calendar.getInstance().getTime());
                dao.insert(ingredient);
                ingredient = new Ingredient("I2","marar",210,"U2", Calendar.getInstance().getTime());
                dao.insert(ingredient);
            });
        }
    };
}
