package com.example.achmad.dictionaryenglishindonesia.Helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.achmad.dictionaryenglishindonesia.Database.DatabaseContract;
import com.example.achmad.dictionaryenglishindonesia.Model.DictionaryModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.achmad.dictionaryenglishindonesia.Database.DatabaseContract.DictionaryColumns.DESC;
import static com.example.achmad.dictionaryenglishindonesia.Database.DatabaseContract.DictionaryColumns.WORD;

/**
 * created by Achmad
 * 16 august 2018
 */

public class DictionaryHelper {
    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private static String ENGLISH = DatabaseContract.TABLE_EN_TO_ID;
    private static String INDONESIA = DatabaseContract.TABLE_ID_TO_EN;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor cursorAllData(boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " ORDER BY " + _ID + " ASC ", null);
    }

    private Cursor searchQuery(String query, boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + WORD + " LIKE '%" + query.trim() + "%'", null);
    }

    public ArrayList<DictionaryModel> getDataByName(String find, boolean isEnglish) {
        DictionaryModel dictionaryModel;
        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        Cursor cursor = searchQuery(find, isEnglish);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId((cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns._ID))));
                dictionaryModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.WORD)));
                dictionaryModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.DESC)));
                arrayList.add(dictionaryModel);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<DictionaryModel> getAllData(boolean isEnglish) {
        DictionaryModel dictionaryModel;

        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        Cursor cursor = cursorAllData(isEnglish);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns._ID)));
                dictionaryModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.WORD)));
                dictionaryModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DictionaryColumns.DESC)));
                arrayList.add(dictionaryModel);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(DictionaryModel dictionaryModel, boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.DictionaryColumns.WORD, dictionaryModel.getWord());
        contentValues.put(DatabaseContract.DictionaryColumns.DESC, dictionaryModel.getDescription());
        return database.insert(DATABASE_TABLE, null, contentValues);
    }

    public void insertTransaction(ArrayList<DictionaryModel> dictionaryModels, boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        String sql = "INSERT INTO " + DATABASE_TABLE + " (" + WORD + ", " +
                DESC + ") VALUES (?, ?)";
        database.beginTransaction();

        SQLiteStatement statement = database.compileStatement(sql);
        for (int i = 0; i < dictionaryModels.size(); i++) {
            statement.bindString(1, dictionaryModels.get(i).getWord());
            statement.bindString(2, dictionaryModels.get(i).getDescription());
            statement.execute();
            statement.clearBindings();

        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }


    public void update(DictionaryModel dictionaryModel, boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORD, dictionaryModel.getWord());
        contentValues.put(DESC, dictionaryModel.getDescription());
        database.update(DATABASE_TABLE, contentValues, _ID + "= '" + dictionaryModel.getId() + "'", null);
    }

    public void delete(int id, boolean isEnglish) {
        String DATABASE_TABLE = isEnglish ? ENGLISH : INDONESIA;
        database.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
    }
}
