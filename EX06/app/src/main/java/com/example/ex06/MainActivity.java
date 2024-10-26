package com.example.ex06;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtID, edtAdditionalInfo;
    private RadioGroup radioGroupDegree;
    private RadioButton radioButtonDefault;
    private CheckBox chkNews, chkBooks, chkCoding;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edtName = findViewById(R.id.edtName);
        edtID = findViewById(R.id.edtID);
        edtAdditionalInfo = findViewById(R.id.edtAdditionalInfo);
        radioGroupDegree = findViewById(R.id.radioGroupDegree);
        radioButtonDefault = findViewById(R.id.radioButtonUniversity);
        radioButtonDefault.setChecked(true);

        chkNews = findViewById(R.id.chkNews);
        chkBooks = findViewById(R.id.chkBooks);
        chkCoding = findViewById(R.id.chkCoding);

        btnSubmit = findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }


    private void submitForm() {
        String name = edtName.getText().toString();
        String id = edtID.getText().toString();
        String additionalInfo = edtAdditionalInfo.getText().toString();
        String degree = ((RadioButton) findViewById(radioGroupDegree.getCheckedRadioButtonId())).getText().toString();


        if (TextUtils.isEmpty(name) || name.length() < 3) {
            Toast.makeText(this, "Tên phải có ít nhất 3 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(id) || id.length() != 9 || !id.matches("\\d+")) {
            Toast.makeText(this, "CMND phải có đúng 9 chữ số", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!chkNews.isChecked() && !chkBooks.isChecked() && !chkCoding.isChecked()) {
            Toast.makeText(this, "Phải chọn ít nhất 1 sở thích", Toast.LENGTH_SHORT).show();
            return;
        }


        StringBuilder hobbies = new StringBuilder();
        if (chkNews.isChecked()) hobbies.append("Đọc báo - ");
        if (chkBooks.isChecked()) hobbies.append("Đọc sách - ");
        if (chkCoding.isChecked()) hobbies.append("Đọc coding");


        if (hobbies.toString().endsWith(" - ")) {
            hobbies.setLength(hobbies.length() - 3);
        }


        new AlertDialog.Builder(this)
                .setTitle("Thông tin cá nhân")
                .setMessage("Tên: " + name + "\n" +
                        "CMND: " + id + "\n" +
                        "Bằng cấp: " + degree + "\n" +
                        "Sở thích: " + hobbies.toString() + "\n" +
                        "Thông tin bổ sung: " + (TextUtils.isEmpty(additionalInfo) ? "Không có" : additionalInfo))
                .setPositiveButton("Đóng", null)
                .show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("Question")
                    .setMessage("Are you sure you want to exit?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("NO", null)
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
