package com.example.campusfoodexpress.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.campusfoodexpress.R;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;
    private String loadingMessage;

    public LoadingDialog(Activity myActivity, String message){
        activity = myActivity;
        loadingMessage = message;
    }

    public void  startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.loading_dialog, null);


        TextView txtLoadingMsg = dialogView.findViewById(R.id.txtLoadingMsg);
        txtLoadingMsg.setText(loadingMessage);
        builder.setView(dialogView);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void  dismissDialog(){
        dialog.dismiss();
    }

    public void showSuccessMessage(String successMessage) {
        if (dialog != null && dialog.isShowing()) {
            ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
            ImageView checkBoxImage = dialog.findViewById(R.id.checkBoxImage);
            TextView txtLoadingMsg = dialog.findViewById(R.id.txtLoadingMsg);

            progressBar.setVisibility(View.GONE); // Hide progress bar
            checkBoxImage.setVisibility(View.VISIBLE); // Show checkbox
            txtLoadingMsg.setText(successMessage); // Set the success message dynamically

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissDialog();
                }
            }, 5000); // Adjust the duration as needed

        }

    }

}
