package com.photo.viewpagerandfragments;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.photo.viewpagerandfragments.local.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment implements ListAdapter.ItemClickListener {

	private RecyclerView recyclerView;
	private ContactAdapter adapter;
	private BroadcastReceiver itemRemoveReceiver;

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

		itemRemoveReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent == null
						|| !Constants.ACTION_ITEM_REMOVE.equals(intent.getAction())){
					return;
				}

				String noteName = intent.getStringExtra(Constants.KEY_NAME);
				sendNotification(context, noteName);
			}
		};

		LocalBroadcastManager.getInstance(getActivity())
				.registerReceiver(itemRemoveReceiver, new IntentFilter(Constants.ACTION_ITEM_REMOVE));

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

		if (grantResults.length > 1) {
			loadContacts();
		}
	}

	private void loadContacts() {
		if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
				&& ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 123);
			}
		} else {
			adapter.setItems(readContacts());
		}

	}

	@Override
	public void onDestroy() {
		if (itemRemoveReceiver != null){
			LocalBroadcastManager
					.getInstance(getActivity())
					.unregisterReceiver(itemRemoveReceiver);
		}
		super.onDestroy();
	}

	private List<Contact> readContacts() {
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

		if (contactCursor == null) {
			return contacts;
		}

		final int displayNameIndex = contactCursor.getColumnIndex(displayName);
		final int phoneNumberIndex = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

		contactCursor.moveToFirst();

		while (contactCursor.moveToNext()) {
			String name = contactCursor.getString(displayNameIndex);
			String phone = contactCursor.getString(phoneNumberIndex);

			contacts.add(new Contact(name, phone));
		}

		contactCursor.close();

		return contacts;
	}

	@Override
	public void onItemClicked(int position, ClickAction action) {
		switch (action) {
			case ACTION_ITEM:
				adapter.select(position);
				break;
		}
	}


	public void sendNotification(Context context, String noteName) {

		String title = context.getString(R.string.notification_title);
		String description = context.getString(R.string.notification_description, noteName);


		String name = context.getString(R.string.channel_name);
		String id = Constants.CHANNEL_ID;

		Intent intent;
		PendingIntent pendingIntent;
		NotificationCompat.Builder builder;

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			int importance = NotificationManager.IMPORTANCE_HIGH;
			NotificationChannel mChannel = notificationManager.getNotificationChannel(id);
			if (mChannel == null) {
				mChannel = new NotificationChannel(id, name, importance);
				mChannel.setDescription(description);
				notificationManager.createNotificationChannel(mChannel);
			}
		}

		builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID);

		intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		pendingIntent = PendingIntent.getActivity(
				context,
				0,
				intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentTitle(title)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentText(description)
				.setDefaults(Notification.DEFAULT_ALL)
				.setAutoCancel(true)
				.setContentIntent(pendingIntent)
				.setTicker(context.getString(R.string.app_name))
				.setVibrate(new long[]{1000, 200, 3000})
				.setPriority(Notification.PRIORITY_HIGH);


		Notification notification = builder.build();
		notificationManager.notify(Constants.NOTIFICATION_ID, notification);
	}
}
