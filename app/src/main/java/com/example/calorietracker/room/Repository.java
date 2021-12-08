//package com.example.calorietracker.room;
//
//import static com.example.calorietracker.room.AppDatabase.databaseExecutor;
//import static com.example.calorietracker.room.AppDatabase.getDatabase;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.calorietracker.room.dao.IngredientDao;
//import com.example.calorietracker.room.dao.UserDao;
//import com.example.calorietracker.room.dao.UserIngredientCrossRefDao;
//import com.example.calorietracker.room.entity.Ingredient;
//import com.example.calorietracker.room.entity.IngredientsWithUsers;
//import com.example.calorietracker.room.entity.User;
//import com.example.calorietracker.room.entity.UserIngredientCrossRef;
//
//import java.util.List;
//
//public class Repository {
//    private static final String TAG = "Repo";
//    private static Repository instance = null;
//    private AppDatabase db;
//    private UserDao userDao;
//    private UserIngredientCrossRefDao uiCrossRefDao;
//    private IngredientDao ingredientDao;
//
//    private LiveData<List<User>> allUsers;
//    private LiveData<List<Ingredient>> allIngredients;
//    private LiveData<List<IngredientsWithUsers>> ingredientsWithUser;
//
//    private MutableLiveData<IngredientsWithUsers> ingredientWithUser = new MutableLiveData<>();
//
//    public static Repository getInstance( Context context ) {
//        if( instance == null ) {
//            instance = new Repository( context );
//        }
//        return instance;
//    }
//
//    public Repository( Context context ) {
//        db = getDatabase( context );
//
//        userDao = db.userDao();
//        ingredientDao = db.ingredientDao();
//        uiCrossRefDao = db.userIngredientCrossRefDao();
//
//        allUsers = userDao.getAllUsers();
//        allIngredients = ingredientDao.getAllIngredients();
//
//        ingredientsWithUser = ingredientDao.getIngredientsFromUser();
//    }
//
//    public LiveData<List<IngredientsWithUsers>> getIngredientsWithUser(){
//        return ingredientsWithUser;
//    }
//
//    public LiveData<List<User>> getAllUsers(){
//        return allUsers;
//    }
//
//    public LiveData<List<Ingredient>> getAllIngredients(){
//        return allIngredients;
//    }
//
//    public void insertUser(User user){
//        databaseExecutor.execute(()->{
//            userDao.insert(user);
//            Log.w(TAG, "Inserted user");
//        });
//    }
//
//    public void insertIngredient(Ingredient ingredient){
//        databaseExecutor.execute(()->{
//            ingredientDao.insert(ingredient);
//            Log.w(TAG, "Inserted ingredient");
//        });
//    }
//
//    public void insertRelUserIngredient(UserIngredientCrossRef userIngredientCrossRef){
//        databaseExecutor.execute(()->{
//            uiCrossRefDao.insert(userIngredientCrossRef);
//            Log.w(TAG, "Inserted UserIngredient cross reference");
//        });
//    }
//}
