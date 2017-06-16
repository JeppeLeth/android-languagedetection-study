package com.jleth.andorid.langdetect;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mResult;
    private EditText mInput;
    private Button mDetect;
    private Switch mSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mResult = (TextView) findViewById(R.id.textView);
        mInput = (EditText) findViewById(R.id.editText);
        mDetect = (Button) findViewById(R.id.button);
        mSwitch = (Switch) findViewById(R.id.switch1);
        mDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                String input = mInput.getText().toString();
                if (mSwitch.isChecked() && DetectionTextClassifier.isAvailable(getApplicationContext())) {
                    new DetectionTextClassifier(getApplicationContext()) {

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            showLoading();
                        }

                        @Override
                        protected void onPostExecute(DetectionResult result) {
                            super.onPostExecute(result);
                            printResult(result);
                        }
                    }.execute(input);
                } else {
                    new DetectionExtLib() {

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            showLoading();
                        }

                        @Override
                        protected void onPostExecute(DetectionResult result) {
                            super.onPostExecute(result);
                            printResult(result);
                        }
                    }.execute(input);
                }
            }
        });
        boolean available = DetectionTextClassifier.isAvailable(getApplicationContext());
        mSwitch.setVisibility(available ? View.VISIBLE : View.GONE);
        if (savedInstanceState == null) {
            mSwitch.setChecked(available);
        }
    }

    private void showLoading() {
        mResult.setText("Wait...");
        mDetect.setEnabled(false);
        mSwitch.setEnabled(false);
    }

    private void printResult(DetectionResult result) {
        mDetect.setEnabled(true);
        mSwitch.setEnabled(true);
        if (result.list != null) {
            mResult.setText("Result: ");
            mResult.append(String.format(Locale.US, "%d ms", (int) result.executionTime));
            mResult.append("\n\n");
            mResult.append("Probability:\n");
            for (Language lang : result.list) {
                mResult.append(String.format(Locale.US, "- %s: %.4f%%\n", lang.lang, lang.prob * 100));
            }
            mResult.append("\nOriginal text:\n");
            mResult.append(mInput.getText());
        } else {
            mResult.setText("Error:\n\n");
            mResult.append(result.e != null ? result.e.getMessage() : "unknown");
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
