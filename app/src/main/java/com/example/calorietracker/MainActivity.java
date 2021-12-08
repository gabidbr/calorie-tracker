package com.example.calorietracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ImageView logo;
    ImageView lottieAnimationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lottieAnimationView = findViewById(R.id.lottie);
        logo = findViewById(R.id.logoFirstPage);
        mAuth = FirebaseAuth.getInstance();

        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(3000);
        splashScreenStarter.start();
    }


    Thread splashScreenStarter = new Thread() {
        public void run() {
            try {
                int delay = 0;
                while (delay < 2000) {
                    sleep(150);
                    delay = delay + 100;
                }
                startActivity(new Intent(MainActivity.this, FirstPageActivity.class));

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                finish();
            }
        }

    };

}