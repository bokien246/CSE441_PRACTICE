package com.example.ex12_2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtWork, edtHour, edtMinute;
    private Button btnAdd;
    private ListView listView;
    private List<String> workList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtWork = findViewById(R.id.edtWork);
        edtHour = findViewById(R.id.edtHour);
        edtMinute = findViewById(R.id.edtMinute);
        btnAdd = findViewById(R.id.btnAdd);
        listView = findViewById(R.id.listView);

        workList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, workList);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String work = edtWork.getText().toString();
                String hour = edtHour.getText().toString();
                String minute = edtMinute.getText().toString();

                if (work.isEmpty() || hour.isEmpty() || minute.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String time = hour + ":" + (minute.length() == 1 ? "0" + minute : minute);
                    String workItem = "+ " + work + " - " + time;
                    workList.add(workItem);
                    adapter.notifyDataSetChanged();

                    edtWork.setText("");
                    edtHour.setText("");
                    edtMinute.setText("");
                }
            }
        });
    }
}