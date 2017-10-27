package com.yousuf.shawon.rxandroidsamples.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yousuf on 10/27/2017.
 */

public class TmdbApiClient {

  public static final String BASE_URL = "http://api.themoviedb.org/3/";
  private static Retrofit retrofit = null;

  public static TmdbApiInterface getTmdbService() {
    if (retrofit==null) {
      retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }
    return retrofit.create(TmdbApiInterface.class);
  }

}
