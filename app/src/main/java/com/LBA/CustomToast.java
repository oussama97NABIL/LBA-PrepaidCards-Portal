package com.LBA;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class CustomToast {
    public void Show_Toast(Context context, View view, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
