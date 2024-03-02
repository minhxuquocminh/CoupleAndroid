package com.example.couple.Base.Handler;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsoupBase extends AsyncTask<String, Void, String> {
    Context context;
    String link;
    int timeout;
    List<String> listClassName;
    HashMap<String, String> hashMap = new HashMap<>();

    boolean isInternetAvailable;

    public JsoupBase(Context context, String link, int timeout, List<String> listClassName) {
        this.isInternetAvailable = InternetBase.isInternetAvailable(context);
        this.context = context;
        this.link = link;
        this.timeout = timeout;
        this.listClassName = listClassName;
    }

    public JsoupBase(Context context, String link, int timeout,
                     List<String> listClassName, HashMap<String, String> hashMap) {
        this.isInternetAvailable = InternetBase.isInternetAvailable(context);
        this.context = context;
        this.link = link;
        this.timeout = timeout;
        this.listClassName = listClassName;
        this.hashMap = hashMap;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (!isInternetAvailable) return "";
        String data = "";

        try {
            if (hashMap.isEmpty()) {
                data = methodGET();
            } else {
                data = methodPOST();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("datajsoup", data);

        return data;
    }

    private String methodGET() throws IOException {
        String data = "";

        Document doc = Jsoup.connect(link).timeout(timeout).get();
        if (listClassName == null || listClassName.isEmpty()) return doc.text();

        for (int i = 0; i < listClassName.size(); i++) {
            Elements elements = doc.getElementsByClass(listClassName.get(i));
            for (int j = 0; j < elements.size(); j++) {
                data += elements.get(j).text();
                if (j != elements.size() - 1) {
                    data += "---";
                }
            }
            if (i != listClassName.size() - 1) {
                data += "===";
            }
        }
        return data;
    }

    private String methodPOST() throws IOException {
        String data = "";

        Connection conn = Jsoup.connect(link).timeout(timeout);

        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            conn.data(key, value);
        }

        Document doc = conn.userAgent("Mozilla").post();
        if (listClassName == null || listClassName.isEmpty()) return doc.text();

        for (int i = 0; i < listClassName.size(); i++) {
            Elements elements = doc.getElementsByClass(listClassName.get(i));
            for (int j = 0; j < elements.size(); j++) {
                data += elements.get(j).text();
                if (j != elements.size() - 1) {
                    data += "---";
                }
            }
            if (i != listClassName.size() - 1) {
                data += "===";
            }
        }
        return data;
    }
}
