package com.yousuf.shawon.rxandroidsamples.model;

/**
 * Created by Yousuf on 10/26/2017.
 */

public class ContactPerson {

  String name;
  String phone;

  public ContactPerson(String name, String phone) {
    this.name = name;
    this.phone = phone;
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }
}
