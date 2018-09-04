package com.techyourchance.fragmenthelpersample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.techyourchance.fragmenthelper.FragmentContainerWrapper;
import com.techyourchance.fragmenthelper.FragmentHelper;

public class SampleFragment extends Fragment {

    private static final String ARG_FRAGMENT_COUNT = "ARG_FRAGMENT_COUNT";

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

    private FragmentHelper mFragmentHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);

        mFragmentHelper = new FragmentHelper(
                requireActivity(),
                ((FragmentContainerWrapper) requireActivity()),
                requireActivity().getSupportFragmentManager()
        );

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

        return view;
    }

    private void switchToNextFragment() {
        Fragment nextFragment = SampleFragment.newInstance(getNextFragmentCount());
        if (shouldAddToBackstack()) {
            mFragmentHelper.replaceFragment(nextFragment);
        } else {
            mFragmentHelper.replaceFragmentDontAddToBackstack(nextFragment);
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
