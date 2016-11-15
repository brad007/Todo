package com.software.fire.todo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Brad on 11/10/2016.
 */

public class TodoLoader extends AsyncTaskLoader<Cursor> {
    private static final String TAG = TodoLoader.class.getSimpleName();

    public TodoLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        DBHelper helper = new DBHelper(this.getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor todoCursor = db.rawQuery("SELECT * FROM todo ORDER BY " + DBHelper.KEY_TIME + " ASC", null);
        return  todoCursor;
    }
}
