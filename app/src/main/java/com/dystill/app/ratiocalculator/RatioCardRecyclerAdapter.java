package com.dystill.app.ratiocalculator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

public class RatioCardRecyclerAdapter extends
        RecyclerView.Adapter<RatioCardRecyclerAdapter.RatioCardHolder> {

    private LayoutInflater inflater;
    private List<double[]> ratioList;
    private char changeFlag = ' ';

    public RatioCardRecyclerAdapter(Context context, List<double[]> rList) {
        this.inflater = LayoutInflater.from(context);
        ratioList = rList;
    }

    @Override
    public RatioCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("onCreateViewHolder", "Started");
        View view = inflater.inflate(R.layout.card_ratiotable, parent, false);
        return new RatioCardHolder(view);
    }

    @Override
    public void onBindViewHolder(RatioCardHolder holder, final int position) {
        Log.v("onBindViewHolder", "Started");

        /*
        Log.v("onBindViewHolder", "Create Text");
        String textA = "" + ratioList.get(position)[0];
        String textB = "" + ratioList.get(position)[1];
        String textC = "" + ratioList.get(position)[2];
        String textD = "" + ratioList.get(position)[3];

        switch (changeFlag) {
            case 'A':
                holder.topLeft.setText(textA);
                break;
            case 'B':
                holder.bottomLeft.setText(textB);
                break;
            case 'C':
                holder.topRight.setText(textC);
                break;
            case 'D':
                holder.bottomRight.setText(textD);
                break;
            default:
                // set the text fo each text field
                Log.v("onBindViewHolder", "Set Text");
                holder.topLeft.setText(textA);
                holder.bottomLeft.setText(textB);
                holder.topRight.setText(textC);
                holder.bottomRight.setText(textD);
        }
        /**/
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


            // when changing the value of A, update the value of C
            topLeft.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    Log.v("afterTextChanged", "topLeft" + position);
                    ratioList.get(position)[0] = Double.parseDouble(editable.toString());
                    ratioList.get(position)[2] = solveRatioForTop(ratioList.get(position)[0],
                            ratioList.get(position)[1], ratioList.get(position)[3]);
                    Log.v("afterTextChanged", "C = " + ratioList.get(position)[2]);
                    String textC = "" + ratioList.get(position)[2];
                    if(topLeft.hasFocus()) {
                        changeFlag = 'C';
                        topRight.setText(textC);
                    }
                }
            });

            // when changing the value of B, update the value of D
            bottomLeft.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    Log.v("afterTextChanged", "bottomLeft" + position);
                    ratioList.get(position)[1] = Double.parseDouble(editable.toString());
                    ratioList.get(position)[3] = solveRatioForBottom(ratioList.get(position)[0],
                            ratioList.get(position)[1], ratioList.get(position)[2]);
                    String textD = "" + ratioList.get(position)[3];
                    if(bottomLeft.hasFocus()) {
                        changeFlag = 'D';
                        bottomRight.setText(textD);
                    }
                }
            });

            // when changing the value of C, update the value of D
            topRight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    Log.v("afterTextChanged", "topRight" + position);
                    ratioList.get(position)[2] = Double.parseDouble(editable.toString());
                    ratioList.get(position)[3] = solveRatioForBottom(ratioList.get(position)[0],
                            ratioList.get(position)[1], ratioList.get(position)[2]);
                    String textD = "" + ratioList.get(position)[3];
                    if(topRight.hasFocus()) {
                        changeFlag = 'D';
                        bottomRight.setText(textD);
                    }
                }
            });

            // when changing the value of D, update the value of C
            bottomRight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    Log.v("afterTextChanged", "bottomRight" + position);
                    ratioList.get(position)[3] = Double.parseDouble(editable.toString());
                    ratioList.get(position)[2] = solveRatioForTop(ratioList.get(position)[0],
                            ratioList.get(position)[1], ratioList.get(position)[3]);
                    String textD = "" + ratioList.get(position)[2];
                    if(bottomRight.hasFocus()) {
                        changeFlag = 'D';
                        topRight.setText(textD);
                    }
                }
            });
        }

        // A:B::C:D
        private double solveRatioForTop(double knownTop, double knownBottom, double unknownBottom) {
            if(knownBottom != 0)
                return (knownTop * unknownBottom) / knownBottom;
            else
                return knownTop;
        }

        // A:B::C:D
        private double solveRatioForBottom(double knownTop, double knownBottom, double unknownTop) {
            if(knownTop != 0)
                return (knownBottom * unknownTop) / knownTop;
            else
                return knownBottom;
        }

    }
}
