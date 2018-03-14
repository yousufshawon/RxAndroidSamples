package com.yousuf.shawon.rxandroidsamples.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yousuf.shawon.rxandroidsamples.R;
import com.yousuf.shawon.rxandroidsamples.util.Log;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

/**
 * A simple {@link Fragment} subclass.
 */
public class CombineLatestFragment extends BaseFragment {

  EditText editTextName, editTextEmail, editTextPhone;
  Button buttonSubmit;

  // Observables
  Observable<CharSequence> nameObservable, emailObservable, phoneObservables;
  private String TAG = getClass().getSimpleName();

  public CombineLatestFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView =  inflater.inflate(R.layout.fragment_combine_latest, container, false);

    initializeViews(rootView);
    addEvents();
    return rootView;
  }

  private void initializeViews(View rootView) {

    editTextName = (EditText) rootView.findViewById(R.id.editTextName);
    editTextEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
    editTextPhone = (EditText) rootView.findViewById(R.id.editTextPhone);

    buttonSubmit = (Button) rootView.findViewById(R.id.buttonSubmit);

    addListeners();

  }

  private void addListeners() {

    nameObservable = RxTextView.textChanges(editTextName).skip(1);
    emailObservable = RxTextView.textChanges(editTextEmail).skip(1);
    phoneObservables = RxTextView.textChanges(editTextPhone).skip(1);

    Disposable disposableSubmit =
    RxView.clicks(buttonSubmit)
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {
            Toast.makeText(getContext(), "Congrats", Toast.LENGTH_SHORT).show();
          }
        });

    compositeDisposable.add(disposableSubmit);

  }


  private void addEvents(){


        Observable.combineLatest(nameObservable, emailObservable, phoneObservables,
            new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
              @Override public Boolean apply(@NonNull CharSequence name,
                  @NonNull CharSequence email, @NonNull CharSequence phone) throws Exception {

                Log.i(TAG, "Validating");

                boolean isNameValid = !TextUtils.isEmpty(name);
                if (!isNameValid) {
                  editTextName.setError("Required");
                }

                boolean isEmailValid = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if (!isEmailValid) {
                  editTextEmail.setError("Invalid Email");
                }


                boolean isPhoneValid = !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
                if (!isPhoneValid) {
                  editTextPhone.setError("Invalid Phone");
                }
                    return isNameValid && isEmailValid && isPhoneValid;
              }
            })
        .subscribe(new Observer<Boolean>() {

          @Override public void onSubscribe(@NonNull Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onNext(@NonNull Boolean isFormValid) {
            Log.i(TAG, "onNext");

            changeSubmitStatus(isFormValid);
          }

          @Override public void onError(@NonNull Throwable e) {
            Log.e(TAG, e.getMessage());
          }

          @Override public void onComplete() {
            Log.i(TAG, "onComplete");
          }
        });


  }


  private void changeSubmitStatus(boolean isEnabled){
    buttonSubmit.setEnabled(isEnabled);
    float alpha = 0.5f;
    if (isEnabled) alpha = 1.0f;

    buttonSubmit.setAlpha(alpha);

  }
}
