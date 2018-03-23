package com.yousuf.shawon.rxandroidsamples.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.jakewharton.rxbinding2.view.RxView;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.util.Log;
import com.yousuf.shawon.rxandroidsamples.util.Utils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConcatExampleFragment extends BaseFragment {


    Button buttonConcat, buttonConcatEager, buttonMerge, buttonPublishMerge;
    TextView textView;

    Handler mainHandler;


    public ConcatExampleFragment() {
        // Required empty public constructor
    }


    public static ConcatExampleFragment newInstance() {

        Bundle args = new Bundle();

        ConcatExampleFragment fragment = new ConcatExampleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_concat_example, container, false);

        mainHandler = new Handler(Looper.getMainLooper());

        iniUI(rootView);


        return rootView;

    }

    private void iniUI(final View rootView) {

        buttonConcat = rootView.findViewById(R.id.buttonConcat);
        buttonConcatEager = rootView.findViewById(R.id.buttonConcatEager);
        buttonMerge = rootView.findViewById(R.id.buttonMerge);
        buttonPublishMerge = rootView.findViewById(R.id.buttonPublishMerge);
        textView = rootView.findViewById(R.id.textView);

        Disposable concatDisposable =
                RxView.clicks(buttonConcat)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        onConcatClick();
                    }
                });

        addToCompositeDisposable(concatDisposable);


        Disposable concatEagerDisposable =
                RxView.clicks(buttonConcatEager)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        onConcatEagerClick();
                    }
                });

        addToCompositeDisposable(concatEagerDisposable);


        Disposable mergeDisposable =
                RxView.clicks(buttonMerge)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                onMergeClick();
                            }
                        });

        addToCompositeDisposable(mergeDisposable);

        Disposable publishMergeDisposable =
                RxView.clicks(buttonPublishMerge)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                onPublishMergeClick();
                            }
                        });

        addToCompositeDisposable(publishMergeDisposable);






    }

    private void onConcatEagerClick() {

        textView.setText("");

        Observable.concatArrayEager(getResultFromSource1(), getResultFromSource2())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {

                        updateText(String.valueOf(s));
                        Log.i(TAG, "Message: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        dispose();
                        Log.i(TAG, "Observer isDisposed: " + isDisposed());
                    }

                    @Override
                    public void onComplete() {
                        dispose();
                        Log.i(TAG, "Observer isDisposed: " + isDisposed());
                    }
                });


    }

    private void onConcatClick() {

        textView.setText("");

        Observable.concat(getResultFromSource1(), getResultFromSource2())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {

                        updateText(String.valueOf(s));
                        Log.i(TAG, "Message: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        dispose();
                        Log.i(TAG, "Observer isDisposed: " + isDisposed());
                    }

                    @Override
                    public void onComplete() {
                        dispose();
                        Log.i(TAG, "Observer isDisposed: " + isDisposed());
                    }
                });

    }

    private void onMergeClick() {

        textView.setText("");

        Observable<String> observableSource1 = getResultFromSource1();
        Observable<String> observableSource2 = getResultFromSource2();

        Observable.merge(observableSource1, observableSource2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {

                        updateText(String.valueOf(s));
                        Log.i(TAG, "Message: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        dispose();
                        Log.i(TAG, "Observer isDisposed: " + isDisposed());
                    }

                    @Override
                    public void onComplete() {
                        dispose();
                        Log.i(TAG, "Observer isDisposed: " + isDisposed());
                    }
                });


    }


    private void onPublishMergeClick() {

        textView.setText("");


        getResultFromSource2()
                .publish(new Function<Observable<String>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Observable<String> observablesource2) throws Exception {

                        return Observable.merge( observablesource2, getResultFromSource1().takeUntil(observablesource2));
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {

                        updateText(String.valueOf(s));
                        Log.i(TAG, "Message: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        dispose();
                        Log.i(TAG, "Observer isDisposed: " + isDisposed());
                    }

                    @Override
                    public void onComplete() {
                        dispose();
                        Log.i(TAG, "Observer isDisposed: " + isDisposed());
                    }
                });


    }

    private void updateText(String s) {

        textView.setText(textView.getText() + "\n" + s);
    }


    private Observable<String> getResultFromSource1(){

        long delay = Utils.getRandomNumber();
      //  delay = delay + delay;
        Log.i(TAG, "Source1 will work " + delay + " ms");

        Observable<String> observable =
                Observable.range(0, 4)
                        .delay(delay, TimeUnit.MILLISECONDS)
                        .flatMap(new Function<Integer, ObservableSource<String>>() {
                            @Override
                            public ObservableSource<String> apply(Integer integer) throws Exception {
                              //  Log.i(TAG, "Source1 Thread for " + integer + " : "  + Thread.currentThread().getName());


                              //  if (integer==2) {
                                   // throw new RuntimeException("Manual Exception");
                              //      return Observable.just(null);
                              //  }

                                long delay = Utils.getRandomNumber();
                                return Observable.just("From Source1: " + integer)
                                        .delay(delay, TimeUnit.MILLISECONDS);
                            }
                        })
                        .subscribeOn(Schedulers.newThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateText("Source1 is started working");
                                    }
                                });
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateText("Source1 is finished work");
                                    }
                                });
                            }
                        });


        return observable;

    }


    private Observable<String> getResultFromSource2(){

        long delay = Utils.getRandomNumber();
        Log.i(TAG, "Source2 will work " + delay + " ms");
        //delay = delay + delay;
        Observable<String> observable =
                Observable.range(10, 4)
                        .delay(delay, TimeUnit.MILLISECONDS)
                        .flatMap(new Function<Integer, ObservableSource<String>>() {
                            @Override
                            public ObservableSource<String> apply(Integer integer) throws Exception {
                              //  Log.i(TAG, "Source2 Thread for " + integer + " : " + Thread.currentThread().getName());
                                return Observable.just("From Source2: " + integer)
                                        .delay(Utils.getRandomNumber(), TimeUnit.MILLISECONDS);
                            }
                        })
                        .subscribeOn(Schedulers.newThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateText("Source2 is started working");
                                    }
                                });
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateText("Source2 is finished work");
                                    }
                                });
                            }
                        });

        return observable;

    }

}
