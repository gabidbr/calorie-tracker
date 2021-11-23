package com.example.calorietracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FirstPageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    private void reload(Activity activity) {
        startActivity(new Intent(FirstPageActivity.this, activity.getClass()));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        TextView textView = findViewById(R.id.textView5);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            DocumentReference documentReference = fStore.collection("users").document(user.getUid());
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value.getString("firstName") == null || value.getString("lastName") == null || value.getString("gender") == null) {
                        reload(new PersonalDataActivity());
                        return;
                    }
                    if (value.getString("currentWeight") == null) {
                        reload(new CurrentWeightActivity());
                        return;
                    }
                    if (value.getString("targetWeight") == null) {
                        reload(new TargetWeightActivity());
                        return;
                    }
                    if (value.getString("activityLevel") == null) {
                        reload(new ExerciseLevelActivity());
                        return;
                    }
                    if (value.getString("firstName") != null && value.getString("lastName") != null && value.getString("gender") != null
                            && value.getString("currentWeight") != null && value.getString("targetWeight") != null && value.getString("activityLevel") != null)
                        reload(new DashboardActivity());
                }
            });

        }
    }

    public void openPersonalDataActivityOnClick(View view) {
        startActivity(new Intent(FirstPageActivity.this, StartActivity.class));
    }
}