package edu.miracosta.cs134.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import edu.miracosta.cs134.tipcalculator.model.Bill;

public class MainActivity extends AppCompatActivity {

    //Instance variables
    //Bridge the View and Model
    private Bill currentBill;

    private EditText amountEditText;
    private TextView percentTextView;
    private SeekBar percentSeekBar;
    private TextView tipTextView;
    private TextView totalTextView;


    //Instance variables to format output (currency and percent)
    //get.Default to get local currency format
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    NumberFormat percent  = NumberFormat.getPercentInstance(Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //"Wire uP" instance variables (initialize them)

        currentBill = new Bill();
        amountEditText = findViewById(R.id.amountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        percentSeekBar  = findViewById(R.id.percentSeekBar);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);

        //Let's set the current tip percentage
        currentBill.setTipPercent(percentSeekBar.getProgress()/100.0);

        //Implement the interface for the EditText
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //Read the input from the amountEditText (View) and store in the currentBill (Model)
                //Double.parseDouble to convert double to string
                //Try catch so if input goes to zero we can fix it
                try {


                    double amount = Double.parseDouble(amountEditText.getText().toString());
                    //Store the amount in the bill
                    currentBill.setAmount(amount);
                    calculateBill();

                } catch (NumberFormatException e) {
                    currentBill.setAmount(0.0);
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

                //Implement the interface for the SeekBar
                percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                        //Update the tip percent
                        //divide by 100 so we can get percent
                        currentBill.setTipPercent(percentSeekBar.getProgress()/100.0);
                        //Update the view
                        percentTextView.setText(percent.format(currentBill.getTipPercent()));

                        calculateBill();


                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {


                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
    }

    public void calculateBill()
    {
        //update the tipTextView and totalTextView
        tipTextView.setText(currency.format(currentBill.getTipAmount()));
        totalTextView.setText(currency.format(currentBill.getTotalAmount()));

    }
}
