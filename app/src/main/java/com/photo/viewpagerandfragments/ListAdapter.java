package com.photo.viewpagerandfragments;

import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

	private List<Note> items;

	public ListAdapter() {
		items = new ArrayList<>();
	}

	public void addAll(List<Note> notes){
		items.clear();
		items.addAll(notes);
		notifyDataSetChanged();
	}

	public void add(Note note){
		items.add(note);
		notifyItemInserted(items.size() - 1);
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
										   int viewType) {
		return new MyViewHolder(LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.item_layout,
						parent,
						false));
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		holder.bind(items.get(position));
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		private TextView textViewName;
		private TextView textViewDesc;
		private TextView textViewTime;
		private ImageView imageView;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			textViewDesc = itemView.findViewById(R.id.tv_desc);
			textViewName = itemView.findViewById(R.id.tv_title);
			textViewTime = itemView.findViewById(R.id.tv_time);
			imageView = itemView.findViewById(R.id.im_avatar);
		}

		public void bind(Note item) {
			textViewName.setText("item id = " + item.getId());
			textViewDesc.setText("Description : \n " + item.getNote());
			textViewTime.setText("time : \n " + item.getTimeStamp());
			imageView.setImageResource(R.mipmap.ic_launcher);
		}

	}
}
