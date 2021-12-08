package com.example.calorietracker.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.calorietracker.room.entity.User;

import java.util.List;

@Dao
public interface UserDao extends BaseDao<User> {

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();
}
