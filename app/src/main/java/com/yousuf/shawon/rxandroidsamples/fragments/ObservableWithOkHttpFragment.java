package com.yousuf.shawon.rxandroidsamples.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.model.Gist;
import com.yousuf.shawon.rxandroidsamples.model.GistFile;
import com.yousuf.shawon.rxandroidsamples.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObservableWithOkHttpFragment extends BaseFragment {


    Disposable disposable;

    String TAG  = getClass().getSimpleName();

    public ObservableWithOkHttpFragment() {
        // Required empty public constructor
    }


    public static ObservableWithOkHttpFragment newInstance() {

        Bundle args = new Bundle();

        ObservableWithOkHttpFragment fragment = new ObservableWithOkHttpFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_observable_with_ok_http, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        disposable =
        getGistObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Gist>() {
                    @Override
                    public void accept(Gist gist) throws Exception {

                        StringBuilder sb = new StringBuilder();
                        // Output
                        for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
                            sb.append(entry.getKey());
                            sb.append(" - ");
                            sb.append("Length of file ");
                            sb.append(entry.getValue().content.length());
                            sb.append("\n");
                        }

                        TextView text = (TextView) getView().findViewById(R.id.textView);
                        text.setText(sb.toString());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i(TAG, "onComplete");
                    }
                });

        addToCompositeDisposable(disposable);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    private Gist getGist() throws IOException{

        OkHttpClient client = new OkHttpClient();

        // Go get this Gist: https://gist.github.com/donnfelker/db72a05cc03ef523ee74
        // via the GitHub API
        Request request = new Request.Builder()
                .url("https://api.github.com/gists/db72a05cc03ef523ee74")
                .build();

        Response response = client.newCall(request).execute();
        Log.i(TAG, "Response: " + response.toString());


        if (response.isSuccessful()) {
            Gist gist = new Gson().fromJson(response.body().charStream(), Gist.class);
            return gist;
        }

        return null;
    }




    public Observable<Gist> getGistObservable(){
       return Observable.defer(new Callable<ObservableSource<? extends Gist>>() {
           @Override
           public ObservableSource<? extends Gist> call() throws Exception {
               return Observable.just(getGist());
           }
       });
    }
}
