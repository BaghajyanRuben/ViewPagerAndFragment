package com.photo.viewpagerandfragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.photo.viewpagerandfragments.local.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

	private List<Note> items;
	private ItemClickListener itemClickListener;

	public ListAdapter(ItemClickListener itemClickListener) {
		items = new ArrayList<>();
		this.itemClickListener = itemClickListener;
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
						false), itemClickListener);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		holder.bind(items.get(position));
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public Note getItem(int position) {
		return items.get(position);
	}

	public void remove(int position) {
		if (position >= 0 && position < items.size()) {
			items.remove(position);
			notifyItemRemoved(position);
		}
	}

	public interface ItemClickListener {
		void onItemClicked(int position, ClickAction action);
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		private TextView textViewName;
		private TextView textViewDesc;
		private TextView textViewTime;
		private ImageView imageView;
		private View btnRemove;
		private ItemClickListener itemClickListener;

		public MyViewHolder(@NonNull View itemView, final ItemClickListener itemClickListener) {
			super(itemView);
			this.itemClickListener = itemClickListener;

			textViewDesc = itemView.findViewById(R.id.tv_desc);
			textViewName = itemView.findViewById(R.id.tv_title);
			textViewTime = itemView.findViewById(R.id.tv_time);
			imageView = itemView.findViewById(R.id.im_avatar);
			btnRemove = itemView.findViewById(R.id.btn_remove);



			btnRemove.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
							.setTitle(R.string.title_remove)
							.setMessage(R.string.description_remove)
							.setPositiveButton(R.string.gen_yes, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {
									itemClickListener.onItemClicked(getAdapterPosition(), ClickAction.ACTION_DELETE);
								}
							})
							.setNegativeButton(R.string.gen_cancel, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {

								}
							});

					AlertDialog dialog = builder.create();
					 dialog.show();
				}
			});

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					itemClickListener.onItemClicked(getAdapterPosition(), ClickAction.ACTION_ITEM);
				}
			});
		}

		public void bind(Note item) {
			textViewName.setText("item id = " + item.getId());
			textViewDesc.setText("Description : \n " + item.getNote());
			textViewTime.setText("time : \n " + item.getTimeStamp());
			imageView.setImageResource(R.mipmap.ic_launcher);
		}

	}
}
