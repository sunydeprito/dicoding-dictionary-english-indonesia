package com.example.achmad.dictionaryenglishindonesia.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.achmad.dictionaryenglishindonesia.Model.DictionaryModel;
import com.example.achmad.dictionaryenglishindonesia.R;

import java.util.ArrayList;

/**
 * created by Achmad
 * 16 august 2018
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private ArrayList<DictionaryModel> arrayList = new ArrayList<>();

    public SearchAdapter() {

    }

    public void replaceAll(ArrayList<DictionaryModel> items) {
        arrayList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
