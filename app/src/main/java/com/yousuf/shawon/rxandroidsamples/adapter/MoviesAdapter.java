package com.yousuf.shawon.rxandroidsamples.adapter;

import android.view.View;
import com.yousuf.shawon.rxandroidsamples.model.Movie;
import com.yousuf.shawon.rxandroidsamples.viewholder.BaseViewHolder;
import com.yousuf.shawon.rxandroidsamples.viewholder.MovieViewHolder;
import java.util.List;

/**
 * Created by Yousuf on 10/27/2017.
 */

public class MoviesAdapter extends BaseRecyclerViewAdapter<List<Movie>> {

  public MoviesAdapter(List<Movie> items, int[] viewTypeIds, int[] viewResourceIds) {
    super(items, viewTypeIds, viewResourceIds);
  }

  @Override public BaseViewHolder createViewHolder(int type, View itemView) {

    // only one type of view used here
    return new MovieViewHolder(itemView);
  }
}
