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

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

  Button buttonDeBounce, buttonRetrofit, buttonZip, buttonCombineLatest, buttonBuffer,
    buttonObservable, buttonThreadSwitch, buttonAmdOperator, buttonCompose, buttonConcat;

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



    return rootView;
  }


  private void initializeViews(View rootView){

    buttonDeBounce = (Button) rootView.findViewById(R.id.buttonDebounce);
    buttonRetrofit= (Button) rootView.findViewById(R.id.buttonRetrofit);
    buttonZip= (Button) rootView.findViewById(R.id.buttonZip);
    buttonCombineLatest= (Button) rootView.findViewById(R.id.buttonCombineLatest);
    buttonBuffer = (Button) rootView.findViewById(R.id.buttonBuffer);
    buttonObservable = (Button) rootView.findViewById(R.id.buttonObservable);
    buttonThreadSwitch = (Button) rootView.findViewById(R.id.buttonThreadSwitch);
    buttonAmdOperator = (Button) rootView.findViewById(R.id.buttonAmbOperator);
    buttonCompose = (Button) rootView.findViewById(R.id.buttonCompose);
    buttonConcat = (Button) rootView.findViewById(R.id.buttonConcat);

    addListeners();
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

    Disposable zipDisposable =
        RxView.clicks(buttonZip).subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {

            onSelectItem(new ZipExampleFragment());

          }
        });

    compositeDisposable.add(zipDisposable);

    Disposable combineLatestDisposable =
        RxView.clicks(buttonCombineLatest).subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            onSelectItem( new CombineLatestFragment());
          }
        });

    compositeDisposable.add(combineLatestDisposable);


    Disposable bufferDisposable =
        RxView.clicks(buttonBuffer).subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            onSelectItem(new BufferFragment());
          }
        });

    compositeDisposable.add(bufferDisposable);


      Disposable concatDisposable =
              RxView.clicks(buttonConcat).subscribe(new Consumer<Object>() {
                  @Override public void accept(Object o) throws Exception {
                      onSelectItem( ConcatExampleFragment.newInstance());
                  }
              });

      compositeDisposable.add(concatDisposable);



      Disposable observableDisposable =
            RxView.clicks(buttonObservable).subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    onSelectItem(ObservableWithOkHttpFragment.newInstance());
                }
            });

      compositeDisposable.add(observableDisposable);


      Disposable threadSwitchDisposable =
              RxView.clicks(buttonThreadSwitch).subscribe(new Consumer<Object>() {
                  @Override
                  public void accept(Object o) throws Exception {
                      onSelectItem( ThreadSwitchFragment.newInstance() );
                  }
              });

      compositeDisposable.add(threadSwitchDisposable);

      Disposable amdDisposable =
              RxView.clicks(buttonAmdOperator).subscribe(new Consumer<Object>() {
                  @Override
                  public void accept(Object o) throws Exception {
                      onSelectItem( AmbExampleFragment.newInstance() );
                  }
              });

      compositeDisposable.add(amdDisposable);

      Disposable composeDisposable =
              RxView.clicks(buttonCompose).subscribe(new Consumer<Object>() {
                  @Override
                  public void accept(Object o) throws Exception {
                      onSelectItem( ComposeExampleFragment.newInstance() );
                  }
              });

      compositeDisposable.add(composeDisposable);

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
