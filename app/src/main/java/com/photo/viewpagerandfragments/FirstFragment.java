package com.photo.viewpagerandfragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.icu.util.ValueIterator;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.photo.viewpagerandfragments.local.ToDoDB;
import com.photo.viewpagerandfragments.local.entity.Note;

import java.util.List;

public class FirstFragment extends Fragment implements ListAdapter.ItemClickListener {

	private RecyclerView recyclerView;
	private ListAdapter adapter;
	private DBhelper dBhelper;
	private ToDoDB toDoDB;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		toDoDB = ToDoDB.getInstance(getActivity());
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_first_layout, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		dBhelper = new DBhelper(getActivity());

		recyclerView =
				view.findViewById(R.id.recycler_view);

		LinearLayoutManager layoutManager =
				new LinearLayoutManager(view.getContext(),
						RecyclerView.VERTICAL,
						false);
		recyclerView.setLayoutManager(layoutManager);

		adapter = new ListAdapter(this);
		recyclerView.setAdapter(adapter);


		new AsyncTask<Void, Void, List<Note>>() {
			@Override
			protected List<Note> doInBackground(Void... voids) {
				return toDoDB.noteDAO().getAll();
			}

			@Override
			protected void onPostExecute(List<Note> notes) {
				adapter.addAll(notes);
			}
		}.execute();

//		adapter.addAll(dBhelper.readAllFromDB());

		ItemOffsetDecoration offsetDecoration =
				new ItemOffsetDecoration((int) getResources()
						.getDimension(R.dimen.item_margin));
		recyclerView.addItemDecoration(offsetDecoration);


		view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				AlertDialog.Builder builder =
//						new AlertDialog.Builder(getActivity());

				new AsyncTask<Void, Note, Note>(){

					@Override
					protected Note doInBackground(Void... voids) {
						Note note = new Note();
						note.setNote("add new " + System.currentTimeMillis());
						note.setId((int) toDoDB.noteDAO().insert(note));
						return note;
					}

					@Override
					protected void onPostExecute(Note note) {
						adapter.add(note);
						recyclerView.scrollToPosition(adapter.getItemCount() - 1);
					}
				}.execute();

//				dBhelper.writeToDB("add new " + System.currentTimeMillis());
//
//				Note item = dBhelper.readLastFromDB();
//
//				if (item != null) {
//					adapter.add(item);
//				}

				recyclerView.scrollToPosition(adapter.getItemCount() - 1);

			}
		});



	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onItemClicked(int position, ClickAction action) {
		switch (action){
			case ACTION_ITEM:



//				Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
//
//				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//					vibrator.vibrate(VibrationEffect.createOneShot(2000l, 0));
//				} else {
//					vibrator.vibrate(new long[]{1000, 2000, 3000}, -1);
//				}
				break;
			case ACTION_DELETE:
				final Note note = adapter.getItem(position);
				adapter.remove(position);
				new AsyncTask<Note, Void, Void>() {
					@Override
					protected Void doInBackground(Note... notes) {
						toDoDB.noteDAO().delete(notes[0]);
						return null;
					}

					@Override
					protected void onPostExecute(Void aVoid) {
						Intent data = new Intent(Constants.ACTION_ITEM_REMOVE);
						data.putExtra(Constants.KEY_NAME, note.getNote());
						//local broadcast
						LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(data);
						// global broadcast
//						getActivity().sendBroadcast(data);
					}
				}.execute(note);
				break;
		}
	}

	static class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

		private int itemMargin;

		public ItemOffsetDecoration(int itemMargin) {
			this.itemMargin = itemMargin;
		}

		@Override
		public void getItemOffsets(@NonNull Rect outRect,
								   @NonNull View view,
								   @NonNull RecyclerView parent,
								   @NonNull RecyclerView.State state) {
			super.getItemOffsets(outRect, view, parent, state);

			int position = parent.getChildAdapterPosition(view);

			outRect.left = itemMargin;
			outRect.right = itemMargin;
			outRect.top = itemMargin;
			outRect.bottom = itemMargin;

			if (position == 0) {
				outRect.top = 2 * itemMargin;
			} else if (position ==
					parent.getAdapter().getItemCount() - 1) {
				outRect.bottom = 2 * itemMargin;
			}

		}
	}

}
