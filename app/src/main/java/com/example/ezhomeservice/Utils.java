package com.example.ezhomeservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import java.sql.Time;
import java.util.Calendar;

public class Utils {


    private static ProgressDialog progressDialog;

    public static void showLoadingDialog(Context context, boolean cancelable) {
        if (progressDialog != null && progressDialog.isShowing()) {
            // A dialog is already showing, no need to create a new one
            return;
        }

        progressDialog = new ProgressDialog(context);

        if (context != null) progressDialog.show();

        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(cancelable);
        progressDialog.setCanceledOnTouchOutside(cancelable);
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public static void hideLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static String Greeting() {
        Calendar c;
        int time;
        String greeting;
        c = Calendar.getInstance();
        time = c.get(Calendar.HOUR_OF_DAY);
        if (time >= 6 && time <= 11) {
            greeting = "Good Morning";
        } else if (time >= 11 && time <= 16) {
            greeting = "Good Noon";
        } else if (time >= 16 && time < 18) {
            greeting = "Good Evening";
        } else {
            greeting = "Good Night";
        }
        return greeting;
    }
}


