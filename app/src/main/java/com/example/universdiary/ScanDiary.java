package com.example.universdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.universdiary.DatabaseHelper.VERSION;

public class ScanDiary extends AppCompatActivity {
    private SQLiteDatabase db;
    public static DatabaseHelper dbHelper;
    private ArrayList<String> diary = new ArrayList<>();
    public static final int INSERT_DIARY = 1;
    public static final int UPDATE_DIARY = 0;
    private String select_diary;
    private int diaryID;
    ListView listView;
    ArrayAdapter<String> adapter;
    private Button insert_diary;
    private SwipeRefreshLayout swipeRefresh;

    public static DatabaseHelper getDbHelper() {
        return dbHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_diary);

        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout
                .OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        dbHelper = new DatabaseHelper(ScanDiary.this, "MyDiary.db", null, VERSION);
        dbHelper.getWritableDatabase();
        init();

        insert_diary = findViewById(R.id.insert_diary);
        insert_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanDiary.this, EditDiary.class);
                //将新建或修改传给编辑活动
                intent.putExtra("OPERATE", INSERT_DIARY);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转至选中日记详情页
                Intent intent = new Intent(ScanDiary.this, EditDiary.class);
                diaryID = getDiaryId(position);
                intent.putExtra("diaryID", diaryID);
                intent.putExtra("OPERATE", UPDATE_DIARY);
                startActivity(intent);
            }
        });

    }

    private void init() {
        db = dbHelper.getWritableDatabase();
        diary.clear();

        //查询数据库,用listview显示日记列表
        Cursor cursor = db.query("diary", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            String diary_item;
            do {
                diary_item = cursor.getString(cursor.getColumnIndex("diary_title"));
                diary.add(diary_item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter = new ArrayAdapter<String>(ScanDiary.this, android.R.layout.simple_list_item_1, diary);
        listView = findViewById(R.id.list_item);
        listView.setAdapter(adapter);
    }
        //下拉刷新日记列表
        private void refresh() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            init();
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                }
            }).start();
        }


    private int getDiaryId(int position) {
        //获取所点击的日记的title
        int ID;
        select_diary = diary.get(position);
        //获取点击日记列表的id
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("diary", new String[]{"diary_id"}, "diary_title=?",
                new String[]{select_diary}, null, null, null);
        cursor.moveToFirst();
        ID = cursor.getInt(cursor.getColumnIndex("diary_id"));
        return ID;
    }

}