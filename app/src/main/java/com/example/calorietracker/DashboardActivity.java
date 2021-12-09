package com.example.calorietracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.calorietracker.food.FoodListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView emailTextViewHeader;

    private Button logout;
    private String targetCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        if (mAuth.getCurrentUser() != null) {
            emailTextViewHeader = navigationView.getHeaderView(0).findViewById(R.id.emailTextViewHeader);
            emailTextViewHeader.setText(mAuth.getCurrentUser().getEmail());
            DocumentReference documentReference = fStore.collection("users").document(mAuth.getCurrentUser().getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        try {
                            addTargetCaloriesToFirebase(task, documentReference);
                        } catch (Exception e) {
                            Log.e("Dash", "get failed" + e.getMessage());
                        }

                    } else {
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

    private void addTargetCaloriesToFirebase(@NonNull Task<DocumentSnapshot> task, DocumentReference documentReference) {
        DocumentSnapshot documentSnapshot = task.getResult();
        if(documentSnapshot.get("targetCalories")==null){
            Object currentWeight = documentSnapshot.get("currentWeight");
            Object targetWeight = documentSnapshot.get("targetWeight");
            Object age = documentSnapshot.get("age");
            double activityLevel = getActivityLevel(documentSnapshot);
            Object height = documentSnapshot.get("height");
            targetCalories = String.valueOf(getTargetCalories(currentWeight, targetWeight, age, activityLevel, height));
            Map<String, Object> user = new HashMap<>();
            user.put("targetCalories", targetCalories);
            documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.e("Target calories", "added calories for user");
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        if ((tw - cw) >= 0 && (tw - cw) < 5) return 1.1;
        if ((tw - cw) >= 5 && (tw - cw) < 8) return 1.15;
        if ((tw - cw) >= 8) return 1.2;
        if ((tw - cw) < 0 && (tw - cw) >= -5) return 0.95;
        if ((tw - cw) < -5 && (tw - cw) >= -8) return 0.9;
        if ((tw - cw) < -8) return 0.85;
        return 1;
    }

    public void onListOfFoodsClick(View view) {
        startActivity(new Intent(DashboardActivity.this, ListOfIngredientsActivity.class));
    }

    public void onTrackMealsClick(View view) {
        startActivity(new Intent(DashboardActivity.this, FoodListActivity.class));
    }

    public void aboutYouOnClick(View view) {
        startActivity(new Intent(DashboardActivity.this, AboutActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(DashboardActivity.this, DashboardActivity.class));
                break;
            case R.id.nav_food:
                startActivity(new Intent(DashboardActivity.this, ListOfIngredientsActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(DashboardActivity.this, AboutActivity.class));
                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setMessage("Do you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(DashboardActivity.this, "You've been logged out!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DashboardActivity.this, FirstPageActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Toast.makeText(DashboardActivity.this, "No action", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Logout");
                alertDialog.show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}