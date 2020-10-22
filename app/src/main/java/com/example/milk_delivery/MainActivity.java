package com.example.milk_delivery;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button signUp,login;
    TextView logo1,logo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = findViewById(R.id.signup);
        login = findViewById(R.id.login);

        logo1 = findViewById(R.id.logo1);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        logo1.setTypeface(face);
        logo2 = findViewById(R.id.logo2);
        logo2.setTypeface(face);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"signup",Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(MainActivity.this, signUP.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"login",Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}