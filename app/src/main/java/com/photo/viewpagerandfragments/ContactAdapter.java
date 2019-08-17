package com.photo.viewpagerandfragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.photo.viewpagerandfragments.local.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

	private List<Contact> items;
	private ListAdapter.ItemClickListener clickListener;

	public ContactAdapter(ListAdapter.ItemClickListener clickListener) {
		items = new ArrayList<>();
		this.clickListener = clickListener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_contact_layout, parent , false);
		return new ViewHolder(itemView, clickListener);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.bide(items.get(position));
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void setItems(List<Contact> contacts) {
		items.clear();
		items.addAll(contacts);
		notifyDataSetChanged();
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public void select(int position) {
		if (position >= 0 && position < items.size()){
			Contact contact = items.get(position);
			contact.setSelected(!contact.isSelected());
			notifyItemChanged(position);
		}
	}

	public List<Contact> getSelectedItems() {
		List<Contact> selectedItems = new ArrayList<>();
		for (Contact item : items) {
			if (item.isSelected()){
				selectedItems.add(item);
			}
		}
		return selectedItems;
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		private TextView name;
		private TextView phone;
		private CheckBox checkBox;

		public ViewHolder(@NonNull View itemView, final ListAdapter.ItemClickListener itemClickListener) {
			super(itemView);

			name = itemView.findViewById(R.id.tv_name);
			phone = itemView.findViewById(R.id.tv_phone);
			checkBox = itemView.findViewById(R.id.check_box);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					itemClickListener.onItemClicked(getAdapterPosition(), ClickAction.ACTION_ITEM);
				}
			});
		}

		public void bide(Contact contact){
			name.setText(contact.getName());
			phone.setText(contact.getPhone());
			checkBox.setChecked(contact.isSelected());
		}
	}
}
