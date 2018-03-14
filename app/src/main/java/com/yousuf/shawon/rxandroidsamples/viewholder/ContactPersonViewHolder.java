package com.yousuf.shawon.rxandroidsamples.viewholder;

import android.view.View;
import android.widget.TextView;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.helper.ContactsQuery;
import com.yousuf.shawon.rxandroidsamples.model.ContactPerson;
import com.yousuf.shawon.rxandroidsamples.util.Log;


/**
 * Created by Yousuf on 10/26/2017.
 */

public class ContactPersonViewHolder extends BaseViewHolder<ContactPerson> {

  TextView textViewName, textViewPhone;

  String TAG = getClass().getSimpleName();

  public ContactPersonViewHolder(View itemView) {
    super(itemView);
  }

  @Override public void initialize(View itemView) {
    textViewName = (TextView) itemView.findViewById(R.id.textViewName);
    textViewPhone = (TextView) itemView.findViewById(R.id.textViewPhone);
  }

  @Override public void bind(ContactPerson item) {

    final String displayName = item.getName();
    String number = item.getPhone();
    textViewName.setText(displayName);
    if (number != null) {
      textViewPhone.setText(number);
    }else {
      textViewPhone.setText("No number found");
    }

   // Log.i(TAG, getAdapterPosition() + " : " + item.toString());

  }


}
