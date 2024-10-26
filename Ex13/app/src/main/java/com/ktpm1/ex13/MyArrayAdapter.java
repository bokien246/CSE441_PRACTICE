package com.ktpm1.ex13;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<phone> { Activity context;
    int idlayout; ArrayList<phone> mylist;
    // Tạo Constructor để MainActivity gọi đến và truyền các tham số
    public MyArrayAdapter(Activity context, int idlayout, ArrayList<phone> mylist) {
        super(context, idlayout, mylist); this.context = context; this.idlayout = idlayout; this.mylist = mylist;
    }
    //Gọi đến hàm getView để xây dựng lại Adapter
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflactor = context.getLayoutInflater(); convertView = myInflactor.inflate(idlayout,null);
        phone myphone = mylist.get(position);
// Ứng với mỗi thuộc tính, ta thực hiện 2 việc
//- Gán id
        ImageView imgphone  =  convertView.findViewById(R.id.imgphone);
// - Thiết lập dữ liệu
        imgphone.setImageResource(myphone.getImagephone());
//	textview
        TextView txtnamephone = convertView.findViewById(R.id.txtnamephone); txtnamephone.setText(myphone.getNamephone());
        return  convertView;
    }
}
