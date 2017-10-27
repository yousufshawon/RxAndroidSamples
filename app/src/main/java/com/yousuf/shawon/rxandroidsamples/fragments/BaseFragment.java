package com.yousuf.shawon.rxandroidsamples.fragments;

import android.support.v4.app.Fragment;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Yousuf on 10/27/2017.
 */

public class BaseFragment extends Fragment {

  // disposable
  CompositeDisposable compositeDisposable = new CompositeDisposable();

  public void addToCompositeDisposable( Disposable disposable){
    if (compositeDisposable != null) {
      compositeDisposable.add(disposable);
    }
  }


  @Override public void onDestroy() {
    super.onDestroy();
    if (compositeDisposable != null) {
      compositeDisposable.dispose();
    }
  }
}
