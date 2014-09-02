package com.codepath.apps.simpletodolist;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TodoSQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_LISTITEMS = "ListItems";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_ITEM = "item";
  public static final String COLUMN_PRIORITY = "priority";
  public static final String COLUMN_DUEDATE = "duedate";

  private static final String DATABASE_NAME = "todolist.db";
  private static final int DATABASE_VERSION = 2;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_LISTITEMS + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_ITEM
      + " text not null, " + COLUMN_PRIORITY
      + " integer, " + COLUMN_DUEDATE
      + " text );";

  public TodoSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(TodoSQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTITEMS);
    onCreate(db);
  }

} 
