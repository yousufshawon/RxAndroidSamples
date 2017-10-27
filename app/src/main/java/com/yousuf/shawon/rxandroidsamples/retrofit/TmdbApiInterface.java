package com.yousuf.shawon.rxandroidsamples.retrofit;

import com.yousuf.shawon.rxandroidsamples.model.MoviesResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Yousuf on 10/27/2017.
 */

public interface TmdbApiInterface {

  @GET("movie/top_rated")
  Observable<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

 // @GET("movie/{id}")
 // Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);



}
