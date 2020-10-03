package com.group11.rentacaradmin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Questionlist extends ArrayAdapter<Datain> {





    private Activity context;
    private List<Datain>  questionlist;
    public Questionlist(Activity context, List<Datain> questionlist){

       super(context, R. layout.list_layouts, questionlist);
        this.context = context;
        this.questionlist = questionlist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listVieItem = inflater.inflate(R.layout.list_layouts, null,true);

        TextView textView3 = (TextView) listVieItem.findViewById(R.id.textView3);
        TextView textView4 = (TextView) listVieItem.findViewById(R.id.textView4);

        Datain datain = questionlist.get(position);

        textView3.setText(datain.getQus());
        textView4.setText(datain.getAns());

        return listVieItem;
    }




}
