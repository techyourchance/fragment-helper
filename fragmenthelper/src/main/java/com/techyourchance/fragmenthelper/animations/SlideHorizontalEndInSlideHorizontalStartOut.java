package com.techyourchance.fragmenthelper.animations;

import com.techyourchance.fragmenthelper.FragmentAnimation;
import com.techyourchance.fragmenthelper.R;

public class SlideHorizontalEndInSlideHorizontalStartOut implements FragmentAnimation {

    @Override
    public int getEnterAnimation() {
        return R.animator.slide_fragment_horizontal_right_in;
    }

    @Override
    public int getExitAnimation() {
        return R.animator.slide_fragment_horizontal_left_out;
    }

    @Override
    public int getPopEnterAnimation() {
        return R.animator.slide_fragment_horizontal_left_in;
    }

    @Override
    public int getPopExitAnimation() {
        return R.animator.slide_fragment_horizontal_right_out;
    }
}
