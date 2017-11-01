package com.yousuf.shawon.rxandroidsamples.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yousuf on 10/29/2017.
 */

public class Genres {

  @SerializedName("id")
  int id;
  @SerializedName("name")
  String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
