package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

public class MainActivity extends AppCompatActivity {
    TextView resultTextView,solutionTextView;
    MaterialButton btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    MaterialButton btnDivide,btnMultiply,btnPlus,btnMinus,btnEquals;
    MaterialButton btnC,btnBracketOpen,btnBracketClose;
    MaterialButton btnAC ,btnDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
        solutionTextView = findViewById(R.id.solutionTextView);

        solutionTextView.addTextChangedListener(textWatcher);
        resultTextView.addTextChangedListener(textWatcher);

        btnC = findViewById(R.id.button_c);
        btnC.setOnClickListener(this::onClick);
        getBtn(btnBracketOpen,R.id.button_open_bracket);
        getBtn(btnBracketClose,R.id.button_close_bracket);
        getBtn(btnDivide,R.id.button_divide);
        getBtn(btnMultiply,R.id.button_multiple);
        getBtn(btnPlus,R.id.button_plus);
        getBtn(btnMinus,R.id.button_minus);
        getBtn(btnEquals,R.id.button_equal);
        getBtn(btn0,R.id.button_0);
        getBtn(btn1,R.id.button_1);
        getBtn(btn2,R.id.button_2);
        getBtn(btn3,R.id.button_3);
        getBtn(btn4,R.id.button_4);
        getBtn(btn5,R.id.button_5);
        getBtn(btn6,R.id.button_6);
        getBtn(btn7,R.id.button_7);
        getBtn(btn8,R.id.button_8);
        getBtn(btn9,R.id.button_9);
        getBtn(btnAC,R.id.button_ac);
        getBtn(btnDot,R.id.button_dot);

        updateButtonC();
    }
    private void updateButtonC() {
        if (solutionTextView.getText().length() < 1 || resultTextView.getText().length() < 1) {
            btnC.setEnabled(false);
        } else {
            btnC.setEnabled(true);
        }
    }

    public void onClick(View view){
        MaterialButton button = (MaterialButton) view;
        String buttonText= button.getText().toString();
        String dataCalculate = solutionTextView.getText().toString();

        if(buttonText.equals("AC")){
            solutionTextView.setText("");
            resultTextView.setText("0");
            return;
        }

        if(buttonText.equals("=")){
            solutionTextView.setText(resultTextView.getText());
            return;
        }

        if(buttonText.equals("C")){
            dataCalculate = dataCalculate.substring(0,dataCalculate.length()-1);
        } else {
            dataCalculate = dataCalculate + buttonText;
        }

        solutionTextView.setText(dataCalculate);
        String finalResult = getResult(dataCalculate);
        if(!finalResult.equals("Having error")){
            resultTextView.setText(finalResult);
        }
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateButtonC();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    void getBtn(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this::onClick);
    }

    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable script = context.initSafeStandardObjects();
            String result = context.evaluateString(script,data,"Javascript",1,null).toString();
            Object evaluationResult = context.evaluateString(script, data, "Javascript", 1, null);

            if (evaluationResult == Undefined.instance) {
                result = "0";
            }

            if(result.endsWith(".0")){
                result = result.replace(".0", "");
            }
            return result;
        } catch(Exception e){
            return "Having error";
        }
    }
}