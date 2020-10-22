package com.example.milk_delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Common.Common;

public class LoginActivity extends AppCompatActivity {

    EditText phone,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        //Firebase

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        //User exists in the db??
                        if (snapshot.child(phone.getText().toString()).exists()) {

                            User user = snapshot.child(phone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(password.getText().toString())) {
                                Toast toast = Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT);
                                toast.show();

                                Intent intent = new Intent(LoginActivity.this,Home.class);
                                Common.user = user;
                                startActivity(intent);
                            } else {
                                Toast toast = Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                        else{
                            Toast toast = Toast.makeText(LoginActivity.this, "user doesnt exist", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

}
