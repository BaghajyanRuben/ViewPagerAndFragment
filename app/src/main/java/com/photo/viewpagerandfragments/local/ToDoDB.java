package com.photo.viewpagerandfragments.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.photo.viewpagerandfragments.local.dao.DAOContacts;
import com.photo.viewpagerandfragments.local.dao.DAONote;
import com.photo.viewpagerandfragments.local.entity.Contact;
import com.photo.viewpagerandfragments.local.entity.Note;

@Database(entities = {
		Note.class,
		Contact.class
},
		version = 1,
		exportSchema = false)
public abstract class ToDoDB extends RoomDatabase {

	private static volatile ToDoDB INSTANCE;
	private static final String DB_NAME = "tb_db";

	public abstract DAONote noteDAO();

	public abstract DAOContacts contactDAO();


	public static ToDoDB getInstance(Context context) {
		if (INSTANCE == null) {
			synchronized (ToDoDB.class) {
				if (INSTANCE == null) {
					INSTANCE = create(context);
				}
			}
		}

		return INSTANCE;
	}

	private static ToDoDB create(Context context) {
		return Room.databaseBuilder(
				context,
				ToDoDB.class,
				DB_NAME).build();
	}
}
