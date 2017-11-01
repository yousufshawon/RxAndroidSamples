package com.yousuf.shawon.rxandroidsamples.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.jakewharton.rxbinding2.view.RxView;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.adapter.MovieItemsAdapter;
import com.yousuf.shawon.rxandroidsamples.model.Movie;
import com.yousuf.shawon.rxandroidsamples.model.MovieItem;
import com.yousuf.shawon.rxandroidsamples.model.MoviesResponse;
import com.yousuf.shawon.rxandroidsamples.retrofit.TmdbApiClient;
import com.yousuf.shawon.rxandroidsamples.retrofit.TmdbApiInterface;
import com.yousuf.shawon.rxandroidsamples.util.Log;
import com.yousuf.shawon.rxandroidsamples.util.Utils;
import com.yousuf.shawon.rxandroidsamples.util.VerticalSpaceItemDecoration;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZipExampleFragment extends BaseFragment {

  TmdbApiInterface tmdbApiInterface;
  Button buttonSearch;
  EditText editTextInput;
  TextView textViewSearchStatus, textViewTotal;
  RecyclerView recyclerViewMovies;

  String apiKey = "";

  MovieItemsAdapter mAdapter;

  private int[] viewResourceIds = {R.layout.row_movie_item};
  private int[] viewTypeIds = {0};

  List<MovieItem> movieItemList;

  String TAG = getClass().getSimpleName();

  public ZipExampleFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView =  inflater.inflate(R.layout.fragment_zip_example, container, false);
    initializeViews(rootView);
    return rootView;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

     apiKey = getString(R.string.tmdb_api_key);

    if (apiKey == null  || apiKey.isEmpty()) {
      Toast.makeText(getContext(), "Please use your api key", Toast.LENGTH_SHORT).show();
      buttonSearch.setEnabled(false);
      buttonSearch.setAlpha(0.5f);
    }else {
      buttonSearch.setEnabled(true);
      buttonSearch.setAlpha(1.0f);
    }

    movieItemList = new ArrayList<>();
    mAdapter = new MovieItemsAdapter(movieItemList, viewTypeIds, viewResourceIds);
    recyclerViewMovies.setAdapter(mAdapter);


  }



  private void initializeViews(View rootView){

    buttonSearch = (Button) rootView.findViewById(R.id.buttonSearch);
    editTextInput = (EditText) rootView.findViewById(R.id.editTextInput);
    recyclerViewMovies = (RecyclerView) rootView.findViewById(R.id.recyclerViewMovies);
    textViewSearchStatus = (TextView) rootView.findViewById(R.id.textViewSearchStatus);
    textViewTotal = (TextView) rootView.findViewById(R.id.textViewTotal);
    recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerViewMovies.addItemDecoration(new VerticalSpaceItemDecoration(Utils.dpToPx( getContext(), 8)));

    initializeListeners();

  }



  private void updateSearchButtonState(boolean enabled, float alpha){

    buttonSearch.setEnabled(enabled);
    buttonSearch.setAlpha(alpha);
  }

  private void initializeListeners(){

    Disposable searchDisposable =
    RxView.clicks(buttonSearch)
        // mapping action to String (input String)
        .map(new Function<Object, String>() {
          @Override public String apply(@NonNull Object o) throws Exception {
            Log.i(TAG, "Returning String '" + editTextInput.getText().toString().trim() + "' ");
            hideSoftKeyboard();
            return editTextInput.getText().toString().trim();
          }
        })
        // checking if string is not empty
        .filter(new Predicate<String>() {
          @Override public boolean test(@NonNull String str) throws Exception {
            Log.i(TAG, "Filtering .... ");
            if (str==null) {
              return false;
            }
            return str.length() > 0;
          }
        })
        .subscribe(new Consumer<String>() {
          @Override public void accept(String str) throws Exception {
            Log.i(TAG, "Got String '" + str + "' for my desired action");
            getSearchResult(str);
          }
        });

    compositeDisposable.add(searchDisposable);


  }


  private void getSearchResult(String searchQuery){



    updateSearchButtonState(false, 0.5f);

    textViewTotal.setText("");
    textViewSearchStatus.setText("Searching ......");

    movieItemList.clear();
    mAdapter.notifyDataSetChanged();

    tmdbApiInterface = TmdbApiClient.getTmdbService();

    Disposable searchDisposable =
    tmdbApiInterface.getSearchResult(apiKey , searchQuery)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<MoviesResponse>() {
          @Override public void accept(MoviesResponse moviesResponse) throws Exception {

            if (moviesResponse != null) {
              final List<Movie> movies = moviesResponse.getResults();
              Log.d(TAG, "Number of movies received: " + movies.size());
              textViewTotal.setText("Total " + movies.size());

              if (movies != null) {
                updateSearchButtonState(true, 1.0f);
                Observable.fromIterable(moviesResponse.getResults())
                    .flatMap(new Function<Movie, ObservableSource<MovieItem>>() {
                      @Override public ObservableSource<MovieItem> apply(@NonNull Movie movie)
                          throws Exception {

                        Log.i(TAG, "Got " + movie.getTitle() + " Movie");

                        Observable<MovieItem> movieObservable = tmdbApiInterface.getMovieDetails(movie.getId(), apiKey);
                        return  Observable.zip(movieObservable, Observable.just(movie), new BiFunction<MovieItem, Movie, MovieItem>() {
                          @Override
                          public MovieItem apply(@NonNull MovieItem movieItem, @NonNull Movie movie) throws Exception {
                            return movieItem;
                          }
                        });
                      }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<MovieItem>() {
                      @Override public void accept(MovieItem movieItem) throws Exception {
                          Log.i(TAG, "Budget: " +  movieItem.getBudget() );

                        if (movieItem != null) {
                          movieItemList.add(movieItem);
                          mAdapter.notifyDataSetChanged();
                          textViewSearchStatus.setText("" + movieItemList.size() + " item loaded");

                        }

                      }

                    }, new Consumer<Throwable>() {
                      @Override public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                      }
                    });


              }else{
                Toast.makeText(getContext(), "Invalid Response", Toast.LENGTH_SHORT).show();
                textViewSearchStatus.setText("Failed");
                updateSearchButtonState(true, 1.0f);
              }
            }else {
              Toast.makeText(getContext(), "Invalid Response", Toast.LENGTH_SHORT).show();
              textViewSearchStatus.setText("Failed");
              updateSearchButtonState(true, 1.0f);
            }

          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
            textViewSearchStatus.setText("Failed");
            updateSearchButtonState(true, 1.0f);
          }
        });

    compositeDisposable.add(searchDisposable);

  }

  private void hideSoftKeyboard() {
    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
    //Find the currently focused view, so we can grab the correct window token from it.
    View view = getActivity().getCurrentFocus();
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
      view = new View(getActivity());
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}
