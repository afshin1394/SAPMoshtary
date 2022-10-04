package com.saphamrah.customer.presentation.view.customView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.saphamrah.customer.R;
import com.saphamrah.customer.listeners.VerifyValidCode;


public class VerifyCodeTextWatcher implements TextWatcher {

    private final EditText[] editText;
    private View view;
    private VerifyValidCode listener;
    public VerifyCodeTextWatcher(View view, EditText editText[], VerifyValidCode listener)
    {
        this.editText = editText;
        this.view = view;
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editText[0].length() + editText[1].length() + editText[2].length() + editText[3].length() == 4) {
            listener.onVerifyValidCode();
        }
        String text = editable.toString();
        switch (view.getId()) {

            case R.id.et_code1:
                if (text.length() == 1)
                    editText[1].requestFocus();
                break;
            case R.id.et_code2:

                if (text.length() == 1)
                    editText[2].requestFocus();
                else if (text.length() == 0)
                    editText[0].requestFocus();
                break;
            case R.id.et_code3:
                if (text.length() == 1)
                    editText[3].requestFocus();
                else if (text.length() == 0)
                    editText[1].requestFocus();
                break;
            case R.id.et_code4:
                if (text.length() == 0)
                    editText[2].requestFocus();
                break;
        }
    }

}
