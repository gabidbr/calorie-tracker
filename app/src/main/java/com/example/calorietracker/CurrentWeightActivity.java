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

public class CurrentWeightActivity extends AppCompatActivity {
    private EditText weightEditText;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_weight);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        weightEditText = findViewById(R.id.weight);
    }

    public void targetWeightOnClick(View view) {
        if(weightEditText!=null){
            String weightValue = weightEditText.getText().toString();
            if(mAuth.getCurrentUser()!=null){
                userId = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String, Object> user = new HashMap<>();
                user.put("currentWeight", weightValue);
                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CurrentWeightActivity.this, "Current weight added for user" + userId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            openTargetWeightActivity();
        }
    }

    public void openTargetWeightActivity(){
        Intent startTargetWeightActivity = new Intent(this, TargetWeightActivity.class);
        startActivity(startTargetWeightActivity);
    }
}