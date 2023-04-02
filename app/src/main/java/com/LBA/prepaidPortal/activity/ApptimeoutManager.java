package com.LBA.prepaidPortal.activity;

import android.app.Application;
import android.util.Log;

import com.LBA.tools.assets.Globals;

import java.util.Timer;
import java.util.TimerTask;



public class ApptimeoutManager extends Application {
    public static AbstractActivity abstractActivity;
    public static Timer timer;
    public static void userSessionStart(){

        {
            Log.i("tag", "Pdialog is"+abstractActivity.pDialog);

       /* if(abstractActivity.pDialog!=null && abstractActivity.pDialog.isShowing()){

                Log.i("tag", "Pdialog is showing");
                abstractActivity.delay=0;


        }
        else{*/
            Log.i("tag", "delay Globals.timeout value"+Globals.timeout);
            if(Globals.timeout==null || abstractActivity.pDialog!=null && abstractActivity.pDialog.isShowing()){
                Log.i("in if ","delay 0: "+abstractActivity.delay);
                abstractActivity.delay=0;
            }else {
                Log.i("in else ","delay 1: "+abstractActivity.delay);
                abstractActivity.delay= Integer.parseInt(Globals.timeout);
            }


            Log.i("tag", "delay"+abstractActivity.delay);
            if (timer != null) {
                timer.cancel();
            }
            if (abstractActivity.delay!=0){

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Log.i("tag", "Timeout");
                        abstractActivity.doLogout(null);




                    }
                },  (abstractActivity.delay*1000) );
            }
        }

    }

    public  static synchronized void resetSession() { userSessionStart(); }
    public static  synchronized void progreesDialog(){
        userSessionStart();
    }

    public static AbstractActivity getAbstractActivity() {
        return abstractActivity;
    }

    public static void setAbstractActivity(AbstractActivity abstractActivity) {
        ApptimeoutManager.abstractActivity = abstractActivity;
    }
}
