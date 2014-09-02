package com.codepath.apps.simpletodolist;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listCustomAdapter extends BaseAdapter {
	private static ArrayList<TodoList> TodoArrayList;
	
	private Integer[] imgid = {
			R.drawable.l_lg,
			R.drawable.m_grey,
			R.drawable.h_black
	};
		
	private LayoutInflater mInflater;

	public listCustomAdapter(Context context, ArrayList<TodoList> results) {
		TodoArrayList = results;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return TodoArrayList.size();
	}

	public Object getItem(int position) {
		return TodoArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.todo_row, null);
			holder = new ViewHolder();
			holder.txtRowtext = (TextView) convertView.findViewById(R.id.rowtext);
			holder.txtRowDuedate = (TextView) convertView.findViewById(R.id.rowduedate);
			holder.imgPhoto = (ImageView) convertView.findViewById(R.id.photo);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtRowtext.setText(TodoArrayList.get(position).getItem());
		holder.txtRowDuedate.setText("Due: " + TodoArrayList.get(position).getDuedate());
		holder.imgPhoto.setImageResource(imgid[TodoArrayList.get(position).getPriImageNumber()]);
		if (TodoArrayList.get(position).getPriImageNumber() == 2) {
			// Highlight high priority items
			holder.txtRowtext.setBackgroundColor(Color.CYAN);
		}
		return convertView;
	}

	static class ViewHolder {
		TextView txtRowtext;
		TextView txtRowDuedate;
		ImageView imgPhoto;
	}
}
