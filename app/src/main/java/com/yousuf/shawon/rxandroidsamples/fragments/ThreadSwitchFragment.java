package com.yousuf.shawon.rxandroidsamples.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreadSwitchFragment extends BaseFragment {


    TextView textView;

    String TAG = getClass().getSimpleName();

    public ThreadSwitchFragment() {
        // Required empty public constructor
    }


    public static ThreadSwitchFragment newInstance() {

        Bundle args = new Bundle();

        ThreadSwitchFragment fragment = new ThreadSwitchFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thread_switch, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textView = getView().findViewById(R.id.textView);

        runThreadSwitchExample();
    }

    private void runThreadSwitchExample() {

        textView.setText("Performing Thread Switch Example");

        Disposable disposable =
        Observable
                .fromCallable(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        long startTime = System.currentTimeMillis();

                        String message = "Working on " + Thread.currentThread().getName() + " Thread for ";
                        Thread.sleep(500);
                        message += (System.currentTimeMillis() - startTime) + " milliseconds";

                        return message;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        long startTime = System.currentTimeMillis();
                        s += "\n\n";
                        String message  = s + "Working on " + Thread.currentThread().getName() + " Thread for ";
                        Thread.sleep(500);
                        message += (System.currentTimeMillis() - startTime) + " milliseconds";

                        return message;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        long startTime = System.currentTimeMillis();
                        s += "\n\n";
                        String message  = s + "Working on " + Thread.currentThread().getName() + " Thread for ";
                        Thread.sleep(500);
                        message += (System.currentTimeMillis() - startTime) + " milliseconds";

                        Log.i(TAG, message);
                        textView.setText(message);
                        Toast.makeText(getActivity(), "Work completed", Toast.LENGTH_SHORT).show();
                    }
                });

        addToCompositeDisposable(disposable);

    }
}
