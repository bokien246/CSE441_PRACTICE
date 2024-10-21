package com.example.ex18;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    public static String DATABASE_NAME = "arirang.sqlite";
    EditText edttim;
    ListView lv1, lv2, lv3;
    ArrayList<Item> list1, list2, list3;
    MyArrayAdapter myarray1, myarray2, myarray3;
    TabHost tab;
    ImageButton btnxoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Copy CSDL từ thư mục assets nếu chưa tồn tại
        processCopy();

        // Mở cơ sở dữ liệu đã copy
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // Thêm các controls và xử lý sự kiện
        addControl();
        addEvents();
    }

    // Thêm các controls vào giao diện
    private void addControl() {
        btnxoa = findViewById(R.id.btnxoa);
        tab = findViewById(R.id.tabhost);
        tab.setup();

        // Thiết lập các tab
        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("", getResources().getDrawable(R.drawable.search));
        tab.addTab(tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("", getResources().getDrawable(R.drawable.list));
        tab.addTab(tab2);

        TabHost.TabSpec tab3 = tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("", getResources().getDrawable(R.drawable.favourite));
        tab.addTab(tab3);

        // Khai báo các ListView và EditText
        edttim = findViewById(R.id.edttim);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);

        // Khởi tạo danh sách và Adapter
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        myarray1 = new MyArrayAdapter(MainActivity.this, R.layout.listitem, list1);
        myarray2 = new MyArrayAdapter(MainActivity.this, R.layout.listitem, list2);
        myarray3 = new MyArrayAdapter(MainActivity.this, R.layout.listitem, list3);

        lv1.setAdapter(myarray1);
        lv2.setAdapter(myarray2);
        lv3.setAdapter(myarray3);
    }

    // Thêm sự kiện cho các thành phần
    private void addEvents() {
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("t2")) {
                    addDanhsach();
                }
                if (tabId.equalsIgnoreCase("t3")) {
                    addYeuthich();
                }
            }
        });

        // Sự kiện khi nhấn nút xóa trong Tab Tìm kiếm
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edttim.setText("");
            }
        });

        // Sự kiện khi thay đổi text trong EditText
        edttim.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getdata();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Hàm thêm dữ liệu vào ListView trên Tab Yêu Thích
    private void addYeuthich() {
        myarray3.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE YEUTHICH=1", null);
        if (c != null) {
            while (c.moveToNext()) {
                list3.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
            }
            c.close();
        }
        myarray3.notifyDataSetChanged();
    }

    // Hàm thêm dữ liệu vào ListView trên Tab Danh Sách Bài Hát
    private void addDanhsach() {
        myarray2.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSongList", null);
        if (c != null) {
            while (c.moveToNext()) {
                list2.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
            }
            c.close();
        }
        myarray2.notifyDataSetChanged();
    }

    // Hàm tìm kiếm dữ liệu dựa trên EditText
    private void getdata() {
        String dulieunhap = edttim.getText().toString();
        myarray1.clear();
        if (!dulieunhap.equals("")) {
            Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE TENBH LIKE ? OR MABH LIKE ?",
                    new String[]{"%" + dulieunhap + "%", "%" + dulieunhap + "%"});
            if (c != null) {
                while (c.moveToNext()) {
                    list1.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
                }
                c.close();
            }
        }
        myarray1.notifyDataSetChanged();
    }

    // Hàm xử lý copy CSDL từ thư mục assets
    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                copyDatabaseFromAsset();
                Toast.makeText(this, "Copying success from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    // Copy CSDL từ thư mục assets vào hệ thống
    private void copyDatabaseFromAsset() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) f.mkdir();

            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
