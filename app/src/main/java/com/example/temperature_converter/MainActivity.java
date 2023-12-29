package com.example.temperature_converter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextValue;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;
    private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextValue = findViewById(R.id.editTextValue);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewResult = findViewById(R.id.textViewResult);

        // Set up Spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.temperature_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Set up Button click listener
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTemperature();
            }
        });
    }
    private void convertTemperature() {
        try {
            double inputValue = Double.p1212arseDouble(editTextValue.getText().toString());
            String fromUnit = spinnerFrom.getSelectedItem().toString();
            String toUnit = spinnerTo.getSelectedItem().toString();

            // Perform the temperature conversion here
            double result = performConversion(inputValue, fromUnit, toUnit);

            textViewResult.setText(inputValue + " " + fromUnit + " is " + result + " " + toUnit);
        } catch (NumberFormatException e) {
            textViewResult.setText("Invalid input. Please enter a valid number.");
        }
    }

    private double performConversion(double inputValue, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) {
            // If the units are the same, no conversion is needed
            return inputValue;
        }

        double result;

        if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
            // Celsius to Fahrenheit conversion
            result = (inputValue * 9 / 5) + 32;
        } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
            // Fahrenheit to Celsius conversion
            result = (inputValue - 32) * 5 / 9;
        } else if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) {
            // Celsius to Kelvin conversion
            result = inputValue + 273.15;
        } else if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) {
            // Kelvin to Celsius conversion
            result = inputValue - 273.15;
        } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Kelvin")) {
            // Fahrenheit to Kelvin conversion
            result = (inputValue + 459.67) * 5 / 9;
        } else if (fromUnit.equals("Kelvin") && toUnit.equals("Fahrenheit")) {
            // Kelvin to Fahrenheit conversion
            result = (inputValue * 9 / 5) - 459.67;
        } else {
            // If the units are not recognized, return the input value as is
            result = inputValue;
        }

        return result;
    }

}

