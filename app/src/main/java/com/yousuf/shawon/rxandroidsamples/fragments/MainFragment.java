package com.yousuf.shawon.rxandroidsamples.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import com.jakewharton.rxbinding2.view.RxView;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.util.Log;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.reactivestreams.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

  Button buttonDeBounce, buttonRetrofit;

  // disposable
  CompositeDisposable compositeDisposable = new CompositeDisposable();

  String TAG = getClass().getSimpleName();

  public MainFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);;
    initializeViews(rootView);

    addListeners();

    return rootView;
  }


  private void initializeViews(View rootView){

    buttonDeBounce = (Button) rootView.findViewById(R.id.buttonDebounce);
    buttonRetrofit= (Button) rootView.findViewById(R.id.buttonRetrofit);
  }


  private void addListeners(){

   // Log.i(TAG, "Adding listeners");
    Disposable disposable =
    RxView.clicks(buttonDeBounce).subscribe(new Consumer<Object>() {
      @Override public void accept(Object o) throws Exception {
        onSelectItem(new DebounceSearchFragment());
      }
    });

    compositeDisposable.add(disposable);

    // Retrofit Example Button Click
    Disposable retrofitDisposable =
        RxView.clicks(buttonRetrofit).subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            onSelectItem(new RetrofitFragment());
          }
        });

    compositeDisposable.add(retrofitDisposable);


  }


  public void onSelectItem(@NonNull Fragment fragment){
    // tag is used to identify the fragment
    String tag = fragment.getClass().toString();
    getActivity().getSupportFragmentManager()
        .beginTransaction()
        .addToBackStack(tag)
        .replace(android.R.id.content, fragment, tag)
        .commit();

  }



  @Override public void onDestroy() {
    super.onDestroy();
    compositeDisposable.dispose();

  }
}
