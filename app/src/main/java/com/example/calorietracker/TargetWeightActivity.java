package com.example.calorietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TargetWeightActivity extends AppCompatActivity {
    private EditText targetWeightEditText;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_weight);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        targetWeightEditText = findViewById(R.id.weight2);
    }

    public void heightOnClick(View view) {
        if(targetWeightEditText.getText().toString().length()!=0){
            String weightValue = targetWeightEditText.getText().toString();
            if(mAuth.getCurrentUser()!=null){
                userId = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String, Object> user = new HashMap<>();
                user.put("targetWeight", weightValue);
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(TargetWeightActivity.this, "Target weight added for user" + userId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            openHeightActivity();
        }else{
            targetWeightEditText.requestFocus();
            targetWeightEditText.setError("Field cannot be empty!");
            Toast.makeText(TargetWeightActivity.this,"Please insert data in each field!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openHeightActivity() {
        startActivity(new Intent(TargetWeightActivity.this, HeightActivity.class));
        finish();
    }
}