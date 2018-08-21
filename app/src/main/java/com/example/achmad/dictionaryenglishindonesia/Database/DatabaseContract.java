package com.example.achmad.dictionaryenglishindonesia.Database;

import android.provider.BaseColumns;

/**
 * created by Achmad
 * 16 august 2018
 */

public class DatabaseContract {

    public static String TABLE_ID_TO_EN = "id_to_en";
    public static String TABLE_EN_TO_ID = "en_to_id";

    public static final class DictionaryColumns implements BaseColumns {
        public static String WORD = "word";
        public static String DESC = "description";
    }
}
