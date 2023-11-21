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
    TextView resultText,solutionText;
    MaterialButton btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    MaterialButton btnDivide,btnMultiply,btnPlus,btnMinus,btnEquals;
    MaterialButton btnC,btnBracketOpen,btnBracketClose;
    MaterialButton btnAC ,btnDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.resultTextView);
        solutionText = findViewById(R.id.solutionTextView);

        solutionText.addTextChangedListener(textWatcher);
        resultText.addTextChangedListener(textWatcher);

        btnC = findViewById(R.id.btnC);
        btnC.setOnClickListener(this::onClick);
        getBtn(btnBracketOpen,R.id.btnOpenBracket);
        getBtn(btnBracketClose,R.id.btnCloseBracket);
        getBtn(btnDivide,R.id.btnDive);
        getBtn(btnMultiply,R.id.btnMultiple);
        getBtn(btnPlus,R.id.btnPlus);
        getBtn(btnMinus,R.id.btnMinus);
        getBtn(btnEquals,R.id.btnEquals);
        getBtn(btn0,R.id.btn0);
        getBtn(btn1,R.id.btn1);
        getBtn(btn2,R.id.btn2);
        getBtn(btn3,R.id.btn3);
        getBtn(btn4,R.id.btn4);
        getBtn(btn5,R.id.btn5);
        getBtn(btn6,R.id.btn6);
        getBtn(btn7,R.id.btn7);
        getBtn(btn8,R.id.btn8);
        getBtn(btn9,R.id.btn9);
        getBtn(btnAC,R.id.btnAC);
        getBtn(btnDot,R.id.btnDot);

        updateButtonC();
    }
    private void updateButtonC() {
        if (solutionText.getText().length() < 1 || resultText.getText().length() < 1) {
            btnC.setEnabled(false);
        } else {
            btnC.setEnabled(true);
        }
    }

    public void onClick(View view){
        MaterialButton btn = (MaterialButton) view;
        String btnText= btn.getText().toString();
        String dataCalculate = solutionText.getText().toString();

        if(btnText.equals("AC")){
            solutionText.setText("");
            resultText.setText("0");
            return;
        }

        if(btnText.equals("=")){
            solutionText.setText(resultText.getText());
            return;
        }

        if(btnText.equals("C")){
            dataCalculate = dataCalculate.substring(0,dataCalculate.length()-1);
        } else {
            dataCalculate = dataCalculate + btnText;
        }

        solutionText.setText(dataCalculate);
        String finalResult = getResult(dataCalculate);
        if(!finalResult.equals("Having error")){
            resultText.setText(finalResult);
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