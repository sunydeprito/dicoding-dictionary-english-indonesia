package com.example.achmad.dictionaryenglishindonesia.Main;

import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.achmad.dictionaryenglishindonesia.Adapter.SearchAdapter;
import com.example.achmad.dictionaryenglishindonesia.Helper.DictionaryHelper;
import com.example.achmad.dictionaryenglishindonesia.Model.DictionaryModel;
import com.example.achmad.dictionaryenglishindonesia.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by Achmad
 * 16 august 2018
 */

public class MainActivity extends AppCompatActivity implements NavigationView.
        OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    @BindView(R.id.search_view)
    SearchView search_view;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private DictionaryHelper dictionaryHelper;
    private SearchAdapter adapter;

    private ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();
    private boolean isEnglish = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);

        dictionaryHelper = new DictionaryHelper(this);

        search_view.onActionViewExpanded();
        search_view.setOnQueryTextListener(this);

        setupList();
        loadData();
        nav_view.getMenu().getItem(0).setChecked(true);


    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_english_indonesia) {
            isEnglish = true;

            loadData();
        }

        if (id == R.id.nav_indonesia_english) {
            isEnglish = false;

            loadData();
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setupList() {
        adapter = new SearchAdapter();
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(adapter);
    }

    private void loadData(String search) {
        try {
            dictionaryHelper.open();
            if (search.isEmpty()) {
                dictionaryModels = dictionaryHelper.getAllData(isEnglish);
            } else {
                dictionaryModels = dictionaryHelper.getDataByName(search, isEnglish);
            }
            String hint;
            if (isEnglish) {
                getSupportActionBar().setSubtitle(getResources().getString(R.string.english_indonesia));
                hint = getResources().getString(R.string.find_word);
            } else {
                getSupportActionBar().setSubtitle(getResources().getString(R.string.indonesia_english));
                hint = getResources().getString(R.string.cari_kosakata);
            }
            search_view.setQueryHint(hint);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dictionaryHelper.close();
        }
        adapter.replaceAll(dictionaryModels);
    }

    private void loadData() {
        loadData("");
    }

    @Override
    public boolean onQueryTextSubmit(String keyword) {
        loadData(keyword);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String keyword) {
        loadData(keyword);
        return false;
    }
}

