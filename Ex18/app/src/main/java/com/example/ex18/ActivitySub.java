package com.example.ex18;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySub extends AppCompatActivity {
    TextView txtmaso, txtbaihat, txtloibaihat, txttacgia;
    ImageButton btnthich, btnkhongthich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity);

        // Ánh xạ các thành phần giao diện
        txtmaso = findViewById(R.id.txtMaso);
        txtbaihat = findViewById(R.id.txtbaihat);
        txtloibaihat = findViewById(R.id.txtloibaihat);
        txttacgia = findViewById(R.id.txttacgia);
        btnthich = findViewById(R.id.btnthich);
        btnkhongthich = findViewById(R.id.btnkhongthich);

        // Nhận Intent từ MyArrayAdapter, lấy dữ liệu khỏi Bundle
        Intent callerIntent = getIntent();
        Bundle packageCaller = callerIntent.getBundleExtra("package");
        if (packageCaller != null) {
            String maso = packageCaller.getString("maso");

            // Hiển thị dữ liệu bài hát từ cơ sở dữ liệu
            Cursor c = MainActivity.database.rawQuery("SELECT * FROM ArirangSongList WHERE MABH LIKE ?", new String[]{maso});
            if (c != null && c.moveToFirst()) {
                txtmaso.setText(maso);
                txtbaihat.setText(c.getString(2)); // Tên bài hát
                txtloibaihat.setText(c.getString(3)); // Lời bài hát
                txttacgia.setText(c.getString(4)); // Tác giả

                // Xử lý trạng thái thích/không thích
                if (c.getInt(6) == 0) { // Cột YEUTHICH = 0 (chưa thích)
                    btnthich.setVisibility(View.INVISIBLE);
                    btnkhongthich.setVisibility(View.VISIBLE);
                } else { // Cột YEUTHICH = 1 (đã thích)
                    btnkhongthich.setVisibility(View.INVISIBLE);
                    btnthich.setVisibility(View.VISIBLE);
                }
                c.close();
            }
        }

        // Xử lý sự kiện khi click vào nút "thích"
        btnthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 0); // Đặt trạng thái không thích
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{txtmaso.getText().toString()});
                btnthich.setVisibility(View.INVISIBLE);
                btnkhongthich.setVisibility(View.VISIBLE);
            }
        });

        // Xử lý sự kiện khi click vào nút "không thích"
        btnkhongthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 1); // Đặt trạng thái thích
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{txtmaso.getText().toString()});
                btnkhongthich.setVisibility(View.INVISIBLE);
                btnthich.setVisibility(View.VISIBLE);
            }
        });
    }
}
