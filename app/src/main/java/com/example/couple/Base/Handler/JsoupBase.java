package com.example.couple.Base.Handler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsoupBase extends AsyncTask<String, Void, String> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String link;
    int timeout;
    List<String> elementClasses;
    Map<String, String> postData = new HashMap<>();

    boolean isInternetAvailable;

    public JsoupBase(Context context, String link, int timeout, List<String> elementClasses) {
        super();
        this.isInternetAvailable = InternetBase.isInternetAvailable(context);
        this.context = context;
        this.link = link;
        this.timeout = timeout;
        this.elementClasses = elementClasses;
    }

    public JsoupBase(Context context, String link, int timeout,
                     List<String> elementClasses, Map<String, String> postData) {
        super();
        this.isInternetAvailable = InternetBase.isInternetAvailable(context);
        this.context = context;
        this.link = link;
        this.timeout = timeout;
        this.elementClasses = elementClasses;
        this.postData = postData;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (!isInternetAvailable) return "";
        Document doc = null;

        try {
            if (postData.isEmpty()) {
                doc = Jsoup.connect(link).timeout(timeout).get();
            } else {
                doc = Jsoup.connect(link).timeout(timeout).data(postData).userAgent("Mozilla").post();
            }
        } catch (IOException ignored) {

        }

        if (doc == null) return "";
        if (elementClasses == null || elementClasses.isEmpty()) return doc.text();
        Document finalDoc = doc;
        return elementClasses.stream().map(elementClass ->
                finalDoc.getElementsByClass(elementClass).stream()
                        .map(Element::text).collect(Collectors.joining("---"))
        ).collect(Collectors.joining("==="));
    }

}
