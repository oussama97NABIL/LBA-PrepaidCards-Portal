package com.LBA.prepaidPortal.activity;

import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.LBA.MainActivity;
import com.LBA.prepaidPortal.R;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.User;
import com.google.android.material.button.MaterialButton;

public class ForgotPassword extends AbstractActivity{


    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);


        MaterialButton resetbtn = (MaterialButton) findViewById(R.id.Resetbtn);

        email = (EditText) findViewById(R.id.email);

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(ForgotPassword.this, "Veuiller entrer votre email", Toast.LENGTH_SHORT).show();
                    return;
                }
               else{
                    Toast.makeText(ForgotPassword.this, "Le lien est envoyé à votre email", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent login = new Intent(ForgotPassword.this, MainActivity.class);
        startActivity(login);
    }
}
