package com.photo.viewpagerandfragments.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.photo.viewpagerandfragments.local.entity.Note;

import java.util.List;

@Dao
public interface DAONote {

	@Query("SELECT * FROM note")
	List<Note> getAll();

	@Delete
	void delete(Note note);

	@Update
	void update(Note note);

	@Insert
	long insert(Note note );
}
