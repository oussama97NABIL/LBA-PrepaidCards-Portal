package com.mastercard.sampleapp.implementations;

import android.util.Log;

import com.mastercard.terminalsdk.emv.Tag;
import com.mastercard.terminalsdk.listeners.PaymentDataProvider;
import com.mastercard.terminalsdk.utility.ByteArrayWrapper;
import com.mastercard.terminalsdk.utility.ByteUtility;
import com.mastercard.sampleapp.mpos.MposApplication;
import com.mastercard.sampleapp.mpos.Tags;
import com.mastercard.sampleapp.utils.DataManager;

import java.util.HashMap;

import static com.mastercard.sampleapp.utils.DataManager.KEY_AMOUNT;


public class PosPaymentData implements PaymentDataProvider {

    private final static String TAG = "PosPaymentData";
    private HashMap<Integer, ByteArrayWrapper> mTagValueMap;

    public PosPaymentData() {

        mTagValueMap = new HashMap<>();

        populateDefault();

        buildActivateSignalData();
    }

    private void populateDefault() {
        // Account Type
        mTagValueMap.put(0x5F57, new ByteArrayWrapper("00"));
        // Amount Authorized (Numeric)
        mTagValueMap.put(0x9F02, new ByteArrayWrapper("000000001500"));
        // Amount Authorized (Binary)
        mTagValueMap.put(0x81, new ByteArrayWrapper("00000000"));
        // Amount Other (Numeric)
        mTagValueMap.put(0x9F03, new ByteArrayWrapper("000000000000"));
        // Amount Other (Binary)
        mTagValueMap.put(0x9F04, new ByteArrayWrapper("00000000"));
        // Balance Read Before Gen AC
        mTagValueMap.put(0xDF8104, null);
        // Balance Read After Gen AC
        mTagValueMap.put(0xDF8105, null);
        // Merchant Custom Data
        mTagValueMap.put(0x9F7C, new ByteArrayWrapper("4469676974616C44657669636573202620496F54"));
        // Transaction Category Code
        mTagValueMap.put(0x9F53, new ByteArrayWrapper("52"));
        // Transaction Currency Code
        mTagValueMap.put(0x5F2A, new ByteArrayWrapper("0978"));
        // Transaction Currency Exponent
        mTagValueMap.put(0x5F36, new ByteArrayWrapper("02"));
        // Transaction Type
        mTagValueMap.put(0x9C, new ByteArrayWrapper("00"));
    }

    public void buildActivateSignalData() {


        int fAmount = (int) (MposApplication.INSTANCE.getDataManager().getFloatData(
                KEY_AMOUNT) + 0.1);

        byte[] newAmount = ByteUtility.padData(ByteUtility.longToBcd(fAmount), 6, Tag.Format.n);

        Log.e(TAG, "buildActivateSignalData: Amount" + ByteUtility.byteArrayToHexString(newAmount));

        setPaymentDataEntry(Tags.AMOUNT_AUTHORIZED_NUMERIC.getTag(), new ByteArrayWrapper(newAmount));

        String currencyCode = MposApplication.INSTANCE.getDataManager().getStringData(
                DataManager.KEY_CURRENCY);
        if (currencyCode == null) {
            setPaymentDataEntry(Tags.TRANSACTION_CURRENCY_CODE.getTag(),
                    new ByteArrayWrapper(ByteUtility.hexStringToByteArray(DataManager.CurrencyCode.GBP.getCurrencyCode())));
        } else {
            setPaymentDataEntry(Tags.TRANSACTION_CURRENCY_CODE.getTag(),
                    new ByteArrayWrapper(ByteUtility.hexStringToByteArray(DataManager.CurrencyCode.valueOf(currencyCode).getCurrencyCode())));
        }

        String transactionType = MposApplication.INSTANCE.getDataManager().getStringData(
                DataManager.KEY_TRANSACTION_TYPE);

        if (transactionType == null) {
            setPaymentDataEntry(Tags.TRANSACTION_TYPE.getTag(),
                    new ByteArrayWrapper(ByteUtility.hexStringToByteArray(DataManager.TransactionType.PURCHASE.getTransactionTypeValue())));
        } else {
            setPaymentDataEntry(Tags.TRANSACTION_TYPE.getTag(),
                    new ByteArrayWrapper(ByteUtility.hexStringToByteArray(DataManager.TransactionType.valueOf(
                            transactionType).getTransactionTypeValue())));
        }
    }

    @Override
    public HashMap<Integer, ByteArrayWrapper> getPaymentDataMap() {
        return mTagValueMap;
    }

    @Override
    public void setPaymentDataEntry(Integer integer, ByteArrayWrapper byteArrayWrapper) {
        mTagValueMap.put(integer, byteArrayWrapper);
    }
}
