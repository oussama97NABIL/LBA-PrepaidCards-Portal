package com.LBA.prepaidPortal.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.LBA.prepaidPortal.R;
//import com.LBA.prepaidPortal.widgets.fragment.CardInformation;

import com.LBA.prepaidPortal.widgets.fragment.AccountToCard;
import com.LBA.prepaidPortal.widgets.fragment.CardInformation1;
import com.LBA.prepaidPortal.widgets.fragment.CardInformationHin;
import com.LBA.prepaidPortal.widgets.fragment.CardLimit;
import com.LBA.prepaidPortal.widgets.fragment.CardOperation;
import com.LBA.prepaidPortal.widgets.fragment.CardToCard;

import com.LBA.prepaidPortal.widgets.fragment.GetBalance;
import com.LBA.prepaidPortal.widgets.fragment.Last10Transactions;
import com.google.android.material.navigation.NavigationView;
import com.LBA.prepaidPortal.widgets.fragment.HomeFragment;

public class HomeActivity extends AbstractActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initWidget();

        displaySelectedFragment(R.id.home_fragment);

    }



    /**
     * Initialize the ui widgets
     */
    private void initWidget() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View header = navigationView.getHeaderView(0);


        TextView statusText = (TextView) header.findViewById(R.id.status_text);
        statusText.setText("status");

        TextView currentInterfaceText = (TextView) header.findViewById(R.id.current_interface_text);
        currentInterfaceText.setText("interface");

        TextView additionalInfoText = (TextView) header.findViewById(R.id.additional_info_text);
        additionalInfoText.setText("plus info");
    }

    /**
     * Display the fragment selected from navigation drawer
     *
     * @param id id of selected item
     */
    private void displaySelectedFragment(final int id) {
        Fragment fragment = null;
// 3/28/2023       if (id == R.id.account_services) {
//            fragment = new LibraryInfoFragment();
//        }

        if (id == R.id.home_fragment) {
            fragment = new HomeFragment();
        }
        else if (id == R.id.card_information){
            fragment = new CardInformation1();
        }
        else if (id == R.id.generate_statement){
            fragment = new GetBalance();
        }
        else if (id == R.id.last_transaction){
            fragment = new Last10Transactions();
        }
        else if (id == R.id.account_card){
            fragment = new AccountToCard();
        }
        else if (id == R.id.card_card){
            fragment = new CardToCard();
        }
        else if (id == R.id.card_operation){
            fragment = new CardOperation();
        }
        else if (id == R.id.card_limit){
            fragment = new CardLimit();
        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }






    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedFragment(item.getItemId());
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//    public NfcProvider getNfcProvider() {
//        return new NfcProvider(this);
//    }
}
