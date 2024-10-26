package com.example.ex04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText edtdoF, edtdoC;
    private Button btnfc, btncf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtdoF = findViewById(R.id.edtdoF);
        edtdoC = findViewById(R.id.edtdoC);
        btnfc = findViewById(R.id.btnfc);
        btncf = findViewById(R.id.btncf);

        // Nút chuyển từ Celsius sang Fahrenheit
        btnfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat dcf = new DecimalFormat("#.00");
                String doC = edtdoC.getText().toString();

                if (!doC.isEmpty()) {
                    try {
                        double C = Double.parseDouble(doC);  // Dùng Double để chuyển đổi số có phần thập phân
                        double F = 1.8 * C + 32;
                        edtdoF.setText(dcf.format(F));  // Chỉ dùng `dcf.format(F)` để định dạng số
                    } catch (NumberFormatException e) {
                        edtdoF.setText("Lỗi đầu vào");
                    }
                } else {
                    edtdoF.setText("Nhập giá trị C");
                }
            }
        });

        // Nút chuyển từ Fahrenheit sang Celsius
        btncf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat dcf = new DecimalFormat("#.00");
                String doF = edtdoF.getText().toString();

                if (!doF.isEmpty()) {
                    try {
                        double F = Double.parseDouble(doF);
                        double C = (F - 32) / 1.8;
                        edtdoC.setText(dcf.format(C));
                    } catch (NumberFormatException e) {
                        edtdoC.setText("Lỗi đầu vào");
                    }
                } else {
                    edtdoC.setText("Nhập giá trị F");
                }
            }
        });
    }
}
