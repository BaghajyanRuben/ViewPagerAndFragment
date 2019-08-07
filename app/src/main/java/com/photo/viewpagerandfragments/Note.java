package com.photo.viewpagerandfragments;

public class Note {
	private int id;
	private String note;
	private String timeStamp;

	public Note() {
	}

	public Note(int id, String note, String timeStamp) {
		this.id = id;
		this.note = note;
		this.timeStamp = timeStamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
