package com.mastercard.sampleapp.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.mastercard.sampleapp.mpos.MposApplication;
import com.mastercard.sampleapp.api.SampleDataProviderImpl;
import com.mastercard.sampleapp.models.MposLibraryStatus;
import com.mastercard.sampleapp.utils.DataManager;

public class BaseActivity extends AppCompatActivity {

    /**
     * Get the current status of MPOS library
     * @param context contxt of application
     * @return MposLibraryStatus
     */
    MposLibraryStatus getMposLibraryStatus(final Context context) {
        return getDataProvider().getMposLibraryStatus(context);
    }

    SampleDataProviderImpl getDataProvider() {
        return MposApplication.INSTANCE.getDataProvider();
    }

    DataManager getDataManager() {
        return MposApplication.INSTANCE.getDataManager();
    }
}
