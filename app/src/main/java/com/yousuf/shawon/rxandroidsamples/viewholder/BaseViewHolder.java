package com.yousuf.shawon.rxandroidsamples.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Yousuf on 10/25/2017.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

  public BaseViewHolder(View itemView) {
    super(itemView);
    initialize(itemView);
  }

  public abstract void initialize(View itemView);
  public abstract void bind(T item);




}
