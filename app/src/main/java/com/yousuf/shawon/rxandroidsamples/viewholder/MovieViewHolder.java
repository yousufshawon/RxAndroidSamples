package com.yousuf.shawon.rxandroidsamples.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.model.Movie;

/**
 * Created by Yousuf on 10/27/2017.
 */

public class MovieViewHolder extends BaseViewHolder<Movie> {

  LinearLayout moviesLayout;
  ImageView imageViewPic;
  TextView movieTitle;
  TextView data;
  TextView movieDescription;
  TextView rating;


  public MovieViewHolder(View itemView) {
    super(itemView);
  }

  @Override public void initialize(View itemView) {

    moviesLayout = (LinearLayout) itemView.findViewById(R.id.movies_layout);
    imageViewPic = (ImageView) itemView.findViewById(R.id.imageViewPic);
    movieTitle = (TextView) itemView.findViewById(R.id.title);
    data = (TextView) itemView.findViewById(R.id.subtitle);
    movieDescription = (TextView) itemView.findViewById(R.id.description);
    rating = (TextView) itemView.findViewById(R.id.rating);

  }

  @Override public void bind(Movie item) {

    movieTitle.setText(item.getTitle());
    data.setText(item.getReleaseDate());
    movieDescription.setText(item.getOverview());
    rating.setText(item.getVoteAverage().toString());

    String url = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + item.getPosterPath();

    Glide
        .with(imageViewPic.getContext())
        .load(url)
        .centerCrop()
        .into(imageViewPic);




  }
}
