package com.example.mixandmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class information extends AppCompatActivity {

    EditText message;
    EditText phone;
    EditText email;

    Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        message = findViewById(R.id.message);
        phone = findViewById(R.id.PhoneNumber);
        email = findViewById(R.id.Email);

        myButton = findViewById(R.id.button);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message.setText("");
                phone.setText("");
                email.setText("");

                Toast.makeText(getApplicationContext(), "Submitted",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}