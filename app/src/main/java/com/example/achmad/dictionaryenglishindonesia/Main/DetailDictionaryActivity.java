package com.example.achmad.dictionaryenglishindonesia.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.achmad.dictionaryenglishindonesia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by Achmad
 * 16 august 2018
 */

public class DetailDictionaryActivity extends AppCompatActivity {
    public static final String ITEM_WORD = "item_word";
    public static final String ITEM_DESC = "item_description";

    @BindView(R.id.tv_word_detail)
    TextView tv_word_detail;
    @BindView(R.id.tv_desc_detail)
    TextView tv_desc_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dictionary);
        ButterKnife.bind(this);

        tv_word_detail.setText(getIntent().getStringExtra(ITEM_WORD));
        tv_desc_detail.setText(getIntent().getStringExtra(ITEM_DESC));
    }
}
