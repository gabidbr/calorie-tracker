//package com.example.calorietracker.room;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import com.example.calorietracker.room.entity.User;
//
//import java.util.List;
//
//public class UserViewModel extends AndroidViewModel {
//    private Repository repository;
//
//    private LiveData<List<User>> allUsers;
//
//    public UserViewModel(@NonNull Application application) {
//        super(application);
//        repository = new Repository(application);
//        allUsers = repository.getAllUsers();
//    }
//
//    public LiveData<List<User>> getAllUsers(){
//        return allUsers;
//    }
//
//    public void insert(User user){
//        repository.insertUser(user);
//    }
//}
