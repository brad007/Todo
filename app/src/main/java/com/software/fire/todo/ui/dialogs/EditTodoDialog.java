package com.software.fire.todo.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.software.fire.todo.R;
import com.software.fire.todo.adapters.ToDoAdapter;
import com.software.fire.todo.database.DBHelper;
import com.software.fire.todo.models.Todo;
import com.software.fire.todo.ui.activities.AddToDoActivity;

/**
 * Created by Brad on 11/10/2016.
 */

@SuppressLint("ValidFragment")
public class EditTodoDialog extends DialogFragment {

    private Todo mTodo;
    private ToDoAdapter mAdapter;

    public EditTodoDialog(Todo todo, ToDoAdapter adapter) {

        mTodo = todo;
        mAdapter = adapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Group Menu")
                .setItems(R.array.todo_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(getContext(), AddToDoActivity.class);
                                intent.putExtra(DBHelper.KEY_ID, mTodo.getId());
                                intent.putExtra(DBHelper.KEY_DESCRIPTION, mTodo.getDescription());
                                intent.putExtra(DBHelper.KEY_TIME, mTodo.getTime());
                                getContext().startActivity(intent);
                                break;
                            case 1:
                                DBHelper dbHelper = new DBHelper(getContext());
                                int num = dbHelper.deleteTodo(mTodo);
                                Toast.makeText(getContext(), "Todo Deleted: " + num, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
        return builder.create();
    }
}
