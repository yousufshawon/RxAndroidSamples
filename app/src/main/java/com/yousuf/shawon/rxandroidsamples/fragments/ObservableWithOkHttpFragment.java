package com.yousuf.shawon.rxandroidsamples.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yousuf.shawon.rxandroidsamples.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObservableWithOkHttpFragment extends Fragment {


    public ObservableWithOkHttpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_observable_with_ok_http, container, false);
    }

}
