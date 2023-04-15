package com.LBA.prepaidPortal.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import androidx.core.app.NotificationCompat;
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

import com.LBA.prepaidPortal.R;
//import com.LBA.prepaidPortal.widgets.fragment.CardInformation;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.Notifications;
import com.LBA.tools.services.Qr;
import com.LBA.tools.services.User;
import com.google.android.material.navigation.NavigationView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Z_WelcomeActivity extends AbstractActivity  {
    NavigationView navigationView;

    CardView AccBtn;
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
  //  Button txtHide;
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

    RoundedImageView userImage ;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Log.e("onCreate: ", "  Globals.userType : "+  Globals.userType);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_home);






        // getNotifications();



        notificationText = findViewById(R.id.notificationText);




        //AccBtn = (CardView) findViewById(R.id.accountServices);
        //BillBtn = (ImageButton) findViewById(R.id.BillBtn);
        //ReqBtn = (CardView) findViewById(R.id.requests);
        TrBtn = (CardView) findViewById(R.id.transfer);
        //expenseManager = (CardView) findViewById(R.id.expenseManager);

        //StnBtn = (ImageButton) findViewById(R.id.StnBtn);
        QRBtn = (CardView) findViewById(R.id.ghqr);
        MMBtn = (CardView) findViewById(R.id.MMBtn);
        //payproxy = (CardView) findViewById(R.id.payproxy);
        AirBtn = (CardView) findViewById(R.id.airtimeAndData);
        PaymentBtn = (CardView) findViewById(R.id.PaymentBtn);
        Setting = (ImageButton)findViewById(R.id.Setting);
        notifications_btn = (ImageButton)findViewById(R.id.notifications_btn);
        history_btn = (ImageButton)findViewById(R.id.history_btn);
        //proxy =(ImageButton) findViewById(R.id.proxy);
        balances = (Button) findViewById(R.id.btnBalances);
        welcomText = (TextView) findViewById(R.id.userWelcome);
//        txtHide = (Button) findViewById(R.id.txtHide);
        //showUser = (TextView) findViewById(R.id.showUser);
        //textView13 = (TextView) findViewById(R.id.textView13);
        updateGhCard = (Button) findViewById(R.id.updateGhCard);
        userImage = (RoundedImageView) findViewById(R.id.userImage);

        //---------------      --------------------------
        Spannable word = new SpannableString("Hello ");

      //3/28/2023  word.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.cbg_red)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        welcomText.setText(word);
        Spannable wordTwo = new SpannableString(Globals.firstName);

        wordTwo.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        welcomText.append(wordTwo);
        //------------ pubs ViewPager2 ------------------------

        viewPager2 = findViewById(R.id.viewPagerImageSlider);

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


        //hajer 03/04/2022 start
//3/28/2023        if(Globals.Ads.isEmpty() || Globals.Ads == null ) {
//            viewPager2_up.setAdapter(new StaticSliderAdapter(sliderItems2,viewPager2_up, this));
//        }else {
//            viewPager2_up.setAdapter(new SliderAdapter(Globals.Ads, viewPager2_up, this));
//        }
        //hajer 03/04/2022 end

        //hajer 04/03/2022 viewPager2_up.setAdapter(new SliderAdapter(sliderItems2,viewPager2_up, this));
        //hajer 04/03/2022

        viewPager2_up.setClipToPadding(false);
        viewPager2_up.setClipChildren(false);
        viewPager2_up.setOffscreenPageLimit(3);
        viewPager2_up.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

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

        viewPager2_up.setPageTransformer(compositePageTransformer2);


        viewPager2_up.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,3000);
            }
        });

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
                if(checkAndRequestPermissions(Z_WelcomeActivity.this)){
                    // Toast.makeText(Z_WelcomeActivity.this, "Permissions allowed", Toast.LENGTH_LONG).show();
                    chooseImage(Z_WelcomeActivity.this);
                }else{
                    //  Toast.makeText(Z_WelcomeActivity.this, "Permissions denied ", Toast.LENGTH_LONG).show();

                }
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
        if (Globals.notificationViewed || Globals.NOTIFICATION_LIST == 0 ) {
            notificationText.setVisibility(View.GONE);
        }
        else {
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
            System.out.println("active notification in android: " + Globals.NOTIFICATION_LIST);
            notificationText.setText(String.valueOf(Globals.NOTIFICATION_LIST));
            notificationText.setVisibility(View.VISIBLE);

        }
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
// 3/28/2023       QRBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // initProgrees();
//                // CustomTask task = new CustomTask(ScanQrActivity.class);
//                // task.execute();
//                Intent PaymentQrIntent= new Intent(Z_WelcomeActivity.this, QrPaymentActivity.class);
//                startActivity(PaymentQrIntent);
//
////                qrScan.initiateScan(IntentIntegrator.QR_CODE_TYPES);
//
//            }
//        });
        //  to add later
// 3/28/2023       BenefBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task = new CustomTask(ListBeneficiaryActivity.class);
//                task.execute();
//            }
//        });
        /*StnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                CustomTask task = new CustomTask(PaymentsServicesActivity.class);
                task.execute();
            }
        });*/
// 3/28/2023       AirBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initProgrees();
//                CustomTask task = new CustomTask(TransferACHMiniService.class);
//                task.execute();
//            }
//        });

        //hajer 28/06/2022 start
        updateGhCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bit.ly/GhanaCU"));
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
        /*if (Globals.firstFetch = true) {
             Z_WelcomeActivity.CustomTask3 task=new Z_WelcomeActivity.CustomTask3();
        try {
            //task.execute();
            Notifications.GetUnviewedNotifications();
            Globals.firstFetch = false;

        } catch (Exception e) {
            dismissProgress();
            androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(Z_WelcomeActivity.this).create();
            alertDialog.setMessage("Failed Save Notification History");
            alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
         }
        }

         */

        // othman
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID1,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Context context = getApplicationContext();
        showNotification(context);



        Log.e("NOTIFICATION_LIST", "onCreate: " + Globals.NOTIFICATION_LIST );
       /* System.out.println("unviewedNotificationSizeList ---------------------------> " +Globals.notificationsList.size());
        notificationText.setText(String.valueOf(Globals.unviewdNotificationsList.size()));

        */


    }

    /*

    private void getNotifications() {
        Log.e("getNotifications size ", " : "+Globals.notificationsList.size() );
        Log.e("getNotifications List ", " : "+Globals.notificationsList );

        for (NotificationEntry notif:Globals.notificationsList
             ) {
            Log.e("getNotifications", " : "+notif.toString() );
        }
        if(Globals.newNotifications){
            for (NotificationEntry notif:Globals.notificationsList
            ) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.cgb_logo_1)
                        .setContentTitle(notif.getTitle())
                        .setContentText(notif.getBody())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                //NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                //notificationManager.notify(0,builder.build());
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                        new Intent(this, NotificationsActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


                builder.setContentIntent(contentIntent);

//          === Removed some obsoletes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    mNotificationManager.createNotificationChannel(channel);
                    builder.setChannelId(channelId);
                }

                mNotificationManager.notify(notif.getId(), builder.build());
            }
            Globals.newNotifications = false;
        }
      /*  List<String> notifications = Arrays.asList("First notification", "second notification", "third notification");
        List<String> notification_content = Arrays.asList("First content", "second content", "third content");
        if(Globals.newNotifications){
            for (int i = 0 ; i < notifications.size();i++) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.cgb_logo_1)
                        .setContentTitle(notifications.get(i))
                        .setContentText(notification_content.get(i))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        //notificationManager.notify(0,builder.build());
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//          === Removed some obsoletes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    mNotificationManager.createNotificationChannel(channel);
                    builder.setChannelId(channelId);
                }

                mNotificationManager.notify(i, builder.build());
            }
            Globals.newNotifications = false;
        }

    }
    */

    /**
     private int currentAd;
     private Handler handler = new Handler();
     private Runnable changeAd = new Runnable() {
     public void run() {
     if(!Globals.Ads.isEmpty()) {
     currentAd++;
     if (currentAd >= Globals.Ads.size())
     currentAd = 0;
     handler.postDelayed(changeAd, 4000);
     AdsEntry ad = Globals.Ads.get(currentAd);
     Bitmap bitmap = BitmapFactory.decodeByteArray(ad.getImage(), 0, ad.getImage().length);

     Adview = (ImageView) findViewById(R.id.ad);
     Adview.setImageBitmap(bitmap);
     }
     else{
     Adview = (ImageView) findViewById(R.id.ad);
     Adview.setImageBitmap(null);
     handler.removeCallbacks(this);
     }
     }
     };
     **/
    //oussama 3/30/2023


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showNotification(Context context) {
        Log.e("NotificationENTEY", " show notification block" );
        Log.e("Globals" , Globals.unviewdNotificationsList.toString());

        for(int i = 0 ; i < Globals.unviewdNotificationsList.size() ; i++) {
            Log.e("NotificationENTEY1", " show notification block" );
            Log.e("NotificationLoop", "show title: " + Globals.unviewdNotificationsList.get(i).getTitle() + " Show Desc " + Globals.unviewdNotificationsList.get(i).getBody() + " with ID" + Globals.unviewdNotificationsList.get(i).getId() + " with user" + Globals.unviewdNotificationsList.get(i).getUser() );
            //3/28/2023   Intent intent = new Intent(context , NotificationsDetail.class);
//            intent.putExtra("title" , Globals.unviewdNotificationsList.get(i).getTitle());
//            intent.putExtra("desc" , Globals.unviewdNotificationsList.get(i).getBody());
            Log.e("NotificationENTEY2", " show notification block" );
//            intent.putExtra("id" , Globals.unviewdNotificationsList.get(i).getId());
            Log.e("NotificationENTEY3", " show notification block" );
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(Z_WelcomeActivity.this , i , intent , PendingIntent.FLAG_CANCEL_CURRENT);
//            System.out.println("PendingIntent ------------------> " + pendingIntent);
//3/28/2023            NotificationCompat.Builder mBuilder =
//                    new NotificationCompat.Builder(this, CHANNEL_ID1)
//                            .setSmallIcon(R.drawable.cbg_notif_logo)
//                            .setContentTitle(Globals.unviewdNotificationsList.get(i).getTitle())
//                            .setContentText(Globals.unviewdNotificationsList.get(i).getBody())
//                            .setAutoCancel(true)
//                            .setPriority(NotificationCompat.PRIORITY_HIGH)
//                            .setContentIntent(pendingIntent);
//3/28/2023            System.out.println("mBuilder ------------------> " + mBuilder);
            NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);
//3/28/2023            mNotificationManager.notify(i, mBuilder.build());
            System.out.println("mNotificationManager ------------------> " + mNotificationManager);
        }

        Globals.unviewdNotificationsList.clear();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }
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

    private class CustomTask extends AsyncTask<String, String, String> {
        Class activity;
        public CustomTask(Class pActivity) {
            super();
            activity=pActivity;
        }
        protected String doInBackground(String... param) {
            try {
                Intent myAccountServicesAct = new Intent(Z_WelcomeActivity.this, activity);
                startActivity(myAccountServicesAct);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);
            if(param!=null) {
                //   Toast.makeText(Z_WelcomeActivity.this, param, Toast.LENGTH_SHORT).show();
                AlertDialog alertDialog = new AlertDialog.Builder(Z_WelcomeActivity.this).create();
                alertDialog.setMessage(param);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        //if the drawer included
      /*   if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }*/
        if (doubleBackToExitPressedOnce) {
            initProgrees();
            doLogout(null);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    public void OpenTime() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String msg = "";

        if (timeOfDay >= 0 && timeOfDay < 12) {
            msg = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            msg = "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            msg = "Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            msg = "Good Evening";
        }
        // textView13.setText(msg);
    }

    // qr scan
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            /*ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));*/
// 3/28/2023       if (requestCode == ImagePicker.REQUEST_CODE
//                && resultCode == Activity.RESULT_OK) {
//            // The result data contains a URI for the document or directory that
//            // the user selected.
//            uri = null;
//            if (data != null) {
//                uri = data.getData();
//
//                  /*  String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                    if (uri != null) {
//                        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
//                        if (cursor != null) {
//                            //Bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
//                            cursor.moveToFirst();
//                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                            String picturePath = cursor.getString(columnIndex);
//                            userImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//
//                            try {
//                                User.SendImageToServer(getEncoded64ImageStringFromBitmap(BitmapFactory.decodeFile(picturePath)));
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            cursor.close();
//                        }
//                    }
//
//**/
//                Picasso.with(this).load(new File(uri.getPath())).into(new Target() {
//
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                        // Set it in the ImageView
//                        userImage.setImageBitmap(bitmap);
//                        encodedImage = getEncoded64ImageStringFromBitmap(bitmap);
//
//                        try {
//                            // User.SendImageToServer(getEncoded64ImageStringFromBitmap(bitmap));
//                            new CustomTask2().execute();
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        Log.d("image to bitmap", bitmap.toString());
//                        Log.d("image to bitmap GBV", bitmap.toString());
//
//                    }
//                    @Override
//                    public void onBitmapFailed(Drawable errorDrawable) {
//                    }
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//                    }
//                });
//
//                // call encode function and store in preferences
//            }
//        }
//3/28/2023        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        // Compressor compressedImageFile = new Compressor(this).compressToFile( data.getExtras().get("data"));

                        // 05112021
                        Log.e( "image string "," ************************** : "+getEncoded64ImageStringFromBitmap(selectedImage) );
                        Globals.profileImage = StringToBitMap2(getEncoded64ImageStringFromBitmap(selectedImage));
                        encodedImage = getEncoded64ImageStringFromBitmap(selectedImage);
                        try {
                            //
                            // User.SendImageToServer(getEncoded64ImageStringFromBitmap(selectedImage));

                            new CustomTask2().execute();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // storeImage(selectedImage);
                        userImage.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    Log.e("onActivityResult: "," ---------- image picker OLD ------ " );
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
//                        Compressor compressedImageFile = new Compressor(this).compressToFile( MediaStore.Images.Media.DATA);

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                //Bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                userImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                encodedImage = getEncoded64ImageStringFromBitmap(BitmapFactory.decodeFile(picturePath));

                                try {
                                    // User.SendImageToServer(getEncoded64ImageStringFromBitmap(BitmapFactory.decodeFile(picturePath)));
                                    new CustomTask2().execute();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }

// 3/28/2023       if (result != null) {
//            //if qrcode has nothing in it
//            if (result.getContents() == null) {
//
//                // Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
//                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(Z_WelcomeActivity.this).create();
//                alertDialog.setMessage("Result Not Found");
//                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
//            } else {
//                //if qr contains data
//                try {
//                    //converting the data to json
//                    //hajer JSONObject obj = new JSONObject(result.getContents());
//                    String obj= result.getContents();
//                    Log.d("","QRDATA++++++++++++++++++++++++++++"+obj);
//                    String qrData= Qr.GetQrData(obj);
//                    Intent PaymentQrIntent= new Intent(Z_WelcomeActivity.this, QrPaymentActivity.class);
//                    PaymentQrIntent.putExtra("qrData",qrData);
//                    PaymentQrIntent.putExtra("obj",obj);
//
//                    startActivity(PaymentQrIntent);
//                    //setting values to textviews
//                    // textView.setText(obj.getString("name"));
//                } //HAJER catch (JSONException e) {
//                catch (Exception e) {
//                    e.printStackTrace();
//                    //if control comes here
//                    //that means the encoded format not matches
//                    //in this case you can display whatever data is available on the qrcode
//                    //to a toast
//                    //   Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
//                    AlertDialog alertDialog = new AlertDialog.Builder(Z_WelcomeActivity.this).create();
//                    alertDialog.setMessage(result.getContents());
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();
//                }
//            }
//        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


 /*     @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        CustomTask task2 ;
       switch (menuItem.getItemId()) {
            case R.id.nav_home: break;
            case R.id.nav_account:
                initProgrees();
                task2 = new CustomTask(AccountServicesActivity.class);
                task2.execute(); break;
            case R.id.nav_card_service:
                initProgrees();
                task2 = new CustomTask(CardsServicesActivity.class);
                task2.execute();
                break;
            case R.id.nav_airtime:
                initProgrees();
                task2 = new CustomTask(TransferACHMiniService.class);
                task2.execute();
                break;
            case R.id.nav_bills:
                initProgrees();
                task2 = new CustomTask(PaymentsServicesActivity.class); /// younes changed activity until future
                task2.execute(); break;
           case R.id.nav_requests:
               initProgrees();
               task2 = new CustomTask(RequestServicesActivity.class);
               task2.execute();
               break;
           case R.id.nav_transfers:
               initProgrees();
               task2 = new CustomTask(TransferServicesActivity.class);
               task2.execute();
               break;
           case R.id.nav_ghqr:
               qrScan.initiateScan(IntentIntegrator.QR_CODE_TYPES);
               break;
           case R.id.nav_proxy_pay:
               Toast.makeText(this, "Service Unavailable for the moment", Toast.LENGTH_SHORT).show();
               break;
       }
        drawerLayout.closeDrawer(GravityCompat.START); return true;
    }*/

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2_up.setCurrentItem(viewPager2_up.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable,3000);

    }




    // function to let's the user to choose image from camera or gallery

    private void chooseImage(Context context){

        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" }; // create a menuOption Array

        // create a dialog for showing the optionsMenu

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // set the items in builder

        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(optionsMenu[i].equals("Take Photo")){

                    // Open the camera and get the photo

                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
// 3/28/2023               else if(optionsMenu[i].equals("Choose from Gallery")){
//
//                    // choose from  external storage
//                    ImagePicker.Companion.with(Z_WelcomeActivity.this)
//                            .galleryOnly()
//                            .crop()	    			//Crop image(Optional), Check Customization for more option
//                            .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//                            .start();
//                 /* Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto , 1);*/
//
//                }
                else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }

            }
        });
        builder.show();
    }


    // function to check permission

    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    // Handled permission Result


    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(Z_WelcomeActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                 /*   Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();*/
                    AlertDialog alertDialog = new AlertDialog.Builder(Z_WelcomeActivity.this).create();
                    alertDialog.setMessage("FlagUp Requires Access to Camara.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                } else if (ContextCompat.checkSelfPermission(Z_WelcomeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                  /*  Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT).show();*/
                    AlertDialog alertDialog = new AlertDialog.Builder(Z_WelcomeActivity.this).create();
                    alertDialog.setMessage("FlagUp Requires Access to Your Storage.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                } else {
                    chooseImage(Z_WelcomeActivity.this);
                }
                break;
        }
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("", "Error accessing file: " + e.getMessage());
        }
    }


    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("profileImage").format(new Date());
        File mediaFile;
        String mImageName="profileImage.jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }
    public String BitMapToString(Bitmap bitmap){
        /*
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;*/
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return  encoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    public static Bitmap StringToBitMap2(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);

            // InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            // Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            Log.e("StringToBitMap2: "," bitmap --> "+ bitmap);

            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }



    private class CustomTask3 extends AsyncTask<String, String, String> {

        protected String doInBackground(String... param) {
            try {


                Notifications.GetUnviewedNotifications();

                // balanceData = Account.GetBalance(selectedAccount);
                Log.e("UnviewdNotificationList", "notifications list=["+Globals.unviewdNotificationsList+"]");
                /**
                 txtBalance.setText(balanceData.substring(0, 0 + 20));
                 Log.d(TAG, "txtBalance=[" + txtBalance.getText().toString() + "][" + balanceData.substring(0, 0 + 20) + "]");
                 txtCurrency.setText(balanceData.substring(0 + 20, 0 + 20 + 3));
                 Log.d(TAG, "txtCurrency=[" + txtCurrency.getText().toString() + "][" + balanceData.substring(0 + 20, 0 + 20 + 3) + "]");
                 txtValueDate.setText(balanceData.substring(0 + 20 + 3, 0 + 20 + 3 + 10));
                 Log.d(TAG, "txtValueDate=[" + txtValueDate.getText().toString() + "][" + balanceData.substring(0 + 20 + 3, 0 + 20 + 3 + 10) + "]");
                 **/
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }

        }
        protected void onPostExecute(String result) {
            dismissProgress();
            super.onPostExecute(result);

            /*if(balData!=null && balData.size()>0){
                String balance = balData.get(0);
                txtBalance.setText(balance);
                txtBalance.setTextSize(18);
                Log.d("balance",""+balance);

                String currency = balData.get(1);
                txtCurrency.setText(currency);
                txtCurrency.setTextSize(18);

                Log.d("balance",""+currency);
                String date = balData.get(2);
                txtValueDate.setText(date);
                txtValueDate.setTextSize(18);

                Log.d("balance",""+date);
            }
            if(balData.size()==0) {
                txtBalance.setText("0.0");
                txtBalance.setTextSize(18);
                txtCurrency.setText("");
                txtValueDate.setText("");

            }

            if(result!=null && result.contains("801")){
                // Toast.makeText(AccountBalanceActivity.this, "SESSION EXPIRED", Toast.LENGTH_LONG).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(AccountBalanceActivity.this).create();
                alertDialog.setMessage("SESSION EXPIRED");
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                doLogout(null);
            }*/

            if(result!=null && !result.contains("801")) {
                // Toast.makeText(AccountBalanceActivity.this, result, Toast.LENGTH_LONG).show();

                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(Z_WelcomeActivity.this).create();
                alertDialog.setMessage(result);
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
    }






    public static String getPath( Context context, Uri uri ) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result ;

    }


    private class CustomTask2 extends AsyncTask<String, String, String> {

        protected String doInBackground(String... param) {
            try {

                // Expense.SetExpenseLimit(accountSelected,service, Double.parseDouble(dailyLimit.getText().toString()),transactionType);
                User.SendImageToServer(encodedImage);


                return null;
            } catch (Exception e) {
                e.printStackTrace();

                return e.getMessage();
            }
        }
        protected void onPostExecute(String param) {
            dismissProgress();
            super.onPostExecute(param);

            Log.e("************","onPost");
           /* if(param!=null && param.contains("801")){
                //Toast.makeText(ExpenseLimit.this, "SESSION EXPIRED", Toast.LENGTH_LONG).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(Z_WelcomeActivity.this).create();
                alertDialog.setTitle("Failed");
                alertDialog.setMessage("SESSION EXPIRED");
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                doLogout(null);
            }

            if(param!=null && !param.contains("801")) {
                // Toast.makeText(ExpenseLimit.this, param, Toast.LENGTH_LONG).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(Z_WelcomeActivity.this).create();
                alertDialog.setTitle("Failed");
                alertDialog.setMessage(param);
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            if(Globals.isSuccessful){
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(Z_WelcomeActivity.this).create();
                alertDialog.setMessage("Limit has been settled successfully");
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

*/
        }
    }

}

