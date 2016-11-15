package com.software.fire.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.software.fire.todo.models.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brad on 11/10/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    //All static variables
    //Database version
    private static final int DATABASE_VERSION = 3;

    //Database name
    private static final String DATABASE_NAME = "todoManager";

    //ToDo table name
    private static final String TABLE_TODO = "todo";

    //ToDo table columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_TIME = "time";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_DESCRIPTION + " TEXT, "
                + KEY_TIME + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(CREATE_TODO_TABLE);
    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);

        //Create tables again
        onCreate(sqLiteDatabase);
    }

    //Adding new todo
    public void addTodo(ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        //Inserting Row
        db.insert(TABLE_TODO, null, values);
        db.close(); //Closing database connection
    }

    //Getting single todo
    public Todo getTodo(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_TODO,
                new String[] {
                        KEY_ID,
                        KEY_DESCRIPTION,
                        KEY_TIME },
                KEY_ID + "=?",
                new String[] { String.valueOf(id) }
                , null, null, null, null);
        Todo todo = new Todo(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2)
        );

        cursor.close();
        return todo;
    }

    //Getting all todos
    public List<Todo> getAllTodos(){
        List<Todo> todoList = new ArrayList<>();

        //Select all query
        String selectQuery = "SELECT * FROM " + TABLE_TODO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Todo todo = new Todo();
                todo.setId(Integer.parseInt(cursor.getString(0)));
                todo.setDescription(cursor.getString(1));
                todo.setTime(cursor.getString(2));

                //Adding todo to list
                todoList.add(todo);
            }while (cursor.moveToNext());
        }

        cursor.close();
        //return todo list
        return todoList;
    }

    //Getting todo count
    public int getTodoCount(){
        String countQuery = "SELECT * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        //return count
        return cursor.getCount();
    }

    //Updating single todo
    public int updateContact(ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();


        //updating row
        int num =  db.update(
                TABLE_TODO,
                values,
                KEY_ID + " = ?",
                new String[]{String.valueOf(values.getAsInteger(DBHelper.KEY_ID))}
        );
        db.close();
        return num;
    }

    //Deleting single todo
    public int deleteTodo(Todo todo){
        SQLiteDatabase db = this.getWritableDatabase();
        int num = db.delete(
                TABLE_TODO,
                KEY_ID + " = ?",
                new String[]{String.valueOf(todo.getId())}
        );
        db.close();
        return num;
    }
}
