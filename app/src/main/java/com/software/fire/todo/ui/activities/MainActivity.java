package com.software.fire.todo.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.software.fire.todo.R;
import com.software.fire.todo.adapters.ToDoAdapter;
import com.software.fire.todo.database.TodoLoader;
import com.software.fire.todo.models.Todo;
import com.software.fire.todo.ui.dialogs.EditTodoDialog;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView mTodoList;
    private ToDoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddToDoActivity.class));
            }
        });

        initialiseScreen();

        getSupportLoaderManager().initLoader(0, null, MainActivity.this);

    }

    private void initialiseScreen() {
        mTodoList = (ListView) findViewById(R.id.todo_listview);
        mAdapter = new ToDoAdapter(MainActivity.this, null);
        mTodoList.setAdapter(mAdapter);
        mTodoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                cursor.moveToPosition(i);
                int id = cursor.getInt(0);
                String description = cursor.getString(1);
                String time = cursor.getString(2);
                Todo todo = new Todo();
                todo.setId(id);
                todo.setTime(time);
                todo.setDescription(description);
                EditTodoDialog dialog = new EditTodoDialog(todo, mAdapter);
                dialog.show(getSupportFragmentManager(), null);
            }
        });

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new TodoLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_about){
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
