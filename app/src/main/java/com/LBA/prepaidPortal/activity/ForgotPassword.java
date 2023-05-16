package com.LBA.prepaidPortal.activity;

import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.LBA.CustomToast;
import com.LBA.MainActivity;
import com.LBA.prepaidPortal.R;
import com.LBA.tools.assets.Globals;
import com.LBA.tools.services.User;
import com.google.android.material.button.MaterialButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AbstractActivity{


    EditText email;
    String getEmailId;

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
                if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
                    Toast.makeText(ForgotPassword.this, "Votre email n'est pas valide", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ForgotPassword.this, "le lien de rénitialisation est envoyé à votre email.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent login = new Intent(ForgotPassword.this, MainActivity.class);
        startActivity(login);
    }
}
