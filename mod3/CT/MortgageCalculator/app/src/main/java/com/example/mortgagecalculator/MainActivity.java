package com.example.mortgagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle; // for saving state information
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.graphics.Color;

import org.w3c.dom.Text;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    final static String LOG="";

    /***Members reqiuring global scope */
    private TextView outputField;
    private TextView customTermPayments;
    private TextView customTermPaymentAmount;
    private double price;
    private double interestRate;
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button= (Button) findViewById(R.id.calculateBtn);
        button.setOnClickListener(this);
        outputField = (TextView) findViewById(R.id.custom_termOutput);
        SeekBar termSeekBar = (SeekBar) findViewById(R.id.custom_termSeekBar);
        termSeekBar.setOnSeekBarChangeListener(seekBarListener);

    } //end
    public void onClick(View v) {
        EditText priceInput = (EditText) findViewById(R.id.purch_priceEditText);
        EditText down_pmtInput = (EditText) findViewById(R.id.down_pmt);
        EditText interestInput = (EditText) findViewById(R.id.interest);
        TextView tenYrView = (TextView) findViewById(R.id.ten_yrPmt);
        TextView twentyYrView = (TextView) findViewById(R.id.twenty_yrPmt);
        TextView thirtyYrView = (TextView) findViewById(R.id.thirty_yrPmt);

        try {
            // get the input
            String totalLoanAmount = priceInput.getText().toString();
            String interest = interestInput.getText().toString();
            String downPmt = down_pmtInput.getText().toString();

            // parse number
            price = Double.parseDouble(totalLoanAmount);
            interestRate = Double.parseDouble(interest);
            interestRate = interestRate/100.00;
            Log.d(MainActivity.LOG, " "+ interestRate);

            double downPayment = Double.parseDouble(downPmt);
            double loanAmount = price - downPayment;

            double tenYrPmt = calculate(10, interestRate, loanAmount);
            double twentyYrPmt = calculate(20, interestRate, loanAmount);
            double thirtyYrPmt = calculate(30, interestRate, loanAmount);

            tenYrView.setText("" + currencyFormat.format(tenYrPmt));
            twentyYrView.setText("" + currencyFormat.format(twentyYrPmt));
            thirtyYrView.setText("" + currencyFormat.format(thirtyYrPmt));

        } catch (NumberFormatException e) {
            String outputStr = "Fill all fields with non negative values.";
            outputField.setText(outputStr);
            outputField.setBackgroundColor(Color.parseColor("#fff111"));
            outputField.setTextColor(Color.parseColor("#ff0005"));


            Log.d(MainActivity.LOG, "NumberFormatException (check null entries): " + e.getMessage());
        }
    } // end

    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {

                    outputField.setText("Years: "+ progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    customTermPayments = (TextView)findViewById(R.id.custom_numPaymentsTextView);
                    customTermPaymentAmount=(TextView) findViewById(R.id.custom_paymentAmountTextView);
                    int currentValue = seekBar.getProgress();
                    int numPayments = currentValue * 12;
                    double customPayment = calculate(currentValue, interestRate, price);
                    outputField.setText("Years: "+ currentValue);
                    customTermPayments.setText("Number of payments: "+ numPayments);
                    customTermPaymentAmount.setText("Payment: "+currencyFormat.format(customPayment));
//                    Log.d(MainActivity.LOG,"currentValue: " + n);
                }
            };



/***Handle payment calculation.
 * @return double
*/
        private double calculate(int yr, double i, double L){
//            Payment calculation
            double monthlyInterestRate = (i/12.00);
            double powerFactor = 12.00 * yr;
            double powerProduct = Math.pow(1.00 + monthlyInterestRate, powerFactor);
            double monthlyPayment = ( L* monthlyInterestRate * powerProduct)/(powerProduct - 1.00);


            Log.d(MainActivity.LOG,"FROM calculate():  "+ monthlyPayment);
            return monthlyPayment;

            };


    }//END




