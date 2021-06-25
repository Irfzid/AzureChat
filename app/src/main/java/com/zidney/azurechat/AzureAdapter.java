package com.zidney.azurechat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zidney.azurechat.model.Read;
import com.zidney.azurechat.model.RootModel;

import java.util.ArrayList;
import java.util.List;

public class AzureAdapter extends RecyclerView.Adapter<AzureAdapter.ViewHolder> {

    ArrayList<Read> readlist = new ArrayList<>();
    String token;

    public AzureAdapter (ArrayList<Read>readlist, String token) {
        this.readlist = readlist;
        this.token = token;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        private TextView test;
        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public AzureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(readlist.get(viewType).equals(token))
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message2, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AzureAdapter.ViewHolder holder, int position) {
        holder.tv_msg.setText(readlist.get(position).getMsg());

    }

    @Override
    public int getItemCount() {
        return readlist.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_msg, tv_msg2;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_msg = itemView.findViewById(R.id.tv_msg);
            tv_msg2 = itemView.findViewById(R.id.tv_msg2);
        }
    }

//    @Override
//    public int getItemViewType(int position) {
//
//        if (readlist.get(position).equals(token))
//        {
//
//        }
//
//        return super.getItemViewType(position);
//    }
}
