package com.LBA.prepaidPortal.widgets.fragment;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.AbstractActivity;
import com.LBA.prepaidPortal.activity.ApptimeoutManager;
import com.LBA.prepaidPortal.activity.NewMainPage;
import com.LBA.prepaidPortal.activity.SharedPrefManager;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.User;


/**
 * ****************************************************************************
 * Copyright (c) 2017, MasterCard International Incorporated and/or its
 * affiliates. All rights reserved.
 * <p/>
 * The contents of this file may only be used subject to the MasterCard
 * Mobile Payment SDK for MCBP and/or MasterCard Mobile MPP UI SDK
 * Materials License.
 * <p/>
 * Please refer to the file LICENSE.TXT for full details.
 * <p/>
 * TO THE EXTENT PERMITTED BY LAW, THE SOFTWARE IS PROVIDED "AS IS", WITHOUT
 * WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NON INFRINGEMENT. TO THE EXTENT PERMITTED BY LAW, IN NO EVENT SHALL
 * MASTERCARD OR ITS AFFILIATES BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 * *****************************************************************************
 */

public class BaseFragment extends Fragment {
    ProgressDialog pDialog;
    //start fatim 08042022
    protected  int delay=0;
    protected ApptimeoutManager apptimeoutManager;




    public void initProgrees() {
        // pDialog = new ProgressDialog(AbstractActivity.this);
        pDialog = new ProgressDialog(new ContextThemeWrapper(getActivity(), R.style.CustomFontDialog));
        pDialog.setMessage(getResources().getString(R.string.pleaseWait));
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setOnShowListener(new DialogInterface.OnShowListener() {


            @Override
            public void onShow(DialogInterface dialog) {
                // TODO Auto-generated method stub

                final int idAlertTitle = getActivity().getApplicationContext().getResources().getIdentifier("alertTitle", "id", "android");
                TextView textDialog = (TextView) ((AlertDialog) dialog).findViewById(idAlertTitle);

                textDialog.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/gilroy_bold.ttf"));
            }
        });

        pDialog.setCancelable(false);

        pDialog.setCanceledOnTouchOutside(false);
        delay=0;
        pDialog.show();
        getProgress();
    }

    public void dismissProgress() {
        if (pDialog != null && pDialog.isShowing()){
            pDialog.dismiss();}
        if(Globals.timeout != null)
            delay= Integer.parseInt(Globals.timeout);


    }


    public synchronized void getProgress(){
        apptimeoutManager.progreesDialog();
    }


}
