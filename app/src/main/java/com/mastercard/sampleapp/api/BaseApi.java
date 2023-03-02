package com.mastercard.sampleapp.api;

import android.util.Log;

import com.mastercard.sampleapp.mpos.BrandIdentifier;
import com.mastercard.sampleapp.mpos.MposApplication;
import com.mastercard.sampleapp.implementations.PosPaymentData;
import com.mastercard.sampleapp.listener.TransactionListener;
import com.mastercard.terminalsdk.exception.*;
import com.mastercard.sampleapp.mpos.PaymentNetwork;
import com.mastercard.terminalsdk.listeners.TransactionProcessLogger;

import static android.content.ContentValues.TAG;

public class BaseApi {

    private BaseApi() {

    }

    /**
     * Start a new transaction
     *
     * @param transactionListener transaction result callback
     */
    public static void performTransaction(final TransactionListener transactionListener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    TransactionProcessLogger loggerImpl = MposApplication.INSTANCE.getTransactionProcessLogger();
                    BrandIdentifier brandIdentifier = new BrandIdentifier(MposApplication.INSTANCE.getNfcProvider(), loggerImpl);
                    brandIdentifier.identifyPaymentNetwork();

                    if (brandIdentifier.isMastercardSupported()) {

                        if (brandIdentifier.getMastercardPriority() > 01) {
                            loggerImpl.logInfo("There is another application with higher priority");
                        }
                        loggerImpl.logInfo("Invoking Mastercard SDK");
                        PosPaymentData paymentData = new PosPaymentData();
                        paymentData.buildActivateSignalData();
                        MposApplication.INSTANCE.getTransactionApi().proceedWithMastercardTransaction(
                                paymentData, brandIdentifier.getPpseResponse());
                    } else {
                        // application can invoke appropriate SDK for other Payment network
                        loggerImpl.logWarning("Unsupported Payment Network: ");
                        transactionListener.onTransactionCancelled();
                    }
                } catch (ReaderBusyException e) {
                    Log.e(TAG, "SDK is busy with another transaction, please wait");
                } catch (Exception er) {
                    Log.e(TAG, "run: ", er.getCause());
                    transactionListener.onTransactionCancelled();
                }
            }
        }

        ).start();
    }

    public static void abortTransaction(final TransactionListener transactionListener) {

        try {
            MposApplication.INSTANCE.getTransactionApi().abortTransaction();
        } catch (Exception er) {
            Log.e(TAG, "run: ", er.getCause());
            transactionListener.onTransactionCancelled();
        }
    }
}
