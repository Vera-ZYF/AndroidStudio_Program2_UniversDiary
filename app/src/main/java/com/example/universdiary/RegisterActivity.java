package com.example.universdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private Button button_register;
    private Button register_login;
    private EditText username;
    private EditText userpassword1;
    private EditText userpassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.register_name);
        userpassword1 = findViewById(R.id.register_password1);
        userpassword2 = findViewById(R.id.register_password2);
        button_register = findViewById(R.id.button_register);
        register_login = findViewById(R.id.register_login);

        editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userpassword1.getText().toString().equals( userpassword2.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                }else{

                    editor.putString("name",username.getText().toString());
                    editor.putString("password",userpassword1.getText().toString());
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    editor.apply();
                }
            }
        });

        register_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}