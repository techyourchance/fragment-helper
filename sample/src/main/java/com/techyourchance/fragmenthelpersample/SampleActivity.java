package com.techyourchance.fragmenthelpersample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.techyourchance.fragmenthelper.FragmentContainerWrapper;
import com.techyourchance.fragmenthelper.FragmentHelper;

public class SampleActivity extends AppCompatActivity implements FragmentContainerWrapper {

    private FragmentHelper mFragmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        mFragmentHelper = new FragmentHelper(this, this, getSupportFragmentManager());

        if (savedInstanceState == null) {
            // don't add the empty state of this activity to backstack
            mFragmentHelper.replaceFragmentAndRemoveCurrentFromHistory(SampleFragment.newInstance(1));
        }
    }

    @Override
    public ViewGroup getFragmentContainer() {
        return findViewById(R.id.frame_content);
    }

    @Override
    public void onBackPressed() {
        mFragmentHelper.navigateBack();
    }
}
