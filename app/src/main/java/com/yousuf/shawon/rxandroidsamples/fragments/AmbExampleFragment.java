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
import com.yousuf.shawon.rxandroidsamples.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmbExampleFragment extends BaseFragment {



    Button buttonExecute;
    TextView textView;

    Disposable disposable1, disposable2;

    public AmbExampleFragment() {
        // Required empty public constructor
    }

    public static AmbExampleFragment newInstance() {

        Bundle args = new Bundle();

        AmbExampleFragment fragment = new AmbExampleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_amb_example, container, false);

        initUI(rootView);

        return rootView;
    }

    private void initUI(View rootView) {

        buttonExecute = rootView.findViewById(R.id.buttonExecute);
        textView = rootView.findViewById(R.id.textView);

        Disposable disposable =
        RxView.clicks(buttonExecute)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        onExecuteButtonClick();
                    }
                });
        addToCompositeDisposable(disposable);

    }


    private void onExecuteButtonClick() {


        Observable.ambArray(getResultFromServerOne(), getResultFromServerTwo())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {

                        Log.i(TAG, "Execution Result: Thread : " + Thread.currentThread().getName());
                        Log.i(TAG, "Execution Result: " + s);

                        textView.setText(textView.getText() + "\n" + s);

                        buttonExecute.setEnabled(true);

                    }

                    @Override
                    public void onError(Throwable e) {
                       Log.e(TAG, e.getMessage());
                    }

                    @Override
                    protected void onStart() {
                        super.onStart();
                        buttonExecute.setEnabled(false);
                        Log.i(TAG, "DisposableObserver: onStart");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "DisposableObserver: onComplete");
                        dispose();
                        Log.i(TAG, "DisposableObserver: isDisposed: " + isDisposed());
                    }
                });



    }



    private Observable<String> getResultFromServerOne(){

        return Observable.<String>defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.<String> create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(final ObservableEmitter<String> emitter) throws Exception {

                        final Thread serverThread1 = new Thread(new Runnable() {
                            @Override
                           public void run() {

                                try {
                                    Log.i(TAG, "Server1: Thread: " + Thread.currentThread().getName());
                                    long sleepTime = getRandomNumber();
                                    Log.i(TAG, "Server1: will do work for " + sleepTime + " ms." );

                                    Thread.sleep(sleepTime);

                                    emitter.onNext("Result form Server1");
                                    emitter.onComplete();
                                    Log.i(TAG, "Server1: IsDisposed: " + emitter.isDisposed());

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    emitter.onError(e);
                                }
                            }
                        });

                        serverThread1.setName("Server Thread 1");
                        serverThread1.start();

//                        emitter.setCancellable(new Cancellable() {
//                            @Override
//                            public void cancel() throws Exception {
//                                serverThread1.interrupt();
//                            }
//                        });
                    }

                });

            }
        });


    }

    private Observable<String> getResultFromServerTwo(){


        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.<String> create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(final ObservableEmitter<String> emitter) throws Exception {

                        final Thread serverThread2 = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    Log.i(TAG, "Server2: Thread: " + Thread.currentThread().getName());

                                    long sleepTime = getRandomNumber();
                                    Log.i(TAG, "Server2: will do work for " + sleepTime + " ms." );

                                    Thread.sleep(sleepTime);

                                    emitter.onNext("Result form Server2");
                                    emitter.onComplete();
                                    Log.i(TAG, "Server2: IsDisposed: " + emitter.isDisposed());

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    emitter.onError(e);
                                }

                            }
                        });

                        serverThread2.setName("Server Thread 2");
                        serverThread2.start();

//                        emitter.setCancellable(new Cancellable() {
//                            @Override
//                            public void cancel() throws Exception {
//                                serverThread2.interrupt();
//                            }
//                        });
                    }

                });

            }
        });

    }



    private long getRandomNumber() {
        return (long) (Math.random() * 1000);
    }


}
