package com.kienluxury.ex23;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ListView lv1;
    ArrayList<List> mylist;
    MyArrayAdapter myadapter;
    String nodeName, title = "", link = "", description = "", des = "", urlStr = "";
    Bitmap mIcon_val = null;
    String URL = "https://vnexpress.net/rss/tin-moi-nhat.rss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tham chiếu ListView
        lv1 = findViewById(R.id.lv1);

        // Khởi tạo danh sách và adapter
        mylist = new ArrayList<>();
        myadapter = new MyArrayAdapter(MainActivity.this, R.layout.layout_listview, mylist);

        // Gán adapter cho ListView
        lv1.setAdapter(myadapter);

        // Bắt đầu tải dữ liệu từ web service
        LoadExampleTask task = new LoadExampleTask();
        task.execute();
    }

    // Lớp AsyncTask để lấy dữ liệu từ URL và cập nhật ListView
    class LoadExampleTask extends AsyncTask<Void, Void, ArrayList<List>> {

        @Override
        protected ArrayList<List> doInBackground(Void... voids) {
            mylist = new ArrayList<>();

            try {
                // Tạo đối tượng Parser để chứa dữ liệu từ file XML
                XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
                XmlPullParser parser = fc.newPullParser();

                // Tạo đối tượng XMLParser và gọi phương thức getXmlFromUrl
                XMLParser myparser = new XMLParser();
                String xml = myparser.getXmlFromUrl(URL); // Lấy XML từ URL

                // Gán dữ liệu từ chuỗi XML vào parser
                parser.setInput(new StringReader(xml));

                // Bắt đầu phân tích dữ liệu XML
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    eventType = parser.next();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("title")) {
                                title = parser.nextText();
                            } else if (nodeName.equals("link")) {
                                link = parser.nextText();
                            } else if (nodeName.equals("description")) {
                                description = parser.nextText();

                                // Tách URL ảnh từ mô tả
                                try {
                                    urlStr = description.substring(
                                            description.indexOf("src=") + 5,
                                            description.indexOf("></a") - 2
                                    );
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                // Lấy mô tả chi tiết từ chuỗi description
                                des = description.substring(description.indexOf("</br>") + 5);

                                // Tải hình ảnh từ URL
                                try {
                                    URL newurl = new URL(urlStr);
                                    mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("item")) {
                                mylist.add(new List(mIcon_val, title, des, link));
                            }
                            break;
                    }
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }

            return mylist;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Xóa dữ liệu cũ trong adapter
            myadapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<List> result) {
            super.onPostExecute(result);
            // Cập nhật adapter với dữ liệu mới
            myadapter.clear();
            myadapter.addAll(result);
        }
    }
}
