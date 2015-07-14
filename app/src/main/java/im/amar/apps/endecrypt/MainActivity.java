package im.amar.apps.endecrypt;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends Activity{
    private static final String TAG = "MainActivity";

    private static boolean isDecrypt = true;

    private static String mSecret;
    private static String mKey;
    private static String mOutput;
    private static String mAlgorithm = "AES";
    private static int mBlockSize = 128; //todo: ask to input block-size maybe; via another dropdown spinner - which changes as per algo spinner

    private Spinner spinner;

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private EditText editTextInput;
    private EditText editTextKey;
    private TextView textViewOutput;

    private Button buttonExecute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        intializeViews();
    }

    private void intializeViews() {
        spinner = (Spinner) findViewById(R.id.spinner_algorithm_selection);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.algorithm_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new ItemSelectedListener());

        editTextInput = (EditText) findViewById(R.id.editTextInput);
        editTextKey = (EditText) findViewById(R.id.editTextKey);
        textViewOutput = (TextView) findViewById(R.id.textViewOutput);

        buttonExecute = (Button) findViewById(R.id.buttonExecute);
        buttonExecute.setOnClickListener(new ClickListener());

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnClickListener(new ClickListener());
    }

    private class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //todo: fetch algo and maybe block size too
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick()");
            if(v == radioGroup) {
                if(radioGroup.getCheckedRadioButtonId() == R.id.radioButtonDecrypt) {
                    isDecrypt = true;
                    buttonExecute.setText(R.string.decrypt_text);
                    Log.d(TAG, "Decryption selected.");
                } else {
                    isDecrypt = false;
                    buttonExecute.setText(R.string.encrypt_text);
                    Log.d(TAG, "Encryption selected.");
                }
            } else if(v == buttonExecute) {
                getTextFields(); //fetch the text values
                if(isDecrypt) {
                    //call decrypt method
                } else {
                    //call encrypt method
                }
            }
        }
    }

    private void getTextFields() {
        mSecret = editTextInput.getText().toString();
        mKey = editTextKey.getText().toString();

        Log.d(TAG, "mSecret: " + mSecret + "\nmKey: " + mKey);
    }

    private void callCrypto() throws NoSuchAlgorithmException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        SimpleCrypto crypto = new SimpleCrypto(mSecret, mKey);
        if(isDecrypt) {
            mOutput = getStringFromBytes(crypto.decrypt());
        } else {
            mOutput = getStringFromBytes(crypto.encrypt());
        }
    }

    private String getStringFromBytes(byte[] input) {
        String output = input.toString(); //todo: not that simple
        return output;
    }
}
