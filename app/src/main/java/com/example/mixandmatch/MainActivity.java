package com.example.mixandmatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DBHandler DB;
    public String mode = "TIME";
    public static String DIFFICULTY = "EASY";
    public static String USERNAME = "Guest";
    public String imageCategory = "CANDY";
    private Spinner spinner;
    private ArrayAdapter arrayAdapter;
    private ImageView delete_icon;
    List<String> labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHandler(this);

        delete_icon = findViewById(R.id.icon_delete);

        spinner = findViewById(R.id.spinner_label);
        spinner.setOnItemSelectedListener(this);

        USERNAME = DB.getLoggedIn();
        labels = DB.getAllUser();
        labels.add("Guest");
        labels.add("+ Add User");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, labels);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(arrayAdapter.getPosition(USERNAME));
    }

    public void onClickShowAlert(View view) {
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
        myAlertBuilder.setTitle(R.string.alert_title);
        myAlertBuilder.setMessage(R.string.alert_message);
        myAlertBuilder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Deleting user...", Toast.LENGTH_SHORT).show();
                DB.deleteUser(USERNAME);
                Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuilder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Deletion cancelled.", Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuilder.show();
    }

    public void click_info(View view) {
        Intent intent = new Intent(this, information.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerLabel = parent.getItemAtPosition(position).toString();
        if (spinnerLabel.equals("+ Add User")){
            Intent intent = new Intent(MainActivity.this, add_user.class);
            spinner.setSelection(arrayAdapter.getPosition(USERNAME));
            startActivity(intent);

        } else if (spinnerLabel.equals("Guest")){
            delete_icon.setVisibility(View.INVISIBLE);
            displayToast("Welcome!");
            DB.logOutUser();
            USERNAME = spinnerLabel;
        } else {
            delete_icon.setVisibility(View.VISIBLE);
            displayToast("Welcome "+spinnerLabel+"!");
            USERNAME = spinnerLabel;
            DB.changeUser(USERNAME);
        }
    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(MainActivity.this, game.class);
        intent.putExtra("DIFFICULTY", DIFFICULTY);
        intent.putExtra("MODE", mode);
        intent.putExtra("USERNAME", USERNAME);
        intent.putExtra("CATEGORY", imageCategory);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }
}