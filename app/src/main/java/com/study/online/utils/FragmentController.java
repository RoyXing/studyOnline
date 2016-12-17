package com.study.online.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by roy on 2016/12/16.
 */

public class FragmentController {

    private int containerId;
    private FragmentManager fm;
    private List<Fragment> fragments;

    private static FragmentController controller;

    public static FragmentController getInstance(FragmentActivity activity, int containerId, List<Fragment> fragments) {
        if (controller == null) {
            controller = new FragmentController(activity, containerId, fragments);
        }
        return controller;
    }

    public static void onDestroy() {
        controller = null;
    }

    private FragmentController(FragmentActivity activity, int containerId, List<Fragment> fragments) {
        this.containerId = containerId;
        this.fragments = fragments;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public void showFragments(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

}
