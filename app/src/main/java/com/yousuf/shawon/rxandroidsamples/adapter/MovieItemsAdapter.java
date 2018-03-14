package com.yousuf.shawon.rxandroidsamples.adapter;

import android.view.View;
import com.yousuf.shawon.rxandroidsamples.model.MovieItem;
import com.yousuf.shawon.rxandroidsamples.viewholder.BaseViewHolder;
import com.yousuf.shawon.rxandroidsamples.viewholder.MovieItemViewHolder;
import java.util.List;

/**
 * Created by Yousuf on 10/31/2017.
 */

public class MovieItemsAdapter extends BaseRecyclerViewAdapter<List<MovieItem>> {

  public MovieItemsAdapter(List<MovieItem> items, int[] viewTypeIds, int[] viewResourceIds) {
    super(items, viewTypeIds, viewResourceIds);
  }

  @Override public BaseViewHolder createViewHolder(int type, View itemView) {
    return new MovieItemViewHolder(itemView);
  }
}
