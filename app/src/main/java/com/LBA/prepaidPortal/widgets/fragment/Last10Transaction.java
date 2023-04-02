package com.LBA.prepaidPortal.widgets.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.HomeActivity;
import com.LBA.tools.assets.Globals;

import java.util.Calendar;


public class Last10Transaction extends BaseFragment implements View.OnClickListener{
    private ListView lv;
    private View mRootView;
    private int nCounter=0;
    private int[] thumb = {R.drawable.plus, R.drawable.minus};
    private String selectedAccount;
    static private final String TAG = Last10Transaction.class.getSimpleName();
    TextView textView_heading;
    // younes
    //   TextView showUser;
    ImageButton homeBtn;
    ImageButton back;
    String DefaultUnameValue = "";


    public Last10Transaction() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_last10_transaction, container, false);
        RelativeLayout yourBackgroundView = (RelativeLayout)mRootView. findViewById(R.id.root);

       // SharedPreferences settings = getSharedPreferences("appBack",
                //Context.MODE_PRIVATE);

        //String imageS  = settings.getString("background", DefaultUnameValue);
        //Log.d("Retr image from device", imageS);
        Bitmap imageB;
        /*if(!imageS.equals("")) {
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
        }*/
        if(Globals.transactionList!=null && Globals.transactionList.size()>0) {
            lv = (ListView) mRootView. findViewById(R.id.listView);
            lv.setAdapter(new VersionAdapter(getActivity().getApplicationContext()));
        }


        textView_heading = (TextView) mRootView.findViewById(R.id.textView14);

        homeBtn = (ImageButton) mRootView.findViewById(R.id.home_button);

       /* Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        textView_heading.setTypeface(tf);*/


        OpenTime();
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });
        back = (ImageButton) mRootView.findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(HomeTask.class);
                task.execute();
            }
        });
        return mRootView;

    }

    @Override
    public void onClick(View view) {

    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account_balance, menu);
        return true;
    }*/
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
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    class VersionAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;

        public VersionAdapter(Context applicationContext) {
        }

        /* public VersionAdapter(Last10Transaction activity) {
             // TODO Auto-generated constructor stub
             layoutInflater = (LayoutInflater) activity
                     .getSystemService(LAYOUT_INFLATER_SERVICE);
         }*/
        public int getCount() {
            // TODO Auto-generated method stub
            return Globals.transactionList.size();
        }
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View listItem = convertView;
            int pos = position;
            if (listItem == null) {
                listItem = layoutInflater.inflate(R.layout.trxlist_item, null);
            }
            // Initialize the views in the layout
            //ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
            TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
            TextView tvDesc = (TextView) listItem.findViewById(R.id.desc);
            TextView tvAmount = (TextView) listItem.findViewById(R.id.amount);

            /*Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
            tvTitle.setTypeface(tf);
            tvDesc.setTypeface(tf);
            tvAmount.setTypeface(tf);*/
            // set the views in the layout
            // younes iv.setBackgroundResource(thumb[0]);
            tvAmount.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            tvAmount.setText(Globals.transactionList.get(pos).getCredit());
            if (Globals.transactionList.get(pos).getDebit() != null && Globals.transactionList.get(pos).getDebit().length() > 0) {
                // younes   iv.setBackgroundResource(thumb[1]);
                tvAmount.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                tvAmount.setText(Globals.transactionList.get(pos).getDebit());
            }

            tvTitle.setText(Globals.transactionList.get(pos).getValueDate());
            // tvDesc.setText(Globals.transactionList.get(pos).getDescription() + " " + Globals.transactionList.get(pos).getDescription2());
            tvDesc.setText(Globals.transactionList.get(pos).getDescription3());


            return listItem;
        }
    }
    // younes
    private class HomeTask extends AsyncTask<String, String, String> {
        Class activity;
        public HomeTask(Class pActivity) {
            super();
            activity=pActivity;
        }
        protected String doInBackground(String... param) {
            try {
                Intent myAccountServicesAct = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
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
                //   Toast.makeText(TransactionListActivityResult.this, param, Toast.LENGTH_SHORT).show();
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity().getApplicationContext()).create();
                alertDialog.setMessage(param);
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
        //    Greetmsg.setText(msg);
    }
    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}