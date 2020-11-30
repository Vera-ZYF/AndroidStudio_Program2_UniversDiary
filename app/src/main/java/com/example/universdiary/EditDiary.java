package com.example.universdiary;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.universdiary.ScanDiary.INSERT_DIARY;
import static com.example.universdiary.ScanDiary.UPDATE_DIARY;
import static com.example.universdiary.ScanDiary.dbHelper;
import static com.example.universdiary.ScanDiary.getDbHelper;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class EditDiary extends AppCompatActivity{

        private SQLiteDatabase db;
        EditText diary_title;
        EditText diary_author;
        TextView diary_time;
        EditText diary_content;
        public DatabaseHelper deHelper = getDbHelper();
        private int tag;
        private int diaryID;

        private Button save_diary;
        private Button delete_diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);

        diary_title = findViewById(R.id.diary_title);
        diary_author = findViewById(R.id.diary_author);
        diary_time = findViewById(R.id.diary_time);
        diary_content = findViewById(R.id.diary_content);

        diary_title.setSelection(diary_title.getText().length());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        diary_time.setText(simpleDateFormat.format(date));
        diary_content.setSelection(diary_content.getText().length());

        db = dbHelper.getWritableDatabase();
        final Intent intent = getIntent();
        tag = intent.getIntExtra("OPERATE",-1);

        diaryID = intent.getIntExtra("diaryID", -1);
        Cursor cursor = db.query("diary", null, "diary_id=?", new String[]{String.valueOf(diaryID)}, null, null, null);
        if (cursor.moveToFirst() && tag==UPDATE_DIARY) {
            String select_title = cursor.getString(cursor.getColumnIndex("diary_title"));
            String select_author = cursor.getString(cursor.getColumnIndex("diary_author"));
            String select_content = cursor.getString(cursor.getColumnIndex("diary_content"));
            diary_title.setText(select_title);
            diary_author.setText(select_author);
            diary_content.setText(select_content);
        }

        save_diary = findViewById(R.id.save_diary);
        save_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("diary_title", diary_title.getText().toString());
                values.put("diary_author", diary_author.getText().toString());
                values.put("diary_content", diary_content.getText().toString());
                values.put("diary_time", String.valueOf(diary_time));
                switch (tag){
                    case INSERT_DIARY:
                        //final ByteArrayOutputStream os = new ByteArrayOutputStream();
                        //Bitmap bitmap = ((BitmapDrawable)picture.getDrawable()).getBitmap();
                        //bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
                        //values.put("picture",os.toByteArray());
                        db.insert("diary", null, values);
                        values.clear();
                        Toast.makeText(EditDiary.this, "已保存", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case UPDATE_DIARY:
                        //Log.d("the ID",)
                        db.update("diary",values, "diary_id=?", new String[]{String.valueOf(diaryID)});
                        break;
                    default:
                }

            }
        });

        delete_diary = findViewById(R.id.delete_diary);
        delete_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("diary", "diary_id=?", new String[]{String.valueOf(diaryID)});
                //Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}