package com.mastercard.sampleapp.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;

import com.mastercard.sampleapp.mpos.MposApplication;
import com.mastercard.sampleapp.api.SampleDataProviderImpl;
import com.mastercard.sampleapp.models.MposLibraryStatus;
import com.mastercard.sampleapp.utils.DataManager;

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
