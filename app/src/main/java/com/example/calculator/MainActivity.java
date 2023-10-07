package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

public class MainActivity extends AppCompatActivity {
    TextView resultTv,solutionTv;
    MaterialButton buttonC,buttonBracketOpen,buttonBracketClose;
    MaterialButton buttonDivide,buttonMultiply,buttonPlus,buttonMinus,buttonEquals;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonAC ,buttonDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        // Set up TextWatchers for solutionTv and resultTv
        solutionTv.addTextChangedListener(textWatcher);
        resultTv.addTextChangedListener(textWatcher);

        buttonC = findViewById(R.id.button_c);
        buttonC.setOnClickListener(this::onClick);
        assignId(buttonBracketOpen,R.id.button_open_bracket);
        assignId(buttonBracketClose,R.id.button_close_bracket);
        assignId(buttonDivide,R.id.button_divide);
        assignId(buttonMultiply,R.id.button_multiple);
        assignId(buttonPlus,R.id.button_plus);
        assignId(buttonMinus,R.id.button_minus);
        assignId(buttonEquals,R.id.button_equal);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);
        assignId(buttonAC,R.id.button_ac);
        assignId(buttonDot,R.id.button_dot);

        updateButtonCState();
    }
    private void updateButtonCState() {
        if (solutionTv.getText().length() < 1 || resultTv.getText().length() < 1) {
            buttonC.setEnabled(false);
        } else {
            buttonC.setEnabled(true);
        }
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateButtonCState();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this::onClick);
    }

    public void onClick(View view){
        MaterialButton button = (MaterialButton) view;
        String buttonText= button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if(buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }

        if(buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }

        if(buttonText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
        } else {
            dataToCalculate = dataToCalculate+buttonText;
        }

        solutionTv.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);
        if(!finalResult.equals("Error")){
            resultTv.setText(finalResult);
        }
    }

    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            Object evaluationResult = context.evaluateString(scriptable, data, "Javascript", 1, null);

            if (evaluationResult == Undefined.instance) {
               finalResult = "0";
            }

            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch(Exception e){
            return "Error";
        }
    }
}