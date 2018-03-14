package com.yousuf.shawon.rxandroidsamples.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.yousuf.shawon.rxandroidsamples.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class BufferFragment extends BaseFragment {

  Button buttonTap;
  TextView textViewStatus;

  public BufferFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView =  inflater.inflate(R.layout.fragment_buffer, container, false);
    initializeViews(rootView);
    return rootView;
  }

  private void initializeViews(View rootView) {

    buttonTap = (Button) rootView.findViewById(R.id.buttonTap);
    textViewStatus = (TextView) rootView.findViewById(R.id.textViewStatus);

    Disposable tapDisposable =
        RxView.clicks(buttonTap)
        .map(new Function<Object, Integer>() {
          @Override public Integer apply(@NonNull Object object) throws Exception {
            return 1;
          }
        })
        .buffer(2, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Integer>>() {
          @Override public void accept(List<Integer> integers) throws Exception {
            textViewStatus.setText("Total " + integers.size() + " tap found");
          }
        });

    compositeDisposable.add(tapDisposable);

  }



}
