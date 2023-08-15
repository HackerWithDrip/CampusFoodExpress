package com.example.campusfoodexpress.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.campusfoodexpress.R;

public class InteractiveDialog {
    private Activity activity;
    private AlertDialog dialog;

    public InteractiveDialog(Activity activity) {
        this.activity = activity;
    }

    public void  startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.loading_dialog, null);


        TextView txtLoadingMsg = dialogView.findViewById(R.id.txtLoadingMsg);

        builder.setView(dialogView);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void  dismissDialog(){
        dialog.dismiss();
    }
}
