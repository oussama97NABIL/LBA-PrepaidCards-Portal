package com.LBA.prepaidPortal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.LBA.prepaidPortal.R;

import com.LBA.prepaidPortal.widgets.fragment.Last10Transactions;
import com.LBA.tools.assets.Globals;


public class LastTransactionResult extends AbstractActivity  {
    private ListView lv;
    ImageButton canlBtn;
    ImageButton back;
    private int nCounter=0;
    private int[] bgcolor = {R.color.white,R.color.colorTransparentWhite,R.color.white,R.color.colorTransparentWhite,R.color.white};
    private String selectedAccount;
    static private final String TAG = LastTransactionResult.class.getSimpleName();
    TextView textView_heading;
     boolean wallet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_result);



        if(getIntent().hasExtra("type") && getIntent().getStringExtra("type").equals("wallet"))
            wallet = true;

        if((!wallet && Globals.transactionList!=null && Globals.transactionList.size()>0) || (wallet && Globals.transactionList!=null && Globals.transactionList.size()>0)) {
            lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(new VersionAdapter(LastTransactionResult.this));
        }


        textView_heading = (TextView) findViewById(R.id.textView_heading);
        back = (ImageButton) findViewById(R.id.Back);
        canlBtn = (ImageButton) findViewById(R.id.imageButton24);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
                HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });
        canlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgrees();
               HomeTask task = new HomeTask(HomeActivity.class);
                task.execute();
            }
        });

        /*Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/miloot_bold.otf");
        textView_heading.setTypeface(tf); omar

        Button button_sign_out = (Button) findViewById(R.id.button_sign_out);
        button_sign_out.setTypeface(tf);*/
    }
    private class HomeTask extends AsyncTask<String, String, String> {
        Class activity;
        public HomeTask(Class pActivity) {
            super();
            activity=pActivity;
        }
        protected String doInBackground(String... param) {
            try {
                Intent myAccountServicesAct = new Intent(LastTransactionResult.this, activity);
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
                androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(LastTransactionResult.this).create();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home_drawer, menu);
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
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public class VersionAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private Boolean gray = true;
        public VersionAdapter(LastTransactionResult activity) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
        }
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
                listItem = layoutInflater.inflate(R.layout.trxlist_item, null);
            }
            // Initialize the views in the layout
            ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
            TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
            //TextView tvDesc = (TextView) listItem.findViewById(R.id.desc);
            TextView tvAmount = (TextView) listItem.findViewById(R.id.amount_list);
            TextView tvTransaction = (TextView) listItem.findViewById(R.id.title);
            /*TextView tvCharge = (TextView) listItem.findViewById(R.id.amount2);
            TextView tvSub = (TextView) listItem.findViewById(R.id.sub);*/

            /*listItem.setBackgroundColor(gray ? getResources().getColor(R.color.trx_list_grey) : getResources().getColor(R.color.transparent)); //omar
            gray = !gray;*/

            //Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/miloot_bold.otf");
//            tvTitle.setTextColor(getResources().getColor(R.color.white));//+++
            //tvTitle.setTypeface(tf);
           // tvDesc.setTextColor(getResources().getColor(R.color.black));//+++
            //tvDesc.setTypeface(tf);
            // omar tvAmount.setTextColor(getResources().getColor(R.color.royalBank1));//+++
            //tvAmount.setTypeface(tf);

            // set the views in the layout
            if(!wallet) {
                if (Globals.transactionList.get(pos).getReferenceNumber() != null && Globals.transactionList.get(pos).getReferenceNumber().length() > 0) {
                    tvAmount.setTextColor(getResources().getColor(R.color.black));
                    tvAmount.setText(Globals.transactionList.get(pos).getReferenceNumber());
                    tvTitle.setText("Reference Number: ");
                } else
                if (Globals.transactionList.get(pos).getTransactionType() != null && Globals.transactionList.get(pos).getTransactionType().length() > 0) {
                    tvTransaction.setTextColor(getResources().getColor(R.color.MurasakiPurple));
                    tvTransaction.setText(Globals.transactionList.get(pos).getDate());
                }

                 else {
                    tvAmount.setTextColor(getResources().getColor(R.color.dark_grey_color));
                    tvAmount.setText("undefined");
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