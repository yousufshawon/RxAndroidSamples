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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.adapter.ContactAdapter;
import com.yousuf.shawon.rxandroidsamples.adapter.ContactRecyclerViewAdapter;
import com.yousuf.shawon.rxandroidsamples.helper.ContactsQuery;
import com.yousuf.shawon.rxandroidsamples.model.ContactPerson;
import com.yousuf.shawon.rxandroidsamples.util.Log;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebounceSearchFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{


  RecyclerView recyclerViewContact;
  SearchView searchView;
  ContactAdapter mAdapter;
  ContactRecyclerViewAdapter mRecyclerViewAdapter;

  List<ContactPerson> mContactPersonList;

  final int typeIds[] = new int[]{0};
  final int viewIds[] = new int[]{ R.layout.row_contact};

  Disposable searchViewDisposable;

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
    mContactPersonList = new ArrayList<>();
    mRecyclerViewAdapter = new ContactRecyclerViewAdapter(mContactPersonList, typeIds, viewIds);

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
   // recyclerViewContact.setAdapter(mAdapter);
    recyclerViewContact.setAdapter(mRecyclerViewAdapter);

    searchView = (SearchView) rootView.findViewById(R.id.searchView);

    searchViewDisposable =
    RxSearchView.queryTextChanges(searchView)
        .debounce(300, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .filter(new Predicate<CharSequence>() {
          @Override public boolean test(CharSequence charSequence) throws Exception {
            if (charSequence == null || charSequence.toString().trim().length() == 0 ) {
              return false;
            }
            return true;
          }
        })
        .subscribe(new Consumer<CharSequence>() {
          @Override public void accept(CharSequence charSequence) throws Exception {
            Log.i(TAG, "Searching for " + charSequence + " .....");
            mRecyclerViewAdapter.filter(charSequence.toString());
          }



        });



    //RxSearchView.queryTextChangeEvents(searchView)
    //    .subscribe(new Consumer<SearchViewQueryTextEvent>() {
    //      @Override public void accept(SearchViewQueryTextEvent searchViewQueryTextEvent)
    //          throws Exception {
    //        Log.i(TAG, "Searching for " + searchViewQueryTextEvent.queryText() + " .....");
    //      }
    //    });

  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    Log.i(TAG, "onCreateLoader");
    // Uri contentUri = ContactsQuery.CONTENT_URI;
    Uri contentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
   // contentUri = ContactsContract.Contacts.CONTENT_URI;

    String filter = ""+ ContactsContract.Contacts.HAS_PHONE_NUMBER + " > 0 and "
        + ContactsContract.CommonDataKinds.Phone.TYPE +"=" + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;

    //   mObserver = new Loader.ForceLoadContentObserver();

    Log.i(TAG, ContactsQuery.SELECTION);

    return new CursorLoader(getActivity(),
        contentUri,
        ContactsQuery.PROJECTION,
        ContactsQuery.SELECTION,
       //filter,
        null,
        ContactsQuery.SORT_ORDER);

  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    // required as is use CustomAdapter not CursorAdapter
    data.moveToFirst();
    Log.i(TAG, "Total " + data.getCount() + " Contact found");

    //mAdapter.updateDataResource(data);
    //  data.registerContentObserver(new Loader.ForceLoadContentObserver());
    data.setNotificationUri(getActivity().getContentResolver(), ContactsQuery.CONTENT_URI);

    mRecyclerViewAdapter.updateResourceFromCursor(data);



  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {
   // mAdapter.updateDataResource(null);
    mRecyclerViewAdapter.updateDataResource(null);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    searchViewDisposable.dispose();
  }
}
