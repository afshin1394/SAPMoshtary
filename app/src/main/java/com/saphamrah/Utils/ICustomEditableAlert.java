package com.saphamrah.Utils;

import android.widget.Button;

import com.saphamrah.CustomView.CustomTextInputLayout;

public interface ICustomEditableAlert <T extends Object>{
    void setOnCancelClick();
    void setOnApplyClick(CustomTextInputLayout customTextInputLayout, T message, Button btnOk);

    void onTextChange(CustomTextInputLayout customTextInputLayout,String s);
}
