package com.example.mixandmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DBHandler myDB;
    public String mode = "TIME";
    public String imageCategory = "CANDIES";
    public String user = "Guest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DBHandler(this);

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

    private void displayToast(String spinnerLabel) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void modeChoose(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.time:
                if (checked) mode="TIME";
                break;
            case R.id.score:
                if (checked) mode="SCORE";
                break;
            default: break;
        }
    }
}