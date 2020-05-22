package com.google.android.gms.samples.vision.ocrreader;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class length extends Activity {
    int unit, unit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        //FIRST DROPDOWN LIST
        Spinner spinner = (Spinner) findViewById(R.id.units_1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.length_unit_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //SECOND DROPDOWN LIST
        Spinner spinner1 = (Spinner) findViewById(R.id.units_2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.length_unit_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

    }

    public void convertTo(View view){
        //Takes number from user.
        EditText numField = (EditText) findViewById(R.id.length_1);
        String text = numField.getText().toString();
        double num=0;
        try
        {
            num = (double) Float.parseFloat(text);
        }
        catch (NumberFormatException e){
            //error();
            numField.setError("Field cannot be left blank.");
            e.printStackTrace();
        }

        //Creates spinner object and takes user option from dropdown list
        Spinner spinner = (Spinner) findViewById(R.id.units_1);
        Spinner spinner1 = (Spinner) findViewById(R.id.units_2);
        unit = spinner.getSelectedItemPosition();
        unit1 = spinner1.getSelectedItemPosition();

        //Convert the user input into the specified unit
        double finalAns = conversion(num, unit, unit1);

        //Displays the ans on screen
        display(finalAns);

    }

    private double conversion(double num, int unit, int unit1){
        double value = 0.0;
        if(unit==0){
            value = metreConverter(unit1, num);
        }
        if(unit==2){
            num=num*1609.344;
            value = metreConverter(unit1, num);
        }
        if(unit==3){
            num=num*0.3048;
            value = metreConverter(unit1, num);
        }
        if(unit==4){
            num=num*0.0254;
            value = metreConverter(unit1, num);
        }
        if(unit==1){
            num=num*0.01;
            value = metreConverter(unit1, num);
        }
        return value;
    }

    private double metreConverter(int unit1, double num){
        double ans=0.0;
        if(unit1==0){
            ans = num*1;
        }
        if(unit1==2){
            ans = num*0.00062137;
        }
        if(unit1==3){
            ans = num*3.2808;
        }
        if(unit1==4){
            ans = num*39.3700787;
        }
        if(unit1==1){
            ans = num*100;
        }
        return ans;
    }

    private void display(double ans){
        TextView text = (TextView) findViewById(R.id.length_2);
        String string = String.format("%.2f", ans);
        text.setText(string);
    }
}

