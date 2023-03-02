package com.mastercard.sampleapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mastercard.sampleapp.R;
import com.mastercard.sampleapp.models.MposLibraryStatus;
import com.mastercard.sampleapp.utils.DataManager;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private final String TAG = "HomeFragment";

    /**
     * Amount edit text field
     */
    private EditText mAmountEditText;

    /**
     * Root view of HomeFragment
     */
    private View mRootView;


    public View getRootView() {
        return mRootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().setTitle(getString(R.string.title_mpos));

        MposLibraryStatus mposLibraryStatus = getMposLibraryStatus(getActivity());

        Button buttonPay = (Button) mRootView.findViewById(R.id.button_start);

        mAmountEditText = (EditText) mRootView.findViewById(R.id.amount_edit_text);
        mAmountEditText.setEnabled(true);
        mAmountEditText.setHint(R.string.enter_amount);
        buttonPay.setText(R.string.start_payment);

        TextView countryText = (TextView) mRootView.findViewById(R.id.country_text);
        countryText.setText(mposLibraryStatus.getDefaultCounty());

        TextView currencyText = (TextView) mRootView.findViewById(R.id.currency_text);
        currencyText.setText(mposLibraryStatus.getDefaultCurrency());

        buttonPay.setOnClickListener(this);

        return mRootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                onStartOperation();
                break;
            default:
                Log.e(TAG, "onClick: unIdentified ButtonID was encountered");
        }
    }

    private void onStartOperation() {

        hideKeyboard();
        Fragment fragmentToLoad = new ProcessingFragment();

        String amount = mAmountEditText.getText().toString();
        if (amount != null && amount.length() > 0) {
            float amountValue2 = Float.parseFloat(amount);

            // update the amount in data manager
            getDataManager().saveFloatData(DataManager.KEY_AMOUNT, (amountValue2 * 100));
            amountValue2 = getDataManager().getFloatData(DataManager.KEY_AMOUNT);
            Log.e(TAG, "onStartOperation: " + amountValue2);
        } else {
            Snackbar.make(mRootView, R.string.invalid_amount, Snackbar.LENGTH_SHORT).show();
            return;
        }

        FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragmentToLoad);
        fragmentTransaction.commit();
    }

    /**
     * Hide the soft keyboard
     */
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
