package com.example.milk_delivery;

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

public class signUP extends AppCompatActivity {

    EditText phone,name,password,eMail;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.signUp);
        eMail = findViewById(R.id.eMAil);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(phone.getText().toString()).exists()){
                            Toast toast = Toast.makeText(signUP.this,"User already exists",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else{
                            User user = new User(name.getText().toString(),eMail.getText().toString(),password.getText().toString());
                            table_user.child(phone.getText().toString()).setValue(user);
                            Toast toast = Toast.makeText(signUP.this,"SignUp successful..!",Toast.LENGTH_SHORT);
                            toast.show();
                            finish();
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
