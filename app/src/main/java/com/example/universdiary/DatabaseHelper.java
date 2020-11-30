package com.example.universdiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;

    public static final String CREATE_TABLE_USER = "create table user("
            +"user_id integer primary key autoincrement,"
            +"user_name text not null unique,"
            +"user_phone text not null unique,"
            +"user_password text not null)";
    public static final String CREATE_TABLE_DIARY = "create table diary("
                    +"diary_id integer primary key autoincrement,"
                    +"diary_title text not null,"
                    +"diary_time text not null,"
                    +"diary_author text not null,"
                    +"diary_content text)";
    private Context myContext;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_DIARY);
        Toast.makeText(myContext, "建表成功", Toast.LENGTH_SHORT).show();
        //Toast.makeText(myContext, "succeeded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table if exists user");
        //db.execSQL("drop table if exists diary");
        //onCreate(db);
    }
}
