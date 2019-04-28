package com.example.imher2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Spinner_Adapter  extends ArrayAdapter<Spinner_class> {


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        return Infelter(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return Infelter(position,convertView,parent);
    }

    public Spinner_Adapter(Context context, ArrayList<Spinner_class> spinner_classes) {
        super(context,0,spinner_classes);


    }

    private View Infelter  (int postiton,View converView,ViewGroup parent ){

         if(converView == null) {

             converView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_content,parent,false);

             ImageView imageView = converView.findViewById(R.id.imge_spiner);
             TextView  textView  = converView.findViewById(R.id.Code_num);

            Spinner_class spinner_class = getItem(postiton);
            if (spinner_class != null){

                imageView.setImageResource(spinner_class.getImage());
                textView .setText(spinner_class.getName());
            }

         }
          return converView ;


    }
}
