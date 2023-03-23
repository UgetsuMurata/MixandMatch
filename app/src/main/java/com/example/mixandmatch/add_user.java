package com.example.mixandmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class add_user extends AppCompatActivity {

    DBHandler DB;

    EditText myUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        myUsername = findViewById(R.id.user_name);
    }

    public void submitButton(View view) {

        String name = myUsername.getText().toString();

        DB.insertUser(name);

        Intent intent = new Intent(add_user.this, MainActivity.class);
        startActivity(intent);

    }
}