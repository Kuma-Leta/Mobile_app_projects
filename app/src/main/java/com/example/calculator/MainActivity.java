package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewDisplay;
    private StringBuilder input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDisplay = findViewById(R.id.textViewDisplay);
        input = new StringBuilder();
    }
    public void onDigitClick(View view) {
        Button button = (Button) view;
        input.append(button.getText());
        updateDisplay();
    }

    public void onOperatorClick(View view) {
        Button button = (Button) view;
        input.append(" ").append(button.getText()).append(" ");
        updateDisplay();
    }

    public void onDotClick(View view) {
        if (!input.toString().contains(".")) {
            input.append(".");
            updateDisplay();
        }
    }

    public void onClear(View view) {
        input.setLength(0);
        updateDisplay();
    }

    public void onEqualsClick(View view) {
        try {
            String result = evaluateExpression(input.toString());
            input.setLength(0);
            input.append(result);
            updateDisplay();
        } catch (Exception e) {
            input.setLength(0);
            input.append("Error");
            updateDisplay();
        }
    }

    private void updateDisplay() {
        textViewDisplay.setText(input.toString());
    }

    private String evaluateExpression(String expression) {
        // Use a library or implement a parser for better expression evaluation.
        // For simplicity, we'll use eval() method, but it's not recommended for production use.
        // You may consider using a library like exp4j for a safer approach.

        // Note: eval() is deprecated and should not be used in production code.
        // This is just a simple example for educational purposes.
        return String.valueOf(eval(expression));
    }

    private double eval(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // Addition
                    else if (eat('-')) x -= parseTerm(); // Subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // Multiplication
                    else if (eat('/')) x /= parseFactor(); // Division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // Unary plus
                if (eat('-')) return -parseFactor(); // Unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // Parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // Numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                return x;
            }
        }.parse();
    }
}