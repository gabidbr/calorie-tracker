package com.example.calorietracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calorietracker.food.FoodListActivity;
import com.example.calorietracker.room.entity.Ingredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.List;

public class ListOfIngredientsActivity extends AppCompatActivity {

    public static final int NEW_INGREDIENT_ACTIVITY_REQUEST_CODE = 1;
    TextView sumCaloriesTextView;
    CircularProgressBar circularProgressBar;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fStore;
    private IngredientViewModel mIngredientViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ingredients);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        sumCaloriesTextView = findViewById(R.id.sumCaloriesTextView);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        floatingActionButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerview);
        final IngredientListAdapter adapter = new IngredientListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        mIngredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);

        mIngredientViewModel.getAllIngredients(firebaseAuth.getCurrentUser().getUid()).observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable final List<Ingredient> ingredients) {
                setVisibility(ingredients, sumCaloriesTextView);
                adapter.setIngredients(ingredients);
                setVisibility(ingredients, circularProgressBar);
                circularProgressBar();
            }
        });

        mIngredientViewModel.getCaloriesFromAllIngredients(firebaseAuth.getCurrentUser().getUid()).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer aInteger) {
                sumCaloriesTextView.setText(String.format("%s kCals", aInteger));
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListOfIngredientsActivity.this, FoodListActivity.class);
                startActivityForResult(intent, NEW_INGREDIENT_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            // on recycler view item swiped then we are deleting the item of our recycler view.
            AlertDialog.Builder builder = new AlertDialog.Builder(ListOfIngredientsActivity.this);
            builder.setMessage("Do you want to delete this?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int swipedPosition = viewHolder.getAdapterPosition();
                            IngredientListAdapter adapter = (IngredientListAdapter) recyclerView.getAdapter();
                            mIngredientViewModel.delete(adapter.getIngredientAtPosition(swipedPosition));
                            adapter.remove(swipedPosition);
                            Toast.makeText(ListOfIngredientsActivity.this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(ListOfIngredientsActivity.this, "No action", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Delete");
            alertDialog.show();
        }
    };



    private void setVisibility(List<Ingredient> ingredientList, View view) {
        if (ingredientList.isEmpty()) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void circularProgressBar() {
        mIngredientViewModel.getCaloriesFromAllIngredients(firebaseAuth.getCurrentUser().getUid()).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer aInteger) {
                if (aInteger != null)
                    circularProgressBar.setProgress((float) aInteger);
            }
        });
        DocumentReference documentReference = fStore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Object targetCalories = documentSnapshot.get("targetCalories");
                    circularProgressBar.setProgressMax(Float.parseFloat(targetCalories.toString()));
                } else {
                    circularProgressBar.setProgressMax(2000f);
                    Log.e("Target calories", "get failed with", task.getException());
                }
            }
        });
        circularProgressBar.setRoundBorder(true);
        circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);
    }

    //TODO Customize toolbar
    //TODO Make Navigation drawer
    //TODO Add TargetCalories, ma folosesc de ceea ce am, mai adaug Activity pt height si apoi in
    // functie de diferenta dintre current height si target height inmultesc cu 0.9 sau 1.1 sau 1.05...
    //TODO Validare form si sa nu ne lase sa trecem din Activity in alta fara campuri completate
}