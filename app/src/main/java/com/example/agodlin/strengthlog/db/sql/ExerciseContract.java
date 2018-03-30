package com.example.agodlin.strengthlog.db.sql;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by agodlin on 7/21/2017.
 */

public class ExerciseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ExerciseContract() {}

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableEntry.TABLE_NAME + " (" +
                    TableEntry._ID + " INTEGER PRIMARY KEY," +
                    TableEntry.COLUMN_NAME_DATE + " INTEGER," +
                    TableEntry.COLUMN_NAME_EXERCISE + " TEXT," +
                    TableEntry.COLUMN_NAME_SET + " TEXT," +
                    TableEntry.COLUMN_NAME_COMMENT + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TableEntry.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static class TableEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercises";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_EXERCISE = "exercise";
        public static final String COLUMN_NAME_SET = "sets";
        public static final String COLUMN_NAME_COMMENT = "comment";
    }
}