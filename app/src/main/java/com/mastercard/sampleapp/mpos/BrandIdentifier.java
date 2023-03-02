package com.mastercard.sampleapp.mpos;


import android.util.Log;

import com.mastercard.terminalsdk.listeners.TransactionProcessLogger;
import com.mastercard.terminalsdk.objects.ContentType;
import com.mastercard.terminalsdk.utility.TLVUtility;
import com.mastercard.terminalsdk.exception.L1RSPException;
import com.mastercard.terminalsdk.exception.LibraryCheckedException;
import com.mastercard.terminalsdk.exception.LibraryUncheckedException;
import com.mastercard.terminalsdk.iso8825.BerTlv;
import com.mastercard.terminalsdk.listeners.CardCommunicationProvider;
import com.mastercard.terminalsdk.utility.ByteUtility;

import java.util.ArrayList;

import java.util.Arrays;

public class BrandIdentifier {

    private static final String TAG = "BrandIdentifier ";

    private CardCommunicationProvider mCardComms;
    private TransactionProcessLogger mLoggerImplementation;

    private byte[] mPpseResponse;

    private boolean isMcAppPresent;

    private int mMcAppPriority;

    public BrandIdentifier(CardCommunicationProvider cardCcmms, TransactionProcessLogger loggerImplementation) {

        mCardComms = cardCcmms;
        mLoggerImplementation = loggerImplementation;

    }

    public void identifyPaymentNetwork() {

        mPpseResponse = new byte[0];
        try {
            mCardComms.disconnectReader();

            mCardComms.connectReader();

            mCardComms.waitForCard();

            mLoggerImplementation.logInfo(TAG + "Sending PPSE command");
            String ppseCommand = "00A404000E325041592E5359532E444446303100";
            byte[] commandBytes = ByteUtility.hexStringToByteArray(ppseCommand);
            mLoggerImplementation.logApduExchange("CMD>> " + ppseCommand);
            mPpseResponse = mCardComms.sendReceive(commandBytes);
            mLoggerImplementation.logApduExchange("RSP<< " + ByteUtility.byteArrayToHexString(mPpseResponse));

            isMcAppPresent = false;
//            mMcAppPriority = 255;
            if (mPpseResponse != null && isSuccess(mPpseResponse)) {
                detectMastercardApp(mPpseResponse);
            }
        } catch (L1RSPException l1rsp) {
            Log.e(TAG, "L1RSP Exception:" + l1rsp.getMessage());
        } catch (LibraryUncheckedException luce) {
            Log.e(TAG, "LibraryCheckedException:" + luce.getMessage());
        }
    }

    private boolean isSuccess(byte[] responseBytes) {
        // copy status word
        if (responseBytes.length > 2) {
            byte[] statusWord = new byte[]{responseBytes[responseBytes.length - 2],
                    responseBytes[responseBytes.length - 1]};
            return (statusWord[0] & 0x00FF) == 0x0090 && statusWord[1] == 0x00;
        }
        return false;
    }

    private byte[] extractRid(byte[] aid) {

        if (aid.length < 5) {
            return new byte[0];
        }
        return Arrays.copyOfRange(aid, 0, 5);
    }

    public byte[] getPpseResponse() {
        return mPpseResponse;
    }

    public void detectMastercardApp(byte[] mPpseResponse) {

        mMcAppPriority = 255;
        BerTlv adfTlv = null;
        try {

            BerTlv value = TLVUtility.extractTLV(mPpseResponse,
                    Tags.FCI_DISC_DATA.getTagBytes(), ContentType.TLV);

            // this returns a collection of 61 templates
            ArrayList<BerTlv> directoryEntries = TLVUtility
                    .conditionalTlvParsing(value.getBytes(),
                            ContentType.TLV, "-", true);

            BerTlv priorityTlv;

            for (BerTlv directoryEntry : directoryEntries) {

                BerTlv adfName = TLVUtility.extractTLV(directoryEntry.getBytes(),
                        Tags.ADF_NAME.getTagBytes(), ContentType.TLV);

                byte[] rid = extractRid(adfName.getBytes());

                PaymentNetwork paymentNetwork = PaymentNetwork.get(ByteUtility.byteArrayToHexString(rid));

                if (paymentNetwork == PaymentNetwork.MASTERCARD
                        || paymentNetwork == PaymentNetwork.MASTERCARD_QPBOC) {
                    isMcAppPresent = true;

                    // check this
                    priorityTlv = TLVUtility.extractTLV(directoryEntry.getBytes(),
                            Tags.PRIORITY.getTagBytes(), ContentType.TLV);

                    if (priorityTlv != null) {
                        int priority = ByteUtility.byteArrayToInt(priorityTlv.getBytes());
                        if (priority < mMcAppPriority) {
                            mMcAppPriority = priority;
                        }
                    }
                }
            }

        } catch (LibraryCheckedException lce) {
            Log.e(TAG, "LibraryCheckedException:" + lce.getMessage());
        }
    }


    public boolean isMastercardSupported() {
        return isMcAppPresent;
    }

    public int getMastercardPriority() {
        return mMcAppPriority;
    }
}
