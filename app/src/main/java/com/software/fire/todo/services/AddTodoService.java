package com.software.fire.todo.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.software.fire.todo.database.DBHelper;

/**
 * Created by Brad on 11/10/2016.
 */

public class AddTodoService extends IntentService {
    private static final String TAG = AddTodoService.class.getSimpleName();

    public static final String ACTION_INSERT = TAG + ".INSERT";
    public static final String ACTION_UPDATE = TAG + ".UPDATE";
    public static final String EXTRA_VALUES = TAG + ".ContentValues";
    private static Context mContext;

    public static void insertNewTodo(Context context, ContentValues values) {
        mContext = context;
        Intent intent = new Intent(context, AddTodoService.class);
        intent.setAction(ACTION_INSERT);
        intent.putExtra(EXTRA_VALUES, values);
        context.startService(intent);
    }

    public AddTodoService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (ACTION_INSERT.equals(intent.getAction())) {
            ContentValues values = intent.getParcelableExtra(EXTRA_VALUES);
            performInsert(values);
        }else{
            ContentValues values = intent.getParcelableExtra(EXTRA_VALUES);
            performUpdate(values);
        }
    }

    private void performUpdate(ContentValues values) {
        DBHelper dbHelper = new DBHelper(mContext);
        dbHelper.updateContact(values);
    }

    private void performInsert(ContentValues values) {
        DBHelper dbHelper = new DBHelper(mContext);
        dbHelper.addTodo(values);
    }

    public static void updateTodo(Context context, ContentValues values) {
        mContext = context;
        Intent intent = new Intent(context, AddTodoService.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_VALUES, values);
        context.startService(intent);
    }
}
