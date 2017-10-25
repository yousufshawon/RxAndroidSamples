package com.yousuf.shawon.rxandroidsamples.adapter;

import android.database.Cursor;
import android.view.View;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.viewholder.BaseViewHolder;
import com.yousuf.shawon.rxandroidsamples.viewholder.ContactViewHolder;

/**
 * Created by Yousuf on 10/25/2017.
 */

public class ContactAdapter extends BaseRecyclerViewAdapter<Cursor> {




  public ContactAdapter(Cursor items,  int [] viewTypeIds, int[]viewResourceIds ) {
    super(items, viewTypeIds, viewResourceIds);
  }

  @Override public BaseViewHolder createViewHolder(int type, View itemView) {

    // only one View Holder is used here

    return new ContactViewHolder(itemView);
  }
}
