package com.example.mixandmatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DBHandler DB;
    public String mode = "TIME";
    public static String DIFFICULTY = "EASY";
    public static String USERNAME = "Guest";
    public String imageCategory = "CANDY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHandler(this);

        Spinner spinner = findViewById(R.id.spinner_label);
        if (spinner != null){
                spinner.setOnItemSelectedListener(this);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null){
            spinner.setAdapter(adapter);
        }
    }

    public void onClickShowAlert(View view) {
        AlertDialog.Builder myAlertBuilder = new
                AlertDialog.Builder(MainActivity.this);
        myAlertBuilder.setTitle(R.string.alert_title);
        myAlertBuilder.setMessage(R.string.alert_message);
        myAlertBuilder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Pressed OK", Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuilder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Pressed Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuilder.show();
    }


    private void openPopUpWindow() {
        Intent PopUpWindow = new Intent(MainActivity.this, PopupWindow.class);
        startActivity(PopUpWindow);
    }

    public void click_info(View view) {
        Intent intent = new Intent(this, information.class);
        startActivity(intent);
    }

    public void click_leaderboard(View view) {
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }

    public void click_delete(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String spinnerLabel = parent.getItemAtPosition(position).toString();
        displayToast(spinnerLabel);

    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void modeChoose(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.time:
                if (checked) mode="TIME";
                displayToast("Scoring mode is switched to Time-based.");
                break;
            case R.id.score:
                if (checked) mode="SCORE";
                displayToast("Scoring mode is switched to Score-Based");
                break;
            default: break;
        }
    }
    public void changeImageCategory(View view) {
        TextView categoryName = findViewById(R.id.category_name);
        switch (view.getId()){
            case R.id.imageCategoryCandy:
                imageCategory = "CANDY";
                displayToast("Image category switched to Candies.");
                categoryName.setText("Candy");
                break;
            case R.id.imageCategoryCubes:
                imageCategory = "CUBE";
                displayToast("Image category switched to Sugar Cubes.");
                categoryName.setText("Sugar Cubes");
                break;
            case R.id.imageCategoryDonut:
                imageCategory = "DONUT";
                displayToast("Image category switched to Donuts.");
                categoryName.setText("Donut");
                break;
            case R.id.imageCategoryCookie:
                imageCategory = "COOKIE";
                displayToast("Image category switched to Cookies.");
                categoryName.setText("Cookie");
                break;
            case R.id.imageCategoryCane:
                imageCategory = "CANE";
                displayToast("Image category switched to Sugar Canes.");
                categoryName.setText("Sugar Cane");
                break;
            case R.id.imageCategoryWiggles:
                imageCategory = "WIGGLES";
                displayToast("Image category switched to Wiggles.");
                categoryName.setText("Wiggles");
                break;
            case R.id.imageCategoryGummy:
                imageCategory = "GUMMY";
                displayToast("Image category switched to Gummies.");
                categoryName.setText("Gummy");
                break;
            case R.id.imageCategoryIceCream:
                imageCategory = "ICE-CREAM";
                displayToast("Image category switched to Ice Cream.");
                categoryName.setText("Ice Cream");
                break;
            case R.id.imageCategoryBeans:
                imageCategory = "BEANS";
                displayToast("Image category switched to Beans.");
                categoryName.setText("Beans");
                break;
            case R.id.imageCategoryPopsicle:
                imageCategory = "POPSICLE";
                displayToast("Image category switched to Popsicle.");
                categoryName.setText("Popsicle");
                break;
            default:
                break;
        }
    }
    public void chooseDifficulty(View view) {
        switch (view.getId()) {
            case R.id.easy:
                DIFFICULTY = "EASY";
                break;
            case R.id.moderate:
                DIFFICULTY = "MODERATE";
                break;
            case R.id.hard:
                DIFFICULTY = "HARD";
                break;
            case R.id.extreme:
                DIFFICULTY = "EXTREME";
                break;
            default: break;
        }
        Intent intent = new Intent(MainActivity.this, GameView.class);
        intent.putExtra("DIFFICULTY", DIFFICULTY);
        intent.putExtra("MODE", mode);
        intent.putExtra("USERNAME", USERNAME);
        intent.putExtra("CATEGORY", imageCategory);
        setResult(RESULT_OK, intent);
        startActivity(intent);

    }
}