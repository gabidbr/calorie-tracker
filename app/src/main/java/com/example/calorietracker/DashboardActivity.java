package com.example.calorietracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calorietracker.food.FoodListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    private Button logout;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    private String targetCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(mAuth.getCurrentUser()!=null){
            DocumentReference documentReference = fStore.collection("users").document(mAuth.getCurrentUser().getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        try{
                            DocumentSnapshot documentSnapshot = task.getResult();
                            Object currentWeight = documentSnapshot.get("currentWeight");
                            Object targetWeight = documentSnapshot.get("targetWeight");
                            Object age = documentSnapshot.get("age");
                            double activityLevel = getActivityLevel(documentSnapshot);
                            Object height = documentSnapshot.get("height");
                            targetCalories = String.valueOf(getTargetCalories(currentWeight, targetWeight, age, activityLevel, height));
                            System.out.println(targetCalories);
                            Map<String, Object> user = new HashMap<>();
                            user.put("targetCalories", targetCalories);
                            documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(DashboardActivity.this, "Target Calories added for user", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }catch (Exception e){
                            Log.e("Dash","get failed" + e.getMessage());
                        }

                    }else{
                        Log.e("DashboardActivity", "get failed with", task.getException());
                    }
                }
            });

        }

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(DashboardActivity.this, "You've been logged out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashboardActivity.this, FirstPageActivity.class));
            }
        });
    }

    private int getTargetCalories(Object currentWeight, Object targetWeight, Object age, double activityLevel, Object height) {
        return (int) (((10 * Float.parseFloat(currentWeight.toString())
                        + 6.25 * Float.parseFloat(height.toString())
                        - 5 * Float.parseFloat(age.toString()) + 5)) * differenceIndex(currentWeight, targetWeight) * activityLevel);
    }

    private double getActivityLevel(DocumentSnapshot documentSnapshot) {
        Object activityLevelObject = documentSnapshot.get("activityLevel");
        String activityLevelString = activityLevelObject.toString();
        double activityLevel;
        switch (activityLevelString) {
            case "sedentary":
                activityLevel = 1.3;
                break;
            case "light":
                activityLevel = 1.55;
                break;
            case "very":
                activityLevel = 1.8;
                break;
            default:
                activityLevel = 1.5;
                break;
        }
        return activityLevel;
    }

    private double differenceIndex(Object currentWeight, Object targetWeight) {
        double cw = Float.parseFloat(currentWeight.toString());
        double tw = Float.parseFloat(targetWeight.toString());
        if((tw-cw)>=0 && (tw-cw)<5) return 1.1;
        if((tw-cw)>=5 && (tw-cw)<8) return 1.15;
        if((tw-cw)>=8) return 1.2;
        if((tw-cw)<0 && (tw-cw)>=-5) return 0.95;
        if((tw-cw)<-5 && (tw-cw)>=-8) return 0.9;
        if((tw-cw)<-8) return 0.85;
        return 1;
    }

    public void onListOfFoodsClick(View view) {
        startActivity(new Intent(DashboardActivity.this, ListOfIngredientsActivity.class));
    }

    public void onTrackMealsClick(View view) {
        startActivity(new Intent(DashboardActivity.this, FoodListActivity.class));
    }
}