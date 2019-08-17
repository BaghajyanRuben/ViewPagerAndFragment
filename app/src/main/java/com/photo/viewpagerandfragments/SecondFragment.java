package com.photo.viewpagerandfragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.photo.viewpagerandfragments.local.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment implements ListAdapter.ItemClickListener {

	private RecyclerView recyclerView;
	private ContactAdapter adapter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_second_layout,
				container,
				false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		recyclerView =
				view.findViewById(R.id.recycler_view);

		LinearLayoutManager layoutManager =
				new LinearLayoutManager(view.getContext(),
						RecyclerView.VERTICAL,
						false);

		recyclerView.setLayoutManager(layoutManager);

		adapter = new ContactAdapter(this);
		recyclerView.setAdapter(adapter);

		view.findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				List<Contact> contacts = adapter.getSelectedItems();
				Log.i("Contacts", "Selected Contacts count " + contacts.size());
			}
		});

		loadContacts();

	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isAdded() && isVisibleToUser && adapter != null && adapter.isEmpty()) {
			loadContacts();
		}
		super.setUserVisibleHint(isVisibleToUser);
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (grantResults.length > 1){
			loadContacts();
		}
	}

	private void loadContacts(){
		if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
				&& ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 123);
			}
		} else {
			adapter.setItems(readContacts());
		}

	}

	private List<Contact> readContacts(){
		List<Contact> contacts = new ArrayList<>();

		Uri contactURI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		final String ID = ContactsContract.Contacts._ID;
		final String displayName = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

		final String[] projection = new String[]{ID, ContactsContract.CommonDataKinds.Phone.NUMBER, displayName};

		String orderDisplayName = ContactsContract.Contacts.DISPLAY_NAME + " ASC ";

		String selection = ContactsContract.CommonDataKinds.Phone.TYPE
				+ " = "
				+ ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;


		Cursor contactCursor = getActivity().getContentResolver().query(contactURI,
				projection,
				selection,
				null,
				orderDisplayName);

		if (contactCursor == null){
			return contacts;
		}

		final int displayNameIndex = contactCursor.getColumnIndex(displayName);
		final int phoneNumberIndex = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

		contactCursor.moveToFirst();

		while (contactCursor.moveToNext()){
			String name = contactCursor.getString(displayNameIndex);
			String phone = contactCursor.getString(phoneNumberIndex);

			contacts.add(new Contact(name, phone));
		}

		contactCursor.close();

		return contacts;
	}

	@Override
	public void onItemClicked(int position, ClickAction action) {
		switch (action){
			case ACTION_ITEM:
				adapter.select(position);
				break;
		}
	}
}
