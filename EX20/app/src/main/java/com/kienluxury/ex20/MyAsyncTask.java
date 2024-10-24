package com.kienluxury.ex20;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class  extends Activity {

    Button btnStart;
    MyAsyncTask mytt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.button1);
        btnStart.setOnClickListener(v -> doStart());
    }

    private void doStart() {
        mytt = new MyAsyncTask(this);
        mytt.execute();
    }

    // Lớp AsyncTask
    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        Activity contextCha;

        // Constructor truyền vào MainActivity
        public MyAsyncTask(Activity ctx) {
            contextCha = ctx;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(contextCha, "onPreExecute!", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            for (int i = 0; i <= 100; i++) {
                SystemClock.sleep(100); // Nghỉ 100ms rồi cập nhật UI
                publishProgress(i); // Gọi onProgressUpdate()
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            ProgressBar paCha = contextCha.findViewById(R.id.progressBar1);
            int giatri = values[0];
            paCha.setProgress(giatri);

            TextView txtmsg = contextCha.findViewById(R.id.textView1);
            txtmsg.setText(giatri + "%");
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(contextCha, "Update xong rồi đó!", Toast.LENGTH_LONG).show();
        }
    }
}
