package com.example.agodlin.strengthlog.db.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.agodlin.strengthlog.common.Date;
import com.example.agodlin.strengthlog.common.Exercise;
import com.example.agodlin.strengthlog.db.sql.ExerciseContract;
import com.example.agodlin.strengthlog.ui.weight.BodyWeightItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
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

        db.execSQL(ExerciseContract.SQL_CREATE_ENTRIES);
        db.execSQL(BodyWeightContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ExerciseContract.SQL_DELETE_ENTRIES);
        db.execSQL(BodyWeightContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void reset()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(ExerciseContract.SQL_DELETE_ENTRIES);
        db.execSQL(ExerciseContract.SQL_CREATE_ENTRIES);
        db.execSQL(BodyWeightContract.SQL_DELETE_ENTRIES);
        db.execSQL(BodyWeightContract.SQL_CREATE_ENTRIES);
    }

    public Exercise insert(Exercise exercise)
    {
        // Gets the data repository in writePrivate mode
        SQLiteDatabase db = this.getWritableDatabase();

        Gson gson = new Gson();
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        calendar.set(exercise.date.year, exercise.date.month, exercise.date.day);
        values.put(ExerciseContract.TableEntry.COLUMN_NAME_DATE, calendar.getTimeInMillis());
        values.put(ExerciseContract.TableEntry.COLUMN_NAME_EXERCISE, exercise.name);
        values.put(ExerciseContract.TableEntry.COLUMN_NAME_SET, gson.toJson(exercise.sets));

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ExerciseContract.TableEntry.TABLE_NAME, null, values);
        if (newRowId < 0)
        {
            //TODO add exception
            Log.e("AppSqlDBHelper", "inset to db fail");
        }
        Exercise exercise1 = new Exercise(newRowId, exercise);

        return exercise1;
    }

    public BodyWeightItem insertWeight(BodyWeightItem bodyWeightItem)
    {
        // Gets the data repository in writePrivate mode
        SQLiteDatabase db = this.getWritableDatabase();

        Gson gson = new Gson();
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        calendar.set(bodyWeightItem.date.year, bodyWeightItem.date.month, bodyWeightItem.date.day);
        values.put(BodyWeightContract.TableEntry.COLUMN_NAME_DATE, calendar.getTimeInMillis());
        values.put(BodyWeightContract.TableEntry.COLUMN_NAME_WEIGHT, bodyWeightItem.weight);
        values.put(BodyWeightContract.TableEntry.COLUMN_NAME_COMMENT, bodyWeightItem.comment);

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(BodyWeightContract.TableEntry.TABLE_NAME, null, values);
        if (newRowId < 0)
        {
            //TODO add exception
            Log.e("AppSqlDBHelper", "inset to db fail");
        }
        BodyWeightItem bodyWeightItem1 = new BodyWeightItem(newRowId, bodyWeightItem);

        return bodyWeightItem1;
    }

    public List<BodyWeightItem> readBodyWeight()
    {
        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                BodyWeightContract.TableEntry.COLUMN_NAME_DATE,
                BodyWeightContract.TableEntry.COLUMN_NAME_WEIGHT,
                BodyWeightContract.TableEntry.COLUMN_NAME_COMMENT
        };

// Filter results WHERE "title" = 'My Title'
        String selection = BodyWeightContract.TableEntry.COLUMN_NAME_DATE + " = ?";
        String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BodyWeightContract.TableEntry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(
                BodyWeightContract.TableEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Gson gson = new Gson();
        List<BodyWeightItem> items = new ArrayList<>();
        while(cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            long millis = cursor.getLong(1);
            float weight = cursor.getFloat(2);
            String comment = cursor.getString(3);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);
            items.add(new BodyWeightItem(_id, new Date(calendar.get(Calendar.DATE),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR)), weight, comment));
        }
        cursor.close();
        return items;
    }

    public List<Exercise> readAll()
    {
        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
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
            int _id = cursor.getInt(0);
            long millis = cursor.getLong(1);
            String name = cursor.getString(2);
            String setsJsonString = cursor.getString(3);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);
            Type listType = new TypeToken<ArrayList<com.example.agodlin.strengthlog.common.Set>>(){}.getType();
            List<com.example.agodlin.strengthlog.common.Set> sets = new Gson().fromJson(setsJsonString, listType);
            items.add(new Exercise(_id, name, new Date(calendar.get(Calendar.DATE),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR)), sets));
        }
        cursor.close();
        return items;
    }

    public void deleteExercise(Exercise e)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define 'where' part of query.
        String selection = ExerciseContract.TableEntry._ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(e._id) };
// Issue SQL statement.
        int nRows = db.delete(ExerciseContract.TableEntry.TABLE_NAME, selection, selectionArgs);
        if (nRows != 1)
            Log.i("SQL", "Error delete expected single row but was: " +nRows);
    }

    public void deleteBodyWeight(BodyWeightItem bodyWeightItem)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define 'where' part of query.
        String selection = ExerciseContract.TableEntry._ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(bodyWeightItem._ID) };
// Issue SQL statement.
        int count = db.delete(BodyWeightContract.TableEntry.TABLE_NAME, selection, selectionArgs);
        if (count != 1)
            Log.i("SQL", "Error delete expected single row but was: " + count);
    }

    public void update(Exercise exercise)
    {
        SQLiteDatabase db = this.getWritableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        Gson gson = new Gson();
        calendar.set(exercise.date.year, exercise.date.month, exercise.date.day);
        values.put(ExerciseContract.TableEntry.COLUMN_NAME_DATE, calendar.getTimeInMillis());
        values.put(ExerciseContract.TableEntry.COLUMN_NAME_EXERCISE, exercise.name);
        values.put(ExerciseContract.TableEntry.COLUMN_NAME_SET, gson.toJson(exercise.sets));

// Which row to update, based on the title
        String selection = ExerciseContract.TableEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(exercise._id) };

        int count = db.update(
                ExerciseContract.TableEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        if (count != 1)
            Log.i("SQL", "Error update expected single row but was: " + count);
    }
}
