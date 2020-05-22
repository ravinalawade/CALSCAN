package com.google.android.gms.samples.vision.ocrreader;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.EmptyStackException;
import java.util.Stack;


public class display_message extends AppCompatActivity {
    String message;
    static float n;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        message = intent.getStringExtra(OcrCaptureActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);

    }
int flag1;
    public void evaluate(String expression)
    {
        flag1=0;
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Float> values = new Stack<Float>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++)
        {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                {
                    sbuf.append(tokens[i++]);
                }
                i--;
                values.push(Float.parseFloat(sbuf.toString()));
            }

            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

                // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/')
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack

                try{
                    while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                catch (EmptyStackException e){
                    error();
                    //e.printStackTrace();
                    flag1=1;
                }
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        /*while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));*/

        try
        {
            while (!ops.empty())
                values.push(applyOp(ops.pop(), values.pop(), values.pop()));
            n = values.pop();
        }
        catch (EmptyStackException e){
            error();
            flag1=1;
           // e.printStackTrace();
        }


    }

    public void error() {
        TextView tv = (TextView) findViewById(R.id.display_ans);
        String string="Wrong expression";
        tv.setText(string);
    }

    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public float applyOp(char op, float b, float a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                try {
                    if (b == 0)
                        return a / b;
                }
                catch (UnsupportedOperationException e)
                {
                    error();
                    flag1=1;
                }
        }
        return 0;
    }

    public void calculate(View view) {

        int flag = 0;

        // Gets edited text from user and evaluates the expression
        EditText input = (EditText) findViewById(R.id.textView);
        String eqn = input.getText().toString();

        if(eqn.trim().equalsIgnoreCase("")){
            flag = 1;
            flag1=1;
            error();
           // input.setError("This field cannot be left blank");
        }

        char[] check = eqn.toCharArray();
        for(int i=0; i<check.length; i++){
            if((check[i]>='a' && check[i]<='z') || check[i]>='A' && check[i]<='Z'){
                flag = 1;
                try {
                    float number = Float.parseFloat(String.valueOf(check[i]));
                }
                catch (NumberFormatException m){
                    //input.setError("Please enter the correct expression");
                    //m.printStackTrace();
                    error();
                    flag1=1;
                }
            }
        }

        if(flag == 0){
            try{
                evaluate(eqn);
            }
            catch (EmptyStackException e){
                error();
                flag1=1;
                //e.printStackTrace();
            }
        }


        // Displays the final result
        if(flag1==0)
        dislay();

    }

    private void dislay() {
        TextView tv = (TextView) findViewById(R.id.display_ans);
        String string = String.format("%.4f", n);
        tv.setText(string);
    }


}
