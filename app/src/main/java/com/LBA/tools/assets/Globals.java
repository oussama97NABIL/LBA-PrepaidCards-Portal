package com.LBA.tools.assets;

import android.graphics.Bitmap;

import com.LBA.prepaidPortal.activity.Z_WelcomeActivity;
import com.LBA.tools.misc.AccountHabEntry;
import com.LBA.tools.misc.AdsEntry;
import com.LBA.tools.misc.AirtimeNetworkEntry;
import com.LBA.tools.misc.BankEntry;
import com.LBA.tools.misc.BeneficiaryEntry;
import com.LBA.tools.misc.BranchEntry;
import com.LBA.tools.misc.BranchGeoEntry;
import com.LBA.tools.misc.CardInformationDetail;
import com.LBA.tools.misc.EStatementEntry;
import com.LBA.tools.misc.ExpenseLimitEntry;
import com.LBA.tools.misc.ExpenseTrackingBean;
import com.LBA.tools.misc.ExpenseTrackingDetailsBean;
import com.LBA.tools.misc.ForexEntry;
import com.LBA.tools.misc.HistoryEntry;
import com.LBA.tools.misc.MomoProviderEntry;
import com.LBA.tools.misc.NotificationEntry;
import com.LBA.tools.misc.ProxyEntry;
import com.LBA.tools.misc.StandingOrderEntry;
import com.LBA.tools.misc.T24TrxHistDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by amine.wahbi on 17/8/2015.
 **/
public class Globals {
    //static public final String serverName = "ibank.theroyalbank.com.gh"; // EPS_INT LIVE
    //com  static public final String serverName = "192.168.1.26:9443"; // EPS_INT LIVE
    //static public final String serverName = "172.30.180.44:8443"; // EPS_INT TEST
    //hajer for local test 30/06/2020
  /*  static public final String serverName = "192.168.137.161:8084"; // EPS_INT LIVE
    static public final String serverURL = "http://192.168.137.161:8084/EPS_MB_SRV";*/
    //hajer for test in CBG SERVER
    /*hajer 07/08/2020 */  //  static public final String serverName = "172.30.180.44:8443"; // EPS_INT LIVE
    /*hajer 07/08/2020 */  //  static public final String serverURL = "https://172.30.180.44:8443/EPS_MB_SRV";

    //****************************************************************************

    // Hind  test  ENV


    static public final String serverName = "qrtest.apps.cbg.com.gh/mb/"; // EPS_INT LIVE
    static public final String serverURL = "https://192.168.1.29:29443/prepaid";
    //static public final String serverURL = "https://172.30.175.44:8443/EPS_MB_SRV";


    //  HIND  pre prod version 2 ********
/*   static public final String serverName = "preprod.apps.cbg.com.gh";
    static public final String serverURL = "https://preprod.apps.cbg.com.gh/EPS_MB_SRV2/";
*/


  // HIND : prod  env :- https://ibank.cbg.com.gh/mbapp/
  /*  static public final String serverName = "ibank.cbg.com.gh/mbapp/"; // EPS_INT LIVE
    static public final String serverURL = "https://ibank.cbg.com.gh/mbapp/";
*/


  ///***********************************************************************
// Younes
    /*
    static public final String serverName = "172.30.180.66:8443"; // EPS_INT LIVE
   static public final String serverURL = "https://172.30.180.66:8443/EPS_MB_SRV";
   */

    ////

    // static public final String serverName = "MAW-Laptop:9443"; // TEST Laptop MAW
    // static public final String serverName = "192.168.1.60:9443";
    // static public final String serverURL = "https://192.168.1.169:9443/EPS_MB_SRV";
    // static public final String serverURL = "https://192.168.1.16:9443/EPS_MB_SRV";
    // static public final String serverURL = "https://172.30.205.53:9443/EPS_MB_SRV"; // EPS_MOB
    // static public final String serverURL = "https://172.30.205.51:9443/EPS_MB_SRV"; // EPS_INT
    //static public final String serverURL = "https://"+serverName+"/EPS_MB_SRV"; // EPS_INT TEST
    //static public final String serverURL = "https://172.30.180.54:8443/EPS_MB_SRV"; //internal use
    //https://ibank.cbg.com.gh/mb
    //com static public final String serverURL = "https://ibank.cbg.com.gh/mb"; //live use
    // https://172.30.180.54:8443/EPS_MB_SRV
    //static public final String serverURL = "https://172.30.180.54:8443/EPS_MB_SRV"; //internal use
    // static public final String serverURL = "https://"+serverName+"/EPS_MB_SRV_TEST"; // EPS_INT TEST Test
    // static public final String serverURL = "https://192.168.10.91:9443/EPS_MB_SRV"; // MAW cbg
    // static public final String serverURL = "https://192.168.0.32:9443/EPS_MB_SRV";
    // static public final String serverURL = "https://192.168.1.70:9443/EPS_MB_SRV";
    //static public final String androidAccMngType ="ma.bits.cbgFaceLift_eps_mobilebankingapp";
    static public final String androidAccMngType ="ma.bits.cbgFaceLift_eps_mobilebankingapp";
    static public final String androidAccAuthenType ="CBG.MB.Authen";
    static public final String androidAccAuthenTokenType ="CBG.MB.AuthenToken";

    static public final String serviceSignIn = "/login";//oussama 3/30/2023
    static public final String ChangePassword = "/ChangePass";
    static public final String ChangePin= "/ChangePin";
    static public final String serviceAccountBalance = "/AccountBalance";
    static public final String serviceAccountInfo = "/AccountInfo";
    static public final String serviceAccountInfoByPhone = "/AccountInfoByPhone";
    static public final String serviceTransactionList = "/TransactionList";
    static public final String serviceExpenseTrackingList = "/ExpenseTracking";
    static public final String serviceExpenseLimitDetails = "/ExpenseLimitDetails";
    static public final String serviceExpenseLimitList = "/ExpenseLimitList";
    static public final String servicePINRequest = "/PINRequest";
    static public final String serviceCardDetails= "/CardDetails";
    static public final String service10LastTransactions= "/getLast10Transaction";
    static public final String serviceAccountToCard= "/accountToCard";
    static public final String serviceStopCard = "/StopCard";
    static public final String serviceCardsNotActiveList = "/CardsNotActiveList";
    static public final String serviceCardsToUnblockList = "/CardsStopList";
    static public final String serviceActivateCard = "/ActivateCard";
    static public final String serviceUnblockCard = "/UnblockCard";
    static public final String serviceUserAuthentication = "/UserAuthentication";
    static public final String serviceCardsActiveList = "/CardsActiveList";
    static public final String servicePINChange = "/PINChange";
    static public final String serviceChangeUserActivation= "/ChangeUserActivation";
    static public final String UserActivityHistory= "/UserActivityHistory";

    //othman

    //Younes
    static public final String serviceSelfSignUp = "/SelfSignUp";
    static public final String serviceConfirmCheque = "/ConfirmCheque";
    static public final String serviceTravelNotice = "/TravelNotice";
    static public boolean useOTPSelfSignUp = false;
    static public boolean SignInExisting = false;
    static public String phone;
    static public String lname;
    static public String fname;
    static public String ClientId; // added by younes for self signup
    static public String errorCustId=""; // added by youunes to display message after SignUp
    static public String errorUserId=""; // added by younes to display error after create user
    static public String clientData;
    static public String accountNumber;
    //static public String user;
    static public final String createUser="/createUser"; // younes
    static public String emailCustomer="";
    static public String telephone="";
    static public String nameCustomer="";
    static public  List<String> accountnbr = new ArrayList<String>();
    static public int  globalNumberOfTries=0;
    static public final String serviceSignUp = "/SignUp";
    public static String fnameCustomer;
    public static String idCustomer;
    public static String acountString;
    // to delete
    public static String qrTest;
    //   13062022
    static public final String doLogOut="/LogOut";
    //hajer 13/06/2022
    static public List<MomoProviderEntry> momoProvidersList;




    //start business logic of reset password services

    static public final String servicePassReset = "/PassReset";
    static public final String servicePassResetCheck = "/CheckPasswordOut"; // younes uncommented
    static public final String servicePassResetChange = "/PassResetChange";
    static public final String servicePassResetChangeSignIN = "/ForgetPassSignIn";
    static public final String servicePinResetChange = "/PINResetChange";

    static public final String servicePinReset = "/PinReset";
    static public final String servicePinResetOut = "/PinResetOut";
    static public final String servicePinResetCheckOut = "/CheckPinOut";

    static public final String serviceCheckValidationCode="/CheckValidationCode";
    static public final String serviceCheckOldAccount="/CheckOldAccount";
    static public final boolean isLoggedIn = false;

    //end  business logic  of reset password services


    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "ma.bits.cbgFaceLift_eps_mobilebankingapp";
    public static Class activity= Z_WelcomeActivity.class;

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an Udinic account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Udinic account";

    static public final String serviceAccountToAccount = "/TransferA2A";
    static public final String servicePrepaidTopUp = "/PrepaidTopUp";

    static public final String serviceACH = "/ACH";
    static public final String serviceInstantPay = "/InstantPay";
    static public final String serviceInstantPayNameEnq = "/InstantPayNameEnq";
    static public final String serviceMoneyVoucher = "/MoneyVoucher";
    static public final String serviceBtwnAcc = "/BetweenAccounts";


    static public final String serviceAirtimeTopup = "/AirtimeTopup";
    static public final String serviceMobileMoney = "/MobileMoney";
    static public final String serviceMobileWalletNameInq = "/MobileWalletNameInq"; // MAW20170816
    static public final String serviceDSTV = "/DSTV";
    static public final String serviceECG = "/ECG";
    static public final String serviceSurfline = "/Surfline";
    static public final String serviceSurflineDeviceQuery = "/SurflineDeviceQuery";
    static public final String serviceVodafone = "/Vodafone";

    static public final String serviceCheque = "/Cheque";
    static public final String serviceExpenseTrackingDetailed = "/ExpenseTrackingDetailed";
    static public final String serviceStandingOrderService = "/StandingRequest";
    static public final String serviceStopCheque = "/StopCheque";
    static public final String serviceChequeStatus = "/ChequeStatus";
    static public       String chequeStatus = "";
    static public final String serviceCheckOTP = "/CheckOTP";
    static public final String serviceCheckPIN = "/CheckPIN"; // Younes AB
    static public final String serviceForexList = "/ForexList";
    static public final String serviceGenerateOTP = "/GenerateOTP";
    static public final boolean useOTP = true;
    static public       String otp; // MAW20190918
    static public       String pinEntered; // YOUNES
    static public       String pin; // YOUNES
    static public       boolean resultAccCheck= false;
    static public       boolean oldAccount = false;
    static public       boolean userValid = false;

    //hajer QR
    static public final String serviceQRParse="/QrDataParse";
    static public final String serviceQRTransfert="/QrInstantPay";

    //   qr
    static public final String serviceQrEnquiry="/QrEnquiry";
    static public String timeout; //fatim 08042022


    static public int choiceTip=0;


    static public String transactionIdTag="transactionId";

    static public String sessionTKO;
    static public String transactionId;
    static public String userLimit = null;
    static public String UpdateAccountMessage = null;
    static public Double ecgAmountDue;
    static public String ecgClientName;
    static public String user;
    static public String cardToken;
    static public String password;
    static public String sessionId = " "; // YOUNES TEST
    static public String authenCode; // MAW20160521
    static public String authenToken; // MAW20160611

    static public List<String> accountsList;
    static public List<String> cardsList;
    static public CardInformationDetail cardOnline = new CardInformationDetail();
    static public  ArrayList<HistoryEntry> historyEntryList = new ArrayList<>();
    static public List<String> cardsToActivateList = new ArrayList<String>(); // MAW20170226
    static public List<String> maskedCardsList = new ArrayList<String>();
    static public List<CardInformationDetail> transactionList = new ArrayList<CardInformationDetail>();
    // static data to change later

    static {
        maskedCardsList.add("Select a card");
        maskedCardsList.add("card 1");
        maskedCardsList.add("card 2");
        maskedCardsList.add("card 3");
    }
    static public List<String> maskedCardsToActivateList = new ArrayList<String>(); // MAW20170226
    static public List<AccountHabEntry> accountsHabList;
    static public List<BankEntry> banksList;
    static public List<BankEntry> achBanksList;
    static public List<BankEntry> gipBanksList;
    static public List<BranchEntry> branchesList;

    static public List<ExpenseTrackingBean> expenseTrackingList = new ArrayList<ExpenseTrackingBean>();
    static public HashMap<String, List<ExpenseTrackingBean>> expenseTrackingmap = new LinkedHashMap<String, List<ExpenseTrackingBean>>();
    static public HashMap<String, ArrayList<ExpenseTrackingDetailsBean>> expenseTrackingDetailsMap = new LinkedHashMap<String, ArrayList<ExpenseTrackingDetailsBean>>();
    static public List<EStatementEntry> TrxList = new ArrayList<EStatementEntry>();

    static public List<ForexEntry> forexList = new ArrayList<ForexEntry>();
    static public List<AirtimeNetworkEntry> airtimeNetworkEntryList = new ArrayList<AirtimeNetworkEntry>();
    public static String Select="Select";
    public static String SelectAccount="Select An Account";
    static public String ReceiverPhoneName;
    static public String DSTVName;
    static public String ECGName;
    static public final String serviceDSTVNameInq = "/DSTVNameInq";
    static public final String serviceECGNameInq = "/ECGInq";
    static{
        airtimeNetworkEntryList.add(new AirtimeNetworkEntry("Select","Select"));
        airtimeNetworkEntryList.add(new AirtimeNetworkEntry("Airtel","62006"));
        airtimeNetworkEntryList.add(new AirtimeNetworkEntry("Expresso","62004"));
        airtimeNetworkEntryList.add(new AirtimeNetworkEntry("Globacom","62007"));
        airtimeNetworkEntryList.add(new AirtimeNetworkEntry("MTN","62001"));
        airtimeNetworkEntryList.add(new AirtimeNetworkEntry("Tigo","62003"));
        airtimeNetworkEntryList.add(new AirtimeNetworkEntry("Vodafone","62002"));
    }


    static public List<AirtimeNetworkEntry> providersEntryList = new ArrayList<AirtimeNetworkEntry>();

    static{
        providersEntryList.add(new AirtimeNetworkEntry("Select","Select"));
        providersEntryList.add(new AirtimeNetworkEntry("AirtelTigo Money","AIRTEL"));
        providersEntryList.add(new AirtimeNetworkEntry("MTN Mobile Money","MTN"));
        // providersEntryList.add(new AirtimeNetworkEntry("NOWAL","NOWAL"));
        providersEntryList.add(new AirtimeNetworkEntry("Vodafone Cash","VODAFONE"));
    }

    static public String surflineAccountType;
    static public List<String> listSurflineBundles = new ArrayList<String>();
    static public List<Double> listSurflineBundlesAmount = new ArrayList<Double>();
    static public List<String> listSurflinePlusBundles = new ArrayList<String>();
    static public List<Double> listSurflinePlusBundlesAmount = new ArrayList<Double>();

    static public String appVersionName="";
    static public int appVersionCode=0;

    //resetting password logic
    //3/28/2023 public  static UserModel userModelSession=null;

    // to test
    static public String name ="";
    // younes
    static public List<BranchGeoEntry> ListBranchGeoEntry = new ArrayList<BranchGeoEntry>();
    static public final String serviceGetBranchesLocations = "/GetLocations";

    static public List<String> accountTBSent;
    static public  List<String> currencyLst;

    static public String domicile_branch="";

    static public String purpose_type="";
    static public final String serviceTrPurpose= "/TrPurpose";
    static public  List<String> PurposeLstAG;
    static public  List<String> PurposeLstTR;
    static public List<String> PurposeLstH;
    static public List<String> PurposeLstU;
    static public List<String> PurposeLstC;
    static public List<String> PurposeMM;//Mobile Money
    static public List<String> PurposeQR;//Qr Payements
    static public List<String> PurposeAD;// AIRTIME_DATA
    static public List<String> PurposeDstv;// dstv
    static public List<String> PurposeVODAFONE;// VODAFONE


    static public List<String> PurposeLstCT;// Prepaid Card Topup -DONE
    static public List<String> PurposeLstA2M;// Mobile Transfer - DONE
    static public List<String> PurposeLstVT;// Money Voucher
    static public List<String> PurposeLstGIP;// Instant Pay Transfer
    static public List<String> PurposeLstBA;// Between Accounts Transfer
    static public List<String> PurposeLstACH;// Two Days Transfer - ACH - DONE
    static public List<String> PurposeLstA2A;// Account to Account Transfer




    static public final String serviceAccountToMobile = "/MobileTransfer";
    static public List<String> DestAccMobile = new ArrayList<String>();


    //younes payment

    static public final String servicePaymentTravel = "/PaymentTravel";
    static public final String servicePaymentHotel = "/PaymentHotel";
    static public final String servicePayeeNamesList = "/GetPayeesList";
    static public final String servicePaymentUniversity = "/PaymentUniversity";
    static public final String servicePaymentSchool = "/PaymentSchool";
    static public final String servicePaymentChurch = "/PaymentChurch";
    static public final String UpdateAccount = "/updateAccounts";

    static public  List<String> agentsList;
    //static public List<String> agentAccount;
    static public List<String> payeeAccount = new ArrayList<String>();
    static public List<String> hotelList;
    static public List<String> universityList;
    static public List<String> schoolsList;
    static public List<String> churchList;

    // younes benef
    // static public List<BankEntry> ALLBanksList;
    static public List<BeneficiaryEntry> BeneficyList = new ArrayList<BeneficiaryEntry>();

    static public final String AddBeneficiary = "/AddBeneficiary";
    static public final String UpdateBeneficiary = "/UpdateBeneficiary";
    static public final String DeleteBeneficiary = "/DeleteBeneficiary";
    static public final String ListBeneficiary = "/ListBeneficiary";

    static public  String nameBenef = "";
    static public  String accBenef = "";
    static public  String mobBenef = "";
    static public  String amountBenef = "";
    static public BeneficiaryEntry Beneficy;
    static public String op;

    static public String errBenef="";
    static public String AccCheck="";
    static public final String AccCheckService = "/AccountCheckDial";

    static public List<AdsEntry> Ads = new ArrayList<>();
    // New Onbrading
    static public List<String> branchOn;
    static public List<BranchEntry> branches =  new ArrayList<BranchEntry>();
    static public final String serviceGetBranches ="/GetBranches";
    static public final String newSignUp = "/NewSignUpDial";
    static public final String newSignUpBiz = "/NewSingUpBizDial";
    static public String username="";
    static public String firstName="";
    static public String firstNameC="";
    static public String errorOnBoard = "";
    static public String AccountLink = null;
    static public String AccountUnLink = null;
    static public String ERmsg = ""; // error sign in
    static public String ERpin = ""; // error pin

    static public final String serviceEStatement= "/EStatement";
    static public final String serviceExpenseLimit= "/ExpenseLimit";
    static public final String serviceUpdateExpenseLimit= "/UpdateExpenseLimit";
    static public final String serviceDeleteExpenseLimit= "/DeleteExpenseLimit";
    static public final String serviceNotification= "/Notifications";
    static public final String serviceUnviewedNotification= "/UnviewedNotifications";
    static public final String getAllNotifications= "/getAllNotifications";
    static public final String serviceOffStatement = "/OffStatement";
    static public final String serviceCardLimit= "/CardLimit";
    static public final String serviceStandingOrderList = "/StandingOrderList";
    static public List<StandingOrderEntry> SOList;
    static public boolean backFlag = false;

    //************* visaStatement *****************************
    static public final String serviceVisaStatement = "/VisaStatement";
    static public final String serviceVisaStatementCharges = "/VisaStatementCharges";

    // PROXY
//    static public final String AddProxyService = "/AddProxy";
    static public final String AddProxyService = "/ProxyRegistration";
    static public final String serviceDeleteProxy= "/ProxyDelete";
    static public final String serviceProxyFetch= "/ProxyFetch";
    static public final String serviceProxyUpdate= "/ProxyUpdate";
    static public final String serviceProxyTransfer= "/ProxyTransfer";
    static public final String serviceProxyList= "/ProxyList";
    static public ArrayList<ProxyEntry> ProxyList = new  ArrayList<ProxyEntry>() ;
    static public Bitmap appBackBit;
    // hajer bio
    static public final String serviceSetfingerPrint = "/SetFingetPrint";
    static public boolean checkMenuFingerPrintActivate = false;
    static public boolean checkMenuFingerPrintDesactivate = false;
    static public String statusBiom;
    static public boolean activateBiometric = false;
    static public String CC="";
    static public boolean surf=false;
    static public String OffAmountPay =null;
    static public String OffPages = null;
    static public final String serviceOffStatementCharges = "/OffStatementCharges";
    static public final String linkAccounts ="/LinkSolPropToIndv";
    static public final String linkAccountsBiz ="/LinkSolPropToIndvBiz";
    static public final String notificationHistory = "/NotificationsHistory";
    static public Bitmap profileImage;

    //------------- Notifications ------------------


    static public ArrayList<NotificationEntry> notificationsList = new ArrayList<>();
    static public ArrayList<NotificationEntry> getAllNotificationList = new ArrayList<>();
    static public ArrayList<NotificationEntry> unviewdNotificationsList = new ArrayList<>();
    static public ArrayList<NotificationEntry> unviewdNotificationsListIn = new ArrayList<>();
    static public boolean notificationViewed = false;
    static public int NOTIFICATION_LIST = 0;
    static public boolean firstNotificationFetch = true;

    static public List<String> limitedOperations = new ArrayList<>();
    static public Map<String, ArrayList<String>> limitedOperationsAccounts = new HashMap<>();
    static public ArrayList<ExpenseLimitEntry> AllAccountsLimits = new ArrayList<>();
    static public ArrayList<NotificationEntry>pushNotificationsList = new ArrayList<>();
    static public String userType;
    static public boolean isSuccessful ;
    static public final String UploadProfileImage = "/UploadProfileImage";
    static public final String UnlinkAccount = "/UnlinkAccount";
    static public boolean hasLinkedAccount = false;
    static public List<String> MustUsedOperations = new ArrayList<>();
//  26092022
   static public List<String> pickUpBy;

    static public final Map<String,String> allTransfersList = new HashMap<String,String>(){
        {
            put("Between Own Accounts Transfer","BA" );
            put("Transfer to CBG Accounts","AT");
            put("Transfer to Other App Users","AT");
            put("Other Banks - Instant Pay(GIP)","OT");
            put("To Other Banks- ACH(Two days)","HT");
            put("Travel Agent Payment","TP");
            put("Hotel Payment","HP");
            put("University Payment","PU");
            put("School Payment","PI");
            put("Church Payment","CP");
            put("Airtime Top Up","PT");
            put("Mobile Money","MM");
            put("Surfline","PS");
            put("QR Instant Pay","QG");
            put("DSTV","PD");
            put("ECG","PE");
            put("Money Voucher","VT");
        }
    };

// 3/28/2023   static public  Map<String, Class> operationLinks  = new HashMap<String, Class>() {{
//        put("Between Own Accounts Transfer", BetweenOwnAccountsActivity.class);
//        put("Transfer to Other CBG Accounts", TransferActivity.class);
//        put("Transfer to Other App Users", TransferNonCBGActivity.class);
//        put("To Other Banks- Instant Pay Transfer", GIPActivity.class);
//        put("To Other Banks- ACH(Two days)", ACHActivity.class);
//        put("Travel Agent Payment", TravelAgentsActivity.class);
//        put("Hotel Payment", HotelsActivity.class);
//        put("QR Instant Pay", QrPaymentActivity.class);
//        put("School Payment", SchoolsActivity.class);
//        put("Church Payment", ChurchesActivity.class);
//        put("Airtime Top Up", AirtimeActivity.class);
//        put("Money Voucher", MoneyVoucherActivity.class);
//        put("Mobile Money", MobileMoneyActivity.class);
//        put("Surfline",SurflineActivity.class);
//        put("Proxy", ProxyPayList.class);
//        put("Prepaid Card Top Up", PrepaidTopUp.class);
//        put("DSTV", DSTVActivity.class);
//    }};

    static public   List<String> defeaultoperationLinks = new ArrayList<>(Arrays.asList("Airtime Top Up", "Mobile Money","QR Instant Pay"));
}
