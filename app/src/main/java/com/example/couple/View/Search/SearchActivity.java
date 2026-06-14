package com.example.couple.View.Search;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.R;

public class SearchActivity extends ActivityBase {
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearch = findViewById(R.id.edtSearch);
        edtSearch.requestFocus();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
