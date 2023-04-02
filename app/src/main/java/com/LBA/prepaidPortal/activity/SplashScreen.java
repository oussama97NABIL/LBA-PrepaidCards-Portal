package com.LBA.prepaidPortal.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.LBA.prepaidPortal.R;
import com.LBA.tools.assets.Globals;



//import butterknife.BindView;
//import butterknife.ButterKnife;

public class SplashScreen extends AppCompatActivity {
    //@BindView (R.id.imageView2) ImageView mLogo;
    LinearLayout descimage,desctxt;
    Animation uptodown,downtoup;
    String UnameValue;
    String DefaultUnameValue = "";
    static private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_user);

        try {
            Globals.appVersionName = getBaseContext().getPackageManager().getPackageInfo(getBaseContext().getPackageName(), 0).versionName;
            Globals.appVersionCode = getBaseContext().getPackageManager().getPackageInfo(getBaseContext().getPackageName(), 0).versionCode;
        }catch (Exception e) {
            Globals.appVersionName = "";
            Globals.appVersionCode = 0;
        }
        SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        String FirstTime = preferences.getString("FirstTimeInstall","");


        //ButterKnife.bind(this);

//      THE CONTENTS ARE PUT INSIDE A LINEAR LAYOUT.
//      We reference the image and text using their id.
//      descimage = (R.id.titleimage)
//      desctxt = (R.id.titletxt)

       /* descimage = (LinearLayout) findViewById(R.id.titleimage);
        desctxt = (LinearLayout) findViewById(R.id.titletxt);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);*/
//      THIS ANIMATIONS ARE SET INSIDE THE (ANIM) FOLDER.
//      This initiaizes the animations.

//      //\\ NOTE! //\\
//      FOR THIS ANIMATION TO WORK CONTENTS MUST BE INSIDE LINEAR LAYOUT
//      CHECK...-> activity_splash_screen.xml for reference!
        // descimage.setAnimation(downtoup);
        // desctxt.setAnimation(uptodown);


//  THIS CODE ROTATES IMAGE! AS AN ANIMATION!
//        mLogo - Is used after we bind with the ID.
//        Id = imageView2

//  Import;
//  import android.view.animation.LinearInterpolator;
//  import android.view.animation.RotateAnimation;
       /* RotateAnimation rotate = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000);
        rotate.setInterpolator(new LinearInterpolator());*/
        // mLogo.startAnimation(rotate);

        Thread myThread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(4000);
                    SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
                    String FirstTime = preferences.getString("FirstTimeInstall","");

                    System.out.println("hajer shared pref"+preferences.getString("FirstTimeInstall",""));

                    if(FirstTime.equals("Yes")){
                        //  Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        Intent intent = new Intent(SplashScreen.this,NewMainPage.class);

                        intent = new Intent(SplashScreen.this, NewMainPage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        //  Intent intent = new Intent(SplashScreen.this,NewSelfOnbroard1.class);
                        SharedPreferences settings = getSharedPreferences("UNSV",
                                Context.MODE_PRIVATE);

                        // Get value
                        UnameValue = settings.getString("username", DefaultUnameValue);
                        if(UnameValue.isEmpty()){
                            //      intent = new Intent(SplashScreen.this,ChooseAccountActivity.class);
                            //hajer 26/04/2022 start
                            String terms;
                            SharedPreferences settings1 = getSharedPreferences("PREFS", 0);
                            terms = settings1.getString("terms","");
                            System.out.println("HAJER TERMS"+terms);
                           /* if(terms == "") {
                                intent = new Intent(SplashScreen.this,PrivacyPolicyPDF.class);

                            }else {
                                //hajer 26/04/2022 end
                                intent = new Intent(SplashScreen.this, ChooseSetupActivity.class);
                            }*/
                        }
                        //startActivity(intent);
                    }
                    else{
                        System.out.println("hajer inside else");
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("FirstTimeInstall", "Yes");
                        editor.apply();
                        /*Intent intent = new Intent(SplashScreen.this,FirstWelcome.class);
                        startActivity(intent);
                        finish();*/
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        myThread.start();

        SplashScreen.context = getApplicationContext();

        new Thread(new Runnable() {
            @Override public void run() { // background code }

                /*hajer 20/06/2022
                RootBeer rootBeer = new RootBeer(context);

                if (rootBeer.isRooted()) {
                    Log.d("root","this device is rooted");

                } else {
                    //we didn't find indication of root
                    Log.d("root","this device is not rooted");
                }
*/
                //hajer 20/06/2022
                /*if(new DeviceUtils().isDeviceRooted(getApplicationContext())){
                    Log.d("root","this device is rooted");

                }*/
           /* else {
                //we didn't find indication of root
                Log.d("root","this device is not rooted");
            }*/
                //hajer 20/06/2022

            }}).start();



    }



}

