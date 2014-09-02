package com.codepath.apps.simpletodolist;

public class TodoList {
	  private long id;
	  private String item;
	  private String duedate = "";
	  private int priimgnum = 0;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getItem() {
	    return item;
	  }

	  public void setItem(String item) {
	    this.item = item;
	  }

	  public String getDuedate() {
		  return duedate;
	  }

	  public void setDuedate(String date) {
		    this.duedate = date;
	  }
	  
		public void setPriImageNum(int imageNumber) {
			this.priimgnum = imageNumber;
		}
		public int getPriImageNumber() {
			return priimgnum;
		}	  
		  
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return item;
	  }
}
