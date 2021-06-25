package com.zidney.azurechat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zidney.azurechat.model.Read;
import com.zidney.azurechat.model.RootModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AzureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Read> readlist = new ArrayList<>();
    String token;

    public AzureAdapter (ArrayList<Read>readlist, String token) {
        this.readlist = readlist;
        this.token = token;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        private TextView tvmessage;
        public ViewHolder1(@NonNull View itemView) {
            super(itemView);

            tvmessage = itemView.findViewById(R.id.tv_msg);
        }
        public TextView getTvmessage() {return tvmessage;}
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        private TextView tvmessage2;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

            tvmessage2 = itemView.findViewById(R.id.tv_msg2);
        }
        public TextView getTvmessage2() { return tvmessage2;}
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
                return new ViewHolder1(view);
            
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message2, parent, false);
                return new ViewHolder2(view);
        }return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case 0:
                ViewHolder1 viewHolder1 = (ViewHolder1) holder;
                viewHolder1.getTvmessage().setText(readlist.get(position).getMsg());
                break;


            case 1:
                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                viewHolder2.getTvmessage2().setText(readlist.get(position).getMsg());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return readlist.size();
    }


    @Override
    public int getItemViewType(int position) {
        int p;

        if(readlist.get(position).getFrom().equals(token))
        {
            p = 1;
        }
        else {
            p = 0;
        }
        return p;
    }
}
