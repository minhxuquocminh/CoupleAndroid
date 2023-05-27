package com.example.couple.Base.Handler;

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
    String link;
    List<String> listClassName;
    HashMap<String, String> hashMap = new HashMap<>();

    public JsoupBase(String link, List<String> listClassName) {
        this.link = link;
        this.listClassName = listClassName;
    }

    public JsoupBase(String link, List<String> listClassName, HashMap<String, String> hashMap) {
        this.link = link;
        this.listClassName = listClassName;
        this.hashMap = hashMap;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = "";

        try {
            if (hashMap.size() == 0) {
                data = methodGET();
            } else {
                data = methodPOST();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("datajsoup",data);

        return data;
    }

    private String methodGET() throws IOException {
        String data = "";

        Document doc = (Document) Jsoup.connect(link).get();
        int size_listClassName = (listClassName == null) ? 0 : listClassName.size();

        if (size_listClassName == 0) {
            data += doc.text();
        } else {
            for (int i = 0; i < size_listClassName; i++) {
                Elements elements = doc.getElementsByClass(listClassName.get(i));
                for (int j = 0; j < elements.size(); j++) {
                    data += elements.get(j).text();
                    if (j != elements.size() - 1) {
                        data += "---";
                    }
                }
                if (i != size_listClassName - 1) {
                    data += "===";
                }
            }
        }
        return data;
    }

    private String methodPOST() throws IOException {
        String data = "";

        Connection conn = Jsoup.connect(link);

        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            conn = conn.data(key, value);
        }

        Document doc = conn.userAgent("Mozilla").post();
        int size_listClassName = (listClassName == null) ? 0 : listClassName.size();

        if (size_listClassName == 0) {
            data += doc.text();
        } else {
            for (int i = 0; i < size_listClassName; i++) {
                Elements elements = doc.getElementsByClass(listClassName.get(i));
                for (int j = 0; j < elements.size(); j++) {
                    data += elements.get(j).text();
                    if (j != elements.size() - 1) {
                        data += "---";
                    }
                }
                if (i != size_listClassName - 1) {
                    data += "===";
                }
            }
        }
        return data;
    }
}
