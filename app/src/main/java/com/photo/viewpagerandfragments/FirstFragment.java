package com.photo.viewpagerandfragments;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FirstFragment extends Fragment {

	private RecyclerView recyclerView;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_first_layout, container, false);
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

		ListAdapter adapter = new ListAdapter();
		recyclerView.setAdapter(adapter);

		ItemOffsetDecoration offsetDecoration =
				new ItemOffsetDecoration((int) getResources()
						.getDimension(R.dimen.item_margin));
		recyclerView.addItemDecoration(offsetDecoration);

	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
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
