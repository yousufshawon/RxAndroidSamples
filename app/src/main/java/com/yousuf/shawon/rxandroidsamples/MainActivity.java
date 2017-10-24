package com.yousuf.shawon.rxandroidsamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.yousuf.shawon.rxandroidsamples.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(android.R.id.content, new MainFragment(), this.toString())
          .commit();
    }

  }
}
