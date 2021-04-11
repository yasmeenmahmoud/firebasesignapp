package com.example.firebasesignapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.FileTypeViewHolder> {
    private ArrayList<Block> dataSet;
    Context mContext;


    public BlockAdapter( Context context) {
        this.mContext = context;
    }

    public void setDataSet(ArrayList<Block> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public FileTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.block_item_layout, parent, false);
        return new BlockAdapter.FileTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FileTypeViewHolder holder, final int listPosition) {
        Block current_position = dataSet.get(listPosition);
        holder.blockAddress.setText(current_position.block_address);
        holder.blockNmae.setText(current_position.block_name);

    }

    @Override
    public int getItemCount() {
        if (dataSet==null)
            return 0;
        return dataSet.size();
    }

    public class FileTypeViewHolder extends RecyclerView.ViewHolder {
        TextView blockNmae, blockAddress;

        public FileTypeViewHolder(View itemView) {
            super(itemView);
            blockNmae = itemView.findViewById(R.id.block_name_txt);
            blockAddress = itemView.findViewById(R.id.block_address_txt);
        }
    }
}