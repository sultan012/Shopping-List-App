package com.hotmail.a_asultan.androidshoppinglistaapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ItemAdapter extends  RecyclerView.Adapter<ItemAdapter.ListItemHolder> {

    private List<Item> mItemList;
    private MainActivity mMainActivity;
    int countItem = 0;

    public ItemAdapter(MainActivity mainActivity, List<Item> itemList){
        mMainActivity = mainActivity;
        mItemList = itemList;
    }

    @NonNull
    @Override
    public ItemAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ListItemHolder holder, int position) {

        final Item item = mItemList.get(position);
        final View itemView2 = holder.itemView;

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                item.mChecked = !item.mChecked;

                if(item.mChecked){
                    item.setChecked(true);
                     countItem++;
                    itemView2.setBackgroundColor(Color.GREEN);

                    ShowToast(countItem);
                }
                else{
                    item.setChecked(false);
                    countItem--;
                    itemView2.setBackgroundColor(Color.WHITE);
                    ShowToast(countItem);
                }
            }
        });
        holder.mName.setText(item.getName());
    }

   public void ShowToast(int countItem){
       Context context = mMainActivity.getApplicationContext();

       CharSequence text = "Du har köpt " + countItem + " av " + getItemCount();
       int duration = Toast.LENGTH_SHORT;
       Toast toast = Toast.makeText(context, text, duration);
       toast.show();

   }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void removeItem(int position){
        mItemList.remove(position);
        notifyDataSetChanged();
    }

    public class ListItemHolder extends
            RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView mName;


        public ListItemHolder(View view){
            super(view);
            mName = view.findViewById(R.id.textViewName);
            
            view.setClickable(true);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
         //  mMainActivity.delete(getAdapterPosition());

        }

    }

}
