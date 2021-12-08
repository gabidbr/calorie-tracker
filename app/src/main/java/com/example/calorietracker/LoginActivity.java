package com.example.calorietracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private EditText email, password;
    private Button login;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        createRequest();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(LoginActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(txt_email, txt_password);
                }
            }
        });

        findViewById(R.id.googleButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });
    }

    private void loginUser(String txt_email, String txt_password) {
        mAuth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    openNewActivity(user);
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openNewActivity(FirebaseUser user) {
        if (user != null) {
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
                    if (value.getString("height") == null) {
                        reload(new HeightActivity());
                        return;
                    }
                    if (value.getString("activityLevel") == null) {
                        reload(new ExerciseLevelActivity());
                        return;
                    }
                    if (value.getString("firstName") != null && value.getString("lastName") != null && value.getString("gender") != null
                            && value.getString("currentWeight") != null && value.getString("targetWeight") != null && value.getString("activityLevel") != null && value.getString("height") != null)
                        reload(new DashboardActivity());
                }
            });

        }
    }
    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void reload(Activity activity) {
        startActivity(new Intent(LoginActivity.this, activity.getClass()));
        finish();
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            openNewActivity(user);
                        } else {
                            Toast.makeText(LoginActivity.this, "Sorry, login failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}