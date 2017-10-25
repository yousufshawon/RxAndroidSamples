package com.yousuf.shawon.rxandroidsamples.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.adapter.ContactAdapter;
import com.yousuf.shawon.rxandroidsamples.helper.ContactsQuery;
import com.yousuf.shawon.rxandroidsamples.util.Log;


/**
 * A simple {@link Fragment} subclass.
 */
public class DebounceSearchFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{


  RecyclerView recyclerViewContact;
  ContactAdapter mAdapter;

  final int typeIds[] = new int[]{0};
  final int viewIds[] = new int[]{ R.layout.row_contact};

  String TAG = getClass().getSimpleName();

  public static DebounceSearchFragment newInstance() {
     Bundle args = new Bundle();
     DebounceSearchFragment fragment = new DebounceSearchFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mAdapter = new ContactAdapter(null, typeIds, viewIds);
  }



  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
     View rootView = inflater.inflate(R.layout.fragment_debounce_search, container, false);
    initViews(rootView);

    return rootView;
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, this);
  }



  private void initViews(View rootView){

    recyclerViewContact = (RecyclerView) rootView.findViewById(R.id.recyclerViewContact);
    recyclerViewContact.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerViewContact.setAdapter(mAdapter);

  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    Log.i(TAG, "onCreateLoader");
    // Uri contentUri = ContactsQuery.CONTENT_URI;
    Uri contentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    //   mObserver = new Loader.ForceLoadContentObserver();

    return new CursorLoader(getActivity(),
        contentUri,
        ContactsQuery.PROJECTION,
        ContactsQuery.SELECTION,
        null,
        ContactsQuery.SORT_ORDER);

  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    // required as is use CustomAdapter not CursorAdapter
    data.moveToFirst();
    Log.i(TAG, "Total " + data.getCount() + " Contact found");

    mAdapter.updateDataResource(data);
    //  data.registerContentObserver(new Loader.ForceLoadContentObserver());
    data.setNotificationUri(getActivity().getContentResolver(), ContactsQuery.CONTENT_URI);


  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {
    mAdapter.updateDataResource(null);
  }
}
