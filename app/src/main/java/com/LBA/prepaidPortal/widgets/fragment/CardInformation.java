package com.LBA.prepaidPortal.widgets.fragment;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.AbstractActivity;
import com.LBA.prepaidPortal.activity.HomeActivity;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.Card;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;


public class CardInformation extends BaseFragment implements View.OnClickListener {

    private final String TAG = "TerminalOptionsFragment";
    ProgressDialog pDialog;


    private Spinner mCountrySpinner;

    private Spinner mCurrencySpinner;

    private View mRootView;
    public static final String COUNTRY_CODE = "9F1A";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_card_information, container, false);
        getActivity().setTitle(getString(R.string.terminal_options));




        Button buttonStart = (Button) mRootView.findViewById(R.id.button_start);
        buttonStart.setOnClickListener(this);


       // buttonStart.setOnClickListener(this);
                /*new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                initProgrees();
                CustomTask task = new CustomTask(getActivity().getApplicationContext());
                task.execute();
            }
        }*/
        return mRootView;
    }



    @Override
    public void onClick(View view) {
        final int id = 0;
        Log.e(TAG, "onClick: 111111111111111111111111111111111111111111111111");
        if (view.getId() == R.id.button_start) {

            Log.e(TAG, "onClick: 222222222222222222222222222222222222222");
                initProgrees();
                CustomTask task = new CustomTask(getActivity().getApplicationContext());
                task.execute();

            }
        }




    private class CustomTask extends AsyncTask<String, String, String> implements com.LBA.prepaidPortal.widgets.fragment.CustomTask {
        String accountSelected=null;
        String balanceData;

        public CustomTask(Context applicationContext) {
        }

        protected String doInBackground(String... param) {
            try {
                Log.e(TAG, "doInBackground: *********************************");
                    //Card.GetCardsToActivateList();
                    Card.CardDetails();
                    dismissProgress();

                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }




}}