package com.example.calorietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HeightActivity extends AppCompatActivity {
    private EditText heightEditText;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        heightEditText = findViewById(R.id.height);
        //TODO sa leg height de firebase si sa adaug acolo respectiv sa
        // vad atunci cand fac login si ies din activitate cum fac, adica pe signup sa nu treaca de acea pagina
    }

    public void dashboardOnClick(View view) {
        if(heightEditText.getText().toString().length()!=0){
            String heightValue = heightEditText.getText().toString();
            if(mAuth.getCurrentUser()!=null){
                userId = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String, Object> user = new HashMap<>();
                user.put("height", heightValue);
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(HeightActivity.this, "Height added for user" + userId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            openExerciseLevelActivity();
        }else{
            heightEditText.requestFocus();
            heightEditText.setError("Field cannot be empty!");
            Toast.makeText(HeightActivity.this,"Please insert data in each field!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openExerciseLevelActivity() {
        startActivity(new Intent(HeightActivity.this, ExerciseLevelActivity.class));
        finish();
    }

    public void goToPreviousActivityOnClick(View view) {
        finish();
    }
}