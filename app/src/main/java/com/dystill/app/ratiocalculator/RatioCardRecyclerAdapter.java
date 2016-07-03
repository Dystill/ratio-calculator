package com.dystill.app.ratiocalculator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.List;

public class RatioCardRecyclerAdapter extends
        RecyclerView.Adapter<RatioCardRecyclerAdapter.RatioCardHolder> {

    private final LayoutInflater inflater;
    private final List<double[]> ratioList;
    DecimalFormat decimalFormat = new DecimalFormat("#.####");

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
    public void onBindViewHolder(final RatioCardHolder holder, int position) {
        Log.v("onBindViewHolder", "Started");

        Log.v("onBindViewHolder", "Create Text");
        String textA = decimalFormat.format(ratioList.get(position)[0]);
        String textB = decimalFormat.format(ratioList.get(position)[1]);
        String textC = decimalFormat.format(ratioList.get(position)[2]);
        String textD = decimalFormat.format(ratioList.get(position)[3]);

        Log.v("onBindViewHolder", "Set Text");
        if(!(holder.topLeft.hasFocus() || holder.bottomLeft.hasFocus() ||
                holder.topRight.hasFocus() || holder.bottomRight.hasFocus())) {
            holder.topLeft.setText(textA);
            holder.bottomLeft.setText(textB);
            holder.topRight.setText(textC);
            holder.bottomRight.setText(textD);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ratioList.size();
    }

    private void removeAt(int position) {
        MainActivity.removeRatio(position);
        notifyDataSetChanged();
    }

    protected class RatioCardHolder extends RecyclerView.ViewHolder {

        EditText topLeft, topRight, bottomLeft, bottomRight;
        Button delete;

        public RatioCardHolder(View itemView) {
            super(itemView);
            topLeft = (EditText) itemView.findViewById(R.id.numberTopLeft);
            topRight = (EditText) itemView.findViewById(R.id.numberTopRight);
            bottomLeft = (EditText) itemView.findViewById(R.id.numberBottomLeft);
            bottomRight = (EditText) itemView.findViewById(R.id.numberBottomRight);
            delete = (Button) itemView.findViewById(R.id.delete_button);

            // when changing the value of A, update the value of C
            topLeft.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    Log.v("afterTextChanged", "Changing A" + position);

                    try {
                        ratioList.get(position)[0] = Double.parseDouble(editable.toString());
                    } catch(Exception e) {
                        ratioList.get(position)[0] = 0.0;
                    }

                    if(ratioList.get(position)[1] != 0)
                        ratioList.get(position)[2] = solveRatioForTop(ratioList.get(position)[0],
                            ratioList.get(position)[1], ratioList.get(position)[3]);

                    Log.v("afterTextChanged", "A = " + ratioList.get(position)[0]);
                    Log.v("afterTextChanged", "C = " + ratioList.get(position)[2]);
                    if(topLeft.hasFocus()) {
                        Log.v("afterTextChanged", "has focus");
                        topRight.setText(decimalFormat.format(ratioList.get(position)[2]));
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
                    Log.v("afterTextChanged", "Changing B" + position);

                    try {
                        ratioList.get(position)[1] = Double.parseDouble(editable.toString());
                    } catch(Exception e) {
                        ratioList.get(position)[1] = 0.0;
                    }

                    if(ratioList.get(position)[0] != 0)
                        ratioList.get(position)[3] = solveRatioForBottom(ratioList.get(position)[0],
                            ratioList.get(position)[1], ratioList.get(position)[2]);

                    Log.v("afterTextChanged", "B = " + ratioList.get(position)[1]);
                    Log.v("afterTextChanged", "D = " + ratioList.get(position)[3]);
                    if(bottomLeft.hasFocus()) {
                        Log.v("afterTextChanged", "has focus");
                        bottomRight.setText(decimalFormat.format(ratioList.get(position)[3]));
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
                    Log.v("afterTextChanged", "Changing C" + position);

                    try {
                        ratioList.get(position)[2] = Double.parseDouble(editable.toString());
                    } catch(Exception e) {
                        ratioList.get(position)[2] = 0.0;
                    }

                    if(ratioList.get(position)[0] != 0)
                        ratioList.get(position)[3] = solveRatioForBottom(ratioList.get(position)[0],
                            ratioList.get(position)[1], ratioList.get(position)[2]);
                    Log.v("afterTextChanged", "C = " + ratioList.get(position)[2]);
                    Log.v("afterTextChanged", "D = " + ratioList.get(position)[3]);
                    if(topRight.hasFocus()) {
                        Log.v("afterTextChanged", "has focus");
                        bottomRight.setText(decimalFormat.format(ratioList.get(position)[3]));
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
                    Log.v("afterTextChanged", "Changing D" + position);

                    try {
                        ratioList.get(position)[3] = Double.parseDouble(editable.toString());
                    } catch(Exception e) {
                        ratioList.get(position)[3] = 0.0;
                    }

                    if(ratioList.get(position)[1] != 0)
                        ratioList.get(position)[2] = solveRatioForTop(ratioList.get(position)[0],
                            ratioList.get(position)[1], ratioList.get(position)[3]);

                    Log.v("afterTextChanged", "D = " + ratioList.get(position)[3]);
                    Log.v("afterTextChanged", "C = " + ratioList.get(position)[2]);
                    if(bottomRight.hasFocus()) {
                        Log.v("afterTextChanged", "has focus");
                        topRight.setText(decimalFormat.format(ratioList.get(position)[2]));
                    }
                }
            });
        }

        // A:B::C:D
        private double solveRatioForTop(double knownTop, double knownBottom, double unknownBottom) {
            Log.v("solveRatioForTop", "Solving Ratio for Top value");
            Log.v("solveRatioForTop", "Result: " + (knownTop * unknownBottom) / knownBottom);
            return (knownTop * unknownBottom) / knownBottom;
        }

        // A:B::C:D
        private double solveRatioForBottom(double knownTop, double knownBottom, double unknownTop) {
            Log.v("solveRatioForBottom", "Solving Ratio for Bottom value");
            Log.v("solveRatioForBottom", "Result: " + (knownBottom * unknownTop) / knownTop);
            return (knownBottom * unknownTop) / knownTop;
        }

    }
}
