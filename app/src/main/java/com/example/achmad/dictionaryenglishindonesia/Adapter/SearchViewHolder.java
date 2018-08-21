package com.example.achmad.dictionaryenglishindonesia.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.achmad.dictionaryenglishindonesia.Main.DetailDictionaryActivity;
import com.example.achmad.dictionaryenglishindonesia.Model.DictionaryModel;
import com.example.achmad.dictionaryenglishindonesia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by Achmad
 * 16 august 2018
 */

public class SearchViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_word)
    TextView tv_word;
    @BindView(R.id.tv_desc)
    TextView tv_desc;

    SearchViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(final DictionaryModel dictionaryModel) {
        tv_word.setText(dictionaryModel.getWord());
        tv_desc.setText(dictionaryModel.getDescription());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetailDictionaryActivity.class);
                intent.putExtra(DetailDictionaryActivity.ITEM_WORD, dictionaryModel.getWord());
                intent.putExtra(DetailDictionaryActivity.ITEM_DESC, dictionaryModel.getDescription());
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
