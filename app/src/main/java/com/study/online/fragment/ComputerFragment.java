package com.study.online.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.online.R;


/**
 * Created by roy on 2016/12/16.
 */

public class ComputerFragment extends BaseFragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_computer, null);
        return mView;
    }
}
