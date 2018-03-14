package com.yousuf.shawon.rxandroidsamples.viewholder;

import android.database.Cursor;
import android.view.View;
import android.widget.TextView;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.helper.ContactsQuery;

/**
 * Created by Yousuf on 10/25/2017.
 */

public class ContactViewHolder extends BaseViewHolder<Cursor> {

  TextView textViewName, textViewPhone;

  public ContactViewHolder(View itemView) {
    super(itemView);
  }


  @Override
  public void initialize(View itemView) {

    textViewName = (TextView) itemView.findViewById(R.id.textViewName);
    textViewPhone = (TextView) itemView.findViewById(R.id.textViewPhone);
  }

  @Override
  public void bind(Cursor item) {
    final String displayName = item.getString(ContactsQuery.DISPLAY_NAME);
    String number = item.getString(ContactsQuery.NUMBER);
    textViewName.setText(displayName);
    if (number != null) {
      textViewPhone.setText(number);
    }else {
      textViewPhone.setText("No number found");
    }

  }
}

