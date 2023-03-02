package com.mastercard.sampleapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mastercard.sampleapp.R;
import com.mastercard.sampleapp.models.LogType;

import java.util.ArrayList;
import java.util.List;

public class TransactionLogsFragment extends BaseFragment {

    /**
     * Request code to receive logs value from bundle
     */
    public static final int REQUEST_CODE = 123456;
    /**
     * TextView to display transaction logs
     */
    private TextView mTransactionLogsText;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            String receivedData = data.getStringExtra(LogTypeSelectionFragment.BUNDLE_KEY);
            mTransactionLogsText.setText(receivedData);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_transaction_logs, container, false);

        getActivity().setTitle(getString(R.string.transaction_logs));

        List<LogType> logTypes = new ArrayList<>();
        String transactionLogs = getDataProvider().getTransactionLogs(logTypes);

        mTransactionLogsText = (TextView) rootView.findViewById(R.id.transaction_logs_text);
        mTransactionLogsText.setText(transactionLogs);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                LogTypeSelectionFragment dialogFragment = new LogTypeSelectionFragment();
                dialogFragment.setTargetFragment(TransactionLogsFragment.this, REQUEST_CODE);
                dialogFragment.show(manager, "Dialog");
            }
        });
        return rootView;
    }

}