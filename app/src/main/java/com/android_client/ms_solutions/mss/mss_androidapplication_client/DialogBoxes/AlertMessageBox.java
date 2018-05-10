package com.android_client.ms_solutions.mss.mss_androidapplication_client.DialogBoxes;

/**
 * Created by Walid Zhani @Walid.Zhy7 on 21/03/2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.android_client.ms_solutions.mss.mss_androidapplication_client.R;

public class AlertMessageBox {

    public static AlertMessageBoxIcon alertMessageBoxIcon;

    public static void Show(Context context, String title, String message, AlertMessageBoxIcon alertMessageBoxIcon) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        switch (alertMessageBoxIcon) {
            case Error:
                alertDialog.setIcon(R.drawable.error);
                break;
            case Info:
                alertDialog.setIcon(R.drawable.info);
                break;
        }
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }

    public static void Show(Context context, String title, String message, AlertMessageBoxIcon alertMessageBoxIcon, final AlertMessageBoxOkClickCallback okClickCallback) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        switch (alertMessageBoxIcon) {
            case Error:
                alertDialog.setIcon(R.drawable.error);
                break;
            case Info:
                alertDialog.setIcon(R.drawable.info);
                break;
        }
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                okClickCallback.run();
            }
        });

        alertDialog.show();
    }

}
