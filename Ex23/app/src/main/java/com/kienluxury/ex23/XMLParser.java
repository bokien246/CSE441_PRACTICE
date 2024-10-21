package com.kienluxury.ex23;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XMLParser {

    public String getXmlFromUrl(String urlString) {
        StringBuilder xml = new StringBuilder();
        try {
            // Tạo đối tượng URL và mở kết nối
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000); // Thời gian chờ kết nối
            connection.setReadTimeout(10000);    // Thời gian chờ đọc dữ liệu

            // Đọc dữ liệu từ kết nối
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                xml.append(line);
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return xml.toString();
    }
}
