package com.mastercard.sampleapp.mpos;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.mastercard.sampleapp.api.SampleDataProviderImpl;
import com.mastercard.sampleapp.implementations.DisplayImplementation;
import com.mastercard.sampleapp.implementations.CardCommProviderStub;
import com.mastercard.sampleapp.implementations.OutcomeObserver;
import com.mastercard.sampleapp.implementations.ResourceProviderImplementation;
import com.mastercard.sampleapp.implementations.TransactionProcessLoggerImplementation;
import com.mastercard.sampleapp.implementations.UnpredictableNumberImplementation;
import com.mastercard.sampleapp.implementations.nfc.NfcProvider;
import com.mastercard.sampleapp.models.Interface;
import com.mastercard.sampleapp.utils.DataManager;
import com.mastercard.terminalsdk.ConfigurationInterface;
import com.mastercard.terminalsdk.LibraryServicesInterface;
import com.mastercard.terminalsdk.TransactionInterface;
import com.mastercard.terminalsdk.emv.Tag;
import com.mastercard.terminalsdk.exception.ConfigurationException;
import com.mastercard.terminalsdk.exception.LibraryCheckedException;
import com.mastercard.terminalsdk.exception.ReaderBusyException;
import com.mastercard.terminalsdk.iso8825.BerTlv;
import com.mastercard.terminalsdk.listeners.CardCommunicationProvider;
import com.mastercard.terminalsdk.listeners.TransactionProcessLogger;
import com.mastercard.terminalsdk.listeners.UnpredictableNumberProvider;
import com.mastercard.terminalsdk.objects.LibraryInformation;
import com.mastercard.terminalsdk.TerminalSdk;
import com.mastercard.terminalsdk.utility.ByteArrayWrapper;
import com.mastercard.terminalsdk.utility.ByteUtility;

public class MposApplication extends Application {


    private final String TAG = "MposApplication";

    /**
     * Application instance
     */
    public static MposApplication INSTANCE;

    /**
     * Instance of {@link DataManager}
     */
    private DataManager mDataManager;
    /**
     * Instance of {@link SampleDataProviderImpl}
     */
    private SampleDataProviderImpl mDataProvider;


    /**
     * Instance of {@link TransactionProcessLogger}
     */
    private TransactionProcessLoggerImplementation mTransactionProcessLogger;

    /**
     * Instance of {@link TransactionInterface}
     */
    private TransactionInterface mTransactionApi;

    /**
     * Instance of {@link ConfigurationInterface}
     */
    private ConfigurationInterface mEmvLibraryConfiguration = null;

    /**
     * Instance of {@link com.mastercard.terminalsdk.listeners.CardCommunicationProvider}
     */
    private NfcProvider mNfcProvider;


    private CardCommunicationProvider mStubImpl;


    /**
     * Instance of {@link com.mastercard.terminalsdk.listeners.TransactionOutcomeObserver}
     */

    private OutcomeObserver mTransactionOutcomeObserver;

    final TerminalSdk emvLibrary = TerminalSdk.getInstance();


    @Override
    public void onCreate() {
        super.onCreate();
        assignInstance(this);
        initializeUiDataComponents();
    }

    private static void assignInstance(MposApplication mposApplication) {
        INSTANCE = mposApplication;
    }

    /**
     * Initialize the ui and data components
     */
    private void initializeUiDataComponents() {
        mDataProvider = new SampleDataProviderImpl();
        mDataManager = new DataManager(this);
    }

    public void configureResources(Activity activity) {

        mEmvLibraryConfiguration = emvLibrary.getConfiguration();
        try {
            mEmvLibraryConfiguration.withResourceProvider(
                    new ResourceProviderImplementation(this.getApplicationContext()));
        } catch (ConfigurationException e) {
        }
    }

    /**
     * Initialize the MPOS library
     */
    public void initializeMposLibrary(Activity activity) {

        //Card Comm Poviders
        mStubImpl = new CardCommProviderStub();
        mNfcProvider = new NfcProvider(activity);

        mTransactionOutcomeObserver = new OutcomeObserver();
        try {
            mTransactionProcessLogger = new TransactionProcessLoggerImplementation();
            mEmvLibraryConfiguration.withLogger(mTransactionProcessLogger)
                    .withCardCommunication(mNfcProvider, mStubImpl)
                    .withTransactionObserver(mTransactionOutcomeObserver)
                    .withUnpredictableNumberProvider(new UnpredictableNumberImplementation())
                    .withMessageDisplayProvider(
                            new DisplayImplementation(mTransactionProcessLogger));
            mTransactionApi = mEmvLibraryConfiguration.initializeLibrary();
        } catch (ConfigurationException e) {
            Log.e(TAG, "initializeMposLibrary: ConfigurationException was "
                    + "encountered " + e.getMessage());
        } catch (LibraryCheckedException ex) {
            Log.e(TAG, "initializeMposLibrary: LibraryCheckedException was "
                    + "encountered " + ex.getMessage());
        }

        setReaderProfile("MPOS");
    }

    /**
     * @return DataProvider
     */
    public SampleDataProviderImpl getDataProvider() {
        return mDataProvider;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    /**
     * TransactionInterface object
     *
     * @return mTransactionApi
     */
    public TransactionInterface getTransactionApi() {
        return mTransactionApi;
    }

    public void setReaderProfile(String readerProfile) {
        try {
            mEmvLibraryConfiguration.selectProfile(readerProfile);
        } catch (ReaderBusyException exc) {
            Log.e(TAG, "initializeMposLibrary: ReaderBusyException was "
                    + "encountered " + exc.getMessage());
        }
    }

    public OutcomeObserver getTransactionOutcomeObserver() {
        return mTransactionOutcomeObserver;
    }

    /**
     * Provides TransactionProcessLoggerImplementation object
     *
     * @return mTransactionProcessLogger
     */
    public TransactionProcessLoggerImplementation getTransactionProcessLogger() {
        return mTransactionProcessLogger;
    }

    /**
     * Provides NfcProvider object
     *
     * @return mNfcProvider
     */
    public NfcProvider getNfcProvider() {
        return mNfcProvider;
    }

    /**
     * Closes the  File connections used for logging
     */
    public void closeFileConnections() {
        mTransactionProcessLogger.closeFileWriter();
    }

    /**
     * Updates Terminal configuration
     *
     * @param tag   Tag to be updated
     * @param value value with which the tag to be updated
     */
    public void updateTerminalData(String tag, String value) {

        Tag wrapperTag = new Tag(Tags.TAG_UPDATE_DATA.getTagBytes(), Tag.Format.b, 0, 255, "Update data");
        BerTlv wrapperTlv = new BerTlv(wrapperTag);

        String tlvToUpdate = tag + ByteUtility.intToBerEncodedLength(value.length() / 2) + value;

        wrapperTlv.setRawBytes(new ByteArrayWrapper(tlvToUpdate));
        try {
            mEmvLibraryConfiguration.update(wrapperTlv);
        } catch (ConfigurationException e) {
            Log.e(TAG, "initializeMposLibrary: ConfigurationException was "
                    + "encountered " + e.getMessage());
        }
    }

    /**
     * @return Library Information
     */
    public LibraryInformation getLibraryInfo() {
        LibraryInformation libInfo = emvLibrary.getLibraryServices().getLibraryInformation();
        mDataManager.saveStringData(DataManager.KEY_LIBRARY_INFO, libInfo.toString());
        return libInfo;
    }

    public LibraryServicesInterface getLibraryServices() {
        return emvLibrary.getLibraryServices();
    }

    public void updateInterface(Interface targetInterface) {

        String readerName = mStubImpl.getDescription();
        switch (targetInterface) {
            case INTERNAL_NFC:
                readerName = mNfcProvider.getDescription();
                break;
            default:
                break;
        }
        try {
            mEmvLibraryConfiguration.setInterface(readerName);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
