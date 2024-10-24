package com.kienluxury.ex21;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Khai báo các thành phần
    Button btnparse;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Liên kết các thành phần giao diện
        btnparse = findViewById(R.id.btnparse);
        lv = findViewById(R.id.lv);

        // Khởi tạo danh sách và adapter
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        // Sự kiện khi nhấn nút
        btnparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parsejson();
            }
        });
    }

    // Phương thức đọc và phân tích file JSON
    private void parsejson() {
        String json = null;
        try {
            // Mở file computer.json từ thư mục Assets
            InputStream inputStream = getAssets().open("computer.json");

            // Đọc file và chuyển đổi thành chuỗi
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

            // Phân tích JSON
            JSONObject reader = new JSONObject(json);

            // Thêm mã danh mục và tên danh mục vào danh sách
            mylist.add("Mã danh mục: " + reader.getString("MaDM"));
            mylist.add("Tên danh mục: " + reader.getString("TenDM"));

            // Lấy danh sách sản phẩm từ JSON
            JSONArray myarray = reader.getJSONArray("Sanphams");
            for (int i = 0; i < myarray.length(); i++) {
                JSONObject myobj = myarray.getJSONObject(i);

                // Thêm thông tin sản phẩm vào danh sách
                mylist.add("Mã sản phẩm: " + myobj.getString("MaSP"));
                mylist.add("Tên sản phẩm: " + myobj.getString("TenSP"));
                mylist.add("Số lượng: " + myobj.getInt("SoLuong"));
                mylist.add("Đơn giá: " + myobj.getInt("DonGia"));
                mylist.add("Thành tiền: " + myobj.getInt("ThanhTien"));
                mylist.add("Hình ảnh: " + myobj.getString("Hinh"));
            }

            // Cập nhật giao diện ListView
            myadapter.notifyDataSetChanged();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
