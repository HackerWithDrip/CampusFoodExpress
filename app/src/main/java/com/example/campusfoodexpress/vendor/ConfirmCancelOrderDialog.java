package com.example.campusfoodexpress.vendor;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.campusfoodexpress.R;

public class ConfirmCancelOrderDialog {
    private Activity activity;
    private AlertDialog dialog;
    public Button btnYesOrder,btnNoOrder;
    private TextView txtMsgOrder;
    private String msg;

    public ConfirmCancelOrderDialog(Activity activity, String textMsg) {
        this.activity = activity;
        this.msg = textMsg;
    }

    public void  startInteractiveDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.confirm_delete_dialog, null);
        btnYesOrder = dialogView.findViewById(R.id.btnYesOrder);
        btnNoOrder = dialogView.findViewById(R.id.btnNoOrder);
        txtMsgOrder = dialogView.findViewById(R.id.txtMsgOrder);

        builder.setView(dialogView);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void  dismissDialog(){
        dialog.dismiss();
    }
}
