//package com.example.calorietracker.room;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import androidx.annotation.NonNull;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//import com.example.calorietracker.room.dao.IngredientDao;
//import com.example.calorietracker.room.dao.UserDao;
//import com.example.calorietracker.room.dao.UserIngredientCrossRefDao;
//import com.example.calorietracker.room.entity.Ingredient;
//import com.example.calorietracker.room.entity.User;
//import com.example.calorietracker.room.entity.UserIngredientCrossRef;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Database(entities = {User.class, Ingredient.class, UserIngredientCrossRef.class}, version = 1, exportSchema = false)
//public abstract class AppDatabase extends RoomDatabase {
//    private static final int NUMBER_OF_THREADS = 4;
//    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
//    private static volatile AppDatabase INSTANCE;
//
//    public abstract UserDao userDao();
//
//    public abstract IngredientDao ingredientDao();
//
//    public abstract UserIngredientCrossRefDao userIngredientCrossRefDao();
//
//    public static AppDatabase getDatabase( Context context ) {
//        if( INSTANCE == null ) {
//            synchronized( AppDatabase.class ) {
//
//                INSTANCE = Room.databaseBuilder( context,
//                        AppDatabase.class, "test_database" )
//                        // TODO: write a migration
//                        .fallbackToDestructiveMigration()
//                        .addCallback( sRoomDatabaseCallback )
//                        .build();
//            }
//        }
//        return INSTANCE;
//    }
//
//    public static void destroyInstance() {
//        INSTANCE = null;
//    }
//
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//
//        @Override
//        public void onOpen( @NonNull SupportSQLiteDatabase db ) {
//            super.onOpen( db );
//            new PopulateDbAsync( INSTANCE ).execute();
//        }
//    };
//
//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private UserDao userDao;
//        private IngredientDao ingredientDao;
//
//        PopulateDbAsync( AppDatabase db ) {
//            userDao = db.userDao();
//            ingredientDao = db.ingredientDao();
//        }
//
//        @Override
//        protected Void doInBackground( final Void... params ) {
//
//            // Add something to the Database every time it is built
//            // Only in the test phase!
//            return null;
//        }
//    }
//}
