package com.example.calorietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CurrentWeightActivity extends AppCompatActivity {
    private EditText weightEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_weight);

        weightEditText = findViewById(R.id.weight);
    }

    public void targetWeightOnClick(View view) {
        if(weightEditText!=null){
            String weightValue = weightEditText.getText().toString();
            openTargetWeightActivity();
        }
    }

    public void openTargetWeightActivity(){
        Intent startTargetWeightActivity = new Intent(this, TargetWeightActivity.class);
        startActivity(startTargetWeightActivity);
    }
}