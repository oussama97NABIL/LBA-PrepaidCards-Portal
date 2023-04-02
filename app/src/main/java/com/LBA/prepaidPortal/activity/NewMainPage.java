package com.LBA.prepaidPortal.activity;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
//import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.LBA.prepaidPortal.R;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.General;
import com.LBA.tools.services.User;
import com.google.android.material.snackbar.Snackbar;
//import com.google.android.play.core.appupdate.AppUpdateManager;
//import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
//import com.google.android.play.core.install.InstallStateUpdatedListener;
//import com.google.android.play.core.install.model.AppUpdateType;
//import com.google.android.play.core.install.model.InstallStatus;
//import com.google.android.play.core.install.model.UpdateAvailability;

import java.io.File;
import java.lang.reflect.Field;
import java.security.KeyStore;
import java.util.List;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;

//import ma.bits.tools.assets.Globals;
//import ma.bits.tools.services.General;
//import ma.bits.tools.services.User;

public class NewMainPage extends AbstractActivity {

    static private final String TAG = NewMainPage.class.getSimpleName();
    static public Context context;
    Button btnSignIn;
    Button btnCancel;
    TextView txtUser;
    EditText txtPassword;
    TextView txtWelcome;
    Button ButtonCall;
    TextView textView;
    TextView textView2;
    TextView txtViewTerms;
    String userCode;
    String password;
    //  TextView resetPasswordTv;
    LinearLayout linearLayoutCall;
    LinearLayout linearLayoutLogin;
    TextView textViewSelfSignUpIB;
    Button locate;
    Button invite;
    Button help;
    Dialog myDialog;
    Button resetApp;
    // younes device remebre
    String UnameValue;
    String DefaultUnameValue = "";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    TextView forg_pass;

    String DefaultUnameValue1 = "";
    // hajer bio
    String  biomFlay ="null";
    ImageButton btnBiometric;
    FingerprintManager fingerprintManagerCompat;
    private Cipher cipher;
    private KeyStore keyStore;
    private static final String KEY_NAME = "androidHive";
    private Executor executor;
    private BiometricPrompt biometricPrompt;
   // 3/28/2023 private BiometricPrompt.PromptInfo promptInfo;
    // hajer bio end
    //fatim 15/08/2022
   // 3/28/2023 private AppUpdateManager appUpdateManager;
    private int MY_REQUEST_CODE = 9;
    private int inAppUpdateType;
    //end fatim
    //Manage responsivity
    TextView terms;
//    private void adjustDisplayLayoutResponsivity() {
//        linearLayoutLogin = (LinearLayout) findViewById(R.id.linear_layout_login);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
//        int width = displayMetrics.widthPixels;
//        if (height <= 1280) {
//            LinearLayout.LayoutParams paramsLogin = (LinearLayout.LayoutParams) linearLayoutLogin.getLayoutParams();
//            paramsLogin.setMargins(0, 350, 0, 0);
//
//            linearLayoutLogin.setLayoutParams(paramsLogin);
//
//        }
//    }

    private Boolean exit = false;
    @Override
    public void onBackPressed () {
        //startSplashScreenActivity();
            /*new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    NewMainPage.this.finish();
                }
            }, 2000);*/
        //finish();
        if (exit) {
            finishAffinity(); // finish activity
            System.exit(0);


        } else {
            // Toast.makeText(this, "Press Back again to Exit.",Toast.LENGTH_SHORT).show();
            AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
            alertDialog.setMessage("Press Back again to Exit.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        NewMainPage.context = getApplicationContext();


        setContentView(R.layout.new_main_page_activity);

        try {
            Globals.appVersionName = getBaseContext().getPackageManager().getPackageInfo(getBaseContext().getPackageName(), 0).versionName;
            Globals.appVersionCode = getBaseContext().getPackageManager().getPackageInfo(getBaseContext().getPackageName(), 0).versionCode;
        } catch (Exception e) {
            Globals.appVersionName = "";
            Globals.appVersionCode = 0;
        }


        /** younes pentesting
         *  new Thread(new Runnable() {
        @Override public void run() { // background code }

        RootBeer rootBeer = new RootBeer(context);
        if (rootBeer.isRooted()) {
        //we found indication of root
        //                    Toast.makeText(NewMainPage.this,"this device is rooted", Toast.LENGTH_LONG).show();
        Log.d("root","this device is rooted");
        finish(); // finish activity


        } else {
        //we didn't find indication of root
        //                  Toast.makeText(NewMainPage.this,"this device is not rooted", Toast.LENGTH_LONG).show();
        Log.d("root","this device is not rooted");
        }


        }}).start();
         **/


        terms = (TextView) findViewById(R.id.terms);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        myDialog = new Dialog(this);

        txtWelcome = (TextView) findViewById(R.id.welcome);
        //3/28/2023 txtUser = (TextView) findViewById(R.id.txtUser);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        forg_pass= (TextView) findViewById(R.id.forg_pass);
        btnBiometric = (ImageButton) findViewById(R.id.btnBiometric); //  hajer

        // resetPasswordTv= (TextView)findViewById(R.id.reset_password_tv);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        btnSignIn.setTypeface(tf);
        //txtUser.setTypeface(tf);
        txtPassword.setTypeface(tf);
        //selfSignUp = (TextView) findViewById(R.id.signup);
        //selfSignUp.setTypeface(tf);
        locate = (Button) findViewById(R.id.imageButton2);
        invite = (Button) findViewById(R.id.imageButton3);
        resetApp = (Button) findViewById(R.id.resetApp);
        help = (Button) findViewById(R.id.imageButton4);

        SharedPreferences settings = getSharedPreferences("UNSV",
                Context.MODE_PRIVATE);
        SharedPreferences settings2 = getSharedPreferences("UNFN",
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString("username", DefaultUnameValue);
        Globals.firstName = settings2.getString("firstName", DefaultUnameValue);
        //txtUser.setText("HELLO, "+Globals.firstName);
        //txtUser.setTextSize(22);
        //txtUser.setEnabled(false);
        System.out.println("onResume load name: " + UnameValue);
        Globals.username = UnameValue;

        Spannable word = new SpannableString("Hello, ");

        //3/28/2023 word.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.cbg_red)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtWelcome.setText(word);
        Spannable wordTwo = new SpannableString(Globals.firstName);
        wordTwo.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtWelcome.append(wordTwo);

        //txtWelcome.setText("Hello, ");
        //txtWelcome.setTextColor(getResources().getColor(R.color.cbg_red));
        //txtWelcome.append(Globals.firstName);
        //txtWelcome.setTextColor(getResources().getColor(R.color.white));

        LinearLayout yourBackgroundView = (LinearLayout) findViewById(R.id.root);

        SharedPreferences setting = getSharedPreferences("appBack",
                Context.MODE_PRIVATE);

        String imageS  = setting.getString("background", DefaultUnameValue1);
        Log.d("Retr image from device", imageS);
        Bitmap imageB;
        if(!imageS.equals("")) {
            imageB = decodeToBase64(imageS);
            Drawable d = new BitmapDrawable(getResources(),imageB);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                yourBackgroundView.setBackgroundDrawable(d);
            } else {
                yourBackgroundView.setBackground(d);
            }
        }
        else{
            //keep it black
        }

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTerms();
            }});

        forg_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Globals.username.isEmpty()) {
                    //   Toast.makeText(NewMainPage.this, "User is Mandatory", Toast.LENGTH_SHORT).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
                    alertDialog.setMessage( "User is Mandatory");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                    return;
                }
// 3/28/2023               UserModel userModel=new UserModel();
//  3/28/2023              userModel.setUserCode(Globals.username);
// 3/28/2023               Globals.userModelSession=userModel;
                //Toast.makeText(MainActivity.this, "UserSession code  : "+Globals.userModelSession.getUserCode(), Toast.LENGTH_LONG).show();
//3/28/2023                Intent intentResetPassword=new Intent(NewMainPage.this,FrgPassInOut.class);
                Globals.activity = NewMainPage.class;
//3/28/2032                startActivity(intentResetPassword);
            }
        });

// 3/28/2023       locate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OpenBranchLocator();
//            }
//        });
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenInviteFriends();
            }
        });
        // context = getApplicationContext();
        resetApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleteCache(context);

                //startActivity(new Intent(NewMainPage.this, SplashScreen.class));
                doResetAppConfirmation(v);
            }
        });
        //hajer 25/03/2021
        SharedPreferences biometric = getSharedPreferences("fptag",
                Context.MODE_PRIVATE);
        // Get value
        biomFlay = biometric.getString("flagFP", DefaultUnameValue);
        if(biomFlay.equals("Y")){

            btnBiometric.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams  parameter =  (RelativeLayout.LayoutParams) btnSignIn.getLayoutParams();
            parameter.setMargins(parameter.leftMargin, parameter.topMargin, 125, parameter.bottomMargin); // left, top, right, bottom
            btnSignIn.setLayoutParams(parameter);

        }
        btnBiometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**hajer 31/03/2021 fingerprintManagerCompat = (FingerprintManager) NewMainPage.context.getSystemService(Context.FINGERPRINT_SERVICE);
                 KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                 // Checks whether lock screen security is enabled or not
                 if (!keyguardManager.isKeyguardSecure()) {
                 // textView.setText("Lock screen security not enabled in Settings");
                 } else {
                 generateKey();
                 if (cipherInit()) {
                 FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                 FingerprintHandlerLogin helper = new FingerprintHandlerLogin(ma.bits.cbgFaceLift_eps_mobilebankingapp.NewMainPage.this);
                 helper.startAuth(fingerprintManagerCompat, cryptoObject);
                 }
                 }
                 **/
                executor = ContextCompat.getMainExecutor(NewMainPage.this);
//                biometricPrompt = new BiometricPrompt(NewMainPage.this,
//                        executor, new BiometricPrompt.AuthenticationCallback() {
//                    @Override
//                    public void onAuthenticationError(int errorCode,
//                                                      @NonNull CharSequence errString) {
//                        super.onAuthenticationError(errorCode, errString);
//                     /*   Toast.makeText(getApplicationContext(),
//                                "Authentication error: " + errString, Toast.LENGTH_SHORT)
//                                .show();
//                    */
//                        AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
//                        alertDialog.setMessage("Authentication error: " + errString);
//                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//
//                                    }
//                                });
//                        alertDialog.show();
//                    }
//
//                    @Override
//                    public void onAuthenticationSucceeded(
//                            @NonNull BiometricPrompt.AuthenticationResult result) {
//                        super.onAuthenticationSucceeded(result);
//                        initProgrees();
//                        CustomTaskBiometric task = new CustomTaskBiometric();
//                        task.execute();
//                        //Toast.makeText(getApplicationContext(),
//                        //       "Authentication succeeded!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onAuthenticationFailed() {
//                        super.onAuthenticationFailed();
//                     /*   Toast.makeText(getApplicationContext(), "Authentication failed",
//                                Toast.LENGTH_SHORT)
//                                .show();
//                    */
//                        AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
//                        alertDialog.setMessage( "Authentication failed");
//                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//
//                                    }
//                                });
//                        alertDialog.show();
//                    }
//                });
//                promptInfo = new BiometricPrompt.PromptInfo.Builder()
//                        .setTitle("Biometrics")
//                        .setSubtitle("Log in using your Biometric Credential")
//                        .setNegativeButtonText("Cancel")
//                        .build();
//                biometricPrompt.authenticate(promptInfo);

/*
                try {
                    Cipher cipher = getCipher();
                    SecretKey secretKey = getSecretKey();
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    biometricPrompt.authenticate(promptInfo,
                            new BiometricPrompt.CryptoObject(cipher));

                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnrecoverableKeyException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }

 */
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Globals.username.isEmpty()) {
                    //   Toast.makeText(NewMainPage.this, "User is Mandatory", Toast.LENGTH_SHORT).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
                    alertDialog.setMessage("User is Mandatory");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                    return;
                }
                if (txtPassword.getText().toString().isEmpty()) {
                    //  Toast.makeText(NewMainPage.this, "User is Mandatory", Toast.LENGTH_SHORT).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
                    alertDialog.setMessage("User is Mandatory");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                    return;
                }
                try {
                    initProgrees();
                    //userCode = txtUser.getText().toString();
                    userCode = Globals.username;
                    password = txtPassword.getText().toString();

                    CustomTask task = new CustomTask();
                    task.execute();
                } catch (Exception e) {
                    Log.d(TAG, "btnSignIn.setOnClickListener()", e);
                    //   Toast.makeText(NewMainPage.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
                    alertDialog.setMessage("Login Failed");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                }

            }
        });
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(NewMainPage.this,"THANK YOU", Toast.LENGTH_LONG).show();


        }
        else {
            Toast.makeText(NewMainPage.this,"THE APP NEEDS PERMISSION TO FUNCTION CORRECTLY", Toast.LENGTH_LONG).show();
            /*   AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
            alertDialog.setMessage("THE APP NEEDS PERMISSION TO FUNCTION CORRECTLY");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();
*/
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }

        //fatim 15/08/2022
        // in app updates
      //3/28/2023  appUpdateManager= AppUpdateManagerFactory.create(this);
        //  listen to Immediate updates

      //3/28/2023  appUpdateManager.registerListener(installStateUpdatedListener);

// 3/28/2023       appUpdateManager.getAppUpdateInfo().addOnSuccessListener(result -> {
//            Log.d("fatim", "Check update available ");
//            if(result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
//                Log.d("fatim", "Update Available");
//                if(result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){// omar15122021
//                    Log.d("fatim", "Immediate update allowed");
//                    try {
//                        appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, NewMainPage.this
//                                ,MY_REQUEST_CODE);
//                            /*if(BuildConfig.DEBUG){
//                                FakeAppUpdateManager fakeAppUpdateManager = (FakeAppUpdateManager) appUpdateManager;
//                                if(fakeAppUpdateManager.isImmediateFlowVisible()){
//                                    Log.d("Omar", "Immediate flow visible");
//                                    fakeAppUpdateManager.userAcceptsUpdate();
//                                    Log.d("Omar", "User accepts update");
//                                    fakeAppUpdateManager.downloadStarts();
//                                    Log.d("Omar", "Download starts");
//                                    fakeAppUpdateManager.downloadCompletes();
//                                    Log.d("Omar", "Download completes");
//                                    fakeAppUpdateManager.completeUpdate();
//                                    Log.d("Omar", "Complete update");
//                                }
//                            }*/
//                    } catch (IntentSender.SendIntentException e) {
//                        Log.e("fatim", "err start update", e);
//                    }
//                } else {
//                    Log.d("fatim", "Immediate update not allowed");
//                }
//            }else {
//                Log.d("fatim", "Update unavailable");
//            }
//        });
        //end fatim

    }


    //fatim 15/08/2022
//    private InstallStateUpdatedListener installStateUpdatedListener = state -> {
//        if(state.installStatus() == InstallStatus.DOWNLOADED)
//        {
//            showCompletedUpdate();
//        }
//    };

// 3/28/2023   private void showCompletedUpdate() {
//        Log.i("tag", "Showing SnackBar");
//        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"New app is ready!",
//                Snackbar.LENGTH_INDEFINITE);
//        snackbar.setAction("Install", new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                appUpdateManager.completeUpdate();
//            }
//        });
//        snackbar.show();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        // we can check without requestCode == RC_APP_UPDATE because
        // we known exactly there is only requestCode from  startUpdateFlowForResult()
        if(requestCode == MY_REQUEST_CODE && resultCode != RESULT_OK)
        {
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //end fatim


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(NewMainPage.this, "THANK YOU", Toast.LENGTH_LONG).show();
           /* AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
            alertDialog.setMessage("THANK YOU");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();*/
        } else {
            Toast.makeText(NewMainPage.this, "THE APP NEEDS PERMISSION TO FUNCTION CORRECTLY", Toast.LENGTH_LONG).show();
          /*  AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
            alertDialog.setMessage("THE APP NEEDS PERMISSION TO FUNCTION CORRECTLY");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();*/
        }
    }



    private void changeAppFont(){

        setDefaultFont(NewMainPage.this, "DEFAULT", "fonts/gtw.ttf");
        setDefaultFont(NewMainPage.this, "DEFAULT_BOLD", "fonts/Oswald-Stencbab.ttf");

        setDefaultFont(NewMainPage.this, "MONOSPACE", "fonts/Robot-Bold.ttf");
        setDefaultFont(NewMainPage.this, "SANS_SERIF", "fonts/Roboto-ThinItalic.ttf");
        setDefaultFont(NewMainPage.this, "SERIF", "fonts/RobotoCondensed-Regular.ttf");
    }

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(NewMainPage.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+233-302-213565"));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+233-302-213565"));
                startActivity(callIntent);            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }



    }
    // 29/06/2021
    public void OpenTerms(){
        try{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cbg.com.gh/terms---conditions.html"));
            startActivity(browserIntent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

// 3/28/2023   public void OpenBranchLocator (){
//        try{
//            General.GetBranchesLocations();
//            Intent intent = new Intent(this, MapsActivity.class);
//            startActivity(intent);
//        }catch(Exception e){
//            //  Toast.makeText(NewMainPage.this,"Error Fetching Branches Location", Toast.LENGTH_LONG).show();
//            AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
//            alertDialog.setMessage("Error Fetching Branches Location");
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//
//                        }
//                    });
//            alertDialog.show();
//        }
//
//    }

    public void OpenInviteFriends () { // younes old link "https://play.google.com/store/apps/details?id=ma.bits.cbg_eps_mobilebankingapp";

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "Hey there, please click on the link to download the CBG Mobile Banking App!  " +
                        "http://onelink.to/n7wrky";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Choose a sharing application"));

            }
        });

    }

//3/28/2023    public void ShowPopup2(View v){
//        TextView txtclose;
//        myDialog.setContentView(R.layout.popup2);
//        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
//        txtclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myDialog.dismiss();
//            }
//        });
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//
//    }

// 3/28/2023   public void ShowPopup(View v) {
//        TextView txtclose;
//        myDialog.setContentView(R.layout.popup);
//        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
//        txtclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myDialog.dismiss();
//            }
//        });
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//    }
    /// ++  USED
// 3/28/2023   public void Appel1(View v){
//        TextView appel1;
//        myDialog.setContentView(R.layout.popup2);
//        appel1 = (TextView) myDialog.findViewById(R.id.appel1);
//        appel1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String phone = "0302 21 6000";
//                Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
//                // Intent i = new Intent(Intent.ACTION_CALL,Uri.parse(String.valueOf(R.id.appel1)));
//                startActivity(i);
//            }
//        });
//
//    }
    //+++
    /**   public void Appel2(View v){
     TextView appel2;
     myDialog.setContentView(R.layout.popup);
     appel2 = (TextView) myDialog.findViewById(R.id.appel2);
     appel2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    String phone = "0264270236";
    Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
    // Intent i = new Intent(Intent.ACTION_CALL,Uri.parse(String.valueOf(R.id.appel1)));
    startActivity(i);
    }
    });

     }

     public void Appel5(View v){
     TextView appel5;
     myDialog.setContentView(R.layout.popup);
     appel5 = (TextView) myDialog.findViewById(R.id.appel5);
     appel5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    String phone = "0302-681531";
    Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
    // Intent i = new Intent(Intent.ACTION_CALL,Uri.parse(String.valueOf(R.id.appel1)));
    startActivity(i);
    }
    });

     }**/
    //+++ USED
// 3/28/2023   public void Appel6(View v){
//        TextView appel6;
//        myDialog.setContentView(R.layout.popup2);
//        appel6 = (TextView) myDialog.findViewById(R.id.appel6);
//        appel6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String phone = "0302-681533";
//                Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
//                // Intent i = new Intent(Intent.ACTION_CALL,Uri.parse(String.valueOf(R.id.appel1)));
//                startActivity(i);
//            }
//        });
//
//    }

    //+++
    /** public void Appel7(View v){
     TextView appel7;
     myDialog.setContentView(R.layout.popup);
     appel7 = (TextView) myDialog.findViewById(R.id.appel7);
     appel7.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    String phone = "0800422422";
    Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
    // Intent i = new Intent(Intent.ACTION_CALL,Uri.parse(String.valueOf(R.id.appel1)));
    startActivity(i);
    }
    });

     }**/
    //+++
// 3/28/2023   public void Appel4(View v){
//        TextView appel4;
//        myDialog.setContentView(R.layout.popup2);
//        appel4 = (TextView) myDialog.findViewById(R.id.appel4);
//        appel4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //   String phone = "customerservice@gcb.com.gh";
//
//               /* initProgrees();
//
//                Intent intent = null;
//                intent = new Intent(MainActivity.this, MailActivity.class);
//                Custom task = new Custom();
//                task.intent = intent;
//                task.execute();*/
//                Intent intent = null;
//                intent = new Intent(Intent.ACTION_SEND);
//                String[] to ={"talktous@cbg.com.gh"};
//                //intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
//                intent.putExtra(Intent.EXTRA_EMAIL, to);
//                intent.putExtra(Intent.EXTRA_SUBJECT,"");
//                intent.putExtra(Intent.EXTRA_TEXT,"");
//                intent.setType("message/rfc822");
//                final PackageManager pm = getPackageManager();
//                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
//                ResolveInfo best = null;
//                for(final ResolveInfo info : matches)
//                    if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
//                        best = info;
//                if (best != null)
//                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
//                startActivity(intent);
//
//
//            }
//        });
//
//    }


    // CUSTOM TASK

    private class CustomTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... param) {
            try {

                AccountManager accountManager = AccountManager.get(NewMainPage.this);
                Account[] accounts = accountManager.getAccountsByType(Globals.androidAccMngType);
                Account myAccount = null;
                Globals.authenCode =null; //ITI_MAW20190725: Auth Code should be reset before fetching the app account from phone
                Log.d(TAG, "CustomTask()" + "> FETCH ACCOUNT");
                for (Account account : accounts) {
                    //      Log.d(TAG, "CustomTask()" + "> account["+account.toString()+"]");
                    if (account.name.equals(userCode)) {
                        myAccount = account;

                        AccountManagerFuture<Bundle> accFut = AccountManager.get(context).getAuthToken(myAccount, Globals.androidAccAuthenType, null, NewMainPage.this, null, null);
                        Bundle authTokenBundle = accFut.getResult();
                        // Globals.authenCode = authTokenBundle.get(AccountManager.KEY_AUTHTOKEN).toString();

                        // Globals.authenCode = accountManager.peekAuthToken(myAccount, Globals.androidAccAuthenType);
                        Globals.authenCode = accountManager.peekAuthToken(myAccount, Globals.androidAccAuthenTokenType);
                        // Globals.authenCode =accountManager.getPassword(myAccount);
                        //      Log.d(TAG, "1. CustomTask()" + "> ACCOUNT FOUND authToken["+Globals.authenCode+"]");
                        // Log.d(TAG, "CustomTask()" + "> ACCOUNT FOUND pass["+accountManager.getPassword(myAccount)+"]");


                        //test

//                        Globals.name = txtUser.getText().toString();
                        Globals.name = Globals.username;

                        //         Log.d(TAG, "User Name" + "> User Name["+Globals.name+"]");

                        break;
                    }

                }



                User.login(userCode, password, Globals.authenCode,1);
                //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Globals.user = userCode;
                Globals.password = password;
                //Log.d(TAG, "1. CustomTask()" + "> ACCOUNT FOUND authToken["+Globals.authenCode+"]");



                if(myAccount!=null) {
                    //Intent myWelcomeAct = new Intent(MainActivity.this, HomeActivity.class);
                    Intent myWelcomeAct = new Intent(NewMainPage.this, Z_WelcomeActivity.class);
                    startActivity(myWelcomeAct);
                    //Intent myHomeAct = new Intent(MainActivity.this, AuthenActivity.class); startActivity(myHomeAct);

                    return null;
                }



                Intent myAuthenAct = new Intent(NewMainPage.this, Z_WelcomeActivity.class);
                startActivity(myAuthenAct);
                return null;

            } catch (Exception e) {
                if(e.getMessage()!=null && e.getMessage().contains("802")){
                    Globals.user = userCode;
                    Globals.password = password;
                    if(Globals.user.equals("REHA1738917")){
                        Intent myAuthenAct = new Intent(NewMainPage.this, Z_WelcomeActivity.class);
                        startActivity(myAuthenAct);
                    }
// 3/28/2023                   else {
//                        Intent myAuthenAct = new Intent(NewMainPage.this, AuthenActivity.class);
//                        myAuthenAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(myAuthenAct);
//                    }return null;
                }
                else if(e.getMessage()!=null && e.getMessage().contains("803")){ // Force Password change
                    Globals.user = userCode;
                    Globals.password = password;
                    Globals.authenCode = "";
// 3/28/2023                    Intent myPasswordChange = new Intent(NewMainPage.this, PasswordChangeActivity.class);
// 3/28/2023                    startActivity(myPasswordChange);
                    return null;
                }
                e.printStackTrace();
                return Globals.ERmsg.toUpperCase();
                //return "LOGIN FAILED";
            }
        }
        protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);
            if (param != null){
                Log.e(TAG, "onPostExecute: "+ param );
                if (param.contains("KINDLY UPDATE,THIS APP VERSION IS OUT OF DATE")){
                    AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
                    alertDialog.setMessage(param);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=ma.bits.cbgFL_eps_mobilebankingapp"); // missing 'http://' will cause crashed
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                    //  Toast.makeText(NewMainPage.this, param, Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
                    alertDialog.setMessage(param);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                }
                //  Toast.makeText(NewMainPage.this, param, Toast.LENGTH_SHORT).show();
            }
            SharedPreferences settings4 = context.getSharedPreferences("fpauth", 0); // to call in load using this key
            SharedPreferences.Editor editor4 = settings4.edit();
            try {
                editor4.putString("flagAuth", Globals.CC);
                editor4.apply();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*@Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        clearApplicationData();
    }

     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    //hajer

    private class CustomTaskBiometric extends AsyncTask<String, String, String>{

        protected String doInBackground(String... param) {
            SharedPreferences settings = context.getSharedPreferences("UNSV",
                    Context.MODE_PRIVATE);
            SharedPreferences settings1 = context.getSharedPreferences("fpauth",
                    Context.MODE_PRIVATE);
            String DefaultUnameValue ="";
            String DefaultAuthValue ="";

            // Get value
            userCode = settings.getString("username", DefaultUnameValue);
            password = settings1.getString("flagAuth", DefaultAuthValue);
            Log.d("before assign", "password is "+ password);
            if(password.isEmpty()){
                password = Globals.CC;
            }
            Log.d("after assign", "password is "+ password);

            //    Log.d("HAJER TEST","HAJER TESE "+password);
            try {




                AccountManager accountManager = AccountManager.get(NewMainPage.this);
                Account[] accounts = accountManager.getAccountsByType(Globals.androidAccMngType);
                Account myAccount = null;
                Globals.authenCode =null; //ITI_MAW20190725: Auth Code should be reset before fetching the app account from phone
                for (Account account : accounts) {
                    if (account.name.equals(userCode)) {
                        myAccount = account;

                        AccountManagerFuture<Bundle> accFut = AccountManager.get(context).getAuthToken(myAccount, Globals.androidAccAuthenType, null, NewMainPage.this, null, null);
                        Bundle authTokenBundle = accFut.getResult();

                        Globals.authenCode = accountManager.peekAuthToken(myAccount, Globals.androidAccAuthenTokenType);

                        Globals.name = Globals.username;


                        break;
                    }

                }



                User.login(userCode, password, Globals.authenCode,3);
                // Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Globals.user = userCode;
                Globals.password = password;



                if(myAccount!=null) {
                    Intent myWelcomeAct = new Intent(NewMainPage.this, Z_WelcomeActivity.class);
                    startActivity(myWelcomeAct);
                    return null;
                }

                Intent myAuthenAct = new Intent(context, Z_WelcomeActivity.class);
                context.startActivity(myAuthenAct);
                return null;

            } catch (Exception e) {
                if(e.getMessage()!=null && e.getMessage().contains("802")){
                    Globals.user = userCode;
                    Globals.password = password;
                    if(Globals.user.equals("REHA1738917")){
                        Intent myAuthenAct = new Intent(NewMainPage.this, Z_WelcomeActivity.class);
                        startActivity(myAuthenAct);
                    }
// 3/28/2023                   else {
//                        Intent myAuthenAct = new Intent(context, AuthenActivity.class);
//                        myAuthenAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(myAuthenAct);
//                    }
                    return null;
                }
                else if(e.getMessage()!=null && e.getMessage().contains("803")){ // Force Password change
                    Globals.user = userCode;
                    Globals.password = password;
                    Globals.authenCode = "";
// 3/28/2023                   Intent myPasswordChange = new Intent(context, PasswordChangeActivity.class);
//                    context.startActivity(myPasswordChange);
                    return null;
                }
                e.printStackTrace();
                return Globals.ERmsg.toUpperCase();
                //return "LOGIN FAILED";
            }
        }
        protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);
            if(param!=null){
                if (param.contains("KINDLY UPDATE,THIS APP VERSION IS OUT OF DATE")){
                    AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
                    alertDialog.setMessage(param);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=ma.bits.cbgFL_eps_mobilebankingapp"); // missing 'http://' will cause crashed
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                    //  Toast.makeText(NewMainPage.this, param, Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    //      Toast.makeText(context, param, Toast.LENGTH_SHORT).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(NewMainPage.this).create();
                    alertDialog.setMessage(param);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    alertDialog.show();
                }
            }}
    }

    public void doResetAppConfirmation(View v){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NewMainPage.this);
        builder.setMessage("All Local Data will be deleted, and application will be required to reconfigure. Continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context = getApplicationContext();
                        //deleteCache(context);
                        deleteCache(context);
      //3/28/2023                  startActivity(new Intent(NewMainPage.this, SplashScreen.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir1(new File(appDir, s));
                    System.out.println("**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                    // Log.i("EEEEEERRRRRRROOOOOOORRRR", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir1(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir1(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }


    public void deleteCache(Context context) {
        try {
            System.out.println("inside delete cash");
            System.out.println("app context is " + context);


            File dir = context.getCacheDir();
            deleteDir(dir);
            System.out.println("done delete cash");
         /*SharedPreferences settings = getSharedPreferences("UNSV",
                    Context.MODE_PRIVATE);
                    */
            System.out.println("hajer before shared pref");

            SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
            System.out.println("hajer before shared pref"+preferences.getString("FirstTimeInstall",""));

            //preferences.edit().remove("FirstTimeInstall").commit();
            preferences.edit().clear().commit();

            SharedPreferences settings1 = getSharedPreferences("PREFS", 0);
            settings1.edit().clear().commit();


            SharedPreferences settings = getSharedPreferences("UNSV",
                    Context.MODE_PRIVATE);

            settings.edit().clear().commit();
            System.out.println("hajer after shared pref");
            System.out.println("hajer before shared pref terms"+settings1.getString("terms",""));

        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
