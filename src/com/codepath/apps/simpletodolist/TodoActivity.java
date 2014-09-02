package com.codepath.apps.simpletodolist;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.codepath.apps.simpletodolist.DialogEditItem.DialogEditItemListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends FragmentActivity implements DialogEditItem.DialogEditItemListener {
	ArrayList<TodoList> values;
	listCustomAdapter adapter;
	ListView lvItems;
	private final int EditRequestCode = 20;
	private TodoListDataSource datasource;
	private int editPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        // Open the SQL DB
        datasource = new TodoListDataSource(this);
        datasource.open();

        lvItems = (ListView) findViewById(R.id.lvItems);
        // Get the items from SQL database
        values = datasource.getAllTodoLists();
        // Display using custom Adapter
        adapter = new listCustomAdapter(this, values);
        lvItems.setAdapter(adapter);
        setupListViewListener();
    }
    
    // Add new item to Todo list
    public void addTodoItem(View v) {
    	TodoList item = null;
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	Toast.makeText(this, etNewItem.getText().toString(), Toast.LENGTH_SHORT).show();
        // Set current date as default duedate
		final Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = formatter.format(c.getTime());
        // save the new item to the database
        item = datasource.createTodoList(etNewItem.getText().toString(), 0, currentDate);
        values.add(item);
	    adapter.notifyDataSetChanged();
    	etNewItem.setText("");
    	
    }
    
    private void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		@Override
    		public boolean onItemLongClick(AdapterView<?> parent,
    				View view, int position, long rowId) {
    			TodoList item = values.get(position);
    	        datasource.deleteTodoList(item);
    	        values.remove(position);
    	        // Delete from SQLite DB
    	        adapter.notifyDataSetChanged();
    			return true;
    		}
    	});
    	
    	lvItems.setOnItemClickListener(new OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> parent,
    				View view, int position, long rowId) {
    			// Start the DialogFragment to edit items
    			TodoList item = values.get(position);
    			editPosition = position;
    			showEditDialog(item.getItem(), editPosition, item.getDuedate(), item.getPriImageNumber());
    		}
    	});
    }
    
    private void showEditDialog(String edittext, int pos, String date, int prio) {
        FragmentManager fm = getSupportFragmentManager();
        DialogEditItem editItem = new DialogEditItem();
        Bundle args = new Bundle();
        // Pass the current item values to DialogFragment
        args.putInt("pos", pos);
        args.putString("text", edittext);
        args.putString("date", date);
        args.putInt("priority",  prio);
        editItem.setArguments(args);
        editItem.show(fm, "fragment_edit_text");
    }


    public void onFinishEditDialog(String inputText, int position, int prio, String duedate) {
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
		TodoList item = values.get(position);
		// Update list item
	    item.setItem(inputText);
	    item.setPriImageNum(prio);
	    item.setDuedate(duedate);
	    // Update SQLite DB
	    datasource.updateTodoList(item);
	    // Notify adapter
        adapter.notifyDataSetChanged();
    }
}
