package com.yeyintlwin.musicsstore.ui.widget.acknowledgement;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.webkit.WebView;

public class LicensesDialog {

    private AlertDialog.Builder builder;

    private LicensesDialog(Context context) {
        setup(context);
    }

    public static LicensesDialog getInstance(Context context) {
        return new LicensesDialog(context);
    }

    private void setup(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Licenses");
        WebView webView = new WebView(context);
        webView.loadUrl("file:///android_asset/licenses.html");
        builder.setView(webView);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface p1, int p2) {
                p1.dismiss();
            }
        });

    }

    public void show() {
        builder.show();
    }
}
