package com.example.universdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText edit_user;
    private EditText edit_password;
    private Button button_login;
    private CheckBox password_remember;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_user = (EditText) findViewById(R.id.name);
        edit_password = (EditText) findViewById(R.id.password);
        password_remember = findViewById(R.id.password_remember);
        button_login = (Button) findViewById(R.id.login);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("password_remember",false);
        if(isRemember){
            String account = pref.getString("name","");
            String password = pref.getString("password","");
            edit_user.setText(account);
            edit_password.setText(password);
            edit_user.setSelection(edit_user.getText().length());
            edit_password.setSelection(edit_password.getText().length());

            password_remember.setChecked(true);
        }

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbHelper.getWritableDatabase();
                pref = getSharedPreferences("data", MODE_PRIVATE);

                String u = edit_user.getText().toString();
                String pwd = edit_password.getText().toString();

                String name = pref.getString("name", "");
                String password = pref.getString("password", "0");

                if (u.equals(name) && pwd.equals(password)) {
                    editor = pref.edit();
                    //Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                    dbHelper = new DatabaseHelper(MainActivity.this, "MyDiary.db",null,1);
//                    dbHelper.getWritableDatabase();
                    if(password_remember.isChecked()){
                        editor.putString("name",name);
                        editor.putString("password",password);
                        editor.putBoolean("password_remember", true);
                    }else{
                        editor.clear();
                    }
                    editor.apply();

                    Intent intent1 = new Intent(MainActivity.this, ScanDiary.class);
                    startActivity(intent1);
                    //finish();
                } else {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent2);
                //finish();
            }
        });
    }
}