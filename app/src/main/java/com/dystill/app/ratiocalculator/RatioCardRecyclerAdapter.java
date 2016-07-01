package com.dystill.app.ratiocalculator;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class RatioCardRecyclerAdapter extends
        RecyclerView.Adapter<RatioCardRecyclerAdapter.RatioCardHolder> {

    private LayoutInflater inflater;
    private List<double[]> ratioList;

    public RatioCardRecyclerAdapter(Context context, List<double[]> rList) {
        this.inflater = LayoutInflater.from(context);
        ratioList = rList;
    }

    @Override
    public RatioCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_ratiotable, parent, false);
        return new RatioCardHolder(view);
    }

    @Override
    public void onBindViewHolder(RatioCardHolder holder, int position) {

        // create string placeholders for each number
        String text1 = "" + ratioList.get(position)[0];
        String text2 = "" + ratioList.get(position)[1];
        String text3 = "" + ratioList.get(position)[2];
        String text4 = "" + ratioList.get(position)[3];

        // set the text fo each text field
        holder.topLeft.setText(text1);
        holder.topRight.setText(text2);
        holder.bottomLeft.setText(text3);
        holder.bottomRight.setText(text4);

    }

    @Override
    public int getItemCount() {
        return ratioList.size();
    }

    public void removeAt(int position) {
        MainActivity.removeRatio(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ratioList.size());
    }

    protected class RatioCardHolder extends RecyclerView.ViewHolder {

        EditText topLeft, topRight, bottomLeft, bottomRight;

        public RatioCardHolder(View itemView) {
            super(itemView);
            topLeft = (EditText) itemView.findViewById(R.id.numberTopLeft);
            topRight = (EditText) itemView.findViewById(R.id.numberTopRight);
            bottomLeft = (EditText) itemView.findViewById(R.id.numberBottomLeft);
            bottomRight = (EditText) itemView.findViewById(R.id.numberBottomRight);
        }
    }
}
