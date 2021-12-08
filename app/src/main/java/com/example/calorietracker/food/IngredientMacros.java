package com.example.calorietracker.food;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.calorietracker.IngredientViewModel;
import com.example.calorietracker.ListOfIngredientsActivity;
import com.example.calorietracker.R;
import com.example.calorietracker.room.entity.Ingredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientMacros extends AppCompatActivity {
    private final String AMOUNT = "100";
    private final String API_KEY = "7f44089524134491ae3b67763196e607";
    TextView nameOfIngredient, proteinText, carbsText, fatsText, caloriesText, nutrientsText;
    ImageView ingredientImageView;
    FloatingActionButton floatingActionButton;
    int kCals;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_ingredient_macros);
        nameOfIngredient = findViewById(R.id.ingredientName);
        proteinText = findViewById(R.id.proteins);
        carbsText = findViewById(R.id.carbs);
        fatsText = findViewById(R.id.fats);
        caloriesText = findViewById(R.id.calories);
        ingredientImageView = findViewById(R.id.ingredientImage);
        nutrientsText = findViewById(R.id.nutrients);
        floatingActionButton = findViewById(R.id.floatingAddButton);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String foodId = extras.getString("foodId");
            Call<IngredientInfo> ingredientInformation = ApiClient.getIngredientInformation().getIngredientInformation(foodId, AMOUNT, API_KEY);
            ingredientInformation.enqueue(new Callback<IngredientInfo>() {
                @Override
                public void onResponse(Call<IngredientInfo> call, Response<IngredientInfo> response) {
                    IngredientInfo ingredientInfo = response.body();
                    setIngredientDataOnView(ingredientInfo);
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Dialog dialog = new Dialog(IngredientMacros.this);
                            dialog.setContentView(R.layout.activity_add_ingredient_calories);

                            EditText gramsOfFood = dialog.findViewById(R.id.gramsOfFood);
                            Button addButton = dialog.findViewById(R.id.addButton);
                            addButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(gramsOfFood.getText().toString().length()!=0){
                                        dialog.dismiss();
                                        addGramsOfFoodToDataBase(gramsOfFood.getText().toString(), ingredientInfo, kCals);
                                    }else {
                                        gramsOfFood.requestFocus();
                                        gramsOfFood.setError("Please insert!");
                                    }

                                }
                            });
                            dialog.show();
                        }
                    });
                    Log.e("Success on ingredient", ingredientInfo.toString());
                }

                @Override
                public void onFailure(Call<IngredientInfo> call, Throwable t) {
                    Log.e("Failed on ingredient", t.getLocalizedMessage());
                }
            });
        }
    }

    private void setIngredientDataOnView(IngredientInfo ingredientInfo) {
        String photoUrl = "https://spoonacular.com/cdn/ingredients_500x500/" + ingredientInfo.getImage();
        Picasso.get().load(photoUrl).fit().centerInside().into(ingredientImageView);
        nameOfIngredient.setText(ingredientInfo.getName().toUpperCase());
        CaloricBreakDown caloricBreakdown = ingredientInfo.getNutrition().getCaloricBreakdown();
        proteinText.setText(String.format("%s Proteins", caloricBreakdown.getProtein()));
        carbsText.setText(String.format("%s Carbs", caloricBreakdown.getCarbs()));
        fatsText.setText(String.format("%s Fats", caloricBreakdown.getFat()));
        List<Nutrient> nutrients = ingredientInfo.getNutrition().getNutrients();
        StringBuilder nutrientsString = new StringBuilder();
        for (Nutrient nutrient : nutrients) {
            if (nutrient.getTitle().equals("Calories")) {
                if (nutrient.getUnit().equals("kcal")) {
                    int v =(int)Float.parseFloat(nutrient.getAmount());
                    calculateKcalsValue(nutrient, v);
                    caloriesText.setText(String.format("%s kCal", kCals));
                }
            } else {
                nutrientsString.append(nutrient.getTitle()).append(" ").append(nutrient.getAmount()).append(nutrient.getUnit()).append("\n");
            }
        }
        nutrientsText.setText(nutrientsString);
    }

    private void calculateKcalsValue(Nutrient nutrient, int calories) {
        if (calories >= 1000 && calories < 10000) {
            nutrient.setAmount(String.valueOf((int)(Float.parseFloat(nutrient.getAmount()) / 10)));
        } else if (calories >= 10000 && calories < 100000) {
            nutrient.setAmount(String.valueOf((int)(Float.parseFloat(nutrient.getAmount()) / 100)));
        } else if (calories >= 100 && calories < 1000) {
            nutrient.setAmount(String.valueOf((int)Float.parseFloat(nutrient.getAmount())));
        } else if (calories >= 100000) {
            nutrient.setAmount(String.valueOf((int)(Float.parseFloat(nutrient.getAmount()) / 1000)));
        }
        kCals = Integer.parseInt(nutrient.getAmount());
    }

    private void addGramsOfFoodToDataBase(String gramsOfFood, IngredientInfo ingredientInfo, int kCals) {
        System.out.println(ingredientInfo.getId() + "i" + ingredientInfo.getId() + ingredientInfo.getName() + Float.parseFloat(gramsOfFood));
        Ingredient ingredient = new Ingredient(ingredientInfo.getId(),
                ingredientInfo.getName(), (int)(Float.parseFloat(gramsOfFood) * kCals / 100), firebaseAuth.getCurrentUser().getUid(), Calendar.getInstance().getTime());
        IngredientViewModel ingredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        ingredientViewModel.insert(ingredient);
        Intent intent = new Intent(IngredientMacros.this, ListOfIngredientsActivity.class);
        startActivity(intent);
        finish();
    }
}