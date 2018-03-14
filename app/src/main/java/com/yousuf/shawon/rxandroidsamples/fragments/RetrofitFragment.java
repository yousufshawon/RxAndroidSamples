package com.yousuf.shawon.rxandroidsamples.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.adapter.MoviesAdapter;
import com.yousuf.shawon.rxandroidsamples.model.Movie;
import com.yousuf.shawon.rxandroidsamples.model.MoviesResponse;
import com.yousuf.shawon.rxandroidsamples.retrofit.TmdbApiClient;
import com.yousuf.shawon.rxandroidsamples.retrofit.TmdbApiInterface;
import com.yousuf.shawon.rxandroidsamples.util.Log;
import com.yousuf.shawon.rxandroidsamples.util.Utils;
import com.yousuf.shawon.rxandroidsamples.util.VerticalSpaceItemDecoration;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetrofitFragment extends BaseFragment {

  TmdbApiInterface tmdbApiInterface;

  RecyclerView recyclerViewMovies;

  MoviesAdapter mMovieAdapter;

  List<Movie> movieList;

  final int typeIds[] = new int[]{0};
  final int viewIds[] = new int[]{ R.layout.list_item_movie};

  String TAG = getClass().getSimpleName();

  public RetrofitFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    movieList = new ArrayList<>();
    mMovieAdapter = new MoviesAdapter(movieList, typeIds, viewIds);

    tmdbApiInterface = TmdbApiClient.getTmdbService();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView =  inflater.inflate(R.layout.fragment_retrofit, container, false);

    initializeViews(rootView);

    return rootView;
  }

  private void initializeViews(View rootView) {

    recyclerViewMovies = (RecyclerView) rootView.findViewById(R.id.recyclerViewMovies);
    recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerViewMovies.addItemDecoration(new VerticalSpaceItemDecoration(Utils.dpToPx( getContext(), 8)));
    recyclerViewMovies.setAdapter(mMovieAdapter);

  }


  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    String apiKey = getString(R.string.tmdb_api_key);

    if (apiKey == null  || apiKey.isEmpty()) {
      Toast.makeText(getContext(), "Please use your api key", Toast.LENGTH_SHORT).show();
      apiKey="";
    }

    Disposable retrofitDisposable =
    tmdbApiInterface.getTopRatedMovies(apiKey)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<MoviesResponse>() {
          @Override public void accept(MoviesResponse moviesResponse) throws Exception {

            if (moviesResponse != null) {
              List<Movie> movies = moviesResponse.getResults();
              Log.d(TAG, "Number of movies received: " + movies.size());

              if (movies != null) {
                movieList.clear();
                movieList.addAll(movies);
                mMovieAdapter.notifyDataSetChanged();
              }
            }else {
              Toast.makeText(getContext(), "Invalid Response", Toast.LENGTH_SHORT).show();
            }

          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            Log.e(TAG,  throwable.getMessage() );
          }
        });

    // must be disposed
     compositeDisposable.add(retrofitDisposable);

  }

  @Override public void onDestroy() {
    super.onDestroy();
    // super BaseFragment will dispose all disposable
  }
}
