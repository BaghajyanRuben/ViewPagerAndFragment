package com.photo.viewpagerandfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {

	AlertDialog dialog;
	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

		View view = getActivity().getLayoutInflater().inflate(R.layout.item_layout, null);

		dialog = new AlertDialog
				.Builder(getActivity())
				.setView(view)
//				.setTitle(R.string.dialog_title)
//				.setMessage(R.string.dialog_message)
//				.setPositiveButton("Pos",
//						new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialogInterface, int i) {
//								Toast.makeText(getActivity(),
//										"Positive button clicked",
//										Toast.LENGTH_SHORT).show();
//							}
//						})
//				.setNegativeButton("neg", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialogInterface, int i) {
//						Toast.makeText(getActivity(),
//								"Negative button clicked",
//								Toast.LENGTH_SHORT).show();
//					}
//				})
//				.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialogInterface, int i) {
//						Toast.makeText(getActivity(),
//								"Negative button clicked",
//								Toast.LENGTH_LONG).show();
//						Log.d("myDialoginfo", "  NeutralButton click");
//						dialog.cancel();
//
//
//					}
//				})
				.create();

		ImageView imageView = view.findViewById(R.id.im_avatar);
		imageView.setImageResource(R.drawable.ic_announcement_black_24dp);
		dialog.setCanceledOnTouchOutside(true);


		return dialog;
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		Log.d("myDialoginfo", "  onDismiss ");

		Toast.makeText(getActivity(),
				"onDismiss",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		Log.d("myDialoginfo", "  onCancel ");

		Toast.makeText(getActivity(),
				"onCancel",
				Toast.LENGTH_SHORT).show();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}
