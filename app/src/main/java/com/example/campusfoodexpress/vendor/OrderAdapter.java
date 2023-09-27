package com.example.campusfoodexpress.vendor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusfoodexpress.R;
import com.example.campusfoodexpress.WelcomeActivity;
import com.example.campusfoodexpress.dialogs.InteractiveDialog;
import com.example.campusfoodexpress.dialogs.LoadingDialog;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import database.DatabaseHelper;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private Context context;
    private List<OrderDetails> orderList;
    Activity activity;
    private View.OnClickListener onClickListener;
    private String username;

    public OrderAdapter(Context context, List<OrderDetails> orderList, Activity activity) {
        this.context = context;
        this.orderList = orderList;
        this.activity = activity;
    }

    public OrderAdapter(List<OrderDetails> orders) {
        this.orderList = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.order_layout,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        OrderDetails order = orderList.get(position);
        holder.txtOrderNumber.setText(String.valueOf(order.getOrderID()));
        holder.txtCustFnameLname.setText( String.valueOf(order.getCustomerName() + ", " + order.getCustomerSurname() + ", " + order.getCustomerPhone()));
        String[] time = String.valueOf(order.getTime()).split("T");
        holder.txtTime.setText(time[1]);
        Double amount = order.getAmountDue();
        Locale southAfricaLocale = new Locale("en", "ZA");

        // Create a NumberFormat instance for currency formatting
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(southAfricaLocale);

        // Set the currency to South African Rand
        currencyFormatter.setCurrency(Currency.getInstance("ZAR"));

        // Format the double as currency
        String formattedAmount = currencyFormatter.format(amount);
        holder.txtTotal.setText(formattedAmount);
        holder.txtOrderStatus.setText(String.valueOf(order.getOrderStatus()));
        if(order.getOrderStatus().equalsIgnoreCase("pending")){
            holder.txtOrderStatus.setTextColor(Color.parseColor("#FF0000"));
        }else if(order.getOrderStatus().equalsIgnoreCase("confirmed")){
            holder.btnAcceptOrder.setText("Update status");
            holder.btnAcceptOrder.setBackgroundColor(Color.parseColor("#2c2cff"));
            holder.txtOrderStatus.setText("Confirmed");
            holder.txtOrderStatus.setTextColor(Color.parseColor("#2DB83D"));
        }



        holder.layoutFoodItemsXQuantity.removeAllViews();

        // Iterate through the food list and add TextViews
        for (int i = 0; i < order.getFoodList().size(); i++) {
            TextView textView = new TextView(context);
            String formattedAmountPerItem = currencyFormatter.format(order.getFoodList().get(i).amountToPay);
            textView.setText(order.getFoodList().get(i).foodName + " x " + order.getFoodList().get(i).qty + " : " + formattedAmountPerItem);
            // Customize the TextView's appearance here if needed
             textView.setTextColor(Color.BLACK);
             textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            // Add the TextView to the linearLayoutFoodItemsXQuantity
            holder.layoutFoodItemsXQuantity.addView(textView);
        }

        holder.btnAcceptOrder.setOnClickListener(v -> {
            ConfirmCancelOrderDialog interactiveDialog = new ConfirmCancelOrderDialog(activity,"You're about to Accept an order...");
            interactiveDialog.startInteractiveDialog();
            interactiveDialog.btnYesOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Dismiss the confirmation dialog
                    interactiveDialog.dismissDialog();
                    // Show the loading dialog
                    LoadingDialog loadingDialog = new LoadingDialog(activity, "Confirming order...");
                    loadingDialog.startLoadingDialog();
                    DatabaseHelper dbHelper = new DatabaseHelper(context);
                    boolean isOrderUpdated = dbHelper.updateOrderStatus(order.customerName,order.customerSurname,order.customerPhone,
                            order.time,activity.getIntent().getStringExtra("username"),String.valueOf(order.getOrderID()));

//                    Intent intent = new Intent(activity, WelcomeActivity.class);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Clear input fields
                            if(isOrderUpdated){
                                loadingDialog.dismissDialog();
                                Toast.makeText(v.getContext(),"Order confirmed successfully!",Toast.LENGTH_LONG).show();
                                holder.btnAcceptOrder.setText("Update status");
                                holder.btnAcceptOrder.setBackgroundColor(Color.parseColor("#2c2cff"));
                                holder.txtOrderStatus.setText("Confirmed");
                                holder.txtOrderStatus.setTextColor(Color.parseColor("#2DB83D"));
                            }
                        }
                    }, 3000);
                }
            });

            interactiveDialog.btnNoOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // User clicked "No," dismiss the confirmation dialog
                    interactiveDialog.dismissDialog();
                }
            });

        });



    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public  class  OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderNumber,txtOrderStatus,txtTotal,txtCustFnameLname,txtTime;

        Button btnAcceptOrder, btnDeclineOrder;
        androidx.cardview.widget.CardView mainLayout;
        LinearLayout layoutFoodItemsXQuantity;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderNumber = itemView.findViewById(R.id.txtOrderId);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtCustFnameLname = itemView.findViewById(R.id.txtCustFnameLname);
            txtTime = itemView.findViewById(R.id.txtTime);
            btnAcceptOrder = itemView.findViewById(R.id.btnAccept);
            btnDeclineOrder = itemView.findViewById(R.id.btnCancelOrder);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            layoutFoodItemsXQuantity = itemView.findViewById(R.id.linearLayoutFoodItemsXqty);


        }
    }

}
