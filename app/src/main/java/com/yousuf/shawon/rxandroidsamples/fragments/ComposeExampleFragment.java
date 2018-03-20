package com.yousuf.shawon.rxandroidsamples.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.model.Gist;
import com.yousuf.shawon.rxandroidsamples.model.GistFile;
import com.yousuf.shawon.rxandroidsamples.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComposeExampleFragment extends BaseFragment {


    Button buttonExecute;
    TextView textView;

    static String TAG = ComposeExampleFragment.class.getSimpleName();


    public ComposeExampleFragment() {
        // Required empty public constructor
    }

    public static ComposeExampleFragment newInstance() {

        Bundle args = new Bundle();

        ComposeExampleFragment fragment = new ComposeExampleFragment();
        fragment.setArguments(args);
        return fragment;
    }



    private static ObservableTransformer GIST_TRANSFORMER =

           new ObservableTransformer<Gist, String>(){

               @Override
               public ObservableSource<String> apply(Observable<Gist> upstream) {
                   return upstream
                           .filter(new Predicate<Gist>() {
                               @Override
                               public boolean test(Gist gist) throws Exception {
                                   Log.i(TAG, "Thread: " + Thread.currentThread().getName());
                                   if (gist != null) {
                                       return true;
                                   }
                                   return false;
                               }
                           })
                           .map(new Function<Gist, String>() {
                               @Override
                               public String apply(Gist gist) throws Exception {
                                    Log.i(TAG, "Thread: " + Thread.currentThread().getName());
                                    StringBuilder sb = new StringBuilder();
                                    // Output
                                    for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
                                        sb.append(entry.getKey());
                                        sb.append(" - ");
                                        sb.append("Length of file ");
                                        sb.append(entry.getValue().content.length());
                                        sb.append("\n");
                                    }

                                    return sb.toString();
                               }
                           });
               }
           };




//
//            new ObservableTransformer<Gist, String>() {
//                @Override
//                public Observable<String> call(final Observable<Gist> optionalObservable) {
//                    return optionalObservable
//                            .filter(new Predicate<Gist>() {
//                                @Override
//                                public boolean test(Gist gist) throws Exception {
//                                    if (gist != null) {
//                                        return true;
//                                    }
//                                    return false;
//                                }
//                            })
//                            .map(new Function<Gist, String>() {
//                                @Override
//                                public String apply(Gist gist) throws Exception {
//                                    Log.i(TAG, "Thread: " + Thread.currentThread().getName());
//                                    StringBuilder sb = new StringBuilder();
//                                    // Output
//                                    for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
//                                        sb.append(entry.getKey());
//                                        sb.append(" - ");
//                                        sb.append("Length of file ");
//                                        sb.append(entry.getValue().content.length());
//                                        sb.append("\n");
//                                    }
//
//                                    return sb.toString();
//                                }
//                            });
//                }
//            };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_compose_example, container, false);

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

        updateText("");

      // Task without compose
     /*
        getGistObservable()
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Gist>() {
                    @Override
                    public boolean test(Gist gist) throws Exception {
                        Log.i(TAG, "Thread: " + Thread.currentThread().getName());
                        if (gist != null) {
                            return true;
                        }
                        return false;
                    }
                })
                .map(new Function<Gist, String>() {
                    @Override
                    public String apply(Gist gist) throws Exception {

                        Log.i(TAG, "Thread: " + Thread.currentThread().getName());
                        StringBuilder sb = new StringBuilder();
                        // Output
                        for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
                            sb.append(entry.getKey());
                            sb.append(" - ");
                            sb.append("Length of file ");
                            sb.append(entry.getValue().content.length());
                            sb.append("\n");
                        }

                        return sb.toString();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {

                        updateText(s);
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
  */


        // Task without compose
        getGistObservable()
                .subscribeOn(Schedulers.io())
                .compose(getGistTransformer())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "Thread: " + Thread.currentThread().getName());
                        updateText(s);
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }




    public Observable<Gist> getGistObservable(){
        return Observable.defer(new Callable<ObservableSource<? extends Gist>>() {
            @Override
            public ObservableSource<? extends Gist> call() throws Exception {
                return Observable.just(getGist());
            }
        });
    }

    @Nullable
    private Gist getGist() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Log.i(TAG, "Thread: " + Thread.currentThread().getName());

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


    public static ObservableTransformer<Gist, String> getGistTransformer(){
        return GIST_TRANSFORMER;
    }


    private void updateText(String str) {
        textView.setText(str);
    }

}
