package com.techyourchance.fragmenthelpersample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.techyourchance.fragmenthelper.FragmentAnimation;
import com.techyourchance.fragmenthelper.FragmentContainerWrapper;
import com.techyourchance.fragmenthelper.FragmentHelper;
import com.techyourchance.fragmenthelper.animations.SlideHorizontalEndInSlideHorizontalStartOut;

import java.util.ArrayList;
import java.util.List;

public class SampleFragment extends Fragment {

    private static final String ARG_FRAGMENT_COUNT = "ARG_FRAGMENT_COUNT";

    private static final String SAVED_STATE_ANIMATION_NAME = "SAVED_STATE_ANIMATION_NAME";

    public static SampleFragment newInstance(int fragmentCount) {
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT_COUNT, fragmentCount);
        SampleFragment fragment = new SampleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView mTxtFragmentCount;
    private Button mBtnNextFragment;
    private CheckBox mChkAddToBackstack;
    private AppCompatSpinner mSpinnerAnimation;

    private ArrayAdapter<String> mAdapterAnimations;

    private FragmentHelper mFragmentHelper;
    private String mFragmentAnimationName = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentHelper = new FragmentHelper(
                requireActivity(),
                ((FragmentContainerWrapper) requireActivity()),
                requireActivity().getSupportFragmentManager()
        );

        if (savedInstanceState != null) {
            mFragmentAnimationName = savedInstanceState.getString(SAVED_STATE_ANIMATION_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);

        mTxtFragmentCount = view.findViewById(R.id.txt_fragment_count);
        mTxtFragmentCount.setText(String.valueOf(getFragmentCount()));

        mBtnNextFragment = view.findViewById(R.id.btn_next_fragment);
        mBtnNextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToNextFragment();
            }
        });

        mChkAddToBackstack = view.findViewById(R.id.chk_add_to_backstack);

        mSpinnerAnimation = view.findViewById(R.id.spinner_animation);

        initSpinner();

        return view;
    }

    private void initSpinner() {
        List<String> animations = new ArrayList<>(5);
        animations.add("No animation");
        animations.add("SlideHorizontalEndInSlideHorizontalStartOut");

        mAdapterAnimations = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, animations);
        mAdapterAnimations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerAnimation.setAdapter(mAdapterAnimations);

        mSpinnerAnimation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                mFragmentAnimationName = adapterView.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_STATE_ANIMATION_NAME, mFragmentAnimationName);
    }

    private void switchToNextFragment() {
        Fragment nextFragment = SampleFragment.newInstance(getNextFragmentCount());
        if (shouldAddToBackstack()) {
            mFragmentHelper.replaceFragment(nextFragment, getFragmentAnimation());
        } else {
            mFragmentHelper.replaceFragmentAndRemoveCurrentFromHistory(nextFragment, getFragmentAnimation());
        }
    }

    private @Nullable FragmentAnimation getFragmentAnimation() {
        if (mFragmentAnimationName.isEmpty()) {
            return null;
        }
        switch (mFragmentAnimationName) {
            case "No animation":
                return null;
            case "SlideHorizontalEndInSlideHorizontalStartOut":
                return new SlideHorizontalEndInSlideHorizontalStartOut();
            default:
                throw new RuntimeException("unhandled animation: " + mFragmentAnimationName);
        }
    }

    private boolean shouldAddToBackstack() {
        return mChkAddToBackstack.isChecked();
    }

    private int getNextFragmentCount() {
        return getFragmentCount() + 1;
    }

    private int getFragmentCount() {
        return getArguments().getInt(ARG_FRAGMENT_COUNT);
    }
}
