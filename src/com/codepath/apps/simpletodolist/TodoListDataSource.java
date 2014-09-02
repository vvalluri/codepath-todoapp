package com.codepath.apps.simpletodolist;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TodoListDataSource {

	  // Database fields
	  private SQLiteDatabase database;
	  private TodoSQLiteHelper dbHelper;
	  private String[] allColumns = { TodoSQLiteHelper.COLUMN_ID,
	      TodoSQLiteHelper.COLUMN_ITEM,
	      TodoSQLiteHelper.COLUMN_PRIORITY,
	      TodoSQLiteHelper.COLUMN_DUEDATE};

	  public TodoListDataSource(Context context) {
	    dbHelper = new TodoSQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public TodoList createTodoList(String itemtext, int priority, String duedate) {
	    ContentValues values = new ContentValues();
	    values.put(TodoSQLiteHelper.COLUMN_ITEM, itemtext);
	    values.put(TodoSQLiteHelper.COLUMN_PRIORITY, priority);
	    values.put(TodoSQLiteHelper.COLUMN_DUEDATE, duedate);
	    long insertId = database.insert(TodoSQLiteHelper.TABLE_LISTITEMS, null,
	        values);
	    Cursor cursor = database.query(TodoSQLiteHelper.TABLE_LISTITEMS,
	        allColumns, TodoSQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    TodoList newTodoList = cursorToTodoList(cursor);
	    cursor.close();
	    return newTodoList;
	  }

	  public void deleteTodoList(TodoList item) {
	    long id = item.getId();
	    System.out.println("TodoList deleted with id: " + id);
	    database.delete(TodoSQLiteHelper.TABLE_LISTITEMS, TodoSQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public void updateTodoList(TodoList item) {
		  ContentValues cv = new ContentValues();
		  cv.put("item", item.getItem());
		  cv.put("priority", item.getPriImageNumber());
		  cv.put("duedate", item.getDuedate());
		  long id = item.getId();
		  System.out.println("TodoList updated for id: " + id);
		  database.update(TodoSQLiteHelper.TABLE_LISTITEMS, cv, TodoSQLiteHelper.COLUMN_ID
		        + " = " + id, null);
	  }
	  
	  public ArrayList<TodoList> getAllTodoLists() {
	    ArrayList<TodoList> listitems = new ArrayList<TodoList>();

	    Cursor cursor = database.query(TodoSQLiteHelper.TABLE_LISTITEMS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      TodoList item = cursorToTodoList(cursor);
	      listitems.add(item);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return listitems;
	  }

	  private TodoList cursorToTodoList(Cursor cursor) {
	    TodoList listitem = new TodoList();
	    listitem.setId(cursor.getLong(0));
	    listitem.setItem(cursor.getString(1));
	    listitem.setPriImageNum(cursor.getInt(2));
	    listitem.setDuedate(cursor.getString(3));
	    return listitem;
	  }
}
