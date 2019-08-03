package com.photo.viewpagerandfragments;

import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

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
		holder.bind();
	}

	@Override
	public int getItemCount() {
		return 100;
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		private TextView textViewName;
		private TextView textViewDesc;
		private ImageView imageView;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			textViewDesc = itemView.findViewById(R.id.tv_desc);
			textViewName = itemView.findViewById(R.id.tv_title);
			imageView = itemView.findViewById(R.id.im_avatar);
		}

		public void bind() {
			textViewName.setText("item N:" + getAdapterPosition());
			textViewDesc.setText("Description N:" + getAdapterPosition());
			imageView.setImageResource(R.mipmap.ic_launcher);
		}

	}
}
