package com.example.campusfoodexpress.vendor;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.campusfoodexpress.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ConfirmCancelOrderDialog {
    private Activity activity;
    private AlertDialog dialog;
    public Button btnYesOrder,btnNoOrder;
    private TextView txtMsgOrder,txtQuestionOrder;
    private String msg;
    private TextInputLayout textInputLayout;
    TextInputEditText textCancelReason;
    String reason;

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
        txtQuestionOrder = dialogView.findViewById(R.id.txtQuestionOrder);
        textInputLayout = dialogView.findViewById(R.id.textCancellationReasonLayout);
        textCancelReason = dialogView.findViewById(R.id.textCancelReason);
        txtMsgOrder.setText(msg);

        if(msg.contains("Accept") ){
//            txtQuestionOrder.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);
        }
        else if(msg.contains("ready")){
            txtQuestionOrder.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);
            btnYesOrder.setText("Yes");
            btnNoOrder.setText("No");
        }
        else if (msg.contains("paid")){
            txtQuestionOrder.setVisibility(View.GONE);
            textInputLayout.setVisibility(View.GONE);
            btnYesOrder.setText("Yes");
            btnNoOrder.setText("No");
        }
        else if (msg.contains("CANCEL") || msg.contains("DECLINE")){
            txtQuestionOrder.setVisibility(View.GONE);
            reason = String.valueOf(textCancelReason.getText());
            btnYesOrder.setText("Send");
            btnNoOrder.setText("Cancel");
        }




        builder.setView(dialogView);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void  dismissDialog(){
        dialog.dismiss();
    }
}
