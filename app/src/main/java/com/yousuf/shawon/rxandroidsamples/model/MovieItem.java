package com.yousuf.shawon.rxandroidsamples.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Yousuf on 10/29/2017.
 */

public class MovieItem {

  @SerializedName("id")
  long id;
  @SerializedName("original_title")
  private String originalTitle;
  @SerializedName("title")
  private String title;
  @SerializedName("overview")
  private String overview;
  @SerializedName("budget")
  long budget;
  @SerializedName("genres")
  List<Genres> genresList;
  @SerializedName("poster_path")
  private String posterPath;
  @SerializedName("popularity")
  private Double popularity;
  @SerializedName("runtime")
  private int runtime;
  @SerializedName("release_date")
  private String releaseDate;
  @SerializedName("status")
  String status;
  @SerializedName("vote_average")
  private Double voteAverage;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public long getBudget() {
    return budget;
  }

  public void setBudget(long budget) {
    this.budget = budget;
  }

  public List<Genres> getGenresList() {
    return genresList;
  }

  public void setGenresList(List<Genres> genresList) {
    this.genresList = genresList;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public Double getPopularity() {
    return popularity;
  }

  public void setPopularity(Double popularity) {
    this.popularity = popularity;
  }

  public int getRuntime() {
    return runtime;
  }

  public void setRuntime(int runtime) {
    this.runtime = runtime;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(Double voteAverage) {
    this.voteAverage = voteAverage;
  }
}
