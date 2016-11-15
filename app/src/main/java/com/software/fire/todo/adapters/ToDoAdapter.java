package com.software.fire.todo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.software.fire.todo.R;
import com.software.fire.todo.database.DBHelper;
import com.software.fire.todo.utils.dateUtils;

/**
 * Created by Brad on 11/10/2016.
 */

public class ToDoAdapter extends CursorAdapter {

    public ToDoAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context)
                .inflate(R.layout.item_layout_todo, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvDescription = (TextView) view.findViewById(R.id.description_view);
        TextView tvTime = (TextView) view.findViewById(R.id.time_view);

        //Extract properties from cursor
        String description = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION));
        String time = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_TIME));

        //Populate fields with extracted properties
        tvDescription.setText(description);
        tvTime.setText(dateUtils.getReadableTime(time));
    }
}
