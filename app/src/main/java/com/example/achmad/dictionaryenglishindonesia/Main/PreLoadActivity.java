package com.example.achmad.dictionaryenglishindonesia.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.achmad.dictionaryenglishindonesia.Helper.DictionaryHelper;
import com.example.achmad.dictionaryenglishindonesia.Model.DictionaryModel;
import com.example.achmad.dictionaryenglishindonesia.Preference.DictionaryPreference;
import com.example.achmad.dictionaryenglishindonesia.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by Achmad
 * 16 august 2018
 */

public class PreLoadActivity extends AppCompatActivity {
    @BindView(R.id.progreesBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_load);
        ButterKnife.bind(this);
        new LoadData().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadData extends AsyncTask<Void, Integer, Void> {
        DictionaryHelper dictionaryHelper;
        DictionaryPreference dictionaryPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            dictionaryHelper = new DictionaryHelper(PreLoadActivity.this);
            dictionaryPreference = new DictionaryPreference(PreLoadActivity.this);
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = dictionaryPreference.getFirstRun();
            if (firstRun) {
                ArrayList<DictionaryModel> dictionaryEnglish = preLoadRaw(R.raw.english_indonesia);
                ArrayList<DictionaryModel> dictionaryIndonesia = preLoadRaw(R.raw.indonesia_english);

                publishProgress((int) progress);

                try {
                    dictionaryHelper.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) / (dictionaryEnglish.size() + dictionaryIndonesia.size());

                dictionaryHelper.insertTransaction(dictionaryEnglish, true);
                progress += progressDiff;
                publishProgress((int) progress);

                dictionaryHelper.insertTransaction(dictionaryIndonesia, false);
                progress += progressDiff;
                publishProgress((int) progress);

                dictionaryHelper.close();
                dictionaryPreference.setFirstRun(false);

                publishProgress((int) maxprogress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(1000);
                        publishProgress(50);

                        this.wait(300);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception ignored) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(PreLoadActivity.this, MainActivity.class);
            startActivity(i);

            finish();
        }
    }

    public ArrayList<DictionaryModel> preLoadRaw(int selection) {
        ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(selection);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            String line;
            do {
                line = reader.readLine();
                String[] splits = line.split("\t");
                DictionaryModel dictionaryModel;
                dictionaryModel = new DictionaryModel(splits[0], splits[1]);
                dictionaryModels.add(dictionaryModel);
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dictionaryModels;
    }
}

