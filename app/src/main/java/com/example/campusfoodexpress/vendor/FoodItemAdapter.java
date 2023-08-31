package com.example.campusfoodexpress.vendor;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusfoodexpress.R;

import java.util.List;

public class FoodItemAdapter  extends RecyclerView.Adapter<FoodItemAdapter.MyViewHolder>  {
    private Context context;
    private List<FoodItem> foodItems;
    Activity activity;
    Boolean switchOn = false;
    private View.OnClickListener onClickListener;

    public FoodItemAdapter(Context context, List<FoodItem> foodItems, Activity activity) {
        this.context = context;
        this.foodItems = foodItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FoodItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemAdapter.MyViewHolder holder, int position) {
        FoodItem foodItem = foodItems.get(position);
        holder.foodItemName.setText(String.valueOf(foodItem.getFoodItemName()));
        holder.btnSwitch.setChecked(foodItem.getSwitchState());
        String fooItemNameString = foodItem.getFoodItemName().toLowerCase();
        if (!fooItemNameString.equals("")) {
            switch(fooItemNameString) {
                case "burger":
                    holder.avatar.setImageResource(R.drawable.burger);
                    break;
                case "grilled chicken":
                    holder.avatar.setImageResource(R.drawable.grilled_chicken);
                    break;
                case "wings & chips":
                    holder.avatar.setImageResource(R.drawable.wings);
                    break;
                case "pie":
                    holder.avatar.setImageResource(R.drawable.pie);
                    break;
                case "kota":
                    holder.avatar.setImageResource(R.drawable.kota);
                    break;
                case "fish & chips":
                    holder.avatar.setImageResource(R.drawable.fish_chips);
                    break;
                case "stuff for the og":
                    holder.avatar.setImageResource(R.drawable.for_the_ogs);
                    break;
                default:
                    holder.avatar.setImageResource(R.drawable.circle_background);
            }
        }

        foodItem.setSwitchState(holder.btnSwitch.isChecked());
    }
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public  class  MyViewHolder extends RecyclerView.ViewHolder {
        TextView foodItemName;
        ImageView avatar;
        Switch btnSwitch;
        androidx.cardview.widget.CardView mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodItemName = itemView.findViewById(R.id.txtFoodItemName);
            avatar = itemView.findViewById(R.id.avatar);
            btnSwitch = itemView.findViewById(R.id.btnSwitch);
            mainLayout = itemView.findViewById(R.id.mainLayout);


        }
    }
}
