package com.codepath.apps.simpletodolist;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;


public class DialogEditItem extends DialogFragment {
	
    public interface DialogEditItemListener {
        void onFinishEditDialog(String inputText, int pos, int priority, String newDueDate);
    }

    private EditText mEditText;
    private int position;
	private String in_edittext, in_date, out_date;
	private int Priovalue = 0;
	private RadioGroup radioPriGroup;
	private View customView;
	private DatePicker dueDate;

    public DialogEditItem() {
        // Empty constructor required for DialogFragment
    }
    
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        customView = inflater.inflate(R.layout.dialog_edit_text, null, false);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(customView)
        // Add action buttons
               .setPositiveButton("save", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                       // save
                       // Return input text to activity
                   		
                	   DialogEditItemListener activity = (DialogEditItemListener) getActivity();
                	   Priovalue = getPrio();
                	   out_date = getDueDate();
                       activity.onFinishEditDialog(mEditText.getText().toString(), position, Priovalue, out_date);
                       dismiss();
                   }
               })
               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Cancel
                   }
               }); 

        // Store the list item position
        position = getArguments().getInt("pos");
        
        // Get the current item text string
        in_edittext = getArguments().getString("text");
        mEditText = (EditText)customView.findViewById(R.id.txt_todo);
		mEditText.setText(in_edittext);
		int text_length = mEditText.length();
		mEditText.setSelection(text_length);
		
		// Store the current priority
		Priovalue = getArguments().getInt("prio");
        radioPriGroup = (RadioGroup) customView.findViewById(R.id.radioPriority);

        // Store the current date
        dueDate = (DatePicker)customView.findViewById(R.id.dpResult);
        in_date = getArguments().getString("date");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal  = Calendar.getInstance();
        try {
			cal.setTime(sdf.parse(in_date));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        dueDate.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

        return builder.create();
    }
    
    private  int getPrio () {

	     // get selected radio button from radioGroup
    	int selectedId = radioPriGroup.getCheckedRadioButtonId();
    	// Initialize the value to previous priority
    	int Prio = Priovalue;
   		if (selectedId == R.id.radio_low) {
   			Prio = 0;
    	} else if (selectedId == R.id.radio_med) {
    		Prio = 1;
   		} else if (selectedId == R.id.radio_high) {
    		Prio = 2;	
    	}
		return Prio;
    }
 
    private String getDueDate () {
    	String newDueDate;
		newDueDate = String.format("%02d/%02d/%d", dueDate.getDayOfMonth(), dueDate.getMonth() + 1, dueDate.getYear());
		return newDueDate;
    }
}
