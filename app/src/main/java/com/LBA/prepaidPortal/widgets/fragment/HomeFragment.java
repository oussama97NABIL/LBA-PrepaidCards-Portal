package com.LBA.prepaidPortal.widgets.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.LBA.MainActivity;
import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.SharedPrefManager;
import com.LBA.prepaidPortal.activity.Z_WelcomeActivity;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.Card;
import com.LBA.tools.services.Notifications;
import com.LBA.tools.services.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeFragment extends BaseFragment implements View.OnClickListener {
    NavigationView navigationView;
    SharedPrefManager sharedPrefManager;

    CardView AccBtn;
    String userCode;
    String password;
    //ImageButton BillBtn;
    CardView MMBtn;
    //CardView ReqBtn;
    CardView expenseManager;
    CardView TrBtn;
    private CardView CardToCard, Balance,Transaction,AccountToCard,BloqueCard,LimitUpdate;

    //ImageButton StnBtn;
    CardView QRBtn;
    CardView BenefBtn;
    CardView AirBtn;
    CardView PaymentBtn;
    ImageButton Setting;
    TextView notificationText;
    ImageView imageshowSolde;
    Button updateGhCard;
    Uri uri;
    CardView payproxy;
    ImageButton notifications_btn , history_btn;
    Button balances;
    TextView showUser; // younes
    TextView textView13;
    TextView textView_heading;
    TextView textView;
    TextView textView2;
    TextView textView4;
    TextView textView5;
    TextView textView6 ,welcomText;
    TextView showSolde;


    String encodedImage = null;
    boolean hidePager = true;
    String notificationId = "";

    // othman
    public static final String CHANNEL_ID1 = "#123";
    public static final String CHANNEL_NAME = "my notification";
    public static final String CHANNEL_DESCRIPTION = "Test";

    /*  DrawerLayout drawerLayout;
      NavigationView navigationView;
      Toolbar toolbar;
      Menu menu;
  */
    //3/28/2023 private IntentIntegrator qrScan;
    String operation = "";
    private final String CHANNEL_ID = "personal_notifications";
    private ImageView Adview;
    private ViewPager2 viewPager2, viewPager2_up;
    private Handler sliderHandler = new Handler();

    // AdView adView = new AdView(this);
    String DefaultUnameValue = "";
    private View mRootView;

    RoundedImageView userImage ;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Log.e("onCreate: ", "  Globals.userType : "+  Globals.userType);
        getActivity().setTitle("Mon Compte\n");
        super.onCreate(savedInstanceState);
        getClientName();
        mRootView = inflater.inflate(R.layout.z_menu_test, container, false);
        sharedPrefManager = new SharedPrefManager(getActivity().getApplicationContext());
        CardToCard = (CardView) mRootView.findViewById(R.id.accountServices);
        Balance = (CardView) mRootView.findViewById(R.id.airtimeAndData);
        Transaction = (CardView) mRootView.findViewById(R.id.transfer);
        AccountToCard = (CardView) mRootView.findViewById(R.id.MMBtn);
        BloqueCard = (CardView) mRootView.findViewById(R.id.PaymentBtn);
        LimitUpdate = (CardView) mRootView.findViewById(R.id.ghqr);
        notificationText = mRootView.findViewById(R.id.notificationText);
        showSolde = mRootView.findViewById(R.id.showSolde);
        imageshowSolde = mRootView.findViewById(R.id.imageView_show_hide);



        //AccBtn = (CardView) mRootView.findViewById(R.id.accountServices);
        TrBtn = (CardView) mRootView.findViewById(R.id.transfer);
        QRBtn = (CardView) mRootView.findViewById(R.id.ghqr);
        MMBtn = (CardView) mRootView.findViewById(R.id.MMBtn);
        AirBtn = (CardView) mRootView.findViewById(R.id.airtimeAndData);
        PaymentBtn = (CardView) mRootView.findViewById(R.id.PaymentBtn);
        Setting = (ImageButton) mRootView.findViewById(R.id.Setting);
        notifications_btn = (ImageButton) mRootView.findViewById(R.id.notifications_btn);
        history_btn = (ImageButton) mRootView.findViewById(R.id.history_btn);
       // balances = (Button) mRootView.findViewById(R.id.btnBalances);
        welcomText = (TextView) mRootView.findViewById(R.id.userWelcome);
        updateGhCard = (Button) mRootView.findViewById(R.id.updateGhCard);
        userImage = (RoundedImageView) mRootView.findViewById(R.id.userImage);

        //---------------      --------------------------
        Spannable word = new SpannableString("Bonjour User");
        word.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.MurasakiPurple)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        welcomText.setText(word);
        Spannable wordTwo = new SpannableString(Globals.firstName);

        wordTwo.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        welcomText.append(wordTwo);
        //------------ pubs ViewPager2 ------------------------

        viewPager2 = mRootView.findViewById(R.id.viewPagerImageSlider);



        viewPager2.setCurrentItem(1);

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer(
                new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        float r = 1 - Math.abs(position);
                        page.setScaleY(0.85f+r * 0.15f);

                    }
                }
        );
        viewPager2.setPageTransformer(compositePageTransformer);



        CompositePageTransformer compositePageTransformer2 = new CompositePageTransformer();
        compositePageTransformer2.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer2.addTransformer(
                new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        float r = 1 - Math.abs(position);
                        page.setScaleY(0.85f+r * 0.15f);

                    }
                }
        );

        ;

        if( Globals.profileImage != null){
            userImage.setImageBitmap(Globals.profileImage);
        }
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if(checkAndRequestPermissions(getActivity())){
                    // Toast.makeText(Z_WelcomeActivity.this, "Permissions allowed", Toast.LENGTH_LONG).show();
                    chooseImage(getActivity());
                }else{
                    //  Toast.makeText(Z_WelcomeActivity.this, "Permissions denied ", Toast.LENGTH_LONG).show();

                }*/
               /* initProgrees();
                CustomTask task = new CustomTask(SettingsActivity.class);
                task.execute();*/
            }
        });

        imageshowSolde.setImageResource(R.drawable.baseline_visibility_off_24);
        imageshowSolde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hidePager){
                    showSolde.setText("****");
                    hidePager = false;
                    imageshowSolde.setImageResource(R.drawable.baseline_visibility_off_24);
                }else{
                    try {
                        initProgrees();
                        new CustomTaskSolde().execute();
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    imageshowSolde.setImageResource(R.drawable.baseline_visibility_24);
                    hidePager = true;
                }
            }
        });
        CardToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentToLoad = new CardToCard();
                FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragmentToLoad);
                fragmentTransaction.commit();
            }
        });
        Balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentToLoad = new GetBalance();
                FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragmentToLoad);
                fragmentTransaction.commit();
            }
        });
        Transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentToLoad = new TransactionListActivity();
                FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragmentToLoad);
                fragmentTransaction.commit();
            }
        });
        AccountToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentToLoad = new AccountToCard();
                FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragmentToLoad);
                fragmentTransaction.commit();
            }
        });
        BloqueCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentToLoad = new BlockCard();
                FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragmentToLoad);
                fragmentTransaction.commit();
            }
        });
        LimitUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentToLoad = new UpdatesLimit();
                FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragmentToLoad);
                fragmentTransaction.commit();
            }
        });




        Log.d("WelcomeActivity", "user["+Globals.user+"]");

        /**     QRBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        // initProgrees();
        // CustomTask task = new CustomTask(ScanQrActivity.class);
        // task.execute();
        qrScan.initiateScan(IntentIntegrator.QR_CODE_TYPES);

        }
        });
         **/


       /* oussama balances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //Account.GetTransactionList(selectedAccount, fromDateEtxt.getText().toString().trim(), toDateEtxt.getText().toString().trim());
                    initProgrees();
                    new CustomTask().execute();

                } catch (Exception e) {
                    //Log.d(TAG, "btnLoad.setOnClickListener()", e);
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });*/
        //hajer 28/06/2022 start
       /* updateGhCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.creditagricole.ma/fr"));
                startActivity(browserIntent);
            }
        });*/
        //hajer 28/06/2022 end
// 3/28/2023       payproxy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task = new CustomTask(ProxyPayList.class);
//                task.execute();
//            }
//        });

        Log.d("WelcomeActivity", "user["+Globals.user+"]");
        Log.d("WelcomeActivity", "id["+Globals.ClientId+"]");
        Log.d("WelcomeActivity", "id["+Globals.statusBiom+"]");


        /** if(!Globals.Ads.isEmpty()) {
         AdsEntry ad = Globals.Ads.get(0);
         Bitmap bitmap = BitmapFactory.decodeByteArray(ad.getImage(), 0, ad.getImage().length);

         Adview = (ImageView) findViewById(R.id.ad);
         Adview.setImageBitmap(bitmap);
         handler.post(changeAd);
         }**/
        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });*/

      /*  adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
*/

        /**if (Globals.branchOn == null) {

         Globals.branchOn = new ArrayList<String>();
         Globals.branchOn.add(Globals.Select);

         try {
         initProgrees();

         General.GetListBranches();
         //new BranchTask().execute();
         // BranchTask task = new BranchTask();
         //task.execute();
         dismissProgress();

         } catch (Exception e) {
         dismissProgress();
         Globals.branches.clear();
         Toast.makeText(Z_WelcomeActivity.this, "ERROR FETCHING BRANCHES", Toast.LENGTH_LONG).show();
         }

         }**/


        // othman
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID1,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Context context = getActivity().getApplicationContext();
       // showNotification(context);



        Log.e("NOTIFICATION_LIST", "onCreate: " + Globals.NOTIFICATION_LIST );
       /* System.out.println("unviewedNotificationSizeList ---------------------------> " +Globals.notificationsList.size());
        notificationText.setText(String.valueOf(Globals.unviewdNotificationsList.size()));

        */


        return mRootView;
    }

    @Override
    public void onClick(View view) {

    }
    public void getCardNumber(){
        try {
            initProgrees();
            new CustomTaskCardNumber().execute();
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void getClientName(){
        try {
            initProgrees();
            new CustomTaskClientName().execute();
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void getCurrency(){
        try {
            initProgrees();
            new CustomTaskCurrency().execute();
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.button_sign_out:
                LogoutUser();
                break;
        }
        return true;
    }
    private void LogoutUser(){
        sharedPrefManager.logout();
        Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Toast.makeText(getActivity().getApplicationContext(), "You have been logged out", Toast.LENGTH_SHORT).show();
    }
    private class CustomTaskCardNumber extends AsyncTask<String, String, String> {
        protected String doInBackground(String... param) {
            try {
                Card.CardDetails();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        protected void onPostExecute(String param) {
            Log.e("TAG", "doInBackground: onPostExecute 3 ");

            dismissProgress();
            Log.e("TAG", "doInBackground: onPostExecute 4 ");

            super.onPostExecute(param);

            if(param!=null && param.contains("801")){
                Toast.makeText(getActivity().getApplicationContext(), "Session expired", Toast.LENGTH_LONG).show();
                try {
                    // doLogout(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            if(param!=null)
                Toast.makeText(getActivity().getApplicationContext(), param, Toast.LENGTH_LONG).show();

            TextInputEditText cardNumber = (TextInputEditText) mRootView.findViewById(R.id.cardNumber);
            cardNumber.setText(Globals.cardNumber);
        }
    }
    private class CustomTaskClientName extends AsyncTask<String, String, String> {
        protected String doInBackground(String... param) {
            try {
                Globals.user = userCode;
                Globals.password = password;

                Log.e("TAG", "doInBackground: CustomTaskClientName");
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        protected void onPostExecute(String param) {
            Log.e("TAG", "doInBackground: onPostExecute");

            dismissProgress();
            Log.e("TAG", "doInBackground: onPostExecute 2 ");

            super.onPostExecute(param);

            if(param!=null && param.contains("801")){
                Toast.makeText(getActivity().getApplicationContext(), "Session expired", Toast.LENGTH_LONG).show();
                try {
                    // doLogout(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            if(param!=null)
                Toast.makeText(getActivity().getApplicationContext(), param, Toast.LENGTH_LONG).show();

            TextView user = (TextView) mRootView.findViewById(R.id.userWelcome);
            user.setText("Bonjour "+Globals.userWelcome);
            EditText cardNumber = (EditText) mRootView.findViewById(R.id.cardNumber);
            cardNumber.setText(Globals.cardNumber);
            getCurrency();
        }
    }
    private class CustomTaskSolde extends AsyncTask<String, String, String> {
        protected String doInBackground(String... param) {
            try {

                Card.GetBalance();
                Log.e("TAG", "doInBackground: CustomTaskClientName");
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        protected void onPostExecute(String param) {
            Log.e("TAG", "doInBackground: onPostExecute");

            dismissProgress();
            Log.e("TAG", "doInBackground: onPostExecute 2 ");

            super.onPostExecute(param);

            if(param!=null && param.contains("801")){
                Toast.makeText(getActivity().getApplicationContext(), "Session expired", Toast.LENGTH_LONG).show();
                try {
                    // doLogout(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            if(param!=null)
                Toast.makeText(getActivity().getApplicationContext(), param, Toast.LENGTH_LONG).show();
            showSolde.setText(Globals.availableBalance);

        }
    }
    private class CustomTaskCurrency extends AsyncTask<String, String, String> {
        protected String doInBackground(String... param) {
            try {

                Card.GetBalance();
                Log.e("TAG", "doInBackground: CustomTaskClientName");
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        protected void onPostExecute(String param) {
            Log.e("TAG", "doInBackground: onPostExecute");

            dismissProgress();
            Log.e("TAG", "doInBackground: onPostExecute 2 ");

            super.onPostExecute(param);

            if(param!=null && param.contains("801")){
                Toast.makeText(getActivity().getApplicationContext(), "Session expired", Toast.LENGTH_LONG).show();
                try {
                    // doLogout(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            if(param!=null)
                Toast.makeText(getActivity().getApplicationContext(), param, Toast.LENGTH_LONG).show();
             TextView currency= (TextView) mRootView.findViewById(R.id.balance);
             currency.setText(Globals.balance);
        }
    }
    private class CustomTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... param) {
            try {
                Card.GetBalance();
                // Intent myWelcomeAct = new Intent(getActivity().getApplicationContext(), CardInformationResult.class);
                //startActivity(myWelcomeAct);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);

            if(param!=null && param.contains("801")){
                Toast.makeText(getActivity().getApplicationContext(), "Session expired", Toast.LENGTH_LONG).show();
                try {
                    // doLogout(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            Toast.makeText(getActivity().getApplicationContext(), "Votre solde est :"+Globals.availableBalance, Toast.LENGTH_SHORT).show();

            if(param!=null)
                Toast.makeText(getActivity().getApplicationContext(), param, Toast.LENGTH_LONG).show();
            ;



        }
    }

}
