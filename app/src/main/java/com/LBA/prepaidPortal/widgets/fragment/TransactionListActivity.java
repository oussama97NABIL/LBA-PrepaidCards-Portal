package com.LBA.prepaidPortal.widgets.fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.LBA.prepaidPortal.R;
import com.LBA.prepaidPortal.activity.HomeActivity;
import com.LBA.prepaidPortal.activity.LastTransactionResult;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.misc.LastTransactionDetail;
import com.LBA.tools.misc.MySpinnerAdapter;
import com.LBA.tools.services.Account;
import com.LBA.tools.services.Transactions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



public class TransactionListActivity extends BaseFragment implements AdapterView.OnItemSelectedListener {
    private ListView lv;
    int position;
    ImageButton canlBtn;

    View mRootView;
    private int nCounter=0;
    private int[] bgcolor = {R.color.white,R.color.colorTransparentWhite,R.color.white,R.color.colorTransparentWhite,R.color.white};
    private String selectedAccount;
    static private final String TAG = LastTransactionResult.class.getSimpleName();
    TextView textView_heading;
    private boolean wallet = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_transaction_result, container, false);

        SpannableString s = new SpannableString("Les dernières transactions");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getActivity().setTitle(s);

        initProgrees();
        new CustomTask().execute();

        textView_heading = (TextView) mRootView.findViewById(R.id.textView_heading);

        canlBtn = (ImageButton) mRootView.findViewById(R.id.imageButton24);

        canlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
               HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });

        return mRootView;
    }
    private class HomeTask extends AsyncTask<String, String, String> {
        Class activity;
        public HomeTask(Class pActivity) {
            super();
            activity=pActivity;
        }
        protected String doInBackground(String... param) {
            try {
                Intent myAccountServicesAct = new Intent(getActivity().getApplicationContext(), activity);
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
                //   Toast.makeText(CardLimitActivity.this, param, Toast.LENGTH_SHORT).show();
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
    private class CustomTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... param) {
            try {
                Transactions.GetLast10Transactions();
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
            else {
                if((!wallet && Globals.transactionList!=null && Globals.transactionList.size()>0) || (wallet && Globals.transactionList!=null && Globals.transactionList.size()>0)) {
                    lv = (ListView) mRootView.findViewById(R.id.listView);
                    lv.setAdapter(new VersionAdapter(getActivity().getApplicationContext()));

                }
            }

            if(param!=null)
                Toast.makeText(getActivity().getApplicationContext(), param, Toast.LENGTH_LONG).show();


        }
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    class VersionAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private Boolean gray = true;

        Context context;
        public VersionAdapter(Context c) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        //-----------------------------------------------------------

        //---------------------------------------------------------
        public int getCount() {
            // TODO Auto-generated method stub
            if(!wallet)
                return Globals.transactionList.size();
            else
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
                listItem = LayoutInflater.from((getContext())).inflate(R.layout.transactions_item,parent , false);

            }

            // Initialize the views in the layout

            //TextView tvTitleRef = (TextView) listItem.findViewById(R.id.titleRef);
            //TextView tvDesc = (TextView) listItem.findViewById(R.id.desc);
            TextView tvAmount = (TextView) listItem.findViewById(R.id.amount);
            TextView tvValRef = (TextView) listItem.findViewById(R.id.valRef);
            TextView tvDate = (TextView) listItem.findViewById(R.id.thumb);
            TextView tvDate1 = (TextView) listItem.findViewById(R.id.date);
            TextView location = (TextView) listItem.findViewById(R.id.location);
            TextView tvTransType = (TextView) listItem.findViewById(R.id.transaction_type);
            if(!wallet) {
                if (Globals.transactionList.get(pos).getReferenceNumber() != null && Globals.transactionList.get(pos).getReferenceNumber().length() > 0) {
                    tvValRef.setTextColor(getResources().getColor(R.color.Black));
                    tvValRef.setText(Globals.transactionList.get(pos).getReferenceNumber());
                }
                if (Globals.transactionList.get(pos).getDate() != null && Globals.transactionList.get(pos).getDate().length() > 0) {
                    tvDate1.setTextColor(getResources().getColor(R.color.GoldenFoil));
                    tvDate1.setText(Globals.transactionList.get(pos).getDate());
                }
                if (Globals.transactionList.get(pos).getAmount() != null && Globals.transactionList.get(pos).getAmount().length() > 0) {
                    tvAmount.setTextColor(getResources().getColor(R.color.Black));
                    tvAmount.setText(Globals.transactionList.get(pos).getAmount());
                }
                if (Globals.transactionList.get(pos).getAmount() != null && Globals.transactionList.get(pos).getAmount().length() > 0) {
                    location.setTextColor(getResources().getColor(R.color.Black));
                    location.setText(Globals.transactionList.get(pos).getLocation());
                }
               /* if (Globals.transactionList.get(pos).getTransactionType() != null && Globals.transactionList.get(pos).getTransactionType().length() > 0) {
                    tvTransType.setTextColor(getResources().getColor(R.color.Black));
                    tvTransType.setText(Globals.transactionList.get(pos).getTransactionType());
                }*/

                else {
                    tvDate.setTextColor(getResources().getColor(R.color.dark_grey_color));
                    tvDate.setText("undefined");
                }

            }
            else {
                String amount = Globals.transactionList.get(pos).getAmount(), date = Globals.transactionList.get(pos).getDate(), charge = Globals.transactionList.get(pos).getLocation(), desc = Globals.transactionList.get(pos).getCurrency();
                if(amount.equals("0.00")){
                    tvAmount.setVisibility(View.GONE);
                }
                else{
                    tvAmount.setText(amount);
                }
                //tvDesc.setText(desc);

                /*if(!charge.equals("0.00")){
                    tvCharge.setVisibility(View.VISIBLE);
                    tvCharge.setText("Charge : "+charge);
                }*/
            }
            // listItem.setBackgroundColor(getResources().getColor(bgcolor[pos]));//+++
            return listItem;
        }
    }

}
