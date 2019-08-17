package com.photo.viewpagerandfragments.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.photo.viewpagerandfragments.local.entity.Contact;

import java.util.List;

@Dao
public interface DAOContacts {

	@Query("SELECT * FROM contact WHERE note_id = :notId")
	List<Contact> getAll(String notId);

	@Insert
	void insert(Contact contact);

	@Delete
	void delete(Contact contact);

	@Update
	void update(Contact contact);
}
