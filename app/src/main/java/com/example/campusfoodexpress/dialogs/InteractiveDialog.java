package com.example.campusfoodexpress.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.campusfoodexpress.R;

public class InteractiveDialog {
    private Activity activity;
    private AlertDialog dialog;
    public Button btnYes,btnCancel;

    public InteractiveDialog(Activity activity) {
        this.activity = activity;
    }

    public void  startInteractiveDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.interactive_dialog, null);
        btnYes = dialogView.findViewById(R.id.btnYes);
        btnCancel = dialogView.findViewById(R.id.btnNo);

        builder.setView(dialogView);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void  dismissDialog(){
        dialog.dismiss();
    }
}
