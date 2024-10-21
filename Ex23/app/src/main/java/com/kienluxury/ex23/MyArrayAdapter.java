package com.kienluxury.ex23;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<List> {
    private Activity context;
    private ArrayList<List> arr;
    private int layoutID;

    // Constructor để khởi tạo MyArrayAdapter
    public MyArrayAdapter(Activity context, int layoutID, ArrayList<List> arr) {
        super(context, layoutID, arr);
        this.context = context;
        this.layoutID = layoutID;
        this.arr = arr;
    }

    // Phương thức getView để tạo View cho từng dòng trong ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        // Kiểm tra convertView, nếu null thì khởi tạo
        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
        }

        // Lấy đối tượng List tại vị trí position
        final List lst = arr.get(position);

        // Tham chiếu các thành phần trong layout_listview.xml
        ImageView imgItem = convertView.findViewById(R.id.imgView);
        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        TextView txtInfo = convertView.findViewById(R.id.txtInfo);

        // Đặt hình ảnh, tiêu đề và tóm tắt cho từng mục
        imgItem.setImageBitmap(lst.getImg());
        txtTitle.setText(lst.getTitle());
        txtInfo.setText(lst.getInfo());

        // Xử lý sự kiện click cho mỗi mục trong ListView
        convertView.setOnClickListener(v -> {
            // Mở trình duyệt với liên kết của bài báo
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lst.getLink()));
            context.startActivity(intent);
        });

        return convertView;
    }
}

