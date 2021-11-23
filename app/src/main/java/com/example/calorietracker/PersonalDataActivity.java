package com.example.calorietracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PersonalDataActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText firstName, lastName;
    RadioButton male, female;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        male = findViewById(R.id.radioButtonMale);
        female = findViewById(R.id.radioButtonFemale);
    }

    public void currentWeightOnClick(View view) {
        String firstNameText = firstName.getText().toString();
        String lastNameText = lastName.getText().toString();
        String gender = "";
        if (male.isChecked()) gender = "male";
        else if (female.isChecked()) gender = "female";
        if (mAuth.getCurrentUser() != null) {
            userId = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fStore.collection("users").document(userId);
            Map<String, Object> user = new HashMap<>();
            user.put("firstName", firstNameText);
            user.put("lastName", lastNameText);
            user.put("gender", gender);
            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(PersonalDataActivity.this, "User Profile created for user" + userId, Toast.LENGTH_SHORT).show();
                }
            });
            Intent currentWeightActivity = new Intent(this, CurrentWeightActivity.class);
            startActivity(currentWeightActivity);
        } else {
            Toast.makeText(PersonalDataActivity.this, "Error!", Toast.LENGTH_SHORT).show();
        }

    }
}