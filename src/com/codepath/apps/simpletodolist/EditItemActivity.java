package com.codepath.apps.simpletodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	private int in_position;
	private String in_edittext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		// Extract the data from intent and populate Edit text
		in_edittext = getIntent().getStringExtra("listitem");
		in_position = getIntent().getIntExtra("listposition", 0);
		EditText etNewItem2 = (EditText) findViewById(R.id.secondeditText);
		etNewItem2.setText(in_edittext);
		int text_length = etNewItem2.length();
		etNewItem2.setSelection(text_length);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public void addEditItem(View v) {
    	EditText etNewItem2 = (EditText) findViewById(R.id.secondeditText);
    	//etNewItem.setText("");
        // Prepare data intent 
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("newEditText", etNewItem2.getText().toString());
        data.putExtra("newEditPosition", in_position);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
    
}
