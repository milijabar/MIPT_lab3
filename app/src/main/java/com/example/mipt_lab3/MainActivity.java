package com.example.mipt_lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView calculatorScreen;
    Button back, clearEverything, plusMinus;
    Button n0, n1, n2, n3, n4, n5, n6, n7, n8, n9;
    Button dot, squareRoot, subtraction, sum, multiplication, division, equals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculatorScreen = findViewById(R.id.calculatorScreen);

        assignId(back, R.id.back);
        assignId(clearEverything, R.id.clearEverything);
        assignId(plusMinus, R.id.plusMinus);
        assignId(n0, R.id.n0);
        assignId(n1, R.id.n1);
        assignId(n2, R.id.n2);
        assignId(n3, R.id.n3);
        assignId(n4, R.id.n4);
        assignId(n5, R.id.n5);
        assignId(n6, R.id.n6);
        assignId(n7, R.id.n7);
        assignId(n8, R.id.n8);
        assignId(n9, R.id.n9);
        assignId(dot, R.id.dot);
        assignId(squareRoot, R.id.squareRoot);
        assignId(subtraction, R.id.subtraction);
        assignId(sum, R.id.sum);
        assignId(multiplication, R.id.multiplication);
        assignId(division, R.id.division);
        assignId(equals, R.id.eguals);
    }

    void assignId(Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = calculatorScreen.getText().toString();
        String finalResult = getResult(dataToCalculate);

        if (buttonText.equals("CE")) {
            calculatorScreen.setText("");
            return;
        }
        if (buttonText.equals("=")) {
            if (!finalResult.equals("Error")) {
                calculatorScreen.setText(finalResult);
            };
            return;
        }
        if (buttonText.equals("±")) {
            if (dataToCalculate.startsWith("-")) {
                dataToCalculate = dataToCalculate.substring(1);
            } else {
                dataToCalculate = "-" + dataToCalculate;
            }
        }
        if (buttonText.equals("←")) {
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
        }
        else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        calculatorScreen.setText(dataToCalculate);

    }


    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();

            if (data.contains("√")) {
                data = data.replace("√", "Math.sqrt(") + ")";
            }

            Object result = context.evaluateString(scriptable, data, "JavaScript", 1, null);
            String finalResult = Context.toString(result);
            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }
}