package com.mastercard.sampleapp.implementations;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.mastercard.sampleapp.mpos.MposApplication;
import com.mastercard.terminalsdk.listeners.ResourceProvider;

import java.io.IOException;
import java.io.InputStream;

import static android.content.ContentValues.TAG;

public class ResourceProviderImplementation implements ResourceProvider {

    Resources res;

    public ResourceProviderImplementation(Context context) {
        res = context.getResources();
    }

    @Override
    public InputStream getResource(String fileName) {
        // * Return an InputStream for the fileName
        try {
            return res.getAssets().open(fileName);
        } catch (IOException e) {
            MposApplication.INSTANCE.getTransactionProcessLogger().logError(e.getMessage());
            Log.e(TAG, "getResource: EXCEPTION " + e.getMessage());
        }
        return null;
    }
}
