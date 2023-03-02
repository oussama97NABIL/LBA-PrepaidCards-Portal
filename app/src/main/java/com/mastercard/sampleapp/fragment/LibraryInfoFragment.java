package com.mastercard.sampleapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mastercard.sampleapp.R;

public class LibraryInfoFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_library_info, container, false);

        getActivity().setTitle(getString(R.string.library_info));

        String libraryInfo = getMposLibraryStatus(getActivity()).getLibraryInfo();
        TextView libraryInfoTextView = (TextView) rootView.findViewById(R.id.library_info_text);
        libraryInfoTextView.setText(libraryInfo);

        return rootView;
    }

}
