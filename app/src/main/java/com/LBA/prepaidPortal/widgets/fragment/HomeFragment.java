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
    CardView CardBtn;
    //ImageButton StnBtn;
    CardView QRBtn;
    CardView BenefBtn;
    CardView AirBtn;
    CardView PaymentBtn;
    ImageButton Setting;
    TextView notificationText;
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

    String encodedImage = null;
    boolean hidePager = false;
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
        getActivity().setTitle("Profile\n");
        super.onCreate(savedInstanceState);
        getCardNumber();
        mRootView = inflater.inflate(R.layout.z_menu_test, container, false);
        sharedPrefManager = new SharedPrefManager(getActivity().getApplicationContext());


        notificationText = mRootView.findViewById(R.id.notificationText);


        AccBtn = (CardView) mRootView.findViewById(R.id.accountServices);
        //BillBtn = (ImageButton) findViewById(R.id.BillBtn);
        //ReqBtn = (CardView) findViewById(R.id.requests);
        TrBtn = (CardView) mRootView.findViewById(R.id.transfer);
        //expenseManager = (CardView) mRootView.findViewById(R.id.expenseManager);
        //StnBtn = (ImageButton) findViewById(R.id.StnBtn);
        QRBtn = (CardView) mRootView.findViewById(R.id.ghqr);
        MMBtn = (CardView) mRootView.findViewById(R.id.MMBtn);
        //payproxy = (CardView) mRootView.findViewById(R.id.payproxy);
        AirBtn = (CardView) mRootView.findViewById(R.id.airtimeAndData);
        PaymentBtn = (CardView) mRootView.findViewById(R.id.PaymentBtn);
        Setting = (ImageButton) mRootView.findViewById(R.id.Setting);
        notifications_btn = (ImageButton) mRootView.findViewById(R.id.notifications_btn);
        history_btn = (ImageButton) mRootView.findViewById(R.id.history_btn);
        //proxy =(ImageButton) findViewById(R.id.proxy);
        balances = (Button) mRootView.findViewById(R.id.btnBalances);
        welcomText = (TextView) mRootView.findViewById(R.id.userWelcome);
//        txtHide = (Button) mRootView.findViewById(R.id.txtHide);
        //showUser = (TextView) findViewById(R.id.showUser);
        //textView13 = (TextView) findViewById(R.id.textView13);
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

        // Globals.MustUsedOperations.clear();
// 3/28/2023       List<SliderItem> sliderItems = new ArrayList<>();
//        List<Integer> images = Arrays.asList(R.drawable.background_1,R.drawable.background_2,R.drawable.background_selfonboarding2);

// 3/28/2023       if(!Globals.MustUsedOperations.isEmpty()) {
//            int  imageIndex =0;
//            for(int j=0;j<Globals.MustUsedOperations.size();j++)
//            {
//                if(sliderItems.size()<3){
//                    if(Globals.operationLinks.keySet().contains(Globals.MustUsedOperations.get(j)))
//                    {
//                        sliderItems.add(new SliderItem(images.get(imageIndex), Globals.operationLinks.get(Globals.MustUsedOperations.get(j)), Globals.MustUsedOperations.get(j)));
//                        imageIndex++;
//                    }
//                }else {
//                    break;
//                }
//
//            }


// 3/28/2023       }else {
//            sliderItems.add(new SliderItem(R.drawable.background_1, AirtimeActivity.class, "Airtime Top Up"));
//            sliderItems.add(new SliderItem(R.drawable.background_2, MobileMoneyActivity.class, "Mobile Money"));
//            sliderItems.add(new SliderItem(R.drawable.background_selfonboarding2, QrPaymentActivity.class, "QR Instant Pay"));
//
//            //  viewPager2.setVisibility(View.GONE);
//        }
        /*
            sliderItems.add(new SliderItem(R.drawable.pub_image_1, AirtimeActivity.class, ""));
            sliderItems.add(new SliderItem(R.drawable.pub_image2, CardsServicesActivity.class, ""));
            sliderItems.add(new SliderItem(R.drawable.pub_image3, PaymentsServicesActivity.class, ""));*/
        //3/28/2023       viewPager2.setAdapter(new SliderAdapterForYou(sliderItems,viewPager2, this));
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

        //------------ pubs ViewPager2 UP Adds ------------------------

        //viewPager2_up = mRootView.findViewById(R.id.viewPagerImageSlider2);

        /*  Here , i'm preparing a list of images from drawable
         *    after the websService is ready we will get them from the api
         *  */

// 3/28/2023       List<SliderItem> sliderItems2 = new ArrayList<>();
//        sliderItems2.add(new SliderItem(R.drawable.up_slider_1));
//        sliderItems2.add(new SliderItem(R.drawable.up_slider_2));
//        sliderItems2.add(new SliderItem(R.drawable.up_slider_3));
//        sliderItems2.add(new SliderItem(R.drawable.up_slider_4));
//        sliderItems2.add(new SliderItem(R.drawable.up_slider_5));
//        sliderItems2.add(new SliderItem(R.drawable.up_slider_6));



       // if(Globals.Ads.isEmpty() || Globals.Ads == null ) {
  //         viewPager2_up.setAdapter(new StaticSliderAdapter(sliderItems2,viewPager2_up, this));
//        }else {
//            viewPager2_up.setAdapter(new SliderAdapter(Globals.Ads, viewPager2_up, this));
//        }
        //hajer 03/04/2022 end

        //hajer 04/03/2022 viewPager2_up.setAdapter(new SliderAdapter(sliderItems2,viewPager2_up, this));
        //hajer 04/03/2022

       /*viewPager2_up.setClipToPadding(false);
        viewPager2_up.setClipChildren(false);
        viewPager2_up.setOffscreenPageLimit(3);
        viewPager2_up.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);*/

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

        /*viewPager2_up.setPageTransformer(compositePageTransformer2);


        viewPager2_up.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
              //  sliderHandler.removeCallbacks(sliderRunnable);
              //  sliderHandler.postDelayed(sliderRunnable,3000);
            }
        });*/

        /*---------------------Hooks "drawer" ------------------------*/
      /*  drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        OpenTime();

*/
        // 1309/to add later
      /*  textView13.setText("Hello, "+Globals.firstName);
        textView13.setTextSize(14);*/

      /*
        File pictureFile = getOutputMediaFile();
        String yourFilePath = context.getFilesDir() + "/" + "profileImage.jpg";
        File yourFile = new File( yourFilePath );
        File mSaveBit; // Your image file
        String filePath = mSaveBit.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        mImageView.setImageBitmap(bitmap);
        */
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
// 3/28/2023       Setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task = new CustomTask(SettingsActivity.class);
//                task.execute();
//            }
//        });

// 3/28/2023       notifications_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                /*notificationText.setVisibility(View.GONE);
                Globals.unviewdNotificationsList.clear();
                 */
//                Globals.notificationViewed = true;
//                initProgrees();
//  3/28/2023              CustomTask task = new CustomTask(NotificationsActivity.class);
//                task.execute();
        //           }
//        });
// 3/28/2023       history_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task = new CustomTask(Account_History.class);
//                task.execute();
//            }
//        });
// 3/28/2023       AccBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task = new CustomTask(AccountServicesActivity.class);
//                task.execute();
//            }
//        });
// 3/28/2023       expenseManager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task = new CustomTask(ExpenseManagerServices.class);
//                task.execute();
//            }
//        });
        // younes
// 3/28/2023       balances.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task = new CustomTask(AccountBalanceActivity.class);
//                Globals.activity = Z_WelcomeActivity.class;
//                task.execute();
//            }
//        });
        //
        /*BillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                CustomTask task=new CustomTask(BillsServicesActivity.class);
                task.execute();
            }
        });*/
        // younes

//3/28/2023        MMBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task=new CustomTask(MobileMoneyActivity.class);
//                task.execute();
//            }
//        });
        //
       /* ReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                CustomTask task = new CustomTask(RequestServicesActivity.class);
                task.execute();
            }
        });*/
// 3/28/2023       TrBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task = new CustomTask(TransferServicesActivity.class);
//                task.execute();
//
//            }
//        });

// 3/28/2023       CardBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task = new CustomTask(CardsServicesActivity.class);
//                task.execute();
//            }
//        });

// 3/28/2023       PaymentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task = new CustomTask(PaymentsServicesActivity.class); /// younes changed activity until future
//                task.execute();
//            }
//        });
        // handling show and hide the notification counter in the home page
 /*       if (Globals.notificationViewed || Globals.NOTIFICATION_LIST == 0 ) {
            notificationText.setVisibility(View.GONE);
        }
        else {
            NotificationManager notificationManager = (NotificationManager) getActivity().getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
            System.out.println("active notification in android: " + Globals.NOTIFICATION_LIST);
            notificationText.setText(String.valueOf(Globals.NOTIFICATION_LIST));
            notificationText.setVisibility(View.VISIBLE);

        }*/
        Log.d("WelcomeActivity", "user["+Globals.user+"]");
// 3/28/2023       qrScan = new IntentIntegrator(this);
//        qrScan.setCaptureActivity(AnyOrientationCaptureActivity.class);
//        qrScan.setOrientationLocked(false);
//        qrScan.setPrompt("");
//
//        /// ADDED
//        qrScan.setBarcodeImageEnabled(true);
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


        balances.setOnClickListener(new View.OnClickListener() {
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
        });
        //hajer 28/06/2022 start
        updateGhCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.creditagricole.ma/fr"));
                startActivity(browserIntent);
            }
        });
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
            //Account.GetTransactionList(selectedAccount, fromDateEtxt.getText().toString().trim(), toDateEtxt.getText().toString().trim());
            initProgrees();
            new CustomTaskCardNumber().execute();
        } catch (Exception e) {
            //Log.d(TAG, "btnLoad.setOnClickListener()", e);
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void getClientName(){
        try {
            //Account.GetTransactionList(selectedAccount, fromDateEtxt.getText().toString().trim(), toDateEtxt.getText().toString().trim());
            initProgrees();
            new CustomTaskClientName().execute();
        } catch (Exception e) {
            //Log.d(TAG, "btnLoad.setOnClickListener()", e);
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
                // Intent myWelcomeAct = new Intent(getActivity().getApplicationContext(), CardInformationResult.class);
                //startActivity(myWelcomeAct);
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
            getClientName();


        }
    }
    private class CustomTaskClientName extends AsyncTask<String, String, String> {
        protected String doInBackground(String... param) {
            try {
                Globals.user = userCode;
                Globals.password = password;
                User.login(userCode, password, Globals.authenCode,1);
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