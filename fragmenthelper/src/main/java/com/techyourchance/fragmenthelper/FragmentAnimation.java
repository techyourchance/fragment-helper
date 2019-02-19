package com.techyourchance.fragmenthelper;

import android.support.annotation.AnimatorRes;

public interface FragmentAnimation {
    @AnimatorRes int getEnterAnimation();
    @AnimatorRes int getExitAnimation();
    @AnimatorRes int getPopEnterAnimation();
    @AnimatorRes int getPopExitAnimation();
}
