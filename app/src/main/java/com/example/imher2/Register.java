package com.example.imher2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class Register extends AppCompatActivity {
    private Button  btn_next;
    private Spinner spinner;
    private EditText number;
    private ArrayList<Spinner_class> mlist;
    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        list();

        spinner = findViewById(R.id.spinner);
        adapter = new Spinner_Adapter(this,mlist);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Spinner_class spinner_class = (Spinner_class) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        number   = findViewById(R.id.phoneNumber);

          findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {


                  String code = mlist.get(spinner.getSelectedItemPosition()).getName();
                  String number1 = number.getText().toString().trim();

                  if (number1.isEmpty() || number1.length() > 10 ) {
                      number.setText(" Valid number is requi");
                      number.requestFocus();

                  }

                  String phoneNumber = code + number1 ;
                  Intent intent = new Intent(Register.this,PhoneVerfid.class);
                  intent.putExtra("phoneNumber",phoneNumber);
                  startActivity(intent);


            }
        });


    }

    private void list () {

        mlist = new ArrayList<>();
        mlist.add(new Spinner_class(R.drawable.iconarabia,"+966"));
        mlist.add(new Spinner_class(R.drawable.iconindia, "+91"));
        mlist.add(new Spinner_class(R.drawable.iconsudan, "+24"));


    }


}
