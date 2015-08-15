
package com.mba;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class TimelineFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String TAG = TimelineFragment.class.getSimpleName();
	private static final String[] FROM = { StatusContract.Columns.USER,
			StatusContract.Columns.MESSAGE, StatusContract.Columns.CREATED_AT,
			StatusContract.Columns.CREATED_AT };
	private static final int[] TO = { R.id.list_item_text_user,
			R.id.list_item_text_message, R.id.list_item_text_created_at };
	private SimpleCursorAdapter mAdapter;
	private static final int LOADER_ID = 42;

	@SuppressWarnings("deprecation")
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item,
				null, FROM, TO);
		mAdapter.setViewBinder(new TimeViewBinder());
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(LOADER_ID, null, this);

	}

	class TimeViewBinder implements
			android.support.v4.widget.SimpleCursorAdapter.ViewBinder,
			ViewBinder {

		
		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			// TODO Auto-generated method stub
			if (view.getId() != R.id.list_item_text_created_at)
				return false;
			long timestamp = cursor.getLong(columnIndex);
			CharSequence relativeTime = DateUtils
					.getRelativeTimeSpanString(timestamp);
			((TextView) view).setText(relativeTime);
			return true;
		}

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		if (id != LOADER_ID) {
			return null;

		}
		Log.d(TAG, "onCreated");
		return new CursorLoader(getActivity(), StatusContract.CONTENT_URI,
				null, null, null, StatusContract.DEFAULT_SORT);

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onLoadFinished with cursor: " + cursor.getCount());
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		mAdapter.swapCursor(null);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		// Get the details fragment
		DetailsFragment fragment = (DetailsFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_details); //
		// Is details fragment visible?
		if (fragment != null && fragment.isVisible()) { //
			fragment.updateView(id); //
		} else {
			startActivity(new Intent(getActivity(), DetailsActivity.class)
					.putExtra(StatusContract.Columns.ID, id)); //
		}
	}

}
