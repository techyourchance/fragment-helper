package com.techyourchance.fragmenthelpersample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.techyourchance.fragmenthelper.FragmentContainerWrapper;
import com.techyourchance.fragmenthelper.FragmentHelper;

public class SampleActivity extends AppCompatActivity implements FragmentContainerWrapper {

    private ImageButton mBtnUp;
    private FragmentHelper mFragmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        mBtnUp = findViewById(R.id.btn_up);
        mBtnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentHelper.navigateUp();
            }
        });

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
