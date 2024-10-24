package com.kienluxury.ex20;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button btnStart; // Nút bắt đầu tiến trình
    private ProgressBar progressBar; // ProgressBar để hiển thị tiến trình
    private TextView textView; // TextView để hiển thị phần trăm tiến trình
    private MyAsyncTask myTask; // Lớp AsyncTask để thực hiện tiến trình nền

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Gắn layout cho MainActivity

        // Ánh xạ các thành phần giao diện
        btnStart = findViewById(R.id.button1);
        progressBar = findViewById(R.id.progressBar1);
        textView = findViewById(R.id.textView1);

        // Sự kiện khi nhấn nút Start
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTask(); // Bắt đầu tiến trình nền
            }
        });
    }

    private void startTask() {
        myTask = new MyAsyncTask(this); // Khởi tạo AsyncTask với ngữ cảnh MainActivity
        myTask.execute(); // Thực thi tiến trình nền
    }

    // Lớp AsyncTask thực hiện cập nhật tiến trình
    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        private Activity context; // Lưu trữ ngữ cảnh của MainActivity

        public MyAsyncTask(Activity context) {
            this.context = context; // Gán ngữ cảnh
        }

        @Override
        protected void onPreExecute() {
            // Thông báo khi bắt đầu tiến trình
            Toast.makeText(context, "Bắt đầu tiến trình...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Tiến hành cập nhật tiến trình
            for (int i = 0; i <= 100; i++) {
                SystemClock.sleep(100); // Nghỉ 100ms trước khi cập nhật
                publishProgress(i); // Gọi onProgressUpdate() để cập nhật giao diện
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Cập nhật giao diện người dùng
            int progress = values[0];
            progressBar.setProgress(progress); // Cập nhật giá trị ProgressBar
            textView.setText(progress + "%"); // Cập nhật phần trăm trên TextView
        }

        @Override
        protected void onPostExecute(Void result) {
            // Thông báo khi hoàn thành tiến trình
            Toast.makeText(context, "Hoàn thành tiến trình!", Toast.LENGTH_LONG).show();
        }
    }
}