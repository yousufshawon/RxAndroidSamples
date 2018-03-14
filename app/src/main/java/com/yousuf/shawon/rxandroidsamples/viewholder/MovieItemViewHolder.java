package com.yousuf.shawon.rxandroidsamples.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.model.MovieItem;

/**
 * Created by Yousuf on 10/31/2017.
 */

public class MovieItemViewHolder extends BaseViewHolder<MovieItem> {

  ImageView imageViewPic;
  TextView movieTitle;
  TextView data;
  TextView movieDescription;
  TextView rating, textViewBudget, textViewRuntime, textViewGenres, textViewPopularity, textViewReleaseStatus;


  public MovieItemViewHolder(View itemView) {
    super(itemView);
  }

  @Override public void initialize(View itemView) {
    imageViewPic = (ImageView) itemView.findViewById(R.id.imageViewPic);
    movieTitle = (TextView) itemView.findViewById(R.id.title);
    data = (TextView) itemView.findViewById(R.id.subtitle);
    movieDescription = (TextView) itemView.findViewById(R.id.description);
    rating = (TextView) itemView.findViewById(R.id.rating);
    textViewBudget = (TextView) itemView.findViewById(R.id.textViewBudget);
    textViewRuntime = (TextView) itemView.findViewById(R.id.textViewRuntime);
    textViewGenres = (TextView) itemView.findViewById(R.id.textViewGenres);
    textViewPopularity = (TextView) itemView.findViewById(R.id.textViewPopularity);
    textViewReleaseStatus = (TextView) itemView.findViewById(R.id.textViewReleaseStatus);
  }

  @Override public void bind(MovieItem item) {

    movieTitle.setText(item.getTitle());
    data.setText(item.getOriginalTitle());
    movieDescription.setText(item.getOverview());
    rating.setText(item.getVoteAverage().toString());
    textViewBudget.setText( "Budget: " + item.getBudget() );
    textViewRuntime.setText( "Runtime: " +  item.getRuntime());
    textViewGenres.setText( "Genres:" +  getFormattedGenres(item));

    textViewPopularity.setText("Popularity: " +  String.format("%.02f", item.getPopularity()) );
    textViewReleaseStatus.setText("Status: " + item.getStatus());

    String url = "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + item.getPosterPath();

    Glide
        .with(imageViewPic.getContext())
        .load(url)
        .centerCrop()
        .into(imageViewPic);



  }

  private String getFormattedGenres(MovieItem item) {
    String genresStr = "";
    int length =  item.getGenresList().size();
    for (int i = 0; i < length; i++) {
      if ( i == 0 ) {
        genresStr += item.getGenresList().get(i).getName();
      }else {
        genresStr+= ", " + item.getGenresList().get(i).getName();

      }
    }

    return genresStr;
  }
}
