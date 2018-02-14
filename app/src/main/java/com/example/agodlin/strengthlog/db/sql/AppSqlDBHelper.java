package com.example.agodlin.strengthlog.db.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.db.sql.ExerciseContract;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by agodlin on 7/21/2017.
 */

public class AppSqlDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "StrengthLog.db";

    public AppSqlDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ExerciseContract.SQL_DELETE_ENTRIES);
        db.execSQL(ExerciseContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ExerciseContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public void insert(Exercise exercise)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        Gson gson = new Gson();
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ExerciseContract.TableEntry.COLUMN_NAME_DATE, gson.toJson(exercise.date));
        values.put(ExerciseContract.TableEntry.COLUMN_NAME_EXERCISE, exercise.name);
        values.put(ExerciseContract.TableEntry.COLUMN_NAME_SET, gson.toJson(exercise.sets));

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ExerciseContract.TableEntry.TABLE_NAME, null, values);
    }

    public List<Exercise> readAll()
    {
        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                ExerciseContract.TableEntry.COLUMN_NAME_DATE,
                ExerciseContract.TableEntry.COLUMN_NAME_EXERCISE,
                ExerciseContract.TableEntry.COLUMN_NAME_SET
        };

// Filter results WHERE "title" = 'My Title'
        String selection = ExerciseContract.TableEntry.COLUMN_NAME_DATE + " = ?";
        String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                ExerciseContract.TableEntry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(
                ExerciseContract.TableEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Gson gson = new Gson();
        List<Exercise> items = new ArrayList<>();
        while(cursor.moveToNext()) {
            String date = cursor.getString(0);
            String name = cursor.getString(1);
            String setsJsonString = cursor.getString(2);
            Type listType = new TypeToken<ArrayList<com.example.agodlin.strengthlog.common.Set>>(){}.getType();
            List<com.example.agodlin.strengthlog.common.Set> sets = new Gson().fromJson(setsJsonString, listType);
            items.add(new Exercise(name, gson.fromJson(date, Date.class), sets));
        }
        cursor.close();
        return items;
    }

    void deleteExercise()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define 'where' part of query.
        String selection = ExerciseContract.TableEntry.COLUMN_NAME_DATE + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { "MyTitle" };
// Issue SQL statement.
        db.delete(ExerciseContract.TableEntry.TABLE_NAME, selection, selectionArgs);
    }

    void update(Exercise exercise)
    {
        SQLiteDatabase db = this.getWritableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ExerciseContract.TableEntry.COLUMN_NAME_DATE, exercise.date.toString());

// Which row to update, based on the title
        String selection = ExerciseContract.TableEntry.COLUMN_NAME_DATE + " LIKE ?";
        String[] selectionArgs = { "MyTitle" };

        int count = db.update(
                ExerciseContract.TableEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
