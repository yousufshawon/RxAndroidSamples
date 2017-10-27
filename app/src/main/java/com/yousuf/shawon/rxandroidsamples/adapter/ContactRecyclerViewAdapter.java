package com.yousuf.shawon.rxandroidsamples.adapter;

import android.database.Cursor;
import android.view.View;
import com.yousuf.shawon.rxandroidsamples.helper.ContactsQuery;
import com.yousuf.shawon.rxandroidsamples.model.ContactPerson;
import com.yousuf.shawon.rxandroidsamples.viewholder.BaseViewHolder;
import com.yousuf.shawon.rxandroidsamples.viewholder.ContactPersonViewHolder;
import com.yousuf.shawon.rxandroidsamples.viewholder.ContactViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yousuf on 10/26/2017.
 */

public class ContactRecyclerViewAdapter extends BaseRecyclerViewAdapter<List<ContactPerson>> {

  public List<ContactPerson> listCopy;

  public ContactRecyclerViewAdapter(List<ContactPerson> items, int[] viewTypeIds,
      int[] viewResourceIds) {
    super(items, viewTypeIds, viewResourceIds);

    listCopy = new ArrayList<>();
    listCopy.addAll(items);
  }

  @Override public BaseViewHolder createViewHolder(int type, View itemView) {
    // only one View Holder is used here

    return new ContactPersonViewHolder(itemView);
  }




  public void updateResourceFromCursor(Cursor cursor){
    if (cursor != null && cursor.moveToFirst() ) {
      items.clear();
      listCopy.clear();
      do {

        String id = cursor.getString(ContactsQuery.ID);
        String lookUpKey = cursor.getString(ContactsQuery.LOOKUP_KEY);
        String displayName = cursor.getString(ContactsQuery.DISPLAY_NAME);
        String number = cursor.getString(ContactsQuery.NUMBER);
        ContactPerson contactPerson = new ContactPerson(displayName, number);
        contactPerson.setId(id);
        contactPerson.setLookupKey(lookUpKey);
        items.add(contactPerson);

      }while (cursor.moveToNext());
      notifyDataSetChanged();
      listCopy.addAll(items);
      cursor.moveToFirst();

    }
  }

  public void filter(String search){
    items.clear();
    if (search == null || search.trim().length() == 0 ) {
      items.addAll(listCopy);
    }else {

      String searchStr = search.toLowerCase();

      for (ContactPerson contactPerson : listCopy) {
        if (contactPerson.getName().toLowerCase().contains(searchStr) || contactPerson.getPhone().toLowerCase().contains(searchStr)) {
          items.add(contactPerson);
        }
      }
    }

    notifyDataSetChanged();

  }


}
